package me.dumb12344.stupidmod.bedrockworkstation;

import me.dumb12344.stupidmod.registry.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.List;

public class BedrockWorkstationMenu extends RecipeBookMenu<CraftingContainer> {
    private final ContainerLevelAccess access;
    private final Player player;
    final TransientCraftingContainer inputSlot = new TransientCraftingContainer(this,1,1);
    final ResultContainer outputSlot = new ResultContainer(){
        public boolean canPlaceItem(){return false;}
    };
    public BedrockWorkstationMenu(int containerId, Inventory playerInventory){
        this(containerId,playerInventory,ContainerLevelAccess.NULL);
    }
    public BedrockWorkstationMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess containerLevelAccess) {
        super(BedrockWorkstationRegistry.BEDROCK_WORKSTATION_MENU.get(), containerId);
        this.access = containerLevelAccess;
        this.player = playerInventory.player;
        this.addSlot(new Slot(this.inputSlot, 0, 56, 35));
        this.addSlot(new ResultSlot(player, this.inputSlot, this.outputSlot, 0, 116, 35){
            @Override
            public void onTake(Player p_150638_, ItemStack stack) {
                //player.sendSystemMessage(stack.getDisplayName());
                if(stack.is(NukeRegistry.NUKE_ITEM.get())){
                    ItemStack newStack =  inputSlot.getItem(0);
                    newStack.shrink(8);
                    inputSlot.setItem(0, newStack);
                }
                else{
                    ItemStack newStack =  inputSlot.getItem(0);
                    newStack.shrink(1);
                    inputSlot.setItem(0, newStack);
                    //super.onTake(p_150638_, stack);
                }
            }
        });
        //this.addSlot(new Slot(this.outputSlot, 0, 116, 35));

        //inventory
        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }
        //container slotid(of container) x y
        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
    }
    protected static void slotChanged(AbstractContainerMenu menu, Level level, Player player, CraftingContainer craftingContainer, ResultContainer resultContainer) {
        if (level.isClientSide)return;
        ItemStack input = craftingContainer.getItems().get(0);
        /*
        ServerPlayer serverplayer = (ServerPlayer) player;
        SimpleContainer inventory = new SimpleContainer(1);
        inventory.setItem(0,craftingContainer.getItem(0));
        Optional<BedrockWorkstationRecipe> optional = level.getServer().getRecipeManager().getRecipeFor(BedrockWorkstationRecipe.Type.INSTANCE, inventory, level);
        if (optional.isPresent()) {
            BedrockWorkstationRecipe bedrockWorkstationRecipe = optional.get();
            if (resultContainer.setRecipeUsed(level, serverplayer, bedrockWorkstationRecipe)) {
                itemstack = bedrockWorkstationRecipe.assemble(inventory, level.registryAccess());
            }
        }
*/
        ItemStack itemstack = getOutput(input);
        resultContainer.setItem(0, itemstack);
        menu.setRemoteSlot(0, itemstack);
        //serverplayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemstack));
    }
    public void slotsChanged(Container p_39366_) {
        this.access.execute((level, pos) -> {
            slotChanged(this, level, this.player, this.inputSlot, this.outputSlot);
        });
    }
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> {
            this.clearContainer(player, this.inputSlot);
            this.clearContainer(player, this.outputSlot);
        });
    }
    @Override
    public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex) {
        //https://docs.minecraftforge.net/en/1.20.1/gui/menus/
        //https://www.youtube.com/watch?v=340HcHexH60
        Slot fromSlot = getSlot(quickMovedSlotIndex);
        ItemStack fromItem = fromSlot.getItem();
        if(fromItem.is(NukeRegistry.NUKE_ITEM.get()))return ItemStack.EMPTY;
        if(fromItem.getCount()<=0)fromSlot.set(ItemStack.EMPTY);
        if(!fromSlot.hasItem())return ItemStack.EMPTY;
        //player.sendSystemMessage(Component.literal(String.valueOf(quickMovedSlotIndex)));
        ItemStack fromItemCopy = fromItem.copy();
        if(quickMovedSlotIndex >= 2){
            //input, startIndex, endIndex, reverseDirection?
            //inventory to menu
            if(!moveItemStackTo(fromItem,0,2,false)){
                return ItemStack.EMPTY;
            }
        }
        else{
            if(!moveItemStackTo(fromItem,2,36,false)){
                return ItemStack.EMPTY;
            }
        }
        fromSlot.setChanged();
        fromSlot.onTake(player, fromItem);
        return fromItemCopy;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, BedrockWorkstationRegistry.BEDROCK_WORKSTATION_BLOCK.get());
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents contents) {
        this.inputSlot.fillStackedContents(contents);
    }

    @Override
    public void clearCraftingContent() {
        this.inputSlot.clearContent();
        this.outputSlot.clearContent();
    }

    @Override
    public boolean recipeMatches(Recipe<? super CraftingContainer> recipe) {
        return recipe.matches(this.inputSlot, this.player.level());
    }

    @Override
    public int getResultSlotIndex() {
        return 0;
    }

    @Override
    public int getGridWidth() {
        return 1;
    }

    @Override
    public int getGridHeight() {
        return 1;
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    @Override
    public boolean shouldMoveToInventory(int i) {
        return i != this.getResultSlotIndex();
    }

    public static ItemStack getOutput(ItemStack input) {
        ItemStack itemstack = ItemStack.EMPTY;
        Enchantment[] armorEnchantmentsArray = {
                Enchantments.ALL_DAMAGE_PROTECTION,
                Enchantments.AQUA_AFFINITY,
                Enchantments.BLAST_PROTECTION,
                Enchantments.DEPTH_STRIDER,
                Enchantments.FALL_PROTECTION,
                Enchantments.PROJECTILE_PROTECTION,
                Enchantments.RESPIRATION,
                Enchantments.FIRE_PROTECTION,
                Enchantments.FROST_WALKER,
                Enchantments.THORNS,
                Enchantments.SWIFT_SNEAK
        };
        Enchantment[] commonEnchantmentsArray = {
                Enchantments.UNBREAKING,
                Enchantments.MENDING,
        };
        Enchantment[] swordEnchantmentsArray = {
                Enchantments.SHARPNESS,
                Enchantments.FIRE_ASPECT,
                Enchantments.KNOCKBACK,
                Enchantments.MOB_LOOTING,
                Enchantments.SWEEPING_EDGE,
        };
        Enchantment[] pickaxeEnchantmentsArray = {
                Enchantments.BLOCK_FORTUNE,
                Enchantments.BLOCK_EFFICIENCY,
        };
        List<Enchantment> commonEnchantments = new java.util.ArrayList<>(Arrays.stream(commonEnchantmentsArray).toList());
        List<Enchantment> armorEnchantments = new java.util.ArrayList<>(Arrays.stream(armorEnchantmentsArray).toList());
        List<Enchantment> swordEnchantments = new java.util.ArrayList<>(Arrays.stream(swordEnchantmentsArray).toList());
        List<Enchantment> pickaxeEnchantments = new java.util.ArrayList<>(Arrays.stream(pickaxeEnchantmentsArray).toList());
        armorEnchantments.addAll(commonEnchantments);
        swordEnchantments.addAll(commonEnchantments);
        pickaxeEnchantments.addAll(commonEnchantments);
        if(input.is(Blocks.BEDROCK.asItem())){
            itemstack = Items.BARRIER.getDefaultInstance();
            itemstack.setCount(input.getCount());
        }
        if(input.is(Items.WOODEN_SWORD)){
            CompoundTag test = new CompoundTag();
            itemstack = BedrockItemRegistry.BEDROCK_SWORD.get().getDefaultInstance();
            itemstack.setTag(test);
            for(Enchantment e : swordEnchantments)itemstack.enchant(e, 127);
        }
        if(input.is(Items.WOODEN_PICKAXE)){
            CompoundTag test = new CompoundTag();
            itemstack = BedrockItemRegistry.BEDROCK_PICKAXE.get().getDefaultInstance();
            itemstack.setTag(test);
            for(Enchantment e : pickaxeEnchantments)itemstack.enchant(e, 127);
        }
        if(input.is(Items.ENDER_PEARL)){
            itemstack = BedrockItemRegistry.BEDROCK_ENDER_PEARL.get().getDefaultInstance();
            itemstack.setCount(input.getCount());
        }
        if(input.is(Items.REDSTONE)){
            itemstack = BedrockItemRegistry.BEDROCK_ACCELERATOR.get().getDefaultInstance();
        }
        if(input.is(Blocks.NETHERITE_BLOCK.asItem())){
            itemstack = BedrockItemRegistry.BEDROCK_COMMAND_LINE.get().getDefaultInstance();
        }
        if(input.is(Blocks.NETHERRACK.asItem())){
            itemstack = BedrockItemRegistry.BEDROCK_MAGNET.get().getDefaultInstance();
        }
        if(input.is(Blocks.COBBLESTONE.asItem())){
            itemstack = DuperRegistry.DUPER_ITEM.get().getDefaultInstance();
        }
        if(input.is(NukeRegistry.NUKE_ITEM.get())){
            itemstack = NukeRegistry.THROWABLE_NUKE_ITEM.get().getDefaultInstance();
        }
        if(input.is(NukeRegistry.THROWABLE_NUKE_ITEM.get())){
            itemstack = NukeRegistry.NUKE_ITEM.get().getDefaultInstance();
        }
        if(input.is(Items.COAL)){
            itemstack = C4Registry.C4_ITEM.get().getDefaultInstance();
        }
        if(input.is(Items.IRON_INGOT)){
            itemstack = C4Registry.C4_DETONATOR_ITEM.get().getDefaultInstance();
        }
        if(input.is(C4Registry.C4_ITEM.get())){
            itemstack = C4Registry.C4_MACHINE_GUN.get().getDefaultInstance();
        }
        if(input.is(Blocks.TNT.asItem())){
            if(input.getCount()>=8) {
                itemstack = NukeRegistry.NUKE_ITEM.get().getDefaultInstance();
            }
        }
        if(input.is(Blocks.DIRT.asItem())){
            itemstack = FallingGrassBlockRegistry.FALLING_GRASS_ITEM.get().getDefaultInstance();
        }
        if(input.is(Blocks.GRASS_BLOCK.asItem())){
            itemstack = ZeroFrictionGrassBlockRegistry.ZERO_FRICTION_GRASS_ITEM.get().getDefaultInstance();
        }
        if(input.is(Blocks.STONE.asItem())){
            itemstack = FakeStoneRegistry.FAKE_STONE_ITEM.get().getDefaultInstance();
        }
        /*
        if(input.is(Items.WOODEN_AXE)){
            CompoundTag test = new CompoundTag();
            test.putBoolean("Unbreakable", true);
            itemstack = Items.NETHERITE_AXE.getDefaultInstance();
            itemstack.setTag(test);
            for(Enchantment e : enchantments)itemstack.enchant(e, 127);

        }
        if(input.is(Items.WOODEN_SHOVEL)){
            CompoundTag test = new CompoundTag();
            test.putBoolean("Unbreakable", true);
            itemstack = Items.NETHERITE_SHOVEL.getDefaultInstance();
            itemstack.setTag(test);
            for(Enchantment e : enchantments)itemstack.enchant(e, 127);

        }
        */
        if(input.is(Items.LEATHER_HELMET)){
            CompoundTag test = new CompoundTag();
            test.putBoolean("Unbreakable", true);
            itemstack = BedrockItemRegistry.BEDROCK_HELMET.get().getDefaultInstance();
            itemstack.setTag(test);
            for(Enchantment e : armorEnchantments)itemstack.enchant(e, 127);

        }if(input.is(Items.LEATHER_CHESTPLATE)){
            CompoundTag test = new CompoundTag();
            test.putBoolean("Unbreakable", true);
            itemstack = Items.NETHERITE_CHESTPLATE.getDefaultInstance();
            itemstack.setTag(test);
            for(Enchantment e : armorEnchantments)itemstack.enchant(e, 127);

        }if(input.is(Items.LEATHER_LEGGINGS)){
            CompoundTag test = new CompoundTag();
            test.putBoolean("Unbreakable", true);
            itemstack = Items.NETHERITE_LEGGINGS.getDefaultInstance();
            itemstack.setTag(test);
            for(Enchantment e : armorEnchantments)itemstack.enchant(e, 127);

        }if(input.is(Items.LEATHER_BOOTS)){
            CompoundTag test = new CompoundTag();
            itemstack = BedrockItemRegistry.BEDROCK_BOOTS.get().getDefaultInstance();
            itemstack.setTag(test);
            for(Enchantment e : armorEnchantments)itemstack.enchant(e, 127);
        }
        return itemstack;
    }
}

package me.dumb12344.stupidmod.BedrockWorkstation;

import me.dumb12344.stupidmod.Registry.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BedrockWorkstationMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final Player player;
    public int recipeCount = 1;
    final SimpleContainer inputSlot = new SimpleContainer(1){
        @Override
        public void setChanged() {
            super.setChanged();
            BedrockWorkstationMenu.this.slotsChanged(this);
        }
    };
    final SimpleContainer outputSlot = new SimpleContainer(1){
        public boolean canPlaceItem(){return false;}
    };
    public BedrockWorkstationMenu(int containerId, Inventory playerInventory){
        this(containerId,playerInventory,ContainerLevelAccess.NULL);
    }
    public BedrockWorkstationMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess containerLevelAccess) {
        super(BedrockWorkstationRegistry.BEDROCK_WORKSTATION_MENU.get(), containerId);
        this.access = containerLevelAccess;
        this.player = playerInventory.player;
        this.addSlot(new Slot(this.inputSlot, 0, 56, 35) {

        });
        this.addSlot(new Slot(this.outputSlot, 0, 116, 35) {
            @Override
            public void onTake(@NotNull Player p_150638_, @NotNull ItemStack stack) {
                inputSlot.getItem(0).shrink(recipeCount);
                BedrockWorkstationMenu.this.slotsChanged(this.container);
                super.onTake(p_150638_, stack);
            }
        });

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
    public static void slotChanged(BedrockWorkstationMenu menu, Level level, SimpleContainer inputContainer, SimpleContainer resultContainer) {
        if (level.isClientSide) return;
        ItemStack itemstack;
        Optional<BedrockWorkstationRecipe> recipe = getCurrentRecipe(inputContainer, level);
        if (recipe.isEmpty()) itemstack = ItemStack.EMPTY;
        else {
            itemstack = recipe.get().getResultItem(level.registryAccess());
            menu.recipeCount = recipe.get().getInputCount();
            applyModifiers(itemstack, recipe.get().getModifiers());
        }
        //ItemStack itemstack = getOutput(input);
        resultContainer.setItem(0, itemstack);
        menu.setRemoteSlot(0, itemstack);
    }

    public static Optional<BedrockWorkstationRecipe> getCurrentRecipe(SimpleContainer container, Level level) {
        return level.getRecipeManager().getRecipeFor(BedrockWorkstationRecipe.Type.INSTANCE, container, level);
    }

    @Override
    public void slotsChanged(@NotNull Container p_39366_) {
        this.access.execute((level, pos) ->
            slotChanged(this, level, this.inputSlot, this.outputSlot)
        );
    }
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> {
            this.clearContainer(player, this.inputSlot);
            this.clearContainer(player, this.outputSlot);
        });
    }
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int quickMovedSlotIndex) {
        //https://docs.minecraftforge.net/en/1.20.1/gui/menus/
        //https://www.youtube.com/watch?v=340HcHexH60
        Slot fromSlot = getSlot(quickMovedSlotIndex);
        ItemStack fromItem = fromSlot.getItem();
        //if(fromItem.is(NukeRegistry.NUKE_ITEM.get()))return ItemStack.EMPTY;
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
    public boolean stillValid(@NotNull Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, BedrockWorkstationRegistry.BEDROCK_WORKSTATION_BLOCK.get());
    }

    public static void applyModifiers(ItemStack itemstack, BedrockWorkstationRecipe.Modifiers modifiers) {
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
        switch (modifiers) {
            case ARMOR -> {
                CompoundTag tag = new CompoundTag();
                tag.putBoolean("Unbreakable", true);
                itemstack.setTag(tag);
                for(Enchantment e : armorEnchantments) itemstack.enchant(e, 127);
            }
            case SWORD -> {
                CompoundTag tag = new CompoundTag();
                tag.putBoolean("Unbreakable", true);
                itemstack.setTag(tag);
                for(Enchantment e : swordEnchantments) itemstack.enchant(e, 127);
            }
            case PICKAXE -> {
                CompoundTag tag = new CompoundTag();
                tag.putBoolean("Unbreakable", true);
                itemstack.setTag(tag);
                for(Enchantment e : pickaxeEnchantments) itemstack.enchant(e, 127);
            }
        }
    }
}

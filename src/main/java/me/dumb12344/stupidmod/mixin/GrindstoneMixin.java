package me.dumb12344.stupidmod.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.HashSet;
import java.util.Set;

@Mixin(GrindstoneMenu.class)
public abstract class GrindstoneMixin {
    @Mutable
    @Final
    @Shadow
    final Container repairSlots;

    protected GrindstoneMixin(Container repairSlots) {
        this.repairSlots = repairSlots;
    }

    @ModifyArg(
        method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
        at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/world/inventory/GrindstoneMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;"),
        index = 0
    )
    public Slot e(Slot par1){
        return new Slot(this.repairSlots, 0, 49, 19) {
            public boolean mayPlace(@NotNull ItemStack p_39607_) {
                boolean isStuff=false;
                Set<Item> stuff = new HashSet<>();
                stuff.add(Items.ENDER_PEARL);
                stuff.add(Blocks.BEDROCK.asItem());
                stuff.add(Items.REDSTONE);
                stuff.add(Blocks.NETHERITE_BLOCK.asItem());
                stuff.add(Blocks.NETHERRACK.asItem());
                stuff.add(Blocks.TNT.asItem());
                for (Item e : stuff) {
                    if (p_39607_.getItem().equals(e)) {
                        isStuff = true;
                    }
                }
                return p_39607_.isDamageableItem() || p_39607_.is(Items.ENCHANTED_BOOK) || p_39607_.isEnchanted() || p_39607_.canGrindstoneRepair() || isStuff;
            }
        };
    }
}

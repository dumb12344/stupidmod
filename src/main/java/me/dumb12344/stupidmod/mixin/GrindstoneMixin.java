package me.dumb12344.stupidmod.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

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
            public boolean mayPlace(ItemStack p_39607_) {
                return p_39607_.isDamageableItem() || p_39607_.is(Items.ENCHANTED_BOOK) || p_39607_.isEnchanted() || p_39607_.canGrindstoneRepair() || p_39607_.is(Items.ENDER_PEARL) || p_39607_.is(Blocks.BEDROCK.asItem());
            }
        };
    }
}

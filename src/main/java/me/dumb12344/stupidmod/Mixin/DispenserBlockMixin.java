package me.dumb12344.stupidmod.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin {
    @Shadow
    @Final
    private static Map<Item, DispenseItemBehavior> DISPENSER_REGISTRY;

    @Inject(method = "registerBehavior", at = @At("HEAD"), cancellable = true)
    private static void registerOverride(ItemLike p_52673_, DispenseItemBehavior p_52674_, CallbackInfo ci){
        if(p_52673_.equals(Blocks.TNT)){
            ci.cancel();
            DISPENSER_REGISTRY.put(Blocks.TNT.asItem(), new DefaultDispenseItemBehavior() {
                protected @NotNull ItemStack execute(@NotNull BlockSource p_123425_, @NotNull ItemStack p_123426_) {
                    Level level = p_123425_.getLevel();
                    BlockPos blockpos = p_123425_.getPos().relative(p_123425_.getBlockState().getValue(DispenserBlock.FACING));
                    PrimedTnt primedtnt = new PrimedTnt(level, (double)blockpos.getX() + 0.5D, blockpos.getY(), (double)blockpos.getZ() + 0.5D, null);
                    level.addFreshEntity(primedtnt);
                    level.playSound(null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent(null, GameEvent.ENTITY_PLACE, blockpos);
                    //p_123426_.shrink(1);
                    return p_123426_;
                }
            });
        }
    }
}

package me.dumb12344.stupidmod.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public class NetherSleepMixin {
    @Inject(method = "canSetSpawn", at = @At("HEAD"), cancellable = true)
    private static void netherSleep(Level level, CallbackInfoReturnable<Boolean> cir){
        if(level.dimension().equals(Level.NETHER)){
            cir.setReturnValue(true);
        }
        else if(level.dimension().equals(Level.OVERWORLD)){
            cir.setReturnValue(false);
        }
    }
    @Inject(method = "use", at = @At("HEAD"))
    private void netherSleep2(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand p_49519_, BlockHitResult p_49520_, CallbackInfoReturnable<InteractionResult> cir){
        if(level.dimension().equals(Level.NETHER)&&!level.isClientSide){
            player.startSleeping(blockPos);
        }
    }
}

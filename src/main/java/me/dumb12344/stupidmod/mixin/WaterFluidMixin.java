package me.dumb12344.stupidmod.mixin;

import net.minecraft.world.level.material.WaterFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WaterFluid.class)
public class WaterFluidMixin {
    @Inject(method = "getExplosionResistance", at = @At("RETURN"), cancellable = true)
    public void e(CallbackInfoReturnable<Float> cir){
        cir.setReturnValue(1000F);
    }
}

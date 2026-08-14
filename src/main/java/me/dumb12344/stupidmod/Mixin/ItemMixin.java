package me.dumb12344.stupidmod.Mixin;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "getMaxStackSize", at = @At("RETURN"), cancellable = true)
    void stacksize(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(100);
    }
}

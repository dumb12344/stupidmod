package me.dumb12344.stupidmod.Mixin;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Item.class)
public abstract class BedrockEatMixin {
    @Shadow
    public abstract ItemStack getDefaultInstance();

    @Mutable
    @Shadow
    @Final
    @Nullable
    private FoodProperties foodProperties;

    @Inject(method = "isEdible", at = @At("RETURN"), cancellable = true)
    public void e(CallbackInfoReturnable<Boolean> cir){
        if(this.getDefaultInstance().is(Blocks.BEDROCK.asItem())){
            this.foodProperties=new FoodProperties.Builder().alwaysEat().nutrition(100).saturationMod(100F).build();
            cir.setReturnValue(true);
        }
    }
}

package me.dumb12344.stupidmod.BedrockItems;

import me.dumb12344.stupidmod.Registry.BedrockArmorMaterialRegistry;
import me.dumb12344.stupidmod.Registry.BedrockItemRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BedrockHelmetItem extends ArmorItem {
    public BedrockHelmetItem(Properties properties) {
        super(BedrockArmorMaterialRegistry.BEDROCK_ARMOR_MATERIAL, Type.HELMET, properties);
    }
    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);
        if(!(entity instanceof Player player))return;
        if(player.getInventory().getArmor(3).is(BedrockItemRegistry.BEDROCK_HELMET.get())){
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 255));
        }

    }
}

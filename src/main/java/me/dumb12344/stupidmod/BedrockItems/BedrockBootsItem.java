package me.dumb12344.stupidmod.BedrockItems;

import me.dumb12344.stupidmod.Registry.BedrockArmorMaterialRegistry;
import me.dumb12344.stupidmod.Registry.BedrockItemRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BedrockBootsItem extends ArmorItem {
    public BedrockBootsItem(Properties properties) {
        super(BedrockArmorMaterialRegistry.BEDROCK_ARMOR_MATERIAL, Type.BOOTS, properties);
    }
    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);
        if(!(entity instanceof Player player))return;
        boolean shouldFly = player.getInventory().getArmor(0).is(BedrockItemRegistry.BEDROCK_BOOTS.get())||player.isCreative();
        player.getAbilities().mayfly=shouldFly;
        if(!shouldFly)player.getAbilities().flying=false;
    }
}

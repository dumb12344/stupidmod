package me.dumb12344.stupidmod.bedrockitems;

import me.dumb12344.stupidmod.registry.BedrockArmorMaterialRegistry;
import me.dumb12344.stupidmod.registry.BedrockItemRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BedrockBootsItem extends ArmorItem {
    public BedrockBootsItem(Properties properties) {
        super(BedrockArmorMaterialRegistry.BEDROCK_ARMOR_MATERIAL, Type.BOOTS, properties);
    }
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);
        if(!(entity instanceof Player player))return;
        boolean shouldFly = player.getInventory().getArmor(0).is(BedrockItemRegistry.BEDROCK_BOOTS.get())||player.isCreative();
        player.getAbilities().mayfly=shouldFly;
        if(!shouldFly)player.getAbilities().flying=false;
    }
}

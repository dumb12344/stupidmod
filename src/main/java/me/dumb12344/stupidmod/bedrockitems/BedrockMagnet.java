package me.dumb12344.stupidmod.bedrockitems;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BedrockMagnet extends Item {
    public BedrockMagnet(Properties properties) {
        super(properties);
    }
    //https://github.com/sinkillerj/ProjectE/blob/mc1.21.1/src/main/java/moze_intel/projecte/utils/WorldHelper.java
    public static void gravitateEntityTowards(Entity ent, Vec3 target, float power) {
        Vec3 difference = target.subtract(ent.position());
        double vel = 1.0 - difference.length() / 15.0;
        if (vel > 0.0D) {
            vel *= vel;
            ent.addDeltaMovement(difference.normalize()
                    .scale(vel)
                    .multiply(power, 2*power, power)
            );
        }
    }
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);
        Item[] items = {
                Items.ANCIENT_DEBRIS,
                Items.DIAMOND,
                Items.RAW_GOLD,
                Items.RAW_IRON,
                Items.RAW_COPPER,
                Items.COAL,
                Items.REDSTONE,
                Items.LAPIS_LAZULI
        };
        if(entity instanceof Player player) {
            if(player.getMainHandItem().is(stack.getItem())||player.getOffhandItem().is(stack.getItem())) {
                level.getEntitiesOfClass(ItemEntity.class, AABB.ofSize(entity.position(), 20, 20, 20)).forEach((ItemEntity e) -> {
                    for(Item item : items){
                        if(e.getItem().is(item)){
                            gravitateEntityTowards(e, player.position(), 0.5F);
                        }
                    }
                });
            }
        }
    }
}

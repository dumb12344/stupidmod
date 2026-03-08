package me.dumb12344.stupidmod.C4;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class C4Detonator extends Item {
    public C4Detonator(Properties p_41188_) {
        super(p_41188_);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if(!level.isClientSide()) {
            level.getEntitiesOfClass(C4Entity.class,
                    player.getBoundingBox().inflate(30)
            ).forEach(
                    player.isCrouching() ? C4Entity::disarm : C4Entity::explode
            );
        }
        return InteractionResultHolder.consume(itemstack);
    }
}

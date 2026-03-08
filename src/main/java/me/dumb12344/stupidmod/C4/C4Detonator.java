package me.dumb12344.stupidmod.C4;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class C4Detonator extends Item {
    public C4Detonator(Properties p_41188_) {
        super(p_41188_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(Component.literal("Use to detonate all nearby C4"));
        p_41423_.add(Component.literal("Use while crouching to disarm all nearby C4"));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
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
            )
            .forEach((C4Entity entity) -> {
                if (!entity.getOwner().is(player))return;
                if(player.isCrouching())entity.disarm();
                else entity.explode();
            });
        }
        return InteractionResultHolder.consume(itemstack);
    }
}

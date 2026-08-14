package me.dumb12344.stupidmod.C4;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class C4Detonator extends Item {
    public C4Detonator(Properties p_41188_) {
        super(p_41188_);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, @NotNull TooltipFlag p_41424_) {
        p_41423_.add(Component.literal("Use to detonate nearby C4"));
        p_41423_.add(Component.literal("Use while crouching to disarm nearby C4"));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack p_41454_) {
        return 72000;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if(!level.isClientSide()) {
            ((ServerLevel)level).getAllEntities().forEach((Entity entity) -> {
                // return if not c4
                if(!(entity instanceof C4Entity c4entity)) return;
                // return if not owned and not in creative
                if (!c4entity.isOwnedBy(player) && !player.getAbilities().instabuild) {return;}
                if (player.isCrouching()) c4entity.delayedDisarm();
                else c4entity.delayedExplode();
            });
        }
        return InteractionResultHolder.consume(itemstack);
    }
}

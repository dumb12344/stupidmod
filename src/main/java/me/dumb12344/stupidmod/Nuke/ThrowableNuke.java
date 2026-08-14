package me.dumb12344.stupidmod.Nuke;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ThrowableNuke extends Item {
    public ThrowableNuke(Item.Properties p_41188_) {
        super(p_41188_);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level p_41190_, Player player, @NotNull InteractionHand p_41192_) {
        ItemStack itemstack = player.getItemInHand(p_41192_);
        p_41190_.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (p_41190_.getRandom().nextFloat() * 0.4F + 0.8F));
        //player.getCooldowns().addCooldown(this, 20);
        if (!p_41190_.isClientSide) {
            PrimedNuke thrownenderpearl = new PrimedNuke(p_41190_, player.getX(), player.getY(), player.getZ(), player);
            thrownenderpearl.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            p_41190_.addFreshEntity(thrownenderpearl);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, p_41190_.isClientSide());
    }
}

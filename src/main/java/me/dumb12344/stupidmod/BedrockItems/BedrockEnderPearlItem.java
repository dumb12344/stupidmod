package me.dumb12344.stupidmod.BedrockItems;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BedrockEnderPearlItem extends Item {
    public BedrockEnderPearlItem(Properties properties) {
        super(properties);
    }

    /*@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        //player.getCooldowns().addCooldown(this, 20)
        if (!level.isClientSide) {
            BedrockEnderPearlProjectile bedrockEnderPearlProjectile = new BedrockEnderPearlProjectile(level, player);
            bedrockEnderPearlProjectile.setItem(itemstack);
            bedrockEnderPearlProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(bedrockEnderPearlProjectile);
        }
        return super.use(level, player, interactionHand);
    }
    */
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand interactionHand) {
        ClipContext context = new ClipContext(player.getEyePosition(), player.getEyePosition().add(player.getViewVector(1).scale(50)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null);
        BlockPos pos = level.clip(context).getBlockPos();
        player.teleportTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        return super.use(level, player, interactionHand);
    }
}

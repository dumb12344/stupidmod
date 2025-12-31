package me.dumb12344.stupidmod.bedrockitems;

import me.dumb12344.stupidmod.bedrockitems.projectiles.BedrockSwordProjectile;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;

public class BedrockSwordItem extends SwordItem {
    public BedrockSwordItem(Properties properties) {
        super(BedrockTier.BEDROCK, 1000000-1-64, 1000000-4, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        //player.getCooldowns().addCooldown(this, 20);
        if (!level.isClientSide) {
            for(int i=0;i<5;i++) {
                for (int j = 0; j < 5; j++) {
                    BedrockSwordProjectile bedrockSwordProjectile = new BedrockSwordProjectile(level, player);
                    bedrockSwordProjectile.setItem(itemstack);
                    bedrockSwordProjectile.shootFromRotation(
                            player,
                            (float) (player.getXRot() + (j - 2.5) * 5),
                            (float) (player.getYRot() + (i - 2.5) * 5),
                            0.0F, 1.0F, 1.0F
                    );
                    level.addFreshEntity(bedrockSwordProjectile);
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}

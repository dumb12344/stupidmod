package me.dumb12344.stupidmod.C4;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class C4MachineGun extends Item {
    public C4MachineGun(Properties p_41188_) {
        super(p_41188_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        p_41433_.startUsingItem(p_41434_);
        return InteractionResultHolder.consume(p_41433_.getItemInHand(p_41434_));
    }

    public int getUseDuration(ItemStack p_272765_) {
        return 72000;
    }

    public void onUseTick(Level level, LivingEntity player, ItemStack stack, int i) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            C4Entity c4 = new C4Entity(level, player.getX(), player.getEyeY(), player.getZ(), player);
            c4.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(c4);
        }
    }
}

package me.dumb12344.stupidmod.C4;

import me.dumb12344.stupidmod.registry.C4Registry;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class C4Entity extends AbstractArrow {
    public boolean isOnWall = false;
    public Direction isOnWallDirection = Direction.DOWN;
    public C4Entity(EntityType<? extends C4Entity> p_32076_, Level p_32077_) {
        super(p_32076_, p_32077_);
        this.blocksBuilding = false;
    }

    public C4Entity(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity owner) {
        this(C4Registry.C4_ENTITY.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        //double d0 = p_32079_.random.nextDouble() * (double)((float)Math.PI * 2F);
        //this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.setSoundEvent(SoundEvents.STONE_HIT);
    }

    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        this.isOnWall = true;
        this.isOnWallDirection = p_36755_.getDirection();
    }

    @Override
    protected boolean canHitEntity(Entity p_36743_) {
        return false;
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.STONE_HIT;
    }

    @Override
    protected ItemStack getPickupItem() {
        return C4Registry.C4_ITEM.get().getDefaultInstance();
    }

    public void explode() {
        this.discard();
        float f = 4.0F;
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), f, Level.ExplosionInteraction.TNT);
    }

    public void disarm() {
        this.discard();
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 0.3F, Level.ExplosionInteraction.NONE);
        //this.level().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
    }

}
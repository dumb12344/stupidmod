/*package me.dumb12344.stupidmod.C4;

import me.dumb12344.stupidmod.registry.C4Registry;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class C4Entity2 extends Projectile {
    // I also don't know why there are so many constructors
    public C4Entity(EntityType<? extends C4Entity> p_32076_, Level p_32077_) {
        super(p_32076_, p_32077_);
    }
    public C4Entity(Level level, LivingEntity entity) {
        this(C4Registry.C4_ENTITY.get(), entity, level);
    }
    protected C4Entity(EntityType<? extends C4Entity> p_37456_, double p_37457_, double p_37458_, double p_37459_, Level p_37460_) {
        this(p_37456_, p_37460_);
        this.setPos(p_37457_, p_37458_, p_37459_);
    }
    protected C4Entity(EntityType<? extends C4Entity> p_37462_, LivingEntity p_37463_, Level p_37464_) {
        this(p_37462_, p_37463_.getX(), p_37463_.getEyeY() - (double)0.1F, p_37463_.getZ(), p_37464_);
        this.setOwner(p_37463_);
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

    @Override
    protected void defineSynchedData() {

    }
}*/
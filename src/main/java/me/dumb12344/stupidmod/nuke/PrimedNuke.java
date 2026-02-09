package me.dumb12344.stupidmod.nuke;

import javax.annotation.Nullable;

import me.dumb12344.stupidmod.registry.NukeRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PrimedNuke extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(PrimedNuke.class, EntityDataSerializers.INT);
    private static final int DEFAULT_FUSE_TIME = 80;
    @Nullable
    private LivingEntity owner;

    public PrimedNuke(EntityType<? extends PrimedNuke> p_32076_, Level p_32077_) {
        super(p_32076_, p_32077_);
        this.blocksBuilding = true;
    }

    public PrimedNuke(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
        this(NukeRegistry.PRIMED_NUKE.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        double d0 = p_32079_.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
    }
    public void shootFromRotation(Entity p_37252_, float p_37253_, float p_37254_, float p_37255_, float p_37256_, float p_37257_) {
        float f = -Mth.sin(p_37254_ * ((float)Math.PI / 180F)) * Mth.cos(p_37253_ * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((p_37253_ + p_37255_) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(p_37254_ * ((float)Math.PI / 180F)) * Mth.cos(p_37253_ * ((float)Math.PI / 180F));
        this.shoot((double)f, (double)f1, (double)f2, p_37256_, p_37257_);
        Vec3 vec3 = p_37252_.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, p_37252_.onGround() ? 0.0D : vec3.y, vec3.z));
    }
    public void shoot(double p_37266_, double p_37267_, double p_37268_, float p_37269_, float p_37270_) {
        Vec3 vec3 = (new Vec3(p_37266_, p_37267_, p_37268_)).normalize().add(this.random.triangle(0.0D, 0.0172275D * (double)p_37270_), this.random.triangle(0.0D, 0.0172275D * (double)p_37270_), this.random.triangle(0.0D, 0.0172275D * (double)p_37270_)).scale((double)p_37269_);
        this.setDeltaMovement(vec3);
        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 80);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }
    protected void explode() {
        float f = 100.0F;
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), f, Level.ExplosionInteraction.TNT);
    }

    protected void addAdditionalSaveData(CompoundTag p_32097_) {
        p_32097_.putShort("Fuse", (short)this.getFuse());
    }

    protected void readAdditionalSaveData(CompoundTag p_32091_) {
        this.setFuse(p_32091_.getShort("Fuse"));
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    protected float getEyeHeight(Pose p_32088_, EntityDimensions p_32089_) {
        return 0.15F;
    }

    public void setFuse(int p_32086_) {
        this.entityData.set(DATA_FUSE_ID, p_32086_);
    }

    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }
}
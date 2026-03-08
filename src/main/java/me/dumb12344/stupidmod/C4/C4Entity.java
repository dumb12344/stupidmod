package me.dumb12344.stupidmod.C4;

import me.dumb12344.stupidmod.registry.C4Registry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class C4Entity extends Entity implements TraceableEntity {
    @Nullable
    private LivingEntity owner;

    public C4Entity(EntityType<? extends C4Entity> p_32076_, Level p_32077_) {
        super(p_32076_, p_32077_);
        this.blocksBuilding = true;
    }

    public C4Entity(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
        this(C4Registry.C4_ENTITY.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        //double d0 = p_32079_.random.nextDouble() * (double)((float)Math.PI * 2F);
        //this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
    }
    public void shootFromRotation(Entity p_37252_, float p_37253_, float p_37254_, float p_37255_, float p_37256_, float p_37257_) {
        float f = -Mth.sin(p_37254_ * ((float)Math.PI / 180F)) * Mth.cos(p_37253_ * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((p_37253_ + p_37255_) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(p_37254_ * ((float)Math.PI / 180F)) * Mth.cos(p_37253_ * ((float)Math.PI / 180F));
        this.shoot(f, f1, f2, p_37256_, p_37257_);
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

    protected void onHit(HitResult p_37260_){
        HitResult.Type hitresult$type = p_37260_.getType();
        if (hitresult$type == HitResult.Type.ENTITY) {
            this.onHitEntity((EntityHitResult)p_37260_);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, p_37260_.getLocation(), GameEvent.Context.of(this, (BlockState)null));
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)p_37260_;
            this.onHitBlock(blockhitresult);
            BlockPos blockpos = blockhitresult.getBlockPos();
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
        }
    }

    protected void onHitEntity(HitResult e){
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
    }

    protected void onHitBlock(BlockHitResult p_37258_) {
        BlockState blockstate = this.level().getBlockState(p_37258_.getBlockPos());
    }

    protected void defineSynchedData() {
    }

    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

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

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    protected float getEyeHeight(Pose p_32088_, EntityDimensions p_32089_) {
        return 0.15F;
    }

}
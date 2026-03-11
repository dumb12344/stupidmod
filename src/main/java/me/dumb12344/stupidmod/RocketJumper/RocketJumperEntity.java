package me.dumb12344.stupidmod.RocketJumper;

import me.dumb12344.stupidmod.registry.RocketJumperRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class RocketJumperEntity extends AbstractArrow {
    public int age = 0;
    public RocketJumperEntity(double p_36712_, double p_36713_, double p_36714_, Level p_36715_) {
        super(RocketJumperRegistry.ROCKET_JUMPER_ENTITY.get(), p_36712_, p_36713_, p_36714_, p_36715_);
    }
    public RocketJumperEntity(EntityType<? extends Entity> type, Level level) {
        super(RocketJumperRegistry.ROCKET_JUMPER_ENTITY.get(), level);
    }
    public RocketJumperEntity(Level level, double x, double y, double z, Player person){
        this(x, y, z, level);
        this.setDeltaMovement(person.getLookAngle().normalize().scale(5));
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();
        age++;
        if(age>=10*20)this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        this.discard();
        float f = 8.0F;
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), f, Level.ExplosionInteraction.NONE);
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }
}
package me.dumb12344.stupidmod.bedrockitems.projectiles;

import me.dumb12344.stupidmod.bedrockitems.registry.BedrockItemRegistry;
import me.dumb12344.stupidmod.bedrockitems.registry.BedrockEntityTypeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class BedrockPickaxeProjectile extends ThrowableItemProjectile {
    public BedrockPickaxeProjectile(EntityType<? extends BedrockPickaxeProjectile> type, Level level) {
        super(type, level);
    }
    public BedrockPickaxeProjectile(Level level, LivingEntity livingEntity) {
        super(BedrockEntityTypeRegistry.BEDROCK_PICKAXE_PROJECTILE.get(), livingEntity, level);
        /*if(livingEntity instanceof Player)livingEntity.sendSystemMessage(Component.literal("X: "+this.getDeltaMovement().x+"Y: "+this.getDeltaMovement().y+"Z: "+this.getDeltaMovement().z));
        this.setDeltaMovement(this.getDeltaMovement().normalize().scale(.5));
        this.setDeltaMovement(Math.floor(this.getDeltaMovement().x),Math.floor(this.getDeltaMovement().y),Math.floor(this.getDeltaMovement().z));
        this.setDeltaMovement(this.getDeltaMovement().normalize().scale(2));*/
    }
    public void checkAboveWorld() {
        if (this.getY() > (double)(this.level().getMaxBuildHeight() + 64)) {
            this.onBelowWorld();
        }
    }
    private int e = 30;
    private int age = 0;
    @Override
    public void baseTick(){
        super.baseTick();
        checkAboveWorld();
        if(isInWater())this.discard();
        if(++age>=200)this.discard();
    }
    @Override
    protected float getGravity() {
        return 0;
    }
    protected Item getDefaultItem() {
        return BedrockItemRegistry.BEDROCK_PICKAXE.get();
    }
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if(e<=0){this.discard();}
        if(this.level().getBlockState(blockHitResult.getBlockPos()).getBlock().defaultDestroyTime()==-1)
            this.discard();
        this.level().destroyBlock(blockHitResult.getBlockPos(),true);
        e--;
    }
}

package me.dumb12344.stupidmod.bedrockitems.projectiles;

import me.dumb12344.stupidmod.registry.BedrockEntityTypeRegistry;
import me.dumb12344.stupidmod.registry.BedrockItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class BedrockSwordProjectile extends ThrowableItemProjectile {
    public BedrockSwordProjectile(EntityType<? extends BedrockSwordProjectile> type, Level level) {
        super(type, level);
    }
    public BedrockSwordProjectile(Level level, LivingEntity livingEntity) {
        super(BedrockEntityTypeRegistry.BEDROCK_SWORD_PROJECTILE.get(), livingEntity, level);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 1000.0F);
    }

    protected @NotNull Item getDefaultItem() {
        return BedrockItemRegistry.BEDROCK_SWORD.get();
    }
}

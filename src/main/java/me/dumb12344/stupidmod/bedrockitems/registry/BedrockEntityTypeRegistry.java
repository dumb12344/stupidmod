package me.dumb12344.stupidmod.bedrockitems.registry;

import me.dumb12344.stupidmod.Stupidmod;
import me.dumb12344.stupidmod.bedrockitems.projectiles.BedrockEnderPearlProjectile;
import me.dumb12344.stupidmod.bedrockitems.projectiles.BedrockPickaxeProjectile;
import me.dumb12344.stupidmod.bedrockitems.projectiles.BedrockSwordProjectile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BedrockEntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Stupidmod.MODID);
    public static final RegistryObject<EntityType<BedrockPickaxeProjectile>> BEDROCK_PICKAXE_PROJECTILE = ENTITY_TYPES.register(
        "bedrock_pickaxe_projectile",
        ()->EntityType.Builder.<BedrockPickaxeProjectile>of(BedrockPickaxeProjectile::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build(new ResourceLocation(Stupidmod.MODID, "bedrock_pickaxe_projectile").toString())
    );
    public static final RegistryObject<EntityType<BedrockSwordProjectile>> BEDROCK_SWORD_PROJECTILE = ENTITY_TYPES.register(
            "bedrock_sword_projectile",
            ()->EntityType.Builder.<BedrockSwordProjectile>of(BedrockSwordProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(new ResourceLocation(Stupidmod.MODID, "bedrock_sword_projectile").toString())
    );
    public static final RegistryObject<EntityType<BedrockEnderPearlProjectile>> BEDROCK_ENDER_PEARL_PROJECTILE = ENTITY_TYPES.register(
            "bedrock_ender_pearl",
            ()->EntityType.Builder.<BedrockEnderPearlProjectile>of(BedrockEnderPearlProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(new ResourceLocation(Stupidmod.MODID, "bedrock_ender_pearl").toString())
    );
}

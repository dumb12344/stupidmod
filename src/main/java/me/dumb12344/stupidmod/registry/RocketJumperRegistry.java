package me.dumb12344.stupidmod.registry;

import me.dumb12344.stupidmod.RocketJumper.RocketJumperEntity;
import me.dumb12344.stupidmod.RocketJumper.RocketJumperItem;
import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RocketJumperRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stupidmod.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Stupidmod.MODID);
    public static final RegistryObject<EntityType<RocketJumperEntity>> ROCKET_JUMPER_ENTITY = ENTITIES.register("rocket_jumper",()->
            EntityType.Builder.<RocketJumperEntity>of(RocketJumperEntity::new, MobCategory.MISC)
            .fireImmune()
            .sized(1F, 1F)
            .clientTrackingRange(30)
            .updateInterval(20)
            .build(new ResourceLocation(Stupidmod.MODID,"rocket_jumper").toString())
    );
    public static final RegistryObject<Item> ROCKET_JUMPER = ITEMS.register("rocket_jumper", () -> new RocketJumperItem(new Item.Properties()));
}

package me.dumb12344.stupidmod.registry;

import me.dumb12344.stupidmod.C4.C4Detonator;
import me.dumb12344.stupidmod.C4.C4Entity;
import me.dumb12344.stupidmod.C4.C4Item;
import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class C4Registry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stupidmod.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Stupidmod.MODID);
    public static final RegistryObject<EntityType<C4Entity>> C4_ENTITY = ENTITIES.register("c4",()->
            EntityType.Builder.<C4Entity>of(C4Entity::new, MobCategory.MISC)
            .fireImmune()
            .sized(0.6F, 0.125F)
            .clientTrackingRange(30)
            .updateInterval(20)
            .build(new ResourceLocation(Stupidmod.MODID,"c4").toString())
    );
    public static final RegistryObject<Item> C4_ITEM = ITEMS.register("c4", () -> new C4Item(new Item.Properties()));
    public static final RegistryObject<Item> C4_DETONATOR_ITEM = ITEMS.register("c4_detonator", () -> new C4Detonator(new Item.Properties()));
}

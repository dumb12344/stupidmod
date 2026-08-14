package me.dumb12344.stupidmod.Registry;

import me.dumb12344.stupidmod.Stupidmod;
import me.dumb12344.stupidmod.Nuke.NukeBlock;
import me.dumb12344.stupidmod.Nuke.PrimedNuke;
import me.dumb12344.stupidmod.Nuke.ThrowableNuke;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NukeRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Stupidmod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stupidmod.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Stupidmod.MODID);
    public static final RegistryObject<Block> NUKE = BLOCKS.register("nuke",()->new NukeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).instabreak().sound(SoundType.GRASS).ignitedByLava().isRedstoneConductor(NukeRegistry::never)));
    public static final RegistryObject<EntityType<PrimedNuke>> PRIMED_NUKE = ENTITIES.register("nuke",()->
            EntityType.Builder.<PrimedNuke>of(PrimedNuke::new, MobCategory.MISC)
            .fireImmune()
            .sized(0.98F, 0.98F)
            .clientTrackingRange(10)
            .updateInterval(10
            ).build(ResourceLocation.fromNamespaceAndPath(Stupidmod.MODID,"nuke").toString())
    );
    public static final RegistryObject<Item> NUKE_ITEM = ITEMS.register("nuke", () -> new BlockItem(NUKE.get(), new Item.Properties()));
    public static final RegistryObject<Item> THROWABLE_NUKE_ITEM = ITEMS.register("throwable_nuke", () -> new ThrowableNuke(new Item.Properties()));
    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }
}

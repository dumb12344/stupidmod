package me.dumb12344.stupidmod.registry;

import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ZeroFrictionGrassBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Stupidmod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stupidmod.MODID);
    // LivingEntity has friction 0.91F applied when grounded
    public static final RegistryObject<Block> ZERO_FRICTION_GRASS_BLOCK = BLOCKS.register("zero_friction_grass_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS).friction(1/0.91F).speedFactor(1)));
    public static final RegistryObject<Item> ZERO_FRICTION_GRASS_ITEM = ITEMS.register("zero_friction_grass_block", () -> new BlockItem(ZERO_FRICTION_GRASS_BLOCK.get(), new Item.Properties()){});
}

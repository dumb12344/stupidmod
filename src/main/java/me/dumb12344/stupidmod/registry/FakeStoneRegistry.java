package me.dumb12344.stupidmod.registry;

import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FakeStoneRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Stupidmod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stupidmod.MODID);
    private static class FakeStoneBlock extends Block {
        public FakeStoneBlock(Properties p_49795_) {
            super(p_49795_);
        }

        @Override
        public boolean isAir(BlockState state) {
            return false;
        }

        @Override
        public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
            return false;
        }

        @Override
        public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
            return Shapes.empty();
//            return Shapes.block();
        }

        @Override
        public float getShadeBrightness(BlockState p_60472_, BlockGetter p_60473_, BlockPos p_60474_) {
            return 0.2F;
        }

        @Override
        public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
            return true;
        }

        @Override
        public VoxelShape getVisualShape(BlockState p_60479_, BlockGetter p_60480_, BlockPos p_60481_, CollisionContext p_60482_) {
            return Shapes.block();
        }

        @Override
        public VoxelShape getBlockSupportShape(BlockState p_60581_, BlockGetter p_60582_, BlockPos p_60583_) {
            return Shapes.block();
        }

        @Override
        public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
            return false;
        }

        @Override
        public VoxelShape getInteractionShape(BlockState p_60547_, BlockGetter p_60548_, BlockPos p_60549_) {
            return Shapes.block();
        }

        @Override
        public VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
            return Shapes.block();
        }
    }
    public static final RegistryObject<Block> FAKE_STONE_BLOCK = BLOCKS.register("fake_stone", () -> new FakeStoneBlock(
            BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F)
            .isValidSpawn(FakeStoneRegistry::always)
            .forceSolidOn()
            .isRedstoneConductor(FakeStoneRegistry::always)
    ));

    private static boolean always(BlockState p_50810_, BlockGetter p_50811_, BlockPos p_50812_) {return true;}
    private static boolean always(BlockState p_50810_, BlockGetter p_50811_, BlockPos p_50812_, EntityType<?> p_50813_) {return true;}
    public static final RegistryObject<Item> FAKE_STONE_ITEM = ITEMS.register("fake_stone", () -> new BlockItem(FAKE_STONE_BLOCK.get(), new Item.Properties()){});
}

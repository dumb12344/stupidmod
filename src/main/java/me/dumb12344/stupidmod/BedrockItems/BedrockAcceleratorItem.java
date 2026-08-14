package me.dumb12344.stupidmod.BedrockItems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BedrockAcceleratorItem extends Item {
    public BedrockAcceleratorItem(Properties properties) {
        super(properties);
    }
    public <T extends BlockEntity> void tickBlockEntity(T blockEntity){
        //https://github.com/GregTechCEu/GregTech-Modern/blob/1.20.1/src/main/java/com/gregtechceu/gtceu/common/machine/electric/WorldAcceleratorMachine.java
        BlockPos pos = blockEntity.getBlockPos();
        BlockEntityTicker<T> blockEntityTicker = Objects.requireNonNull(blockEntity.getLevel()).getBlockState(pos).getTicker(blockEntity.getLevel(),(BlockEntityType<T>) blockEntity.getType());
        if (blockEntityTicker == null) return;
        for (int i = 0; i < 10000; i++) {
            blockEntityTicker.tick(blockEntity.getLevel(), blockEntity.getBlockPos(), blockEntity.getBlockState(),blockEntity);
        }
    }
    public void randomTick(Level level,BlockPos pos){
        level.getBlockState(pos).randomTick((ServerLevel)level,pos,level.getRandom());
    }
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if(context.getLevel().isClientSide())return InteractionResult.SUCCESS;
        if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof EntityBlock) {
            tickBlockEntity(Objects.requireNonNull(context.getLevel().getBlockEntity(context.getClickedPos())));
        }
        if(context.getLevel().getBlockState(context.getClickedPos()).isRandomlyTicking()){
            randomTick(context.getLevel(),context.getClickedPos());
        }
        return InteractionResult.SUCCESS;
    }
}

package me.dumb12344.stupidmod.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BedrockMixin {
    @Shadow
    public abstract Block getBlock();

    @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    public void e(BlockGetter getter, BlockPos pos, CallbackInfoReturnable<Float> cir){
        if(this.getBlock().equals(Blocks.BEDROCK)){
           cir.setReturnValue(Blocks.OBSIDIAN.defaultDestroyTime()*5);
        }
        else if(this.getBlock().equals(Blocks.BARRIER)){
            cir.setReturnValue(Blocks.OBSIDIAN.defaultDestroyTime()*50);
        }
    }
}

/*@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BedrockMixin {
    @Shadow
    public abstract Block getBlock();

    @Inject(method = "getDestroyProgress", at = @At("RETURN"), cancellable = true)
    public void e(Player p_60626_, BlockGetter p_60627_, BlockPos p_60628_, CallbackInfoReturnable<Float> cir){
        if(this.getBlock().equals(Blocks.BEDROCK)){
            if(p_60626_.getMainHandItem().is(Items.NETHERITE_PICKAXE)) {
                cir.setReturnValue(1F);
            }
            else cir.setReturnValue(0.1F);
        }
    }
}
/*
@Mixin(Blocks.class)
public class BedrockMixin {
    @Inject(method = "register(Ljava/lang/String;Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;", at = @At("HEAD"), cancellable = true)
    private static void BEDROCK2(String name, Block block, CallbackInfoReturnable<Block> cir){
        if(Objects.equals(name, "bedrock")) {
            cir.setReturnValue(
                 new Block(BlockBehaviour.Properties.of()
                         .mapColor(MapColor.STONE)
                         .instrument(NoteBlockInstrument.BASEDRUM)
                         .strength(1.0F, 3600000.0F)
                         .noLootTable()
                         .isValidSpawn((state, level, pos, entity) -> false)
                 ));
        }
    }
    /*
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=bedrock"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;"
            ),
            index = 1
    )
    private static Block replaceBedrock(Block original) {
        return new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .strength(1.0F, 3600000.0F)
                .isValidSpawn((state, level, pos, entity) -> false)
        );
    }
    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;"
            )
    )
    private static Block modifyBedrock(String name, Block original) {
        if (name.equals("bedrock")) {
            return new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.0F, 3600000.0F)
                    .isValidSpawn((state, level, pos, entity) -> false)
            );
        }
        return original;
    }
}
 */

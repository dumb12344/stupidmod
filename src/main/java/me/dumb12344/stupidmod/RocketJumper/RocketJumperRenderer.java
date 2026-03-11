package me.dumb12344.stupidmod.RocketJumper;

import com.mojang.blaze3d.vertex.PoseStack;
import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RocketJumperRenderer extends EntityRenderer<RocketJumperEntity>  {
    private final BlockRenderDispatcher blockRenderer;

    public RocketJumperRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.1F;
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    public void render(RocketJumperEntity entity, float p_116178_, float p_116179_, PoseStack poseStack, MultiBufferSource bufferSource, int p_116182_) {
        poseStack.pushPose();
        poseStack.popPose();
        poseStack.scale(0.3F,0.3F,0.3F);
        TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, Blocks.TNT.defaultBlockState(), poseStack, bufferSource, p_116182_, false);
        super.render(entity, p_116178_, p_116179_, poseStack, bufferSource, p_116182_);
    }

    public ResourceLocation getTextureLocation(RocketJumperEntity p_116175_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
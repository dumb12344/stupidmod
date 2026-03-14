package me.dumb12344.stupidmod.C4;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class C4Renderer extends EntityRenderer<C4Entity> implements RenderLayerParent<C4Entity, C4Model> {
    protected C4Model model;
    private BlockRenderDispatcher blockRenderer;
    private EntityRendererProvider.Context context;

    public C4Renderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new C4Model(context.bakeLayer(C4Model.LAYER_LOCATION));
        this.shadowRadius = 0.1F;
        this.blockRenderer = context.getBlockRenderDispatcher();
        this.context = context;
    }

    public void render(C4Entity entity, float p_116178_, float p_116179_, PoseStack poseStack, MultiBufferSource bufferSource, int p_116182_) {
        switch(entity.isOnWallDirection){
            case DOWN:
                poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                break;
            case UP:
                poseStack.mulPose(Axis.ZP.rotationDegrees(180));
                break;
            case NORTH:
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                break;
            case SOUTH:
                poseStack.mulPose(Axis.XN.rotationDegrees(90));
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                break;
            case EAST:
                poseStack.mulPose(Axis.ZP.rotationDegrees(90));
                poseStack.mulPose(Axis.YN.rotationDegrees(90));
                break;
            case WEST:
                poseStack.mulPose(Axis.ZN.rotationDegrees(90));
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                break;
        }
        if(entity.camo) poseStack.scale(0.3F,0.3F,0.3F);
        poseStack.translate(0,-0.125,0);
        //blockRenderer.getBlockModel(entity.blockHitState).getRenderTypes(entity.blockHitState, entity.level().getRandom(), ModelData.EMPTY).;
        this.model.renderToBuffer(
                poseStack,
                bufferSource.getBuffer(this.model.renderType(getTextureLocation(entity))),
                p_116182_,
                10 << 16,
                1,
                1,
                1,
                1
        );
        super.render(entity, p_116178_, p_116179_, poseStack, bufferSource, p_116182_);
    }
    @Override
    public C4Model getModel() {
        return this.model;
    }

    public ResourceLocation getTextureLocation(C4Entity entity) {
        /*if(entity.camo) {
            blockRenderer.getBlockModel(entity.blockHitState).getParticleIcon(ModelData.EMPTY).contents().name();
            blockRenderer.getBlockModel(entity.blockHitState).getQuads(
                    entity.blockHitState,
                    entity.isOnWallDirection,
                    entity.level().getRandom(),
                    ModelData.EMPTY,
                    blockRenderer.getBlockModel(entity.blockHitState).getRenderTypes(entity.blockHitState, entity.level().getRandom(), ModelData.EMPTY).asList().get(0)
            ).get(0).getSprite().contents().name();
        }*/
        return new ResourceLocation(Stupidmod.MODID, "textures/entity/c4texture.png");
    }
}
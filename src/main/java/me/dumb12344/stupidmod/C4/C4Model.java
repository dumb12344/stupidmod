package me.dumb12344.stupidmod.C4;
// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class C4Model extends EntityModel<C4Entity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Stupidmod.MODID, "c4texture"), "main");
    private final ModelPart bb_main;

    public C4Model(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -2.0F, 0.0F, 8.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 25).addBox(-1.2F, -1.9F, 0.4F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(-6.3F, -2.2F, 2.2F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(10, 28).addBox(-6.2F, -2.4F, 1.2F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 25).addBox(-6.0F, -2.2F, 1.4F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 25).addBox(-6.0F, -2.2F, 1.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 28).addBox(-6.4F, -2.2F, 1.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-6.4F, -2.2F, 1.4F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 14).addBox(-7.9F, -2.3F, 7.3F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 22).addBox(-8.1F, -1.9F, 7.3F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 17).addBox(-6.2F, -2.4F, 2.1F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 17).addBox(-8.0F, -2.1F, 5.4F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 19).addBox(-8.0F, -2.3F, 2.7F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 11).addBox(-8.1F, -1.9F, 2.7F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 31).addBox(-0.3F, -2.1F, 2.7F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 31).addBox(-3.0F, -2.8F, 4.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-4.3F, -2.8F, 4.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 0).addBox(-5.6F, -2.8F, 4.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 31).addBox(-3.0F, -2.8F, 5.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 32).addBox(-4.3F, -2.8F, 5.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 3).addBox(-5.6F, -2.8F, 5.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 31).addBox(-3.0F, -2.8F, 6.7F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 31).addBox(-4.3F, -2.8F, 6.7F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 6).addBox(-5.6F, -2.8F, 6.7F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-5.8F, -2.7F, 3.9F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4F, 2F, -4F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(30, 25).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.6F, -2.3F, 4.0F, -0.0873F, -0.7854F, 0.0F));

        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(30, 28).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.7F, -2.3F, 7.4F, 0.0F, 1.5708F, -0.1745F));

        PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(10, 31).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.6F, 1.6F, 0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(C4Entity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

    }
}
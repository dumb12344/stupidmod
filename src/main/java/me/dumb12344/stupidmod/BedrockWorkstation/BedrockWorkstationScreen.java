package me.dumb12344.stupidmod.BedrockWorkstation;

import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BedrockWorkstationScreen extends AbstractContainerScreen<BedrockWorkstationMenu> {
    private static final ResourceLocation GUI_LOCATION = ResourceLocation.fromNamespaceAndPath(Stupidmod.MODID,"textures/gui/container/bedrock_workstation.png");

    public BedrockWorkstationScreen(BedrockWorkstationMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }
    public void render(@NotNull GuiGraphics graphics, int x, int y, float p_98482_) {
        this.renderBackground(graphics);
        super.render(graphics, x, y, p_98482_);
        this.renderTooltip(graphics, x, y);
    }

    protected void renderBg(GuiGraphics graphics, float p_282132_, int p_283078_, int p_283647_) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

}
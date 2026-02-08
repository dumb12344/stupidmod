package me.dumb12344.stupidmod.duper;

import me.dumb12344.stupidmod.Stupidmod;
import me.dumb12344.stupidmod.bedrockworkstation.BedrockWorkstationMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DuperScreen extends AbstractContainerScreen<DuperMenu> {
    private static final ResourceLocation GUI_LOCATION = new ResourceLocation(Stupidmod.MODID,"textures/gui/container/duper.png");

    public DuperScreen(DuperMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    public void render(GuiGraphics graphics, int x, int y, float p_98482_) {
        this.renderBackground(graphics);
        super.render(graphics, x, y, p_98482_);
        this.renderTooltip(graphics, x, y);
    }
    @Override
    protected void renderBg(GuiGraphics graphics, float p_97788_, int p_97789_, int p_97790_) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}

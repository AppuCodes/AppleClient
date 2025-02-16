package appleclient.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Mouse;

import appleclient.Apple;
import appleclient.mods.Mod;
import appleclient.utils.ScissorUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiModsList extends GuiScreen
{
    private int scroll = 0, maxScroll = (int) -(48.5F * (Apple.modsManager.mods.length / 4));
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int w2 = width / 2, h2 = height / 2;
        drawRect(w2 - 200, h2 - 150, w2 + 200, h2 + 150, new Color(0, 0, 0, 200).getRGB());
        float x = w2 - 190, y = (h2 - 140) + scroll;
        int i = 0;
        ScissorUtil.begin(w2 - 200, h2 - 150, w2 + 200, h2 + 150);
        
        for (Mod mod : Apple.modsManager.mods)
        {
            if (i++ == 4)
            {
                x = w2 - 190;
                y += 97;
                i = 1;
            }
            
            boolean insideBox = cursorInsideBox(mouseX, mouseY, x, y, x + 88, y + 88);
            drawRect(x, y, x + 88, y + 88, insideBox ? new Color(70, 70, 70, 128).getRGB() : new Color(60, 60, 60, 128).getRGB());
            drawCenteredString(fontRendererObj, mod.name, x + 44, y + 35, -1);
            GlStateManager.color(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(GuiButton.buttonTextures);
            this.drawTexturedModalRect(x, y + 68, 0, 46 + (insideBox ? 40 : 20), 44, 20);
            this.drawTexturedModalRect(x + 44, y + 68, 200 - 44, 46 + (insideBox ? 40 : 20), 44, 20);
            
            if (mod.isEnabled())
            {
                drawRect(x + 1, y + 69, x + 87, y + 87, new Color(0, 255, 0, 100).getRGB());
            }
            
            else
            {
                drawRect(x + 1, y + 69, x + 87, y + 87, new Color(255, 0, 0, 100).getRGB());   
            }
            
            drawCenteredString(fontRendererObj, mod.isEnabled() ? "ENABLED" : "DISABLED", x + 44, y + 74, insideBox ? 16777120 : 14737632);
            x += 97.5F;
        }
        
        ScissorUtil.end();
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        float w2 = width / 2, h2 = height / 2;
        float x = w2 - 190, y = (h2 - 140) + scroll;
        int i = 0;
        
        for (Mod mod : Apple.modsManager.mods)
        {
            if (i++ == 4)
            {
                x = w2 - 190;
                y += 97.5F;
                i = 1;
            }
            
            if (cursorInsideBox(mouseX, mouseY, x, y, x + 88, y + 88) && mouseButton == 0)
            {
                mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                mod.toggle();
            }
            
            x += 97.5F;
        }
    }
    
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        int event = Mouse.getDWheel();
        
        if (event != 0)
        {
            if (event < 0) event = -1;
            if (event > 0) event = 1;
            scroll += (int) (event * 48.5F);
            if (scroll > 0) scroll = 0;
            if (scroll < maxScroll) scroll = maxScroll;
        }
    }
    
    public boolean cursorInsideBox(int mouseX, int mouseY, float x, float y, float width, float height)
    {
        return mouseX < width && mouseX > x && mouseY < height && mouseY > y;
    }
    
    public void onGuiClosed()
    {
        Apple.config.saveMods();
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}

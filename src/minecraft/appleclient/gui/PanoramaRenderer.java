package appleclient.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import appleclient.interfaces.IMinecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class PanoramaRenderer implements IMinecraft
{
    /** Timer used to rotate the panorama, increases every tick. */
    public static int panoramaTimer;
    
    /**
     * Texture allocated for the current viewport of the panorama
     * background.
     */
    private static DynamicTexture viewportTexture = new DynamicTexture(256, 256);
    private static ResourceLocation backgroundTexture = mc.getTextureManager().getDynamicTextureLocation("background", viewportTexture);
    
    /** An array of all the paths to the panorama pictures. */
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]
    {
        new ResourceLocation("textures/gui/title/background/panorama_0.png"),
        new ResourceLocation("textures/gui/title/background/panorama_1.png"),
        new ResourceLocation("textures/gui/title/background/panorama_2.png"),
        new ResourceLocation("textures/gui/title/background/panorama_3.png"),
        new ResourceLocation("textures/gui/title/background/panorama_4.png"),
        new ResourceLocation("textures/gui/title/background/panorama_5.png")
    };
    
    public static void tick()
    {
        ++panoramaTimer;
    }
    
    /**
     * Draws the main menu panorama
     */
    private static void drawPanorama(float partialTicks)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int i = 8;
        
        for (int j = 0; j < i * i; ++j)
        {
            GlStateManager.pushMatrix();
            float f = ((float) (j % i) / (float) i - 0.5F) / 64.0F;
            float f1 = ((float) (j / i) / (float) i - 0.5F) / 64.0F;
            float f2 = 0.0F;
            GlStateManager.translate(f, f1, f2);
            GlStateManager.rotate(MathHelper.sin(((float) panoramaTimer + partialTicks) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-((float) panoramaTimer + partialTicks) * 0.1F, 0.0F, 1.0F, 0.0F);
            
            for (int k = 0; k < 6; ++k)
            {
                GlStateManager.pushMatrix();
                
                if (k == 1)
                {
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                }
                
                if (k == 2)
                {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }
                
                if (k == 3)
                {
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                }
                
                if (k == 4)
                {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }
                
                if (k == 5)
                {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }
                
                mc.getTextureManager().bindTexture(titlePanoramaPaths[k]);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int l = 255 / (j + 1);
                float f3 = 0.0F;
                worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }
            
            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }
        
        worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }
    
    /**
     * Rotate and blurs the skybox view
     */
    private static void rotateAndBlurSkybox(int width, int height)
    {
        mc.getTextureManager().bindTexture(backgroundTexture);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        int i = 3;
        
        for (int j = 0; j < i; ++j)
        {
            float f = 1.0F / (float) (j + 1);
            int k = width;
            int l = height;
            float f1 = (float) (j - i / 2) / 256.0F;
            worldrenderer.pos(k, l, 0).tex((0.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(k, 0.0D, 0).tex((1.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(0.0D, 0.0D, 0).tex((1.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(0.0D, l, 0).tex((0.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
        }
        
        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }
    
    /**
     * Renders the skybox
     */
    public static void renderSkybox(float partialTicks, int width, int height)
    {
        GlStateManager.disableLighting();
        mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        drawPanorama(partialTicks);
        rotateAndBlurSkybox(width, height);
        rotateAndBlurSkybox(width, height);
        rotateAndBlurSkybox(width, height);
        rotateAndBlurSkybox(width, height);
        rotateAndBlurSkybox(width, height);
        rotateAndBlurSkybox(width, height);
        rotateAndBlurSkybox(width, height);
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, mc.displayWidth, mc.displayHeight);
        float f = width > height ? 120.0F / (float) width : 120.0F / (float) height;
        float f1 = (float) height * f / 256.0F;
        float f2 = (float) width * f / 256.0F;
        int i = width;
        int j = height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0D, j, 0).tex((0.5F - f1), (0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos(i, j, 0).tex((0.5F - f1), (0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos(i, 0.0D, 0).tex((0.5F + f1), (0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos(0.0D, 0.0D, 0).tex((0.5F + f1), (0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        tessellator.draw();
    }
}

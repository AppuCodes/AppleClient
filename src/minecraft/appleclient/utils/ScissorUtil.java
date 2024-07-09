package appleclient.utils;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.ScaledResolution;

public class ScissorUtil
{
    public static void begin(int x, int y, int width, int height)
    {
        int scaleFactor = ScaledResolution.get().getScaleFactor();
        x *= scaleFactor;
        y *= scaleFactor;
        width *= scaleFactor;
        height *= scaleFactor;
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x, Display.getHeight() - height, width - x, height - y);
    }
    
    public static void end()
    {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}

package appleclient.mods.impl;

import org.lwjgl.opengl.GL11;

import appleclient.Apple;
import appleclient.mods.Mod;
import appleclient.settings.impl.ColorSetting;
import appleclient.settings.impl.SliderSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import optifine.Config;
import shadersmod.client.Shaders;

public class NoHurtCam extends Mod
{
    public NoHurtCam()
    {
        super("No Hurt Cam", "Does not show the hurt cam.");
    }
}

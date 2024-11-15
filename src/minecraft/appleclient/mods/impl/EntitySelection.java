package appleclient.mods.impl;

import org.lwjgl.opengl.GL11;

import appleclient.Apple;
import appleclient.mods.Mod;
import appleclient.mods.settings.ColorSetting;
import appleclient.mods.settings.SliderSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import optifine.Config;
import shadersmod.client.Shaders;

public class EntitySelection extends Mod
{
    public EntitySelection()
    {
        super("Entity Selection", "Shows a selection for entities, similar to block selection.");
    }
    
    /**
     * Draws the selection box of the entity for the player.
     */
    public void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int p_72731_3_, float partialTicks)
    {
        if (p_72731_3_ == 0 && movingObjectPositionIn.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY)
        {
            Mod blockSelection = Apple.CLIENT.modsManager.getMod("Block Selection");
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            
            if (blockSelection.isEnabled())
            {
                ColorSetting colorSetting = (ColorSetting) blockSelection.getSetting("Outline Color");
                GlStateManager.color(colorSetting.red / 255F, colorSetting.green / 255F, colorSetting.blue / 255F, colorSetting.alpha / 255F);
                SliderSetting sliderSetting = (SliderSetting) blockSelection.getSetting("Outline Thickness");
                GL11.glLineWidth(sliderSetting.currentValue);
            }
            
            else
            {
                GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
                GL11.glLineWidth(2.0F);
            }
            
            GlStateManager.disableTexture2D();

            if (Config.isShaders())
            {
                Shaders.disableTexture2D();
            }

            GlStateManager.depthMask(false);
            Entity entity = movingObjectPositionIn.entityHit;

            if (!entity.isInvisibleToPlayer(player) && entity.isEntityAlive())
            {
                double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
                double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
                double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
                double entityX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
                double entityY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
                double entityZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
                float width = entity.width / 2.0F, height = entity.height;
                AxisAlignedBB boundingBox = new AxisAlignedBB(entityX - width, entityY, entityZ - width, entityX + width, entityY + height, entityZ + width);
                RenderGlobal.drawSelection(boundingBox.offset(-playerX, -playerY, -playerZ));
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();

            if (Config.isShaders())
            {
                Shaders.enableTexture2D();
            }

            GlStateManager.disableBlend();
        }
    }
}

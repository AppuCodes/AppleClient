package dev.tr7zw.skinlayers.renderlayers;

import java.util.Set;

import com.google.common.collect.Sets;

import appleclient.interfaces.IMinecraft;
import dev.tr7zw.skinlayers.SkinUtil;
import dev.tr7zw.skinlayers.accessor.PlayerSettings;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HeadLayerFeatureRenderer implements LayerRenderer<AbstractClientPlayer>, IMinecraft
{
    private Set<Item> hideHeadLayers = Sets.newHashSet(Items.skull);
    private RenderPlayer playerRenderer;
    private boolean thinArms;
    
    public HeadLayerFeatureRenderer(RenderPlayer playerRenderer)
    {
        thinArms = playerRenderer.hasThinArms();
        this.playerRenderer = playerRenderer;
    }
    
    @Override
    public void doRenderLayer(AbstractClientPlayer player, float paramFloat1, float paramFloat2, float paramFloat3, float deltaTick, float paramFloat5, float paramFloat6, float paramFloat7)
    {
        ItemStack itemStack = player.getEquipmentInSlot(1);
        PlayerSettings settings = (PlayerSettings) player;
        
        if (!player.hasSkin() || player.isInvisible())
        {
            return;
        }
        
        if (mc.player.getPositionVector().squareDistanceTo(player.getPositionVector()) > 14 * 14)
        {
            return;
        }
        
        if (itemStack != null && hideHeadLayers.contains(itemStack.getItem()))
        {
            return;
        }
        
        if (settings.getHeadLayers() == null && !setupModel(player, settings))
        {
            return;
        }
        
        renderCustomHelmet(settings, player, deltaTick);
    }

    private boolean setupModel(AbstractClientPlayer abstractClientPlayerEntity, PlayerSettings settings)
    {
        if (!SkinUtil.hasCustomSkin(abstractClientPlayerEntity))
        {
            return false;
        }
        
        SkinUtil.setup3DLayers(abstractClientPlayerEntity, settings, thinArms, null);
        return true;
    }
    
    public void renderCustomHelmet(PlayerSettings settings, AbstractClientPlayer abstractClientPlayer, float deltaTick)
    {
        if (settings.getHeadLayers() == null)
        {
            return;
        }
        
        if (playerRenderer.getMainModel().bipedHead.isHidden)
        {
            return;
        }
        
        float voxelSize = 1.18F;
        GlStateManager.pushMatrix();
        
        if (abstractClientPlayer.isSneaking()) 
        {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }
        
        playerRenderer.getMainModel().bipedHead.postRender(0.0625F);
        GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);
        GlStateManager.scale(voxelSize, voxelSize, voxelSize);
        boolean tintRed = abstractClientPlayer.hurtTime > 0 || abstractClientPlayer.deathTime > 0;
        settings.getHeadLayers().render(tintRed);
        GlStateManager.popMatrix();
    }
    
    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}
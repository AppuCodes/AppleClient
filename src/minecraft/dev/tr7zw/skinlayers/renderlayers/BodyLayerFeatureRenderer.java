package dev.tr7zw.skinlayers.renderlayers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import appleclient.interfaces.IMinecraft;
import dev.tr7zw.skinlayers.SkinUtil;
import dev.tr7zw.skinlayers.accessor.PlayerSettings;
import dev.tr7zw.skinlayers.render.CustomizableModelPart;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class BodyLayerFeatureRenderer implements LayerRenderer<AbstractClientPlayer>, IMinecraft
{
    private final List<Layer> bodyLayers = new ArrayList<>();
    private RenderPlayer playerRenderer;
    private final boolean thinArms;
    
    public BodyLayerFeatureRenderer(RenderPlayer playerRenderer)
    {
        this.playerRenderer = playerRenderer;
        thinArms = playerRenderer.hasThinArms();
        bodyLayers.add(new Layer(0, false, EnumPlayerModelParts.LEFT_PANTS_LEG, Shape.LEGS, () -> playerRenderer.getMainModel().bipedLeftLeg, () -> true));
        bodyLayers.add(new Layer(1, false, EnumPlayerModelParts.RIGHT_PANTS_LEG, Shape.LEGS, () -> playerRenderer.getMainModel().bipedRightLeg, () -> true));
        bodyLayers.add(new Layer(2, false, EnumPlayerModelParts.LEFT_SLEEVE, thinArms ? Shape.ARMS_SLIM : Shape.ARMS, () -> playerRenderer.getMainModel().bipedLeftArm, () -> true));
        bodyLayers.add(new Layer(3, true, EnumPlayerModelParts.RIGHT_SLEEVE, thinArms ? Shape.ARMS_SLIM : Shape.ARMS, () -> playerRenderer.getMainModel().bipedRightArm, () -> true));
        bodyLayers.add(new Layer(4, false, EnumPlayerModelParts.JACKET, Shape.BODY, () -> playerRenderer.getMainModel().bipedBody, () -> true));
    }
    
    @Override
    public void doRenderLayer(AbstractClientPlayer player, float paramFloat1, float paramFloat2, float paramFloat3, float deltaTick, float paramFloat5, float paramFloat6, float paramFloat7)
    {
        PlayerSettings settings = (PlayerSettings) player;
        
        if (!player.hasSkin() || player.isInvisible())
        {
            return;
        }
        
        if (mc.theWorld == null)
        {
            return;
        }
        
        if (mc.thePlayer.getPositionVector().squareDistanceTo(player.getPositionVector()) > 14 * 14)
        {
            return;
        }
        
        if (settings.getSkinLayers() == null && !setupModel(player, settings))
        {
            return;
        }
        
        renderLayers(player, settings.getSkinLayers(), deltaTick);
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
    
    class Layer
    {
        Supplier<ModelRenderer> vanillaGetter;
        EnumPlayerModelParts modelPart;
        Supplier<Boolean> configGetter;
        boolean mirrored;
        Shape shape;
        int layersId;
        
        public Layer(int layersId, boolean mirrored, EnumPlayerModelParts modelPart, Shape shape, Supplier<ModelRenderer> vanillaGetter, Supplier<Boolean> configGetter)
        {
            this.layersId = layersId;
            this.mirrored = mirrored;
            this.modelPart = modelPart;
            this.shape = shape;
            this.vanillaGetter = vanillaGetter;
            this.configGetter = configGetter;
        }
    }
    
    private enum Shape
    {
        HEAD(0.0F), BODY(0.6F), LEGS(-0.2F), ARMS(0.4F), ARMS_SLIM(0.4F);
        private float yOffsetMagicValue;
        
        private Shape(float yOffsetMagicValue)
        {
            this.yOffsetMagicValue = yOffsetMagicValue;
        }
    }

    public void renderLayers(AbstractClientPlayer abstractClientPlayer, CustomizableModelPart[] layers, float deltaTick)
    {
        if (layers == null)
        {
            return;
        }
        
        float pixelScaling = 1.15F, heightScaling = 1.035F, widthScaling = 1.15F;
        boolean redTint = abstractClientPlayer.hurtTime > 0 || abstractClientPlayer.deathTime > 0;
        
        for (Layer layer : bodyLayers)
        {
            if (abstractClientPlayer.isWearing(layer.modelPart) && !layer.vanillaGetter.get().isHidden && layer.configGetter.get())
            {
                GlStateManager.pushMatrix();
                
                if (abstractClientPlayer.isSneaking())
                {
                    GlStateManager.translate(0.0F, 0.2F, 0.0F);
                }
                
                layer.vanillaGetter.get().postRender(0.0625F);
                
                if (layer.shape == Shape.ARMS)
                {
                    layers[layer.layersId].x = 0.998F * 16;
                }
                
                else if (layer.shape == Shape.ARMS_SLIM)
                {
                    layers[layer.layersId].x = 0.499F * 16;
                }
                
                if (layer.shape == Shape.BODY)
                {
                    widthScaling = 1.05F;
                }
                
                else
                {
                    widthScaling = 1.15F;
                }
                
                if (layer.mirrored)
                {
                    layers[layer.layersId].x *= -1;
                }
                
                GlStateManager.scale(0.0625, 0.0625, 0.0625);
                GlStateManager.scale(widthScaling, heightScaling, pixelScaling);
                layers[layer.layersId].y = layer.shape.yOffsetMagicValue;
                layers[layer.layersId].render(redTint);
                GlStateManager.popMatrix();
            }
        }
    }
    
    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}
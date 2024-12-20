package dev.tr7zw.skinlayers.render;

import java.util.List;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;

public class CustomizableModelPart
{
    private List<CustomizableCube> cubes;
    public boolean visible = true;
    public float x, y, z;
    
    public CustomizableModelPart(List<CustomizableCube> list)
    {
        this.cubes = list;
    }
    
    public void copyFrom(ModelBox modelPart)
    {
        x = modelPart.posX1;
        y = modelPart.posY1;
        z = modelPart.posZ1;
    }
    
    public void setPos(float f, float g, float h)
    {
        x = f;
        y = g;
        z = h;
    }
    
    public void render(boolean redTint)
    {
        if (!visible)
            return;
        
        GlStateManager.pushMatrix();
        translateAndRotate();
        compile(redTint);
        GlStateManager.popMatrix();
    }
    
    public void translateAndRotate()
    {
        GlStateManager.translate((x / 16.0F), (y / 16.0F), (z / 16.0F));
    }
    
    private void compile(boolean redTint)
    {
        for (CustomizableCube cube : this.cubes)
        {
            cube.render(Tessellator.getInstance().getWorldRenderer(), redTint);
        }
    }
}
package dev.tr7zw.skinlayers.render;

import java.util.List;

import com.google.common.collect.Lists;

import dev.tr7zw.skinlayers.Direction;

public class CustomizableCubeListBuilder
{
    private List<CustomizableCube> cubes = Lists.newArrayList();
    private int xTexOffs, yTexOffs;
    private boolean mirror;
    
    public static CustomizableCubeListBuilder create()
    {
        return new CustomizableCubeListBuilder();
    }
    
    public CustomizableCubeListBuilder texOffs(int i, int j)
    {
        xTexOffs = i;
        yTexOffs = j;
        return this;
    }
    
    public CustomizableCubeListBuilder mirror(boolean bl)
    {
        mirror = bl;
        return this;
    }
    
    public List<CustomizableCube> getCubes()
    {
        return cubes;
    }
    
    public CustomizableCubeListBuilder addBox(float x, float y, float z, float pixelSize, Direction[] hide)
    {
        int textureSize = 64;
        cubes.add(new CustomizableCube(xTexOffs, yTexOffs, x, y, z, pixelSize, pixelSize, pixelSize, 0, 0, 0, mirror, textureSize, textureSize, hide));
        return this;
    }
}
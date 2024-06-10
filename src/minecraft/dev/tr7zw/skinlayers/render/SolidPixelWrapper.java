package dev.tr7zw.skinlayers.render;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.tr7zw.skinlayers.Direction;
import dev.tr7zw.skinlayers.opengl.NativeImage;

public class SolidPixelWrapper
{
    public static CustomizableModelPart wrapBox(NativeImage natImage, int width, int height, int depth, int textureU, int textureV, boolean topPivot, float rotationOffset)
    {
        List<CustomizableCube> cubes = new ArrayList<>();
        float pixelSize = 1F, staticXOffset = -width / 2F, staticYOffset = topPivot ? rotationOffset : -height + rotationOffset, staticZOffset = -depth / 2F;
        
        for (int u = 0; u < width; u++)
        {
            for (int v = 0; v < height; v++)
            {
                addPixel(natImage, cubes, pixelSize, u == 0 || v == 0 || u == width - 1 || v == height - 1, textureU + depth + u, textureV + depth + v, staticXOffset + u, staticYOffset + v, staticZOffset, Direction.SOUTH);
                addPixel(natImage, cubes, pixelSize, u == 0 || v == 0 || u == width - 1 || v == height - 1, textureU + 2 * depth + width + u, textureV + depth + v, staticXOffset + width - 1 - u, staticYOffset + v, staticZOffset + depth - 1, Direction.NORTH);
            }
        }
        
        for (int u = 0; u < depth; u++)
        {
            for (int v = 0; v < height; v++)
            {
                addPixel(natImage, cubes, pixelSize, u == 0 || v == 0 || u == depth - 1 || v == height - 1, textureU - 1 + depth - u, textureV + depth + v, staticXOffset, staticYOffset + v, staticZOffset + u, Direction.EAST);
                addPixel(natImage, cubes, pixelSize, u == 0 || v == 0 || u == depth - 1 || v == height - 1, textureU + depth + width + u, textureV + depth + v, staticXOffset + width - 1F, staticYOffset + v, staticZOffset + u, Direction.WEST);
            }
        }
        
        for (int u = 0; u < width; u++)
        {
            for (int v = 0; v < depth; v++)
            {
                addPixel(natImage, cubes, pixelSize, u == 0 || v == 0 || u == width - 1 || v == depth - 1, textureU + depth + u, textureV + depth - 1 - v, staticXOffset + u, staticYOffset, staticZOffset + v, Direction.UP);
                addPixel(natImage, cubes, pixelSize, u == 0 || v == 0 || u == width - 1 || v == depth - 1, textureU + depth + width + u, textureV + depth - 1 - v, staticXOffset + u, staticYOffset + height - 1F, staticZOffset + v, Direction.DOWN);
            }
        }
        
        return new CustomizableModelPart(cubes);
    }
    
    private static int[][] offsets = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    private static Direction[] hiddenDirN = new Direction[] { Direction.WEST, Direction.EAST, Direction.UP, Direction.DOWN };
    private static Direction[] hiddenDirS = new Direction[] { Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN };
    private static Direction[] hiddenDirW = new Direction[] { Direction.SOUTH, Direction.NORTH, Direction.UP, Direction.DOWN };
    private static Direction[] hiddenDirE = new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.UP, Direction.DOWN };
    private static Direction[] hiddenDirUD = new Direction[] { Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH };
    
    private static void addPixel(NativeImage natImage, List<CustomizableCube> cubes, float pixelSize, boolean onBorder, int u, int v, float x, float y, float z, Direction direction)
    {
        if (natImage.getLuminanceOrAlpha(u, v) != 0)
        {
            Set<Direction> hide = new HashSet<>();
            
            if (!onBorder)
            {
                for (int i = 0; i < offsets.length; i++)
                {
                    int tU = u + offsets[i][1];
                    int tV = v + offsets[i][0];
                    
                    if (tU >= 0 && tU < 64 && tV >= 0 && tV < 64 && natImage.getLuminanceOrAlpha(tU, tV) != 0)
                    {
                        if (direction == Direction.NORTH)
                        {
                            hide.add(hiddenDirN[i]);
                        }
                        
                        if (direction == Direction.SOUTH)
                        {
                            hide.add(hiddenDirS[i]);
                        }
                        
                        if (direction == Direction.EAST)
                        {
                            hide.add(hiddenDirE[i]);
                        }
                        
                        if (direction == Direction.WEST)
                        {
                            hide.add(hiddenDirW[i]);
                        }
                        
                        if (direction == Direction.UP || direction == Direction.DOWN)
                        {
                            hide.add(hiddenDirUD[i]);
                        }
                    }
                }
                
                hide.add(direction);
            }
            
            cubes.addAll(CustomizableCubeListBuilder.create().texOffs(u - 2, v - 1).addBox(x, y, z, pixelSize, hide.toArray(new Direction[hide.size()])).getCubes());
        }
    }

}
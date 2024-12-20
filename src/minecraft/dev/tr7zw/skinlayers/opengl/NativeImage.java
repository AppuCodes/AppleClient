package dev.tr7zw.skinlayers.opengl;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

public final class NativeImage implements AutoCloseable
{
    private int width, height, size;
    private Format format;
    private ByteBuffer buffer;
    
    public NativeImage(int i, int j, boolean bl)
    {
        this(Format.RGBA, i, j, bl);
    }
    
    public NativeImage(Format format, int i, int j, boolean bl)
    {
        if (i <= 0 || j <= 0)
        {
            throw new IllegalArgumentException("Invalid texture size: " + i + "x" + j);
        }
        
        this.format = format;
        width = i;
        height = j;
        size = i * j * format.components();
        buffer = ByteBuffer.allocateDirect(size);
    }
    
    public static void getTexImage(int i, int j, int k, int l, ByteBuffer m)
    {
        GL11.glGetTexImage(i, j, k, l, m);
    }
    
    public static void pixelStore(int i, int j)
    {
        GL11.glPixelStorei(i, j);
    }
    
    public void close()
    {
    }
    
    public int getPixelRGBA(int i, int j)
    {
        int l = (i + j * width) * 4;
        return buffer.getInt(l);
    }
    
    public void setPixelRGBA(int i, int j, int k)
    {
        int l = (i + j * width) * 4;
        buffer.putInt(l, k);
    }
    
    public byte getLuminanceOrAlpha(int i, int j)
    {
        int k = (i + j * width) * format.components() + format.luminanceOrAlphaOffset() / 8;
        return buffer.get(k);
    }
    
    public void downloadTexture(int i, boolean bl)
    {
        format.setPackPixelStoreState();
        getTexImage(3553, i, format.glFormat(), 5121, buffer);
        
        if (bl && format.hasAlpha())
        {
            for (int j = 0; j < height; j++)
            {
                for (int k = 0; k < width; k++)
                {
                    setPixelRGBA(k, j, getPixelRGBA(k, j) | 255 << format.alphaOffset());
                }
            }
        }
    }
    
    public enum Format
    {
        RGBA(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
        RGB(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
        LUMINANCE_ALPHA(2, 33319, false, false, false, true, true, 255, 255, 255, 0, 8, true),
        LUMINANCE(1, 6403, false, false, false, true, false, 0, 0, 0, 0, 255, true);
        private boolean hasRed, hasGreen, hasBlue, hasLuminance, hasAlpha, supportedByStb;
        private int glFormat, components, redOffset, greenOffset, blueOffset, luminanceOffset, alphaOffset;
        
        Format(int j, int k, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, int l, int m, int n, int o, int p, boolean bl6)
        {
            components = j;
            glFormat = k;
            hasRed = bl;
            hasGreen = bl2;
            hasBlue = bl3;
            hasLuminance = bl4;
            hasAlpha = bl5;
            redOffset = l;
            greenOffset = m;
            blueOffset = n;
            luminanceOffset = o;
            alphaOffset = p;
            supportedByStb = bl6;
        }
        
        public int components()
        {
            return components;
        }
        
        public void setPackPixelStoreState()
        {
            pixelStore(3333, components());
        }
        
        public void setUnpackPixelStoreState()
        {
            pixelStore(3317, components());
        }
        
        public int glFormat()
        {
            return glFormat;
        }
        
        public boolean hasAlpha()
        {
            return hasAlpha;
        }
        
        public int alphaOffset()
        {
            return alphaOffset;
        }
        
        public int luminanceOrAlphaOffset()
        {
            return hasLuminance ? luminanceOffset : alphaOffset;
        }
    }
}
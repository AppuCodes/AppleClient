package dev.tr7zw.skinlayers.render;

import org.lwjgl.util.vector.Vector3f;

import dev.tr7zw.skinlayers.Direction;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class CustomizableCube
{
    public float minX, minY, minZ, maxX, maxY, maxZ;
    private int polygonCount = 0;
    private Direction[] hidden;
    private Polygon[] polygons;
    
    public CustomizableCube(int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, float textureWidth, float textureHeight, Direction[] hide)
    {
        hidden = hide;
        minX = x;
        minY = y;
        minZ = z;
        maxX = x + sizeX;
        maxY = y + sizeY;
        maxZ = z + sizeZ;
        polygons = new Polygon[6];
        float pX = x + sizeX;
        float pY = y + sizeY;
        float pZ = z + sizeZ;
        x -= extraX;
        y -= extraY;
        z -= extraZ;
        pX += extraX;
        pY += extraY;
        pZ += extraZ;
        
        if (mirror)
        {
            float i = pX;
            pX = x;
            x = i;
        }
        
        Vertex vertex = new Vertex(x, y, z, 0.0F, 0.0F);
        Vertex vertex2 = new Vertex(pX, y, z, 0.0F, 8.0F);
        Vertex vertex3 = new Vertex(pX, pY, z, 8.0F, 8.0F);
        Vertex vertex4 = new Vertex(x, pY, z, 8.0F, 0.0F);
        Vertex vertex5 = new Vertex(x, y, pZ, 0.0F, 0.0F);
        Vertex vertex6 = new Vertex(pX, y, pZ, 0.0F, 8.0F);
        Vertex vertex7 = new Vertex(pX, pY, pZ, 8.0F, 8.0F);
        Vertex vertex8 = new Vertex(x, pY, pZ, 8.0F, 0.0F);
        float l = u + sizeZ + sizeX;
        float n = u + sizeZ + sizeX + sizeZ;
        float q = v + sizeZ;
        float r = v + sizeZ + sizeY;
        
        if (visibleFace(Direction.DOWN))
        {
            polygons[polygonCount++] = new Polygon(new Vertex[] { vertex6, vertex5, vertex, vertex2 }, l, q, n, r, textureWidth, textureHeight, mirror, Direction.DOWN);
        }
        
        if (visibleFace(Direction.UP))
        {
            polygons[polygonCount++] = new Polygon(new Vertex[] { vertex3, vertex4, vertex8, vertex7 }, l, q, n, r, textureWidth, textureHeight, mirror, Direction.UP);
        }
        
        if (visibleFace(Direction.WEST))
        {
            polygons[polygonCount++] = new Polygon(new Vertex[] { vertex, vertex5, vertex8, vertex4 }, l, q, n, r, textureWidth, textureHeight, mirror, Direction.WEST);
        }
        
        if (visibleFace(Direction.NORTH))
        {
            polygons[polygonCount++] = new Polygon(new Vertex[] { vertex2, vertex, vertex4, vertex3 }, l, q, n, r, textureWidth, textureHeight, mirror, Direction.NORTH);
        }
        
        if (visibleFace(Direction.EAST))
        {
            polygons[polygonCount++] = new Polygon(new Vertex[] { vertex6, vertex2, vertex3, vertex7 }, l, q, n, r, textureWidth, textureHeight, mirror, Direction.EAST);
        }
        
        if (visibleFace(Direction.SOUTH))
        {
            polygons[polygonCount++] = new Polygon(new Vertex[] { vertex5, vertex6, vertex7, vertex8 }, l, q, n, r, textureWidth, textureHeight, mirror, Direction.SOUTH);
        }
    }
    
    private boolean visibleFace(Direction face)
    {
        for (Direction direction : hidden)
        {
            if (direction == face)
            {
                return false;
            }
        }
        
        return true;
    }
    
    public void render(WorldRenderer worldRenderer, boolean redTint)
    {
        redTint = false;
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        Polygon polygon;
        
        for (int id = 0; id < polygonCount; id++)
        {
            polygon = polygons[id];
            
            for (int i = 0; i < 4; i++)
            {
                Vertex vertex = polygon.vertices[i];
                worldRenderer.pos(vertex.pos.x, vertex.pos.y, vertex.pos.z).tex(vertex.u, vertex.v).color(255, redTint ? 127 : 255, redTint ? 127 : 255, 255).normal(polygon.normal.x, polygon.normal.y, polygon.normal.z).endVertex();
            }
        }
        
        Tessellator.getInstance().draw();
    }
    
    private static class Polygon
    {
        public Vertex[] vertices;
        public Vector3f normal;
        
        public Polygon(Vertex[] vertices, float f, float g, float h, float i, float j, float k, boolean bl, Direction direction)
        {
            this.vertices = vertices;
            float l = 0.0F / j;
            float m = 0.0F / k;
            vertices[0] = vertices[0].remap(h / j - l, g / k + m);
            vertices[1] = vertices[1].remap(f / j + l, g / k + m);
            vertices[2] = vertices[2].remap(f / j + l, i / k - m);
            vertices[3] = vertices[3].remap(h / j - l, i / k - m);
            
            if (bl)
            {
                int n = vertices.length;
                
                for (int o = 0; o < n / 2; o++)
                {
                    Vertex vertex = vertices[o];
                    vertices[o] = vertices[n - 1 - o];
                    vertices[n - 1 - o] = vertex;
                }
                
            }
            
            normal = direction.step();
            
            if (bl)
            {
                normal.setX(normal.getX() * -1);
            }
        }
    }
    
    private static class Vertex
    {
        public float u, v, o, p, q;
        public Vector3f pos;
        
        public Vertex(float f, float g, float h, float i, float j)
        {
            this(new Vector3f(f, g, h), i, j);
        }
        
        public Vertex remap(float f, float g)
        {
            return new Vertex(this.pos, f, g);
        }
        
        public Vertex(Vector3f vector3f, float f, float g)
        {
            this.pos = vector3f;
            this.u = f;
            this.v = g;
            o = pos.x / 16.0F;
            p = pos.y / 16.0F;
            q = pos.z / 16.0F;
        }
    }
}
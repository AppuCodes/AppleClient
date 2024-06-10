package dev.tr7zw.skinlayers;

import org.lwjgl.util.vector.Vector3f;

import net.minecraft.util.Vec3i;

public enum Direction
{
    DOWN(new Vec3i(0, -1, 0)), UP(new Vec3i(0, 1, 0)), NORTH(new Vec3i(0, 0, -1)), SOUTH(new Vec3i(0, 0, 1)), WEST(new Vec3i(-1, 0, 0)), EAST(new Vec3i(1, 0, 0));
    private Vec3i normal;
    
    private Direction(Vec3i normal)
    {
        this.normal = normal;
    }
    
    public Vector3f step()
    {
        return new Vector3f(normal.getX(), normal.getY(), normal.getZ());
    }
}
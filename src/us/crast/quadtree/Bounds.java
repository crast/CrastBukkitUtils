package us.crast.quadtree;

import org.bukkit.util.BlockVector;

public class Bounds {
    private final int x;
    private final int y;
    private final int z;
    
    public Bounds(BlockVector vec) {
        x = vec.getBlockX();
        y = vec.getBlockY();
        z = vec.getBlockZ();
    }
    
    public Bounds(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Bounds replaceX(int newX) {
        return new Bounds(newX, y, z);
    }
    
    public Bounds replaceZ(int newZ) {
        return new Bounds(x, y, newZ);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}

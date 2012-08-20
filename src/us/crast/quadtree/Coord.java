package us.crast.quadtree;

import org.bukkit.block.Block;
import org.bukkit.util.BlockVector;

public class Coord {
    private final int x;
    private final int y;
    private final int z;
    
    public Coord(BlockVector vec) {
        this(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
    }
    
    public Coord(Block block) {
        this(block.getX(), block.getY(), block.getZ());
    }
    
    public Coord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Coord replaceX(int newX) {
        return new Coord(newX, y, z);
    }
    
    public Coord replaceZ(int newZ) {
        return new Coord(x, y, newZ);
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
    
    @Override
    public int hashCode() {
        return x ^ y ^ z;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other instanceof Coord) {
            Coord o = (Coord) other;
            return o.getX() == x && o.getY() == y && o.getZ() == z;
        }
        return false;
    }
}

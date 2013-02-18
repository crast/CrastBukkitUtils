package us.crast.quadtree;

import org.bukkit.util.BlockVector;

public interface QuadTreeSegment<T> {
    public T get(BlockVector v);
    
    public void put(Coord vec, T value);
    
    public int getSize();
    
    public QuadTreeSegment<T> makeNew();
}

package us.crast.quadtree;

import java.util.Collection;
import java.util.Map;

/**
 * Abstract class for implementing leaf nodes.
 * 
 * @author James Crasta
 *
 * @param <T>
 */
public abstract class QuadTreeLeafNode<T> extends QuadTreeNode<T> {
    protected QuadTreeLeafNode(QuadTreeInternalNode<T> parent, QuadTreeStorage<T> storage) {
        super(parent, storage);
    }
    
    @Override
    public boolean isLeaf() {
        return true;
    }
    
    /**
     * Leafs don't have children
     */
    @Override
    public QuadTreeNode<T> getChild(Coord vec) {
        return null;
    }
    
    /**
     * Convert me to an internal node, adding all my points to it.
     * @return the new node
     */
    public QuadTreeNode<T> convertToInternalNode() {
        Collection<Coord> points = allPoints();
        int xSum = 0;
        int zSum = 0;
        for (Coord b: points) {
            xSum += b.getX();
            zSum += b.getZ();
        }
        Coord avg = new Coord(xSum / points.size(), 0, zSum / points.size());
        if (parent != null && parent.getChild(avg) != this) {
            throw new IllegalStateException("What the hell?");
        }
        QuadTreeInternalNode<T> newInternal = new QuadTreeInternalNode<T>(parent, storage, avg);
        if (parent != null) parent.putChild(avg, newInternal);
        for (Map.Entry<Coord, T> e : getEntries()) {
            newInternal.put(e.getKey(), e.getValue());
        }
        return newInternal;
    }
    
    /**
     * All points stored in this leaf.
     * @return A collection of points.
     */
    public abstract Collection<Coord> allPoints();
    
    /**
     * All entries stored in this leaf.
     * @return A collection of Map.Entries.
     */
    public abstract Collection<Map.Entry<Coord, T>> getEntries();
                
}

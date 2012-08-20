package us.crast.quadtree;


public abstract class QuadTreeNode<T> {
    protected QuadTreeInternalNode<T> parent;
    protected QuadTreeStorage<T> storage;

    protected QuadTreeNode(QuadTreeInternalNode<T> parent, QuadTreeStorage<T> storage) {
        this.parent = parent;
        this.storage = storage;
    }
    
    protected QuadTreeStorage<T> getStorage() {
        return storage;
    }
    
    public abstract int size();
    
    public abstract QuadTreeNode<T> getChild(Coord vec);
    
    public abstract boolean isLeaf();
    
    public abstract void put(Coord vec, T obj);
    
    public abstract T get(Coord vec);

    public QuadTreeInternalNode<T> getParent() {
        return parent;
    }
	
}
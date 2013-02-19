package us.crast.quadtree;

public class QuadTreeInternalNode<T> extends QuadTreeNode<T> {
    private int pivotX;
    private int pivotZ;
    
    private QuadTreeNode<T> topLeft;
    private QuadTreeNode<T> topRight;
    private QuadTreeNode<T> bottomLeft;
    private QuadTreeNode<T> bottomRight;
    
    public QuadTreeInternalNode(QuadTreeInternalNode<T> parent, QuadTreeStorage<T> storage, Coord pivot) {
        super(parent, storage);
        pivotX = pivot.getX();
        pivotZ = pivot.getZ();
        
        topLeft = storage.makeNode(this);
        topRight = storage.makeNode(this);
        bottomLeft = storage.makeNode(this);
        bottomRight = storage.makeNode(this);
    }
    
    @Override
    public void put(Coord vec, T obj) {
        getChild(vec).put(vec, obj);
    }
    
    @Override
    public QuadTreeNode<T> getChild(Coord vec) {
        if (vec.getX() <= pivotX) {
            if (vec.getZ() <= pivotZ) {
                return bottomLeft;
            } else {
                return topLeft;
            }
        } else {
            if (vec.getZ() <= pivotZ) {
                return bottomRight;
            } else {
                return topRight;
            }
        }
    }
    
    public QuadTreeNode<T> putChild(Coord vec, QuadTreeNode<T> node) {
        if (vec.getX() <= pivotX) {
            if (vec.getZ() <= pivotZ) {
                bottomLeft = node;
            } else {
                topLeft = node;
            }
        } else {
            if (vec.getZ() <= pivotZ) {
                bottomRight = node;
            } else {
                topRight = node;
            }
        }
        return node;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public T get(Coord vec) {
        return getChild(vec).get(vec);
    }
    
    @Override
    public int size() {
        int sum = 0;
        for (QuadTreeNode<T> child : allChildren()) {
            sum += (child == null)? 0 : child.size();
        }
        return sum;
    }

    @SuppressWarnings("unchecked")
    private QuadTreeNode<T>[] allChildren() {
        return new QuadTreeNode[]{topLeft, bottomLeft, topRight, bottomRight};
    }
    
    public int getPivotX() {
        return pivotX;
    }
    
    public int getPivotZ() {
        return pivotZ;
    }

}

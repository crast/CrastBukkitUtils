package us.crast.quadtree;

import org.bukkit.util.BlockVector;

public class QuadTreeNode<T> {
	private QuadTreeSegment<T> data;

	private QuadTreeNode<T> topLeft;
	private QuadTreeNode<T> topRight;
	private QuadTreeNode<T> bottomLeft;
	private QuadTreeNode<T> bottomRight;
	
	private int pivotX;
	private int pivotZ;
	
	private Bounds bound1;
	private Bounds bound2;
	public boolean leaf;
	
	public QuadTreeNode(QuadTreeSegment<T> data, Bounds a, Bounds b) {
	    bound1 = a;
	    bound2 = b;
	    pivotX = (a.getX() + b.getX()) / 2;
	    pivotZ = (a.getZ() + b.getZ()) / 2;
	}
	
	public QuadTreeNode(QuadTreeSegment<T> data, BlockVector a, BlockVector b) {
		this(data, new Bounds(a), new Bounds(b));
	}

	public void setData(QuadTreeSegment<T> data) {
		this.data = data;
		this.leaf = (data != null);
	}

    public QuadTreeSegment<T> getData() {
        return data;
    }
    
    public void put(BlockVector vec, T obj) {
    	if (leaf) {
    	    this.data.put(vec, obj);
    	} else {
    		QuadTreeNode<T> node = getChild(vec);
    		if (node == null) node = addChild(vec);
    	}
    }

    public QuadTreeNode<T> getChild(BlockVector vec) {
    	if (vec.getBlockX() <= pivotX) {
    	    if (vec.getBlockZ() <= pivotZ) {
    	        return bottomLeft;
    	    } else {
    	    	return topLeft;
    	    }
    	} else {
    		if (vec.getBlockZ() <= pivotZ) {
    			return bottomRight;
    		} else {
    			return topRight;
    		}
    	}
    }
    
    public QuadTreeNode<T> addChild(BlockVector vec) {
    	QuadTreeNode<T> node;
    	if (vec.getBlockX() <= pivotX) {
    		if (vec.getBlockZ() <= pivotZ) {
        	    bottomLeft = node = new QuadTreeNode<T>(
        	            data.makeNew(),
        	            bound1,
        	            bound2.replaceX(pivotX).replaceZ(pivotZ)
        	    );
    		} else {
    			topLeft = node = new QuadTreeNode<T>(
    			        data.makeNew(),
    					bound1.replaceZ(pivotZ+1),
    					bound2.replaceX(pivotX)
    			);
    		}
    	} else {
            if (vec.getBlockZ() <= pivotZ) {
            	bottomRight = node = new QuadTreeNode<T>(
            	        data.makeNew(),
            			bound1.replaceX(pivotX+1),
            			bound2.replaceZ(pivotZ)
            	);
            } else {
            	topRight = node = new QuadTreeNode<T>(
            	        data.makeNew(),
            			bound1.replaceX(pivotX+1).replaceZ(pivotZ+1),
            			bound2
            	);
            }
    	}
    	return node;
    }
	
}
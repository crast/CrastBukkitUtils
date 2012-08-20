package us.crast.quadtree;

import org.bukkit.util.BlockVector;

public class QuadTree<T> {
	private QuadTreeStorage<T> storage;
	private QuadTreeNode<T> head;
    private QuadTreeConfig config;
    private int counter = 0;

	public QuadTree(QuadTreeStorage<T> storage, QuadTreeConfig config) {
		this.storage = storage;
		this.config = config;
	    this.head = storage.makeNode(null);
	}
	
	public QuadTreeNode<T> findSegment(BlockVector vec) {
	    return findSegment(new Coord(vec));
	}
	
	public QuadTreeNode<T> findSegment(Coord vec) {
		QuadTreeNode<T> node = head;
		while (!node.isLeaf()) {
		    node = node.getChild(vec);
		}
		return node;
	}
	
	public void put(Coord vec, T value) {
	    head.put(vec, value);
	    // do resize check
	    if (++counter >= config.getResizeCheckFrequency()) {
	        counter = 0;
	        tryResize(findSegment(vec));
	    }
	}
	
	public T get(Coord vec) {
	    return head.get(vec);
	}
	
	private void tryResize(QuadTreeNode<T> node) {
	    // If we're big enough, convert this leaf to an internal node.
        if (node.size() > config.getMaxInnerElements()) {
            QuadTreeLeafNode<T> leaf = (QuadTreeLeafNode<T>) node;
            QuadTreeNode<T> newNode = leaf.convertToInternalNode();
            if (node == head) {
                head = newNode;
            }
        }
    }

    public void close() {
		this.storage.close();
		this.storage = null;
	}

    public QuadTreeNode<T> getHead() {
        return head;
    }
}

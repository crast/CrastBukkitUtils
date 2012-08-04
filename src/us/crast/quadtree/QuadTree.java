package us.crast.quadtree;

import org.bukkit.util.BlockVector;

public class QuadTree<T> {
	private QuadTreeStorage<T> storage;
	
	private QuadTreeNode<T> head;
    private QuadTreeConfig config;

	public QuadTree(QuadTreeStorage<T> storage, QuadTreeConfig config) {
		this.storage = storage;
		this.config = config;
	}
	
	public QuadTreeSegment<T> findSegment(BlockVector vec) {
		QuadTreeNode<T> node = null;
		QuadTreeNode<T> child = head;
		while (child != null) {
			node = child;
		    child = node.getChild(vec);
		}
		return node.getData();
	}
	
	public void put() {
		
	}
	
	public void close() {
		this.storage.close();
		this.storage = null;
	}
}

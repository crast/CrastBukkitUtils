package us.crast.quadtree;

public interface QuadTreeStorage<T> {
    public QuadTreeLeafNode<T> makeNode(QuadTreeInternalNode<T> parent);

    void close();

}

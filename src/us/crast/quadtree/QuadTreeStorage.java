package us.crast.quadtree;

public interface QuadTreeStorage<T> {
    public T makeSegment(QuadTreeNode<T> node);

    void close();

}

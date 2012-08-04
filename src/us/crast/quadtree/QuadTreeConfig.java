package us.crast.quadtree;

public class QuadTreeConfig {
    private int smallestBoundX = 1;
    private int smallestBoundZ = 1;
    private int maxInnerElements = -1;

    public QuadTreeConfig() { 
    }
    
    QuadTreeConfig setSmallestBound(int value) {
        this.smallestBoundX = this.smallestBoundZ = value;
        return this;
    }
    
    QuadTreeConfig setSmallestBoundX(int value) {
        this.smallestBoundX = value;
        return this;
    }
    
    QuadTreeConfig setMaxInnerElements(int value) {
        this.maxInnerElements = value;
        return this;
    }
    
    public int getSmallestBoundX() {
        return smallestBoundX;
    }
    
    public int getSmallestBoundZ() {
        return smallestBoundZ;
    }

    public int getMaxInnerElements() {
        return maxInnerElements;
    }
}

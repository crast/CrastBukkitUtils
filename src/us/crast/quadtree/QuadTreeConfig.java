package us.crast.quadtree;

public class QuadTreeConfig {
    private int smallestBoundX = 1;
    private int smallestBoundZ = 1;
    private int maxInnerElements = Integer.MAX_VALUE;
    private int resizeCheckFrequency = -1;

    public QuadTreeConfig() { 
    }
    
    public QuadTreeConfig setSmallestBound(int value) {
        this.smallestBoundX = this.smallestBoundZ = value;
        return this;
    }
    
    public QuadTreeConfig setSmallestBoundX(int value) {
        this.smallestBoundX = value;
        return this;
    }
    
    public QuadTreeConfig setMaxInnerElements(int value) {
        this.maxInnerElements = value;
        if (resizeCheckFrequency == -1) {
            resizeCheckFrequency = value;
        }
        return this;
    }
    
    public QuadTreeConfig setResizeCheckFrequency(int value) {
        resizeCheckFrequency = value;
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

    public int getResizeCheckFrequency() {
        return resizeCheckFrequency;
    }
}

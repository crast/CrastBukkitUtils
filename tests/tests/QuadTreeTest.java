package tests;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import us.crast.quadtree.Coord;
import us.crast.quadtree.QuadTree;
import us.crast.quadtree.QuadTreeConfig;
import us.crast.quadtree.QuadTreeInternalNode;
import us.crast.quadtree.QuadTreeLeafNode;
import us.crast.quadtree.QuadTreeNode;
import us.crast.quadtree.QuadTreeStorage;

import static org.junit.Assert.*;
import org.junit.Test;

public class QuadTreeTest {
    static AtomicInteger f = new AtomicInteger();
    Coord ZERO = new Coord(0,0,0);
    private QuadTreeConfig config = new QuadTreeConfig().setMaxInnerElements(4).setResizeCheckFrequency(1);
    
    @Test
    public void testBasic() {
        QuadTree<Value> q = new QuadTree<Value>(new Storage(), config);
        assertNull(q.get(new Coord(4, 7, 11)));
        Value testValue = new Value();
        q.put(new Coord(4, 5, 5), testValue);
        assertSame(testValue, q.get(new Coord(4, 5, 5)));
        q.put(new Coord(4, 5, 5), null);
        assertNull(q.get(new Coord(4, 5, 5)));
    }

    
    @Test
    public void testRootResize() {
        QuadTree<Value> q = new QuadTree<Value>(new Storage(), config);
        
        QuadTreeNode<Value> head = q.getHead();
        assertSame(head, q.findSegment(ZERO));
        assertTrue(q.getHead() instanceof QuadTreeLeafNode);
        put1(q);
        
        // The tree ought to rebalance now, because we're beyond 
        put2(q);
        assertNotSame(head, q.findSegment(ZERO));
        assertTrue(q.getHead() instanceof QuadTreeInternalNode);
        
        
        QuadTreeInternalNode<Value> iHead = q.getHead().getChild(ZERO).getParent();
        assertSame(q.getHead(), iHead);
        assertEquals(0, iHead.getPivotX());
        assertEquals(3, iHead.getPivotZ());
        
        assertEquals(1, q.getHead().getChild(new Coord(1, 0, -5)).size());
        assertEquals(2, q.getHead().getChild(new Coord(4, 0, 18)).size());
        assertEquals(1, q.getHead().getChild(new Coord(-4, 0, -20)).size());
        assertEquals(1, q.getHead().getChild(new Coord(-1, 0, 4)).size());
        
        q.put(new Coord(14, 0, -30), new Value());
        q.put(new Coord(25, 0, -50), new Value());
        assertEquals(3, q.getHead().getChild(new Coord(1, 0, -5)).size());
        
    }
    
    @Test
    public void testInternalResize() {
        QuadTree<Value> q = new QuadTree<Value>(new Storage(), config);
        put1(q);
        put2(q);
        assertEquals(5, q.getHead().size());
        Coord leafCheck = new Coord(1, 0, -5);
        assertTrue(q.getHead().getChild(leafCheck).isLeaf());
        q.put(new Coord(14, 0, -30), new Value());
        q.put(new Coord(25, 0, -50), new Value());
        q.put(new Coord(18, 0, -100), new Value());
        Value test = new Value();
        q.put(leafCheck, test);
        assertSame(test, q.get(leafCheck));
        assertFalse(q.getHead().getChild(leafCheck).isLeaf());
        
    }
    
    private void put1(QuadTree<Value> q) {
        q.put(new Coord(4, 17, 18), new Value());
        q.put(new Coord(4, 17, 19), new Value());
        q.put(new Coord(-4, 5, -20), new Value());
    }
    
    private void put2(QuadTree<Value> q) {
        q.put(new Coord(4, 5, -20), new Value());
        q.put(new Coord(-12, 5, 20), new Value());
    }
    
    class Value {
        int i = f.incrementAndGet();
        
        public String toString() {
            return "VALUE" + i;
        }
    }
    class Storage implements QuadTreeStorage<Value> {

        @Override
        public QuadTreeLeafNode<Value> makeNode(QuadTreeInternalNode<Value> parent) {
            return new LeafNode(parent, this);
        }

        @Override
        public void close() {
            // TODO Auto-generated method stub
            
        }
        
    }
    class LeafNode extends QuadTreeLeafNode<Value> {
        private HashMap<Coord, Value> data = new HashMap<Coord, Value>();

        protected LeafNode(QuadTreeInternalNode<Value> parent, QuadTreeStorage<Value> storage) {
            super(parent, storage);
        }

        @Override
        public Collection<Coord> allPoints() {
            return data.keySet();
        }

        @Override
        public Collection<Entry<Coord, Value>> getEntries() {
            return data.entrySet();
        }

        @Override
        public int size() {
            return data.size();
        }

        @Override
        public void put(Coord vec, Value obj) {
            data.put(vec, obj);
        }

        @Override
        public Value get(Coord vec) {
            return data.get(vec);
        }
        
    }
}

package us.crast.datastructures;

public interface ObjectMaker<V> {
	public V build(Object key);	
}

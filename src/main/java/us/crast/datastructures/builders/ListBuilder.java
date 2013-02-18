package us.crast.datastructures.builders;

import java.util.List;

import us.crast.datastructures.ObjectMaker;

public final class ListBuilder<T> implements ObjectMaker<List<T>> {
	@Override
	public List<T> build(Object key) {
		return new java.util.ArrayList<T>();
	}
}
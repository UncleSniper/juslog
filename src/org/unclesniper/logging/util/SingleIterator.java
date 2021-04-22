package org.unclesniper.logging.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingleIterator<T> implements Iterator<T> {

	private T object;

	private boolean had;

	public SingleIterator(T object) {
		this.object = object;
	}

	@Override
	public boolean hasNext() {
		return !had;
	}

	@Override
	public T next() {
		if(had)
			throw new NoSuchElementException();
		T o = object;
		had = true;
		return o;
	}

}

package org.unclesniper.logging.util;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MultiIterator<T> implements Iterator<T> {

	private final Iterator<Iterable<? extends T>> outer;

	private Iterator<? extends T> inner;

	public MultiIterator(Iterable<Iterable<? extends T>> subjects) {
		if(subjects == null)
			throw new IllegalArgumentException("Subject collection cannot be null");
		outer = subjects.iterator();
		advanceOuter();
	}

	@SafeVarargs
	public MultiIterator(Iterable<? extends T>... subjects) {
		if(subjects == null)
			throw new IllegalArgumentException("Subject array cannot be null");
		List<Iterable<? extends T>> s = new ArrayList<Iterable<? extends T>>(subjects.length);
		for(Iterable<? extends T> subject : subjects)
			s.add(subject);
		outer = s.iterator();
		advanceOuter();
	}

	private void advanceOuter() {
		for(;;) {
			if(!outer.hasNext()) {
				inner = null;
				break;
			}
			Iterable<? extends T> piece = outer.next();
			if(piece == null)
				continue;
			inner = piece.iterator();
			if(inner.hasNext())
				break;
		}
	}

	@Override
	public boolean hasNext() {
		return inner != null;
	}

	@Override
	public T next() {
		if(inner == null)
			throw new NoSuchElementException();
		T element = inner.next();
		if(!inner.hasNext())
			advanceOuter();
		return element;
	}

}

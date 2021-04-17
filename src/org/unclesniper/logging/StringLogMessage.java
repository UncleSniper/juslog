package org.unclesniper.logging;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StringLogMessage extends AbstractLogMessage implements Iterable<String> {

	private static final class StringIterator implements Iterator<String> {

		private String message;

		public StringIterator(String message) {
			this.message = message;
		}

		@Override
		public boolean hasNext() {
			return message != null;
		}

		@Override
		public String next() {
			if(message == null)
				throw new NoSuchElementException();
			String m = message;
			message = null;
			return m;
		}

	}

	private final String message;

	public StringLogMessage(String message) {
		if(message == null)
			throw new IllegalArgumentException("Log message cannot be null");
		this.message = message;
	}

	@Override
	public Iterable<String> toLogMessageLines() {
		return this;
	}

	@Override
	public Iterator<String> iterator() {
		return new StringIterator(message);
	}

}

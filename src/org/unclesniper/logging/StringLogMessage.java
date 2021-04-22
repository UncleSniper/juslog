package org.unclesniper.logging;

import java.util.Iterator;
import org.unclesniper.logging.util.SingleIterator;

public class StringLogMessage extends AbstractLogMessage implements Iterable<String> {

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
		return new SingleIterator<String>(message);
	}

}

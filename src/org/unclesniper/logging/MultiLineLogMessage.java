package org.unclesniper.logging;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MultiLineLogMessage extends AbstractLogMessage
		implements Iterable<String>, ExceptionBearingLogMessage {

	private static final class MultiLineIterator implements Iterator<String> {

		private final Iterator<String> lines;

		private final ExceptionLineGenerator exception;

		public MultiLineIterator(Iterable<String> lines, Throwable exception) {
			this.lines = lines.iterator();
			this.exception = exception == null ? null : new ExceptionLineGenerator(exception);
		}

		@Override
		public boolean hasNext() {
			return lines.hasNext() || (exception != null && exception.generatesMoreLines());
		}

		@Override
		public String next() {
			if(lines.hasNext())
				return lines.next();
			if(exception == null || !exception.generatesMoreLines())
				throw new NoSuchElementException();
			return exception.generateNextLine();
		}

	}

	private final List<String> lines = new LinkedList<String>();

	private final Throwable exception;

	public MultiLineLogMessage(Iterable<String> lines) {
		this(null, lines);
	}

	public MultiLineLogMessage(Throwable exception, Iterable<String> lines) {
		if(lines == null)
			throw new IllegalArgumentException("Log messages cannot be null");
		for(String line : lines) {
			if(line != null)
				this.lines.add(line);
		}
		if(this.lines.isEmpty())
			throw new IllegalArgumentException("At least one log message line is required");
		this.exception = exception;
	}

	public MultiLineLogMessage(String... lines) {
		this(null, lines);
	}

	public MultiLineLogMessage(Throwable exception, String... lines) {
		if(lines == null)
			throw new IllegalArgumentException("Log messages cannot be null");
		for(String line : lines) {
			if(line != null)
				this.lines.add(line);
		}
		if(this.lines.isEmpty())
			throw new IllegalArgumentException("At least one log message line is required");
		this.exception = exception;
	}

	@Override
	public Iterable<String> toLogMessageLines() {
		return this;
	}

	@Override
	public Iterator<String> iterator() {
		return new MultiLineIterator(lines, exception);
	}

	@Override
	public Throwable getLogMessageException() {
		return exception;
	}

}

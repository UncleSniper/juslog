package org.unclesniper.logging;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StringAndExceptionLogMessage extends AbstractLogMessage
		implements Iterable<String>, ExceptionBearingLogMessage {

	private static final class StringAndExceptionIterator implements Iterator<String> {

		private String message;

		private final ExceptionLineGenerator exception;

		public StringAndExceptionIterator(String message, Throwable exception) {
			this.message = message;
			this.exception = exception == null ? null : new ExceptionLineGenerator(exception);
		}

		@Override
		public boolean hasNext() {
			return message != null || (exception != null && exception.generatesMoreLines());
		}

		@Override
		public String next() {
			if(message != null) {
				String m = message;
				message = null;
				return m;
			}
			if(exception == null || !exception.generatesMoreLines())
				throw new NoSuchElementException();
			return exception.generateNextLine();
		}

	}

	private final String message;

	private final Throwable exception;

	public StringAndExceptionLogMessage(String message, Throwable exception) {
		if(message == null)
			throw new IllegalArgumentException("Log message cannot be null");
		this.message = message;
		this.exception = exception;
	}

	@Override
	public Iterable<String> toLogMessageLines() {
		return this;
	}

	@Override
	public Iterator<String> iterator() {
		return new StringAndExceptionIterator(message, exception);
	}

	@Override
	public Throwable getLogMessageException() {
		return exception;
	}

}

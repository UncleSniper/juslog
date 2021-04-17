package org.unclesniper.logging;

public class MisconfiguredLoggerException extends RuntimeException {

	public MisconfiguredLoggerException(String message) {
		super(message);
	}

	public MisconfiguredLoggerException(String message, Throwable cause) {
		super(message, cause);
	}

}

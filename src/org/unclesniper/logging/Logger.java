package org.unclesniper.logging;

public interface Logger {

	void log(LogLevel level, LogSource source, LogMessage message);

	default void closeLogger() {}

	default String getLoggerName() {
		return Logger.getDefaultName(this);
	}

	default void log(LogLevel level, LogSource source, String message) {
		this.log(level, source, (LogMessage)new StringLogMessage(message));
	}

	default void log(LogLevel level, LogSource source, Throwable exception, String message) {
		this.log(level, source, (LogMessage)new StringAndExceptionLogMessage(message, exception));
	}

	default void log(LogLevel level, LogSource source, Iterable<String> message) {
		this.log(level, source, (LogMessage)new MultiLineLogMessage(message));
	}

	default void log(LogLevel level, LogSource source, Throwable exception, Iterable<String> message) {
		this.log(level, source, (LogMessage)new MultiLineLogMessage(exception, message));
	}

	default void log(LogLevel level, LogSource source, String... message) {
		this.log(level, source, (LogMessage)new MultiLineLogMessage(message));
	}

	default void log(LogLevel level, LogSource source, Throwable exception, String... message) {
		this.log(level, source, (LogMessage)new MultiLineLogMessage(exception, message));
	}

	default void logf(LogLevel level, LogSource source, String message, Object... args) {
		if(message == null)
			throw new IllegalArgumentException("Log message format string cannot be null");
		this.log(level, source, (LogMessage)new StringLogMessage(String.format(message, args)));
	}

	default void logf(LogLevel level, LogSource source, Throwable exception, String message, Object... args) {
		if(message == null)
			throw new IllegalArgumentException("Log message format string cannot be null");
		this.log(level, source,
				(LogMessage)new StringAndExceptionLogMessage(String.format(message, args), exception));
	}

	public static String getDefaultName(Logger logger) {
		return "<anonymous logger of type " + logger.getClass().getName() + '>';
	}

}

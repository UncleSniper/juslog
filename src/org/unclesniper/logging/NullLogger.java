package org.unclesniper.logging;

public class NullLogger implements Logger {

	public static final Logger instance = new NullLogger();

	@Override
	public void log(LogLevel level, LogSource source, LogMessage message) {}

	@Override
	public void log(LogLevel level, LogSource source, String message) {}

	@Override
	public void log(LogLevel level, LogSource source, Throwable exception, String message) {}

	@Override
	public void log(LogLevel level, LogSource source, Iterable<String> message) {}

	@Override
	public void log(LogLevel level, LogSource source, Throwable exception, Iterable<String> message) {}

	@Override
	public void log(LogLevel level, LogSource source, String... message) {}

	@Override
	public void log(LogLevel level, LogSource source, Throwable exception, String... message) {}

	@Override
	public void logf(LogLevel level, LogSource source, String message, Object... args) {}

	@Override
	public void logf(LogLevel level, LogSource source, Throwable exception, String message, Object... args) {}

}

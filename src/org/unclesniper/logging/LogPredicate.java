package org.unclesniper.logging;

public interface LogPredicate {

	boolean shouldLog(LogLevel level, LogSource source, LogMessage message);

}

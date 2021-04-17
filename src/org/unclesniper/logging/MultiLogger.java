package org.unclesniper.logging;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

public class MultiLogger extends AbstractLogger {

	private final List<Logger> loggers = new LinkedList<Logger>();

	public MultiLogger() {}

	public List<Logger> getLoggers() {
		return Collections.unmodifiableList(loggers);
	}

	public void addLogger(Logger logger) {
		if(logger != null)
			loggers.add(logger);
	}

	public boolean removeLogger(Logger logger) {
		return loggers.remove(logger);
	}

	@Override
	public void log(LogLevel level, LogSource source, LogMessage message) {
		for(Logger logger : loggers)
			logger.log(level, source, message);
	}

	@Override
	public void closeLogger() {
		for(Logger logger : loggers)
			logger.closeLogger();
	}

}

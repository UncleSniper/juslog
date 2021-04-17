package org.unclesniper.logging;

public abstract class AbstractLogger implements Logger {

	private String loggerName;

	public AbstractLogger() {}

	public AbstractLogger(String loggerName) {
		this.loggerName = loggerName;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	protected String getDefaultLoggerName() {
		return Logger.getDefaultName(this);
	}

	@Override
	public String getLoggerName() {
		return loggerName == null ? getDefaultLoggerName() : '\'' + loggerName + '\'';
	}

}

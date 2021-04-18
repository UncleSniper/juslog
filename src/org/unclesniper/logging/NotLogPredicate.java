package org.unclesniper.logging;

public class NotLogPredicate implements LogPredicate {

	private LogPredicate predicate;

	public NotLogPredicate() {}

	public NotLogPredicate(LogPredicate predicate) {
		this.predicate = predicate;
	}

	public LogPredicate getPredicate() {
		return predicate;
	}

	public void setPredicate(LogPredicate predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean shouldLog(LogLevel level, LogSource source, LogMessage message) {
		return predicate != null && !predicate.shouldLog(level, source, message);
	}

}

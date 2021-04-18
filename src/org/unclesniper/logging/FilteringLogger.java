package org.unclesniper.logging;

public class FilteringLogger extends AbstractLogger {

	private Logger target;

	private LogPredicate predicate;

	public FilteringLogger() {}

	public FilteringLogger(Logger target) {
		this(target, null);
	}

	public FilteringLogger(LogPredicate predicate) {
		this(null, predicate);
	}

	public FilteringLogger(Logger target, LogPredicate predicate) {
		this.target = target;
		this.predicate = predicate;
	}

	public Logger getTarget() {
		return target;
	}

	public void setTarget(Logger target) {
		this.target = target;
	}

	public LogPredicate getPredicate() {
		return predicate;
	}

	public void setPredicate(LogPredicate predicate) {
		this.predicate = predicate;
	}

	@Override
	public void log(LogLevel level, LogSource source, LogMessage message) {
		if(target == null)
			throw new MisconfiguredLoggerException("No target logger has been configured");
		if(predicate == null || predicate.shouldLog(level, source, message))
			target.log(level, source, message);
	}

	@Override
	public void closeLogger() {
		if(target != null)
			target.closeLogger();
	}

}

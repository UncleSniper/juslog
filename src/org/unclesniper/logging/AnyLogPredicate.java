package org.unclesniper.logging;

public class AnyLogPredicate extends AbstractMultiLogPredicate {

	public AnyLogPredicate() {}

	@Override
	public boolean shouldLog(LogLevel level, LogSource source, LogMessage message) {
		for(LogPredicate predicate : predicates) {
			if(predicate.shouldLog(level, source, message))
				return true;
		}
		return false;
	}

}

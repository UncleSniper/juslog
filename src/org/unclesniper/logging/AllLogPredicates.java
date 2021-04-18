package org.unclesniper.logging;

public class AllLogPredicates extends AbstractMultiLogPredicate {

	public AllLogPredicates() {}

	@Override
	public boolean shouldLog(LogLevel level, LogSource source, LogMessage message) {
		for(LogPredicate predicate : predicates) {
			if(!predicate.shouldLog(level, source, message))
				return false;
		}
		return true;
	}

}

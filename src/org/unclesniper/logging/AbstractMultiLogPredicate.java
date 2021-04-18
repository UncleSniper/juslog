package org.unclesniper.logging;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

public abstract class AbstractMultiLogPredicate implements LogPredicate {

	protected final List<LogPredicate> predicates = new LinkedList<LogPredicate>();

	public AbstractMultiLogPredicate() {}

	public List<LogPredicate> getPredicates() {
		return Collections.unmodifiableList(predicates);
	}

	public void addPredicate(LogPredicate predicate) {
		if(predicate != null)
			predicates.add(predicate);
	}

	public boolean removePredicate(LogPredicate predicate) {
		return predicates.remove(predicate);
	}

}

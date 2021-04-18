package org.unclesniper.logging;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import org.unclesniper.logging.util.OrderConstraint;

public class LevelLogPredicate implements LogPredicate {

	private OrderConstraint relation = OrderConstraint.GREATER_OR_EQUAL;

	private LogLevel level;

	private final List<LogLevelParser> levelParsers = new LinkedList<LogLevelParser>();

	public LevelLogPredicate() {}

	public LevelLogPredicate(OrderConstraint relation) {
		this(relation, null);
	}

	public LevelLogPredicate(LogLevel level) {
		this(null, level);
	}

	public LevelLogPredicate(OrderConstraint relation, LogLevel level) {
		this.relation = relation;
		this.level = level;
	}

	public OrderConstraint getRelation() {
		return relation;
	}

	public void setRelation(OrderConstraint relation) {
		this.relation = relation == null ? OrderConstraint.GREATER_OR_EQUAL : relation;
	}

	public LogLevel getLevel() {
		return level;
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}

	public void setLevel(int level) {
		this.level = new NumericLogLevel(level);
	}

	public void setLevel(String level) {
		if(level == null) {
			this.level = null;
			return;
		}
		for(LogLevelParser parser : levelParsers) {
			LogLevel parsed = parser.parseLogLevel(level);
			if(parsed != null) {
				this.level = parsed;
				return;
			}
		}
		throw new IllegalArgumentException("No log level parser could parse string representation of log level: "
				+ level);
	}

	public List<LogLevelParser> getLevelParsers() {
		return Collections.unmodifiableList(levelParsers);
	}

	public void addLevelParser(LogLevelParser parser) {
		if(parser != null)
			levelParsers.add(parser);
	}

	public boolean removeLevelParser(LogLevelParser parser) {
		return levelParsers.remove(parser);
	}

	public void clearLevelParsers() {
		levelParsers.clear();
	}

	@Override
	public boolean shouldLog(LogLevel level, LogSource source, LogMessage message) {
		if(level == null)
			throw new IllegalArgumentException("Log level cannot be null");
		if(this.level == null)
			return false;
		int mine = this.level.toNumericalLogLevel(), theirs = level.toNumericalLogLevel();
		switch(relation) {
			case LESS:
				return theirs < mine;
			case LESS_OR_EQUAL:
				return theirs <= mine;
			case EQUAL:
				return theirs == mine;
			case GREATER_OR_EQUAL:
				return theirs >= mine;
			case GREATER:
				return theirs > mine;
			default:
				throw new Doom("Unrecognized order relation: " + relation.name());
		}
	}

}

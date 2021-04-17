package org.unclesniper.logging;

public enum DefaultLogLevel implements LogLevel {

	DEBUG,
	CONFIG,
	INFO,
	WARNING,
	ERROR,
	FATAL;

	static {
		int maxLength = 0;
		for(DefaultLogLevel level : DefaultLogLevel.values()) {
			int length = level.name().length();
			if(length > maxLength)
				maxLength = length;
		}
		for(DefaultLogLevel level : DefaultLogLevel.values()) {
			StringBuilder aname = new StringBuilder(level.name());
			for(int length = aname.length(); length < maxLength; ++length)
				aname.append(' ');
			level.adjustedName = aname.toString();
		}
	}

	private String adjustedName;

	@Override
	public int toNumericalLogLevel() {
		return ordinal();
	}

	@Override
	public String toStringLogLevel(boolean adjusted) {
		return adjusted ? adjustedName : name();
	}

}

package org.unclesniper.logging;

public interface LogLevel {

	int toNumericalLogLevel();

	String toStringLogLevel(boolean adjusted);

}

package org.unclesniper.logging;

public class NumericLogLevel implements LogLevel {

	private int value;

	private String name;

	public NumericLogLevel() {
		name = "0";
	}

	public NumericLogLevel(int value) {
		this.value = value;
		name = String.valueOf(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		name = String.valueOf(value);
	}

	@Override
	public int toNumericalLogLevel() {
		return value;
	}

	@Override
	public String toStringLogLevel(boolean adjusted) {
		return name;
	}

}

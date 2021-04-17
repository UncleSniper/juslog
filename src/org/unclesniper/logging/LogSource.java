package org.unclesniper.logging;

public interface LogSource {

	String toStringLogSource();

	public static LogSource in(Class<?> clazz) {
		return new ClassLogSource(clazz);
	}

	public static LogSource in(Class<?> clazz, String methodName) {
		return new ClassLogSource(clazz, methodName);
	}

}

package org.unclesniper.logging;

public class ClassLogSource implements LogSource {

	private final String className;

	private final String methodName;

	public ClassLogSource(String className) {
		this(className, null);
	}

	public ClassLogSource(String className, String methodName) {
		if(className == null)
			throw new IllegalArgumentException("Class name cannot be null");
		if(className.length() == 0)
			throw new IllegalArgumentException("Class name cannot be empty");
		this.className = className;
		if(methodName != null && methodName.length() == 0)
			methodName = null;
		this.methodName = methodName;
	}

	public ClassLogSource(Class<?> clazz) {
		this(clazz, null);
	}

	public ClassLogSource(Class<?> clazz, String methodName) {
		if(clazz == null)
			throw new IllegalArgumentException("Class cannot be null");
		className = clazz.getName();
		if(methodName != null && methodName.length() == 0)
			methodName = null;
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	@Override
	public String toStringLogSource() {
		if(methodName == null)
			return className;
		StringBuilder builder = new StringBuilder(className);
		builder.append('.');
		builder.append(methodName);
		builder.append("()");
		return builder.toString();
	}

}

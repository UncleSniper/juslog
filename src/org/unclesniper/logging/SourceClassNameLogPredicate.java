package org.unclesniper.logging;

import java.util.regex.Pattern;

public class SourceClassNameLogPredicate implements LogPredicate {

	private String className;

	private Pattern pattern;

	public SourceClassNameLogPredicate() {}

	public SourceClassNameLogPredicate(String className) {
		setClassName(className);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		if(className == null)
			pattern = null;
		else
			pattern = SourceClassNameLogPredicate.makePattern(className);
		this.className = className;
	}

	@Override
	public boolean shouldLog(LogLevel level, LogSource source, LogMessage message) {
		if(pattern == null || !(source instanceof ClassNameBearingLogSource))
			return false;
		String className = ((ClassNameBearingLogSource)source).getClassName();
		return pattern.matcher(className).matches();
	}

	private static Pattern makePattern(String className) {
		StringBuilder builder = new StringBuilder();
		int old = 0, pos, length = className.length();
		while((pos = className.indexOf('*', old)) >= 0) {
			if(pos > old)
				builder.append(Pattern.quote(className.substring(old, pos)));
			if(pos < length - 1 && className.charAt(pos + 1) == '*') {
				builder.append(".*");
				old = pos + 2;
			}
			else {
				builder.append("[^.]*");
				old = pos + 1;
			}
		}
		if(old < length)
			builder.append(Pattern.quote(className.substring(old)));
		return Pattern.compile(builder.toString());
	}

}

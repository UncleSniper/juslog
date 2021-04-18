package org.unclesniper.logging;

public class SourcePackageNameLogPredicate implements LogPredicate {

	private String prefix;

	public SourcePackageNameLogPredicate() {}

	public SourcePackageNameLogPredicate(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		if(prefix != null) {
			int length = prefix.length();
			if(length > 0 && prefix.charAt(length - 1) != '.')
				prefix += '.';
		}
		this.prefix = prefix;
	}

	@Override
	public boolean shouldLog(LogLevel level, LogSource source, LogMessage message) {
		if(prefix == null || !(source instanceof ClassNameBearingLogSource))
			return false;
		String className = ((ClassNameBearingLogSource)source).getClassName();
		return className != null && className.startsWith(prefix);
	}

}

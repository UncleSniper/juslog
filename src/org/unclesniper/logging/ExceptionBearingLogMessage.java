package org.unclesniper.logging;

public interface ExceptionBearingLogMessage extends LogMessage {

	Throwable getLogMessageException();

}

package org.unclesniper.logging;

import java.util.Date;

public abstract class AbstractLogMessage implements LogMessage {

	private final Date timestamp = new Date();

	public AbstractLogMessage() {}

	@Override
	public Date getLogMessageTimestamp() {
		return timestamp;
	}

}

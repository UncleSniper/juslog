package org.unclesniper.logging;

import java.util.Date;

public interface LogMessage {

	Iterable<String> toLogMessageLines();

	Date getLogMessageTimestamp();

}

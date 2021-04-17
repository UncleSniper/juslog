package org.unclesniper.logging;

import java.io.Writer;
import java.util.Date;
import java.io.IOException;
import java.util.IllegalFormatException;

public class WriterLogger extends AbstractIOLogger {

	public static final String DEFAULT_EOL_STRING = System.getProperty("line.separator");

	private static final char[] DEFAULT_EOL_CHARS = DEFAULT_EOL_STRING.toCharArray();

	public static final String DEFAULT_HEAD_LINE_FORMAT = "%7$s%1$s%4$s%9$s";

	public static final String DEFAULT_TAIL_LINE_FORMAT = "%8$s%2$s%5$s%9$s";

	public static final String DEFAULT_LEVEL_FORMAT = "[%s] ";

	public static final String DEFAULT_TIMESTAMP_FORMAT = "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS] ";

	public static final String DEFAULT_SOURCE_FORMAT = "%s: ";

	private static volatile String INDENT_STRING = "                ";

	private volatile Writer writer;

	private String endOfLineString = WriterLogger.DEFAULT_EOL_STRING;

	private char[] endOfLineChars = WriterLogger.DEFAULT_EOL_CHARS;

	private boolean adjustLogLevel = true;

	private String headLineFormat;

	private String tailLineFormat;

	private String levelFormat = WriterLogger.DEFAULT_LEVEL_FORMAT;

	private String noLevelFormat = "";

	private String timestampFormat = WriterLogger.DEFAULT_TIMESTAMP_FORMAT;

	private String noTimestampFormat = WriterLogger.DEFAULT_TIMESTAMP_FORMAT;

	private String sourceFormat = WriterLogger.DEFAULT_SOURCE_FORMAT;

	private String noSourceFormat = "";

	public WriterLogger(Writer writer) {
		this.writer = writer;
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public String getEndOfLine() {
		return endOfLineString;
	}

	public void setEndOfLine(String endOfLine) {
		if(endOfLine == null) {
			endOfLineString = WriterLogger.DEFAULT_EOL_STRING;
			endOfLineChars = WriterLogger.DEFAULT_EOL_CHARS;
		}
		else {
			endOfLineString = endOfLine;
			endOfLineChars = endOfLine.toCharArray();
		}
	}

	public boolean isAdjustLogLevel() {
		return adjustLogLevel;
	}

	public void setAdjustLogLevel(boolean adjustLogLevel) {
		this.adjustLogLevel = adjustLogLevel;
	}

	public String getHeadLineFormat() {
		return headLineFormat;
	}

	public void setHeadLineFormat(String headLineFormat) {
		this.headLineFormat = headLineFormat;
	}

	public String getTailLineFormat() {
		return tailLineFormat;
	}

	public void setTailLineFormat(String tailLineFormat) {
		this.tailLineFormat = tailLineFormat;
	}

	public void setLineFormat(String format) {
		headLineFormat = format;
		tailLineFormat = null;
	}

	public String getLevelFormat() {
		return levelFormat;
	}

	public void setLevelFormat(String levelFormat) {
		this.levelFormat = levelFormat == null ? WriterLogger.DEFAULT_LEVEL_FORMAT : levelFormat;
	}

	public String getNoLevelFormat() {
		return noLevelFormat;
	}

	public void setNoLevelFormat(String noLevelFormat) {
		this.noLevelFormat = noLevelFormat == null ? "" : noLevelFormat;
	}

	public String getTimestampFormat() {
		return timestampFormat;
	}

	public void setTimestampFormat(String timestampFormat) {
		this.timestampFormat = timestampFormat == null ? WriterLogger.DEFAULT_TIMESTAMP_FORMAT : timestampFormat;
	}

	public String getNoTimestampFormat() {
		return noTimestampFormat;
	}

	public void setNoTimestampFormat(String noTimestampFormat) {
		this.noTimestampFormat = noTimestampFormat == null ? WriterLogger.DEFAULT_TIMESTAMP_FORMAT
				: noTimestampFormat;
	}

	public String getSourceFormat() {
		return sourceFormat;
	}

	public void setSourceFormat(String sourceFormat) {
		this.sourceFormat = sourceFormat == null ? WriterLogger.DEFAULT_SOURCE_FORMAT : sourceFormat;
	}

	public String getNoSourceFormat() {
		return noSourceFormat;
	}

	public void setNoSourceFormat(String noSourceFormat) {
		this.noSourceFormat = noSourceFormat == null ? "" : noSourceFormat;
	}

	protected Writer initializeWriter() throws IOException {
		return null;
	}

	@Override
	protected void logIO(LogLevel level, LogSource source, LogMessage message) throws IOException {
		if(writer == null) {
			writer = initializeWriter();
			if(writer == null)
				throw new MisconfiguredLoggerException("No writer has been configured");
		}
		String slevel = level.toStringLogLevel(adjustLogLevel);
		if(slevel == null || slevel.length() == 0) {
			try {
				slevel = String.format(noLevelFormat);
			}
			catch(IllegalFormatException ife) {
				throw new MisconfiguredLoggerException("Invalid noLevelFormat string: " + noLevelFormat, ife);
			}
		}
		else {
			try {
				slevel = String.format(levelFormat, slevel);
			}
			catch(IllegalFormatException ife) {
				throw new MisconfiguredLoggerException("Invalid levelFormat string: " + levelFormat, ife);
			}
		}
		String lvlindent = WriterLogger.getIndentString(slevel.length());
		String ssource = source == null ? null : source.toStringLogSource();
		if(ssource == null || ssource.length() == 0) {
			try {
				ssource = String.format(noSourceFormat);
			}
			catch(IllegalFormatException ife) {
				throw new MisconfiguredLoggerException("Invalid noSourceFormat string: " + noSourceFormat, ife);
			}
		}
		else {
			try {
				ssource = String.format(sourceFormat, ssource);
			}
			catch(IllegalFormatException ife) {
				throw new MisconfiguredLoggerException("Invalid sourceFormat string: " + sourceFormat, ife);
			}
		}
		String srcindent = WriterLogger.getIndentString(ssource.length());
		Date timestamp = message.getLogMessageTimestamp();
		String stimestamp;
		if(timestamp == null) {
			try {
				timestamp = new Date();
				stimestamp = String.format(noTimestampFormat, timestamp);
			}
			catch(IllegalFormatException ife) {
				throw new MisconfiguredLoggerException("Invalid noTimestampFormat string: "
						+ noTimestampFormat, ife);
			}
		}
		else {
			try {
				stimestamp = String.format(timestampFormat, timestamp);
			}
			catch(IllegalFormatException ife) {
				throw new MisconfiguredLoggerException("Invalid timestampFormat string: " + timestampFormat, ife);
			}
		}
		String tsindent = WriterLogger.getIndentString(stimestamp.length());
		String headfmt, headprop;
		if(headLineFormat == null) {
			headfmt = WriterLogger.DEFAULT_HEAD_LINE_FORMAT;
			headprop = "<builtin; you should not be seeing this>";
		}
		else if(headLineFormat.length() == 0) {
			headfmt = null;
			headprop = "<null; you should not be seeing this>";
		}
		else {
			headfmt = headLineFormat;
			headprop = "headLineFormat";
		}
		String tailfmt, tailprop;
		if(tailLineFormat == null) {
			if(headLineFormat == null) {
				tailfmt = WriterLogger.DEFAULT_TAIL_LINE_FORMAT;
				tailprop = "<builtin; you should not be seeing this>";
			}
			else {
				tailfmt = headfmt;
				tailprop = headprop;
			}
		}
		else if(tailLineFormat.length() == 0) {
			tailfmt = null;
			tailprop = "<null; you should not be seeing this>";
		}
		else {
			tailfmt = tailLineFormat;
			tailprop = "tailLineFormat";
		}
		boolean head = true;
		for(String line : message.toLogMessageLines()) {
			if(line == null)
				continue;
			String fmt, prop;
			if(head) {
				fmt = headfmt;
				prop = headprop;
				head = false;
			}
			else {
				fmt = tailfmt;
				prop = tailprop;
			}
			if(fmt == null)
				continue;
			String fline;
			try {
				fline = String.format(fmt, slevel, lvlindent, level.toNumericalLogLevel(), ssource, srcindent,
						timestamp, stimestamp, tsindent, line);
			}
			catch(IllegalFormatException ife) {
				throw new MisconfiguredLoggerException("Invalid " + prop + " string: " + fmt, ife);
			}
			writer.write(fline);
			writer.write(endOfLineChars);
			writer.flush();
		}
	}

	@Override
	protected void closeLoggerIO() throws IOException {
		if(writer != null)
			writer.close();
	}

	private static String getIndentString(int length) {
		String is = WriterLogger.INDENT_STRING;
		int isl = is.length();
		while(isl < length) {
			is = is + is;
			isl *= 2;
		}
		WriterLogger.INDENT_STRING = is;
		if(length == isl)
			return is;
		return is.substring(isl - length);
	}

}

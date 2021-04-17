package org.unclesniper.logging;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class ConsoleLogger extends WriterLogger {

	boolean useStderr;

	private String charset;

	public ConsoleLogger() {
		super(null);
		try {
			setCharset(null);
		}
		catch(UnsupportedEncodingException uee) {
			throw new IllegalStateException("Default charset not supported"
					+ (uee.getMessage() != null && uee.getMessage().length() > 0 ? ": " + uee.getMessage() : ""),
					uee);
		}
	}

	private void initWriter() throws UnsupportedEncodingException {
		String cs = charset == null ? (System.getProperty("os.name").startsWith("Windows") ? "IBM850" : "UTF-8")
				: charset;
		setWriter(new OutputStreamWriter(useStderr ? System.err : System.out, cs));
	}

	public boolean isUseStderr() {
		return useStderr;
	}

	public void setUseStderr(boolean useStderr) {
		this.useStderr = useStderr;
		try {
			initWriter();
		}
		catch(UnsupportedEncodingException uee) {
			throw new Doom("Changing stream broke the charset!?", uee);
		}
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) throws UnsupportedEncodingException {
		this.charset = charset;
		initWriter();
	}

}

package org.unclesniper.logging;

import java.io.File;
import java.io.Writer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import org.unclesniper.logging.util.ResourceHolder;

public class TextFileLogger extends WriterLogger {

	private File file;

	private String charset;

	public TextFileLogger() {
		super(null);
	}

	public TextFileLogger(File file) {
		this(file, null);
	}

	public TextFileLogger(File file, String charset) {
		super(null);
		this.file = file;
		this.charset = charset;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	protected Writer initializeWriter() throws IOException {
		if(file == null)
			throw new MisconfiguredLoggerException("No output file has been configured");
		try(ResourceHolder<OutputStream, IOException> osh
				= new ResourceHolder<OutputStream, IOException>(new FileOutputStream(file), OutputStream::close)) {
			Writer w = new OutputStreamWriter(osh.getObject(), charset == null ? "UTF-8" : charset);
			osh.release();
			return w;
		}
	}

}

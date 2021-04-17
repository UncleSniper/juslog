package org.unclesniper.logging;

import java.io.IOException;

public abstract class AbstractIOLogger extends AbstractLogger {

	private final Object ioLock = new Object();

	private volatile boolean dead;

	public AbstractIOLogger() {}

	protected abstract void logIO(LogLevel level, LogSource source, LogMessage message) throws IOException;

	protected void closeLoggerIO() throws IOException {}

	@Override
	public void log(LogLevel level, LogSource source, LogMessage message) {
		if(level == null)
			throw new IllegalArgumentException("Log level cannot be null");
		if(message == null)
			throw new IllegalArgumentException("Log message cannot be null");
		synchronized(ioLock) {
			if(dead)
				return;
			try {
				logIO(level, source, message);
			}
			catch(IOException | MisconfiguredLoggerException e) {
				dead = true;
				System.err.print("Logger " + getLoggerName() + " failed to log: ");
				e.printStackTrace();
				System.err.flush();
			}
		}
	}

	@Override
	public void closeLogger() {
		synchronized(ioLock) {
			try {
				closeLoggerIO();
			}
			catch(IOException | MisconfiguredLoggerException e) {
				if(!dead) {
					dead = true;
					System.err.print("Logger " + getLoggerName() + " failed to close: ");
					e.printStackTrace();
					System.err.flush();
				}
			}
		}
	}

}

package org.unclesniper.logging;

import java.util.Map;
import java.util.IdentityHashMap;
import java.util.NoSuchElementException;

public class ExceptionLineGenerator {

	private enum State {
		NONE,
		TRACE,
		SUPPRESSED
	}

	private Throwable exception;

	private State state = State.NONE;

	private final Map<Throwable, Throwable> seen;

	private Throwable[] suppressed;

	private ExceptionLineGenerator child;

	private StackTraceElement[] currentTrace;

	private StackTraceElement[] enclosingTrace;

	private int currentIndex;

	private String prefix;

	private String caption;

	private int framesInCommon;

	private int framesToPrint;

	public ExceptionLineGenerator(Throwable exception) {
		this(exception, "", "", new IdentityHashMap<Throwable, Throwable>());
	}

	private ExceptionLineGenerator(Throwable exception, String prefix, String caption,
			Map<Throwable, Throwable> seen) {
		this.seen = seen;
		this.prefix = prefix;
		resetChain(exception);
		this.caption = caption;
	}

	private void resetChain(Throwable exception) {
		this.exception = exception;
		state = State.NONE;
		suppressed = null;
		child = null;
		currentTrace = enclosingTrace = null;
		caption = "";
	}

	public final void resetException(Throwable exception) {
		resetChain(exception);
		seen.clear();
		prefix = "";
	}

	public final boolean generatesMoreLines() {
		switch(state) {
			case NONE:
				return exception != null;
			case TRACE:
			case SUPPRESSED:
				return true;
			default:
				throw new Doom("Unrecognized state: " + state.name());
		}
	}

	public final String generateNextLine() {
		String line;
		switch(state) {
			case NONE:
				if(exception == null)
					throw new NoSuchElementException();
				if(seen.containsKey(exception)) {
					exception = null;
					return "\t[CIRCULAR REFERENCE:" + exception + ']';
				}
				currentTrace = exception.getStackTrace();
				suppressed = exception.getSuppressed();
				currentIndex = 0;
				line = prefix + caption + exception;
				if(currentTrace.length > 0) {
					int framesHere = currentTrace.length - 1;
					int framesThere = (enclosingTrace == null ? 0 : enclosingTrace.length) - 1;
					while(framesHere >= 0 && framesThere >= 0
							&& currentTrace[framesHere].equals(enclosingTrace[framesThere])) {
						--framesHere;
						--framesThere;
					}
					framesInCommon = currentTrace.length - 1 - framesHere;
					assert framesInCommon >= 0;
					framesToPrint = framesHere;
					assert framesToPrint >= 0;
					assert framesInCommon + framesToPrint > 0;
					state = State.TRACE;
				}
				else
					moveToSuppressedIfPresent();
				return line;
			case TRACE:
				if(currentIndex <= framesToPrint) {
					line = prefix + "\tat " + currentTrace[currentIndex];
					if(++currentIndex > framesToPrint && framesInCommon == 0) {
						currentIndex = 0;
						moveToSuppressedIfPresent();
					}
				}
				else {
					assert framesInCommon > 0;
					line = prefix + "\t... " + framesInCommon + " more";
					currentIndex = 0;
					moveToSuppressedIfPresent();
				}
				return line;
			case SUPPRESSED:
				line = child.generateNextLine();
				if(!child.generatesMoreLines()) {
					if(++currentIndex >= suppressed.length) {
						suppressed = null;
						moveToCause();
					}
					else
						child.resetChain(suppressed[currentIndex]);
				}
				return line;
			default:
				throw new Doom("Unrecognized state: " + state.name());
		}
	}

	private void moveToSuppressedIfPresent() {
		if(suppressed.length > 0) {
			child = new ExceptionLineGenerator(suppressed[0], prefix + '\t', "Suppressed: ", seen);
			state = State.SUPPRESSED;
		}
		else
			moveToCause();
	}

	private void moveToCause() {
		exception = exception.getCause();
		if(exception == null)
			currentTrace = enclosingTrace = null;
		else {
			enclosingTrace = currentTrace;
			caption = "Caused by: ";
		}
		state = State.NONE;
	}

}

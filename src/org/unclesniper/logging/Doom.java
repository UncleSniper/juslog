package org.unclesniper.logging;

public class Doom extends Error {

	public Doom(String message) {
		super("Programmer fsck(8)ed up" + (message == null || message.length() == 0 ? "" : ": " + message));
	}

	public Doom(String message, Throwable cause) {
		super("Programmer fsck(8)ed up" + (message == null || message.length() == 0 ? "" : ": " + message), cause);
	}

}

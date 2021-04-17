package org.unclesniper.logging.util;

public class ResourceHolder<ObjectT, ErrorT extends Exception> implements AutoCloseable {

	public interface Closer<ObjectT, ErrorT extends Exception> {

		void closeObject(ObjectT object) throws ErrorT;

	}

	private ObjectT object;

	private final Closer<ObjectT, ErrorT> closer;

	public ResourceHolder(ObjectT object, Closer<ObjectT, ErrorT> closer) {
		if(closer == null)
			throw new IllegalArgumentException("Closer cannot be null");
		this.object = object;
		this.closer = closer;
	}

	public ObjectT getObject() {
		return object;
	}

	public void release() {
		object = null;
	}

	@Override
	public void close() throws ErrorT {
		if(object == null)
			return;
		closer.closeObject(object);
		object = null;
	}

}

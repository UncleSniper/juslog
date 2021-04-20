package org.unclesniper.logging;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.lang.reflect.Modifier;
import org.unclesniper.logging.util.MultiIterator;

public class MessageTypeLogPredicate implements LogPredicate {

	private class SelfIterable implements Iterable<Class<?>> {

		public SelfIterable() {}

		@Override
		public Iterator<Class<?>> iterator() {
			return new MultiIterator<Class<?>>(leaves, nonLeaves);
		}

	}

	private final Set<Class<?>> leaves = new HashSet<Class<?>>();

	private final Set<Class<?>> nonLeaves = new HashSet<Class<?>>();

	public MessageTypeLogPredicate() {}

	public void addType(Class<?> type) {
		if(type == null)
			throw new IllegalArgumentException("Type cannot be null");
		if(Modifier.isFinal(type.getModifiers()))
			leaves.add(type);
		else
			nonLeaves.add(type);
	}

	public Iterable<Class<?>> getTypes() {
		return new SelfIterable();
	}

	public boolean removeType(Class<?> type) {
		if(type == null)
			return false;
		if(Modifier.isFinal(type.getModifiers()))
			return leaves.remove(type);
		else
			return nonLeaves.remove(type);
	}

	@Override
	public boolean shouldLog(LogLevel level, LogSource source, LogMessage message) {
		if(message == null)
			return false;
		Class<?> mc = message.getClass();
		if(Modifier.isFinal(mc.getModifiers()))
			return leaves.contains(mc);
		for(Class<?> cc : nonLeaves) {
			if(cc.isAssignableFrom(mc))
				return true;
		}
		return false;
	}

}

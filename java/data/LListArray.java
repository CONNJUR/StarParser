package data;

import java.util.Arrays;
import java.util.Iterator;

import com.google.common.base.Optional;

/**
 * @author mattf
 * 
 * A list backed by an array that supports efficient
 * sequential processing by dropping leading items.
 * 
 */
public class LListArray<T> implements LList<T> {

	private final T [] items;
	private final int index;
	
	public LListArray(T [] items) {
		this.items = items;
		this.index = 0;
	}
	
	private LListArray(T [] items, int index) {
		this.items = items;
		this.index = index;
	}

	@Override
	public Iterator<T> iterator() {
		return Arrays.asList(this.items).iterator();
	}

	@Override
	public Optional<T> getHead() {
		if(this.index >= this.items.length) {
			return Optional.absent();
		} else {
			return Optional.of(this.items[this.index]);
		}
	}

	@Override
	public Optional<LList<T>> getTail() {
		if(this.index >= this.items.length) {
			return Optional.absent();
		} else {
			return Optional.of((LList<T>) new LListArray<T>(this.items, this.index + 1));
		}
	}

	@Override
	public int size() {
		int sSize = this.items.length - this.index;
		if(sSize < 0) {
			return 0;
		}
		return sSize;
	}
	
	public LListArray<T> drop(int i) {
		return new LListArray<T>(this.items, this.index + i);
	}

}

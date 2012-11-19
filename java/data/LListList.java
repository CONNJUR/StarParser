package data;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;

public class LListList<T> implements LList<T> {

	private final List<T> items;
	private final int index;
	
	public LListList(List<T> items) {
		this.items = items;
		this.index = 0;
	}
	
	private LListList(List<T> items, int index) {
		this.items = items;
		this.index = index;
	}

	@Override
	public Iterator<T> iterator() {
		return this.items.iterator();
	}

	@Override
	public Optional<T> getHead() {
		if(this.index >= this.items.size()) {
			return Optional.absent();
		} else {
			return Optional.of(this.items.get(this.index));
		}
	}

	@Override
	public Optional<LList<T>> getTail() {
		if(this.index >= this.items.size()) {
			return Optional.absent();
		} else {
			return Optional.of((LList<T>) new LListList<T>(this.items, this.index + 1));
		}
	}

	@Override
	public int size() {
		int sSize = this.items.size() - this.index;
		if(sSize < 0) {
			return 0;
		}
		return sSize;
	}

}

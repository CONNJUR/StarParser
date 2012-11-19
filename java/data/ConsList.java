package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;



/**
 * @author mattf
 * 
 * A basic linked-list.
 * Not sure if I need this.
 * 
 */
public class ConsList<T> implements LList<T> {
	
	private final Optional<Tuple2<T, ConsList<T>>> pair;
	

	public ConsList(T head, ConsList<T> tail) {
		this.pair = Optional.of(Tuple2.of(head, tail));
	}
	
	public ConsList() {
		this.pair = Optional.absent();
	}


	@Override
	public Iterator<T> iterator() {
		return this.toList().iterator();
	}
	
	private List<T> toList() {
		Optional<Tuple2<T, ConsList<T>>> current = this.pair;
		List<T> items = new ArrayList<T>();
		while(current.isPresent()) {
			items.add(current.get().first);
			current = current.get().second.pair;
		}
		return items;
	}


	@Override
	public Optional<T> getHead() {
		if(this.pair.isPresent()) {
			return Optional.of(this.pair.get().first);
		}
		return Optional.absent();
	}


	@Override
	public Optional<LList<T>> getTail() {
		if(this.pair.isPresent()) {
			// is there any way to avoid this upcast?
			return Optional.of((LList<T>) this.pair.get().second);
		}
		return Optional.absent();
	}


	@Override
	public int size() {
		return this.toList().size();
	}

}

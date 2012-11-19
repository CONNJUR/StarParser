package data;

import com.google.common.base.Optional;



public interface LList<T> extends Iterable<T> {

	Optional<T> getHead();
	
	Optional<LList<T>> getTail();
	
	int size();
	
}

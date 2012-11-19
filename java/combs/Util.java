package combs;

import java.util.Comparator;

public class Util {

	static <T extends Comparable<T>> Comparator<T> getDefaultComparator() {
		return new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
		};
	}

}

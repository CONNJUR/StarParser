package data;

public class Tuple2<A, B> {
	
	public final A first;
	public final B second;
	
	public Tuple2(A a, B b) {
		this.first = a;
		this.second = b;
	}
	
	public static <X, Y> Tuple2<X,Y> of(X x, Y y) {
		return new Tuple2<X,Y>(x,y);
	}

}
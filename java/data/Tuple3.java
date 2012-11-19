package data;

public class Tuple3<A, B, C> {

	public final A first;
	public final B second;
	public final C third;
	
	public Tuple3(A a, B b, C c) {
		this.first = a;
		this.second = b;
		this.third = c;
	}
	
	public static <X, Y, Z> Tuple3<X,Y,Z> of(X x, Y y, Z z) {
		return new Tuple3<X,Y,Z>(x,y,z);
	}
}

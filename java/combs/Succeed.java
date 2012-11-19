package combs;

import data.LList;

/**
 * @author mattf
 * 
 * A parser that always succeeds with
 * the specified value, consuming no
 * tokens.
 * 
 */
public class Succeed<T, A> extends Parser<T, A> {

	private final A value;
	
	public Succeed(A value) {
		this.value = value;
	}
	
	@Override
	public ParseResult<T, A> parse(LList<T> tokens) {
		return ParseResult.success(tokens, this.value);
	}
	
	public static <T,A> Succeed<T, A> of(A a) {
		return new Succeed<T,A>(a);
	}
	
}
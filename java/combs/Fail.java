package combs;

import data.LList;

/**
 * @author mattf
 * 
 * A parser that always fails.
 * 
 */
public class Fail<T, A> extends Parser<T, A> {
	
	public Fail() {
		
	}

	@Override
	public ParseResult<T, A> parse(LList<T> tokens) {
		return ParseResult.failure("'fail'", tokens);
	}
	
	
	/*
	 * 
	 */
	
	public static <T,A> Fail<T,A> of() {
		return new Fail<T,A>();
	}
	
}
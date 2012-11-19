package combs;

import data.LList;

/**
 * @author mattf
 * 
 * Succeeds if either parser succeeds.
 * Fails only if both parsers fail.
 * Left-biased.  
 * 
 */
public class Choice<T, A> extends Parser<T, A> {

	private final Parser<T, A> left;
	private final Parser<T, A> right;
	
	public Choice(Parser<T, A> left, Parser<T, A> right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public ParseResult<T, A> parse(LList<T> tokens) {
		ParseResult<T,A> r = this.left.parse(tokens);
		if(r.isSuccess()) {
			return r;
		} else {
			return this.right.parse(tokens);
		}
	}
	
}
package combs;

import java.util.List;

import data.LList;

/**
 * @author mattf
 *
 * Succeeds if any of its parsers succeed.
 * Fails if *all* of its parsers fail.
 * Left-biased.
 * 
 */
public class Any<T, A> extends Parser<T, A> {
	
	private final List<Parser<T, A>> parsers;

	public Any(List<Parser<T, A>> parsers) {
		this.parsers = parsers;
	}
	
	public static <T,A> Parser<T,A> of(List<Parser<T,A>> parsers) {
		return new Any<T,A>(parsers);
	}

	@Override
	public ParseResult<T, A> parse(LList<T> tokens) {
		for(Parser<T,A> p: this.parsers) {
			ParseResult<T,A> r = p.parse(tokens);
			if(r.isSuccess()) {
				return r;
			}
		}
		return Fail.<T,A>of().parse(tokens);
	}
}
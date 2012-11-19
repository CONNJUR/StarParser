package combs;


import data.F1;
import data.LList;

/**
 * @author mattf
 *
 * Succeeds, consuming one token, if the next
 * token passes the given predicate.
 * 
 */
public class Satisfy<T> extends Parser<T, T> {

	private final Parser<T, T> parser;
	
	public Satisfy(F1<T, Boolean> pred) {
		this.parser = new Check<T, T>(new GetOne<T>(), pred);
	}

	@Override
	public ParseResult<T, T> parse(LList<T> tokens) {
		return this.parser.parse(tokens);
	}
	
	public static <T> Satisfy<T> of(F1<T, Boolean> pred) {
		return new Satisfy<T>(pred);
	}
	
}
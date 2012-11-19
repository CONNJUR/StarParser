package combs;

import data.LList;
import data.Unit;

/**
 * @author mattf
 *
 * A parser that succeeds only when the token stream is empty.
 * 
 */
public class End<T> extends Parser<T, Unit> {
	
	private final Parser<T,Unit> parser;
	
	public End() {
		this.parser = new Not0<T>(new GetOne<T>());
	}
	
	public static <T> Parser<T,Unit> of() {
		return new End<T>();
	}

	@Override
	public ParseResult<T, Unit> parse(LList<T> tokens) {
		return this.parser.parse(tokens);
	}
}
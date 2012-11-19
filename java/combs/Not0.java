package combs;

import data.LList;
import data.Unit;

/**
 * @author mattf
 *
 * A parser that tries its parser, and fails
 * if it succeeds and vice versa.  It consumes
 * no input.
 * 
 */
public class Not0<T> extends Parser<T, Unit> {
	
	private final Parser<T, ?> parser;
	
	public Not0(Parser<T, ?> parser) {
		this.parser = parser;
	}

	@Override
	public ParseResult<T, Unit> parse(LList<T> tokens) {
		if(this.parser.parse(tokens).isSuccess()) {
			return ParseResult.failure("'not0' failed", tokens);
		} else {
			return ParseResult.success(tokens, Unit.of());
		}
	}
}
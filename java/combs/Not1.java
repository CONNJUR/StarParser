package combs;

import data.LList;
import data.Unit;

/**
 * @author mattf
 *
 * A parser that tries its parser, and fails
 * if it succeeds.  If it fails, then it runs
 * GetOne and returns the result.
 * 
 */
public class Not1<T> extends Parser<T, T> {
	
	private final Parser<T, T> parser;
	
	public Not1(Parser<T, ?> parser) {
		this.parser = new Seq2R<T,Unit,T>(new Not0<T>(parser), new GetOne<T>());
	}

	@Override
	public ParseResult<T, T> parse(LList<T> tokens) {
		return this.parser.parse(tokens);		
	}
}
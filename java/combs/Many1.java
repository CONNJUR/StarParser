package combs;

import java.util.List;

import data.LList;

/**
 * @author mattf
 *
 * A parser that matches its parser as many times as possible.
 * It must match at least one time.
 * 
 */
public class Many1<T, A> extends Parser<T, List<A>> {

	private final Parser<T, List<A>> parser;
	
	public Many1(Parser<T, A> parser) {
		this.parser = new Many0<T,A>(parser);
	}

	@Override
	public ParseResult<T, List<A>> parse(LList<T> tokens) {
		ParseResult<T, List<A>> r = this.parser.parse(tokens);
		if(!r.isSuccess()) {
			assert false; // many0 should *always* succeed
		}
		// have to match parser at least one time
		if(r.getValue().size() >= 1) {
			return r;
		}
		return ParseResult.failure("unable to match 'many1'", tokens);
	}
	
}
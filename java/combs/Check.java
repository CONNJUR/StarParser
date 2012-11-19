package combs;


import data.F1;
import data.LList;

/**
 * @author mattf
 * 
 * A parser that first runs its parser; if that succeeds,
 * it checks the result with its predicate.  True means
 * the parser succeeds, false means it fails.
 * 
 */
public class Check<T, A> extends Parser<T, A> {
	
	private final Parser<T, A> parser;
	private final F1<A, Boolean> pred;
	
	public Check(Parser<T,A> p, F1<A, Boolean> pred) {
		this.parser = p;
		this.pred = pred;
	}

	@Override
	public ParseResult<T, A> parse(LList<T> tokens) {
		ParseResult<T,A> r = this.parser.parse(tokens);
		if(!r.isSuccess()) {
			// parser failed -> fail
			return r;
		} else if(this.pred.apply(r.getValue())) {
			// result is present and predicate passes -> pass
			return r;
		} else {
			// result is present but predicate fails -> fail
			return ParseResult.failure("'check' failed", tokens);
		}
	}
	
}
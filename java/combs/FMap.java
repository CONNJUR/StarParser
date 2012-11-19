package combs;


import data.F1;
import data.LList;

/**
 * @author mattf
 *
 * Runs a parser and maps a function over its
 * result; thus, it succeeds if the parser succeeds
 * and vice versa.
 * 
 */
public class FMap<T, A, B> extends Parser<T, B> {
	
	private final Parser<T, A> parser;
	private final F1<A, B> f;
	
	public FMap(Parser<T, A> parser, F1<A, B> f) {
		this.parser = parser;
		this.f = f;
	}

	@Override
	public ParseResult<T, B> parse(LList<T> tokens) {
		ParseResult<T, A> r = this.parser.parse(tokens);
		if(r.isSuccess()) {
			return ParseResult.success(r.getRestTokens(), this.f.apply(r.getValue()));
		} else {
			return ParseResult.failure(r.getErrorMessage(), r.getRestTokens());
		}
	}
	
}
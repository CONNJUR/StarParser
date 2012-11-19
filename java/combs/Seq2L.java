package combs;

import data.LList;
import data.Tuple2;

/**
 * @author mattf
 * 
 * Run two parsers in sequence, returning only the value
 * from the first parser.
 * 
 */
public class Seq2L<T, A, B> extends Parser<T, A> {

	private final Parser<T, Tuple2<A,B>> parser;
	
	public Seq2L(Parser<T, A> left, Parser<T, B> right) {
		this.parser = new Seq2<T, A, B>(left, right);
	}

	@Override
	public ParseResult<T, A> parse(LList<T> tokens) {
		ParseResult<T, Tuple2<A, B>> r = this.parser.parse(tokens);
		if(r.isSuccess()) {
			return ParseResult.success(r.getRestTokens(), r.getValue().first);
		} else {
			return ParseResult.failure(r.getErrorMessage(), r.getRestTokens());
		}
	}
}
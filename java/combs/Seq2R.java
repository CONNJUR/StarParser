package combs;

import data.LList;
import data.Tuple2;

/**
 * @author mattf
 * 
 * Run two parsers in sequence, returning only the value
 * from the second parser.
 * 
 */
public class Seq2R<T, A, B> extends Parser<T, B> {

	private final Parser<T, Tuple2<A,B>> parser;
	
	public Seq2R(Parser<T, A> left, Parser<T, B> right) {
		this.parser = new Seq2<T, A, B>(left, right);
	}

	@Override
	public ParseResult<T, B> parse(LList<T> tokens) {
		ParseResult<T, Tuple2<A, B>> r = this.parser.parse(tokens);
		if(r.isSuccess()) {
			return ParseResult.success(r.getRestTokens(), r.getValue().second);
		} else {
			return ParseResult.failure(r.getErrorMessage(), r.getRestTokens());
		}
	}
}
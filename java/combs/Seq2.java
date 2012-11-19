package combs;

import data.LList;
import data.Tuple2;

/**
 * @author mattf
 *
 * Run two parsers in sequence, tupling their results.
 * 
 */
public class Seq2<T, A, B> extends Parser<T, Tuple2<A, B>> {

	private final Parser<T, A> left;
	private final Parser<T, B> right;
	
	public Seq2(Parser<T, A> left, Parser<T, B> right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public ParseResult<T, Tuple2<A, B>> parse(LList<T> tokens) {
		ParseResult<T,A> r1 = this.left.parse(tokens);
		if(!r1.isSuccess()) {
			return ParseResult.failure(r1.getErrorMessage(), r1.getRestTokens());
		}
		ParseResult<T,B> r2 = this.right.parse(r1.getRestTokens());
		if(!r2.isSuccess()) {
			return ParseResult.failure(r2.getErrorMessage(), r2.getRestTokens());
		}
		return ParseResult.success(r2.getRestTokens(), Tuple2.of(r1.getValue(), r2.getValue()));
	}
	
}
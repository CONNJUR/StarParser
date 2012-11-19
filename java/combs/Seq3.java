package combs;

import data.LList;
import data.Tuple3;

/**
 * @author mattf
 *
 * Run three parsers in sequence,
 * tupling their results.
 * 
 */
public class Seq3<T, A, B, C> extends Parser<T, Tuple3<A, B, C>> {

	private final Parser<T, A> left;
	private final Parser<T, B> middle;
	private final Parser<T, C> right;
	
	public Seq3(Parser<T, A> left, Parser<T, B> middle, Parser<T, C> right) {
		this.left = left;
		this.middle = middle;
		this.right = right;
	}

	@Override
	public ParseResult<T, Tuple3<A, B, C>> parse(LList<T> tokens) {
		ParseResult<T,A> r1 = this.left.parse(tokens);
		if(!r1.isSuccess()) {
			return ParseResult.failure(r1.getErrorMessage(), r1.getRestTokens());
		}
		ParseResult<T,B> r2 = this.middle.parse(r1.getRestTokens());
		if(!r2.isSuccess()) {
			return ParseResult.failure(r2.getErrorMessage(), r2.getRestTokens());
		}
		ParseResult<T,C> r3 = this.right.parse(r2.getRestTokens());
		if(!r3.isSuccess()) {
			return ParseResult.failure(r3.getErrorMessage(), r3.getRestTokens());
		}
		return ParseResult.success(r3.getRestTokens(), Tuple3.of(r1.getValue(), r2.getValue(), r3.getValue()));
	} 

}

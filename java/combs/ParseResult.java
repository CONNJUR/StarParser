package combs;

import data.Either;
import data.Either.Left;
import data.Either.Right;
import data.LList;
import data.Tuple2;

public class ParseResult<T, A> {

	private final Either<Tuple2<String, LList<T>>, Tuple2<LList<T>, A>> result;

	public ParseResult(Either<Tuple2<String, LList<T>>, Tuple2<LList<T>, A>> result) {
		this.result = result;
	}
	
	
	public boolean isSuccess() {
		return this.result.isRight();
	}
	
	public LList<T> getRestTokens() {
		if(this.isSuccess()) {
    		return this.result.getRight().first;
		} else {
			return this.result.getLeft().second;
		}
	}
	
	public A getValue() {
		return this.result.getRight().second;
	}
	
	public String getErrorMessage() {
		return this.result.getLeft().first;
	}
	
	
	
	/*
	 * some static constructor methods to save the pain of writing out the types
	 */
	
	public static <T,A> ParseResult<T,A> success(Right<Tuple2<String, LList<T>>, Tuple2<LList<T>, A>> pair) {
		return of(pair);
	}
	
	public static <T,A> ParseResult<T,A> success(LList<T> restTokens, A value) {
		return success(Right.<Tuple2<String, LList<T>>, Tuple2<LList<T>, A>>of(Tuple2.of(restTokens, value)));
	}
	
	public static <T,A> ParseResult<T,A> failure(Left<Tuple2<String, LList<T>>, Tuple2<LList<T>, A>> pair) {
		return of(pair);
	}
	
	public static <T,A> ParseResult<T,A> failure(String message, LList<T> restTokens) {
		return failure(Left.<Tuple2<String, LList<T>>, Tuple2<LList<T>, A>>of(Tuple2.of(message, restTokens)));
	}
	
	public static <T,A> ParseResult<T,A> of(Either<Tuple2<String, LList<T>>, Tuple2<LList<T>, A>> result) {
		return new ParseResult<T,A>(result);
	}
}

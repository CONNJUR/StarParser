package combs;

import data.LList;

/**
 * @author mattf
 *
 * Succeeds, consuming one token, if token stream is not empty.
 * Fails if it is empty.
 * 
 */
public class GetOne<T> extends Parser<T, T> {
	
	public GetOne() {
		
	}
	
	public static <T> GetOne<T> of() {
		return new GetOne<T>();
	}

	@Override
	public ParseResult<T, T> parse(LList<T> tokens) {
		if(tokens.size() == 0) {
			return ParseResult.failure("unable to get token: empty stream", tokens);
		} else {
			// I think these 'get's are safe because I already checked 
			// if the list is empty
			return ParseResult.success(tokens.getTail().get(), tokens.getHead().get());
		}
	}
	
}
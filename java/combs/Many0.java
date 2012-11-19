package combs;

import java.util.ArrayList;
import java.util.List;

import data.LList;

/**
 * @author mattf
 * 
 * A parser that matches its parser as many times as possible.
 * It always succeeds since 0 parses is fine.
 * 
 */
public class Many0<T, A> extends Parser<T, List<A>> {

	private final Parser<T, A> parser;
	
	public Many0(Parser<T, A> parser) {
		this.parser = parser;
	}

	@Override
	public ParseResult<T, List<A>> parse(LList<T> tokens) {
		List<A> results = new ArrayList<A>();
		ParseResult<T, A> r;
		LList<T> restTokens = tokens;
		while(true) {
			r = this.parser.parse(restTokens);
			if(r.isSuccess()) {
				results.add(r.getValue());
				restTokens = r.getRestTokens();
			} else {
				break;
			}
		}
		return ParseResult.success(restTokens, results);
	}
	
}
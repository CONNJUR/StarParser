package combs;

import java.util.Comparator;
import java.util.List;

import data.LList;

/**
 * @author mattf
 *
 * @param <T>
 */
public class StringP<T> extends Parser<T, List<T>> {
	
	private final List<T> string;
	private final Comparator<T> comp;
	
	public StringP(Comparator<T> comp, List<T> string) {
		this.comp = comp;
		this.string = string;
	}

	@Override
	public ParseResult<T, List<T>> parse(LList<T> tokens) {
		LList<T> restTokens = tokens;
		for(T t : this.string) {
			// I believe it's safe to use Optional.get() here b/c
			// if the size is greater than 1, that means the Optional 
			// *is* present
			if(restTokens.size() > 0 && this.comp.compare(t, restTokens.getHead().get()) == 0) {
				// cool, keep going
				restTokens = restTokens.getTail().get();
			} else {
				return ParseResult.failure("can't match string", tokens);
			}
		}
		return ParseResult.success(restTokens, this.string);
	}
	
	/*
	 * 
	 */
	
	public static <T> StringP<T> of(Comparator<T> comp, List<T> string) {
		return new StringP<T>(comp, string);
	}
	
	public static <T extends Comparable<T>> StringP<T> of(List<T> string) {
		return new StringP<T>(Util.<T>getDefaultComparator(), string);
	}

}

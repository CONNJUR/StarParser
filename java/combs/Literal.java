package combs;

import java.util.Comparator;

import data.F1;
import data.LList;

/**
 * @author mattf
 * 
 * Succeeds, consuming and returning one token,
 * if the next token matches the specified value
 * according to the given comparator.
 * 
 */
public class Literal<T> extends Parser<T, T> {

	private final Parser<T,T> parser;
	
	public Literal(final Comparator<T> comp, final T t) {
		this.parser = new Satisfy<T>(new F1<T, Boolean>() {
			public Boolean apply(T t2) {
				return comp.compare(t, t2) == 0;
			}
		});
	}

	@Override
	public ParseResult<T, T> parse(LList<T> tokens) {
		return this.parser.parse(tokens);
	}
	
	/*
	 * 
	 */
	
	public static <T extends Comparable<T>> Literal<T> of(T t) {
		// TODO: why does the compiler get confused if I leave out 'Literal.<T>'?
		return new Literal<T>(Util.<T>getDefaultComparator(), t);
	}
	
	public static <T> Literal<T> of(Comparator<T> comp, T t) {
		return new Literal<T>(comp, t);
	}
	
}
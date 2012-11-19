package combs;

import java.util.List;

import data.F1;
import data.F2;
import data.F3;
import data.LList;
import data.Tuple2;
import data.Tuple3;
import data.Unit;




public abstract class Parser<T, A> {

	
	public Parser() {
	}
	
	public abstract ParseResult<T, A> parse(LList<T> tokens);
	
	/*
	 * combinators that can reasonably be called 
	 * on 'this' parser
	 */
	
	public <B> Parser<T, B> map(F1<A, B> f) {
		return new FMap<T, A, B>(this, f);
	}
	
	public Parser<T, A> check(F1<A, Boolean> f) {
		return new Check<T, A>(this, f);
	}
	
	public Parser<T, A> choice(Parser<T, A> right) {
		return new Choice<T, A>(this, right);
	}
	
	public Parser<T, List<A>> many0() {
		return new Many0<T, A>(this);
	}
	
	public Parser<T, List<A>> many1() {
		return new Many1<T, A>(this);
	}
	
	
	public <B> Parser<T, Tuple2<A, B>> seq2(Parser<T, B> right) {
		return new Seq2<T, A, B>(this, right);
	}
	
	public <B, C> Parser<T, C> seq2(Parser<T, B> right, final F2<A,B,C> f) {
		// g is basically just uncurrying f ... ugh
		F1<Tuple2<A,B>, C> g = new F1<Tuple2<A,B>, C>() {
			public C apply(Tuple2<A,B> pair) {
				return f.apply(pair.first, pair.second);
			}
		};
		return new FMap<T, Tuple2<A,B>, C>(this.seq2(right), g);
	}
	
	public <B> Parser<T, A> seq2L(Parser<T, B> right) {
		return new Seq2L<T,A,B>(this, right);
	}
	
	public <B> Parser<T, B> seq2R(Parser<T, B> right) {
		return new Seq2R<T,A,B>(this, right);
	}
	
	
	public <B,C> Parser<T, Tuple3<A, B, C>> seq3(Parser<T,B> middle, Parser<T, C> right) {
		return new Seq3<T, A, B, C>(this, middle, right);
	}
	
	public <B,C,D> Parser<T, D> seq3(Parser<T,B> middle, Parser<T, C> right, final F3<A,B,C,D> f) {
		// g is basically just uncurrying f ... ugh
		F1<Tuple3<A,B,C>, D> g = new F1<Tuple3<A,B,C>, D>() {
			public D apply(Tuple3<A,B,C> three) {
				return f.apply(three.first, three.second, three.third);
			}
		};
		return new FMap<T, Tuple3<A,B,C>, D>(this.seq3(middle, right), g);
	}
	
	
	public Parser<T, Unit> not0() {
		return new Not0<T>(this);
	}
	
	public Parser<T, T> not1() {
		return new Not1<T>(this);
	}
}
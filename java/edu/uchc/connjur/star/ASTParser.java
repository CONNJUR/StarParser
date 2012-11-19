package edu.uchc.connjur.star;

import java.util.ArrayList;
import java.util.List;

import combs.Parser;
import combs.Satisfy;
import data.Either;
import data.Either.Right;
import data.Either.Left;
import data.F1;
import data.F2;
import data.F3;
import data.Tuple2;
import edu.uchc.connjur.star.Token.TokenType;

public class ASTParser {
	
	static F1<Token, Boolean> byTokenType(final TokenType tt) {
		return new F1<Token, Boolean>() {
			public Boolean apply(Token t) {
				return t.type == tt;
			}
		};
	}
	
	static <L, R> F1<R, Either<L,R>> tagRight() {
		return new F1<R, Either<L,R>>() {
			public Either<L, R> apply(R r) {
				return Right.of(r);
			}
		};
	}
	
	static <L, R> F1<L, Either<L,R>> tagLeft() {
		return new F1<L, Either<L,R>>() {
			public Either<L, R> apply(L l) {
				return Left.of(l);
			}
		};
	}
	
	/*
	 * 
	 */
	
	static Parser<Token, Token> ident = Satisfy.of(byTokenType(TokenType.IDENTIFIER));
	
	static Parser<Token, Token> val = Satisfy.of(byTokenType(TokenType.VALUE));
	
	static Parser<Token, Token> save = Satisfy.of(byTokenType(TokenType.SAVE_OPEN));
	
	static Parser<Token, Token> saveClose = Satisfy.of(byTokenType(TokenType.SAVE_CLOSE));
	
	static Parser<Token, Token> data = Satisfy.of(byTokenType(TokenType.DATA_OPEN));
	
	/*
	 * Loop   :=   LOOP  Identifier(*)  Value(*)  STOP
	 */
	static Parser<Token, Loop> loop = Satisfy.of(byTokenType(TokenType.LOOP))
			.seq2R(ident.many0())
			.seq3(val.many0(), 
			      Satisfy.of(byTokenType(TokenType.STOP)),
			      new F3<List<Token>, List<Token>, Token, Loop>() {
				  	  public Loop apply(List<Token> idents, List<Token> vals, Token meh) {
						  List<String> sIdents = new ArrayList<String>();
						  for(Token i : idents) {
							  sIdents.add(i.value);
						  }
						  List<String> sVals = new ArrayList<String>();
						  for(Token v : vals) {
							  sVals.add(v.value);
						  }
						  return new Loop(sIdents, sVals);
					  }
				  }
	);
	
	
	/*
	 * Datum    :=   Identifier  Value
	 */
	static Parser<Token, Tuple2<String, String>> datum = ident.seq2(val, 
			new F2<Token, Token, Tuple2<String, String>>() {
				public Tuple2<String, String> apply(Token i, Token v) {
					return Tuple2.of(i.value, v.value);
				}
			}
	);
	
	/*
	 * SaveFrame    :=   SAVE_OPEN   ( Datum  |  Loop )(*)   SAVE_CLOSE 
	 */
	static Parser<Token, SaveFrame> saveFrame = save.seq3(
			datum.map(ASTParser.<Loop,Tuple2<String,String>>tagRight())
				.choice(loop.map(ASTParser.<Loop,Tuple2<String,String>>tagLeft()))
				.many0(),
			saveClose,
			new F3<Token, List<Either<Loop,Tuple2<String, String>>>, Token, SaveFrame>() {
				public SaveFrame apply(Token meh1, List<Either<Loop, Tuple2<String, String>>> contents, Token meh2) {
					/*
					 * strategy:  tag the datums and loops differently,
					 * then sort through them and distinguish by their
					 * tags in the mapping function
					 */
					List<Loop> loops = new ArrayList<Loop>();
					List<Tuple2<String, String>> datums = new ArrayList<Tuple2<String,String>>();
					for(Either<Loop, Tuple2<String, String>> thing : contents) {
						if(thing.isRight()) {
							datums.add(thing.getRight());
						} else {
							loops.add(thing.getLeft());
						}
					}
					return new SaveFrame(datums, loops);
				}
			}
	);
	
	/*
	 * Data     :=     DATA_OPEN   SaveFrame(+)
	 */
	static Parser<Token, DataBlock> dataBlock = data.seq2R(saveFrame.many1())
			.map(new F1<List<SaveFrame>, DataBlock>() {
				public DataBlock apply(List<SaveFrame> saves) {
					return new DataBlock(saves);
				}
			});

}

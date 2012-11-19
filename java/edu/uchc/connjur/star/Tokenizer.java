package edu.uchc.connjur.star;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.primitives.Chars;
import combs.Any;
import combs.Literal;
import combs.Parser;
import combs.Satisfy;
import combs.StringP;

import data.F1;
import data.F2;
import data.F3;
import data.Tuple2;
import edu.uchc.connjur.star.Token.TokenType;

public class Tokenizer {

	/*
	 * some utility methods and constants
	 */
		
	static String getString(List<Character> cs) {
		StringBuilder sb = new StringBuilder(cs.size());
		for(Character c : cs) {
			sb.append(c);
		}
		return sb.toString();
	}
	
	static F1<List<Character>, Token> constF(final Token t) {
		return new F1<List<Character>, Token>() {
			public Token apply(List<Character> cs) {
				return t;
			}
		};
	}
	
	static F1<List<Character>, Token> tokenString(final TokenType t) {
		return new F1<List<Character>, Token>() {
			public Token apply(List<Character> s) {
				return new Token(t, getString(s));
			}
		};
	}
	
	static F1<Character, Boolean> isCharIn(final String s) {
		return new F1<Character, Boolean>() {
			public Boolean apply(Character c) {
				return s.indexOf(c) != -1;
			}
		};
	}
	
	static <A,B,C> F3<A,B,C,B> getSecond() {
		return new F3<A,B,C,B>() {
			public B apply(A a, B b, C c) {
				return b;
			}
		};
	}
	
	/*
	 * some parsers
	 */

	//  Newline   :=  '\n'  |  '\r'  |  '\f'
	static final Parser<Character, Character> NEWLINE = Satisfy.of(isCharIn("\n\r\f"));
	//  Blank     :=  ' '  |  '\t'
	static final Parser<Character, Character> BLANK = Satisfy.of(isCharIn(" \t"));
	//  Space     :=  Blank  |  Newline
	static final Parser<Character, Character> SPACE = BLANK.choice(NEWLINE);
	//  Special   :=  '"'  |  '#'  |  '_'  |  Blank  |  Newline
	static final Parser<Character, Character> SPECIAL = Satisfy.of(isCharIn("\"#'_ \t\r\f\n"));
	
	//  Stop      :=  "stop_"
	static final Parser<Character, Token> STOP = StringP.of(Chars.asList("stop_".toCharArray()))
			.map(constF(new Token(TokenType.STOP, "")));
	//  SaveClose :=  "save_"
	static final Parser<Character, Token> SAVE_CLOSE = StringP.of(Chars.asList("save_".toCharArray()))
			.map(constF(new Token(TokenType.SAVE_CLOSE, "")));
	//  Loop      :=  "loop_"
	static final Parser<Character, Token> LOOP = StringP.of(Chars.asList("loop_".toCharArray()))
			.map(constF(new Token(TokenType.LOOP, "")));
	//  Comment   :=  '#'  (not Newline)(*)
	static final Parser<Character, Token> COMMENT = Literal.of('#')
			.seq2R(NEWLINE.not1().many0())
			.map(tokenString(TokenType.COMMENT));
	//  DataOpen  :=  "data_"  (not Space)(+)
	static final Parser<Character, Token> DATA_OPEN = StringP.of(Chars.asList("data_".toCharArray()))
			.seq2R(SPACE.not1().many1())
			.map(tokenString(TokenType.DATA_OPEN));
	//  SaveOpen  :=  "save_"  (not Space)(+)
	static final Parser<Character, Token> SAVE_OPEN = StringP.of(Chars.asList("save_".toCharArray())).
			seq2R(SPACE.not1().many1()).
			map(tokenString(TokenType.SAVE_OPEN));
	
	//  Identifier :=  '_'  (not Space)(+)
	static final Parser<Character, Token> IDENTIFIER = Literal.of('_')
			.seq2R(SPACE.not1().many1())
			.map(tokenString(TokenType.IDENTIFIER));
	
	//  Unquoted  :=  (not Special)  (not Space)(*)
	static final Parser<Character, List<Character>> UNQUOTED = SPECIAL.not1().seq2(
			SPACE.not1().many0(),
			new F2<Character, List<Character>, List<Character>>() {
				public List<Character> apply(Character c, List<Character> chars) {
					// TODO wow, this is horrible
					List<Character> l = new ArrayList<Character>();
					l.add(c);
					l.addAll(chars);
					return l;
				}
			}
	);
	
	static final Parser<Character, Character> SEMICOLON = Literal.of(';');
	//  EndSC    :=  Newline  ';'
	static final Parser<Character, Tuple2<Character, Character>> END_SEQUENCE = NEWLINE.seq2(SEMICOLON);
	//  SCString :=  ';'  (not EndSC)(*)  EndSC
	static final Parser<Character, List<Character>> SCSTRING = SEMICOLON
			.seq3(END_SEQUENCE.not1().many0(),
			      END_SEQUENCE,
			      Tokenizer.<Character, List<Character>, Tuple2<Character, Character>>getSecond());
	
	static final Parser<Character, Character> SQ = Literal.of('\'');
	//  SQString  :=  '\''  (not '\'')(+)  '\''
	static final Parser<Character, List<Character>> SQSTRING = SQ
			.seq3(SQ.not1().many1(), SQ, Tokenizer.<Character, List<Character>, Character>getSecond());
	
	static final Parser<Character, Character> DQ = Literal.of('"');
	//  DQString  :=  '"'  (not '"')(+)  '"'
	static final Parser<Character, List<Character>> DQSTRING = DQ
			.seq3(DQ.not1().many1(), DQ, Tokenizer.<Character, List<Character>, Character>getSecond());
	//  Value     :=  SQString  |  DQString  |  SCString  |  Unquoted
	static final Parser<Character, Token> VALUE = SQSTRING
		.choice(DQSTRING)
		.choice(SCSTRING)
		.choice(UNQUOTED)
		.map(tokenString(TokenType.VALUE));
	
	//  Whitespace  :=  Blank(+)
	static final Parser<Character, Token> WHITESPACE = BLANK.many1()
			.map(constF(new Token(TokenType.WHITESPACE, "")));
	//  Newlines    :=  Newline(+)
	static final Parser<Character, Token> NEWLINES = NEWLINE.many1()
			.map(constF(new Token(TokenType.NEWLINES, "")));
	
	//  Token   :=  DataOpen  |  SaveOpen  |  SaveClose  |  Loop  |  Stop  |  Value  
	//           |  Whitespace  |  Newlines  |  Comment  |  Identifier
	// see http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#FAQ300
	//   for why this is okay
	@SuppressWarnings("unchecked")
	static final Parser<Character, Token> ONE_TOKEN = Any.of(
		Arrays.asList(
			DATA_OPEN, SAVE_OPEN, 
			SAVE_CLOSE, LOOP, STOP, 
			VALUE, WHITESPACE, 
			NEWLINES, COMMENT, IDENTIFIER)
	);
	
	//  Scanner  :=  Token(*)
	static final Parser<Character, List<Token>> SCANNER = ONE_TOKEN.many0();
	
}

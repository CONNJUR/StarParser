package edu.uchc.connjur.star;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import combs.Literal;
import combs.ParseResult;
import data.CharStream;
import data.LList;
import data.LListList;
import edu.uchc.connjur.star.Token.TokenType;



public class Example1 {
	
	
	public static void main(String [] args) {
		
		try {
			// open a star file, read it in 
			String cs = readFile("resources/bmrb7170.txt");
			// run it through the scanner
			ParseResult<Character, List<Token>> v5 = Tokenizer.SCANNER.parse(new CharStream(cs));//cs.substring(0, 1000)));
			printIt(v5);

			// have to remove whitespace, newlines, comments
			List<Token> tokens = new ArrayList<Token>();
			for(Token t : v5.getValue()) {
				if(t.type == TokenType.WHITESPACE || t.type == TokenType.COMMENT || t.type == TokenType.NEWLINES) {
					// ditch the token
				} else {
					tokens.add(t);
				}
			}
			
			ParseResult<Token, DataBlock> v6 = ASTParser.dataBlock.parse(new LListList<Token>(tokens));
			printIt2(v6);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		printIt3(Tokenizer.SEMICOLON
//				.seq2R(Tokenizer.NEWLINE.seq2(Literal.of(';')).not1().many0())
//				.seq2L(Tokenizer.NEWLINE.seq2(Literal.of(';')))
//				.parse(new CharStream(";\n1H, 13C, C: Nortst Structural Genomics Cont SR482\n;x")));
	}

	private static void printIt3(ParseResult<Character, List<Character>> r) {
		if(!r.isSuccess()) {
			print("couldn't parse *LINE*: " + r.getErrorMessage());
			return;
		}
		print("rest: " + r.getRestTokens().size() + " " + r.getRestTokens().getHead().get());
		print("number of tokens: " + r.getValue().size());
		print("what we got: <<<" + Tokenizer.getString(r.getValue()) + ">>>");
	}

	private static void printIt(ParseResult<Character, List<Token>> r) {
		if(!r.isSuccess()) {
			print("couldn't parse file");
			return;
		}
		print("rest: " + r.getRestTokens().size());
		print("number of tokens: " + r.getValue().size());
		int j = 0;
		for(Token t : r.getValue()) {
			print("token type: " + t.type + "; value: " + t.value);
			if(j > 30) break;
			j++;
		}
	}

	private static void printIt2(ParseResult<Token, DataBlock> v6) {
		if(!v6.isSuccess()) {
			print("oh noes, parsing failed: ");
			print("tokens left: " + v6.getRestTokens().size());
			LList<Token> toks = v6.getRestTokens();
			int i = 0;
			for(Token t : toks) {
				if(i > 20) break;
				print(t.type + ", " + t.value);
				i++;
			}
			return;
		}
		print("parse rest: " + v6.getRestTokens().size());
	}

	static void print(Object o) {
		System.out.println(o);
	}
	
	
	static String readFile(String path) throws IOException {
		return FileUtils.readFileToString(new File(path));
	}
}

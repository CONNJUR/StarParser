package edu.uchc.connjur.star;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import combs.ParseResult;

import data.CharStream;
import data.LListList;
import edu.uchc.connjur.star.Token.TokenType;

public class StarParser {

	/**
	 * exit error codes:
	 *   - 1: bad command-line args
	 *   - 2: IO input problem
	 *   - 3: tokenization problem
	 *   - 4: syntactic parsing problem
	 *   - 5: IO output problem
	 */
	public static void main(String [] args) {
		// args: 
		//   -h
		//   infile, outfile
		if(args.length == 2) {
			runParser(args[0], args[1]);
		} else if(args.length == 1 && args[0].equals("-h")) {
			printHelp();
			System.exit(0);
		} else {
			print("bad command line args");
			printHelp();
			System.exit(1);
		}
	}
	
	static void runParser(String inPath, String outPath) {
		String cs;
		try {
			// open a star file, read it in 
			cs = readFile(inPath);
		} catch (IOException e) {
			print("sorry, unable to read file.  aborting");
			System.exit(2);
			return; // what?  why do I need a return here?  why isn't System.exit enough?
		}

		// run input through the scanner
		ParseResult<Character, List<Token>> tokenization = Tokenizer.SCANNER.parse(new CharStream(cs));
		
		if(!tokenization.isSuccess()) {
			print("sorry, unable to tokenize star file");
			System.exit(3);
		}

		// have to remove whitespace, newlines, comments
		List<Token> tokens = new ArrayList<Token>();
		for(Token t : tokenization.getValue()) {
			if(t.type == TokenType.WHITESPACE || t.type == TokenType.COMMENT || t.type == TokenType.NEWLINES) {
				// ditch the token
			} else {
				tokens.add(t);
			}
		}
		
		ParseResult<Token, DataBlock> parseTree = ASTParser.dataBlock.parse(new LListList<Token>(tokens));
		
		if(!parseTree.isSuccess()) {
			print("sorry, unable to assemble star file tokens into parse tree");
			System.exit(4);
		}
		
		try {
			Gson gson = new Gson();
			String json = gson.toJson(parseTree.getValue());
//			String json = toJsonOrSomething(parseTree);
			writeFile(outPath, json);
		} catch (IOException e) {
			print("sorry, unable to write output to file");
			System.exit(5);
		}
	}

	private static void printHelp() {
		print("no help yet");
	}

	static void print(Object o) {
		System.out.println(o);
	}
	
	static void writeFile(String path, String contents) throws IOException {
		FileUtils.writeStringToFile(new File(path), contents);
	}
	
	static String readFile(String path) throws IOException {
		return FileUtils.readFileToString(new File(path));
	}
}

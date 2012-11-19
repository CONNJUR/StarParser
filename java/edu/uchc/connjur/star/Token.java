package edu.uchc.connjur.star;

/**
 * @author mattf
 * 
 */
public class Token {
	
	public final TokenType type;
	public final String value;
	
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "token type: " + this.type + " , value: " + this.value;
	}
	
	public static enum TokenType {
		DATA_OPEN, 
		SAVE_OPEN, 
		SAVE_CLOSE, 
		LOOP, 
		STOP, 
		VALUE, 
		WHITESPACE, 
		NEWLINES, 
		COMMENT, 
		IDENTIFIER
	}

}

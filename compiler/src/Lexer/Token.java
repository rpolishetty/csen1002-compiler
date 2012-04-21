package Lexer;


public class Token {
	
	// list of tokens
	public static final int EOF = 0;
	public static final int SM = 1;
	public static final int PO = 2;
	public static final int MO = 3;
	public static final int TO = 4;
	public static final int DO = 5;
	public static final int NM = 6;
	public static final int FA = 7;
	public static final int LP = 8;
	public static final int RP = 9;
	public static final int LB = 10;
	public static final int RB = 11;
	public static final int MD = 12;
	public static final int EQ = 13;
	public static final int NE = 14;
	public static final int LO = 15;
	public static final int LA = 16;
	public static final int KW = 17;
	public static final int ID = 18;
	public static final int BL = 19;
	public static final int ST = 20;
	public static final int AO = 21;
	public static final int InvalidInput = 22;
	public static final int InvalidString = 23;
	public static final int CLASS = 24;
	public static final int STATIC = 25;
	public static final int IF = 26;
	public static final int ELSE = 27;
	public static final int INT = 28;
	public static final int FLOAT = 29;
	public static final int BOOLEAN = 30;
	public static final int STRING = 31;
	public static final int RETURN = 32;
	public static final int WHILE = 33;
	
	private int token; // Type of of token
	private String lexeme; // The lexeme

	public Token(int token, String lexeme) {
		this.token = token;
		this.lexeme = lexeme;
	}

	// Returns the type of the token
	public int getTokenType() {
		return token;
	}

	// Returns the lexeme of the token
	public String getLexeme() {
		return lexeme;
	}

	// Returns a string representation of the token
	public String toString() {
		return token + "\t" + lexeme;
	}
}
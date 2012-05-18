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

	
	private int token; // Type of of token
	private String lexeme; // The lexeme
	public int lineNumber;
	public int charNumber;

	public Token(int token, String lexeme, int lineNumber, int charNumber) {
		this.token = token;
		this.lexeme = lexeme;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber - lexeme.length() ;
	}

	// Returns the type of the token
	public int getTokenType() {
		return token;
	}

	// Returns the lexeme of the token
	public String getLexeme() {
		return lexeme;
	}
	
	public static String returnLexeme(int token) {
		switch (token) {
			case 0: return "EOF";
			case 1: return ";";
			case 2: return "+";
			case 3: return "-";
			case 4: return "*";
			case 5: return "/";
			case 6: return "Number";
			case 7: return ",";
			case 8: return "(";
			case 9: return ")";
			case 10: return "{";
			case 11: return "}";
			case 12: return "%";
			case 13: return "==";
			case 14: return "!=";
			case 15: return "||";
			case 16: return "&&";
			case 17: return "Keyword";
			case 18: return "ID";
			case 19: return "boolean";
			case 20: return "string";
			case 21: return "=";
			
		}
		return null;
	}

	// Returns a string representation of the token
	public String toString() {
		return token + "\t" + lexeme + "\tline: " + lineNumber + "\tchar: " + charNumber;
	}
}
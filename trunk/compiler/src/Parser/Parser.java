package Parser;

import Lexer.LexerManual;
import Lexer.Token;

/*
 * class Parser
 * 
 * Parses the tokens returned from the lexical analyzer.
 * 
 * Update the parser implementation to parse the Decaf
 * language according to the grammar provided. 
 */
public class Parser {

	private LexerManual lexer; // lexical analyzer
	private Token token; // look ahead token
	
	public Parser(LexerManual lex) {
		lexer = lex;
	}
	
	public boolean parse() {
		
		token = lexer.nextToken();
		
		boolean value;
		
		while(token.getTokenType() != Token.EOF) {
			value = classDecl();
			value &= match(Token.SM);
			
			if(!value)
				return false;
		}
		
		return true;
	}
	
	private boolean classDecl(){
		
		boolean value = true;
		
		value &= matchLexeme("class");
		value &= match(Token.ID);
		value &= match(Token.LB);
		value &= methodDecl();
		value &= match(Token.RB);
		
		return value;
	}
	
	private boolean methodDecl() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean expr(){
		boolean value = term();

		while (true) {
			switch (token.getTokenType()) {
			case Token.MO:
				value &= match(token.getTokenType());
				value &= term();
				break;

			case Token.PO:
				value &= match(token.getTokenType());
				value &= term();
				break;

			default:
				return value;
			}
		}
	}

	private boolean term(){
		boolean value = match(Token.NM);

		while (true) {
			switch (token.getTokenType()) {
			
			case Token.DO:
				value &= match(token.getTokenType());
				value &= match(Token.NM);
				break;

			case Token.TO:
				value &= match(token.getTokenType());
				value &= match(Token.NM);
				break;

			default:
				return value;
			}
		}
	}

	private boolean match(int t) {
		if (token.getTokenType() == t) {
			token = lexer.nextToken();
			return true;
		} else {
			return false;
		}
	}
	
	private boolean matchLexeme(String lexeme) {
		if (token.getLexeme().equals(lexeme)) {
			token = lexer.nextToken();
			return true;
		} else {
			return false;
		}
	}
}

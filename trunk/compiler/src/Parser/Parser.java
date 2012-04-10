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
			
			if(!value)
				return false;
		}
		
		return true;
	}
	
	private boolean classDecl(){
		
		boolean value = true;
		
		switch (token.getTokenType()) {
		case Token.KW:
			
			if(token.getLexeme().equals("class")){
		
				value &= match(Token.KW);
				value &= match(Token.ID);
				value &= match(Token.LB);
				value &= methodDecls();
				value &= match(Token.RB);
		
				return value;
			}
		}
		
		return false;
	}
	
	private boolean methodDecls() {
		
		boolean value = true;
		
		while(true){
			switch (token.getTokenType()) {
			case Token.KW:
				
				if(token.getLexeme().equals("static")){
					value &= methodDecl();
				}
				break;

			default:
				return value;
			}			
		}
		
	}

	private boolean methodDecl() {
		
		boolean value = true;
		
		switch (token.getTokenType()) {
		case Token.KW:
			
			if(token.getLexeme().equals("static")){
				value &= match(Token.KW);
				value &= type();
				value &= match(Token.ID);
				value &= match(Token.LP);
				value &= formalParams();
				value &= match(Token.RP);
				value &= block();
		
				return value;
			}
		}
		return false;
	}

	private boolean type() {
			
		switch (token.getTokenType()) {
		case Token.KW:
				
			if(token.getLexeme().equals("int") || token.getLexeme().equals("float") 
					|| token.getLexeme().equals("boolean") || token.getLexeme().equals("String"))
				
				return true;
		}
		
		return false;
	}

	private boolean formalParams() {
		
		boolean value = true;
		
		if(type()){
			value &= properFormalParams();
		}
		
		return value;
	}
	
	private boolean properFormalParams() {
		
		boolean value = formalParam();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.FA:
				value &= match(token.getTokenType());
				value &= formalParam();
				break;
				
			default:
				return value;	
			}
		}
	}

	private boolean formalParam() {
		
		boolean value = true;
		
		value &= match(Token.KW);
		value &= match(Token.ID);
		
		return value;
	}
	

	private boolean block() {
		
		boolean value = true;
				
		value &= match(Token.LB);
		value &= statements();
		value &= match(Token.RB);
		
		return value;
	}

	private boolean statements() {
		
		boolean value = true;
		
		while(true){
			switch (token.getTokenType()) {
			case Token.LB:
			case Token.ID:
			case Token.KW:
				if(token.getLexeme().equals("else") || token.getLexeme().equals("static") 
						|| token.getLexeme().equals("class"))
					return false;
				
				value &= statement();
				break;

			default:
				return value;
			}			
		}
	}

	private boolean statement() {
		
		boolean value = true;
		
		switch (token.getTokenType()) {
		case Token.KW:
			if(token.getLexeme().equals("if"))
				value &= ifStmt();
			else
				value &= stmtWithoutIf();
			break;
		
		case Token.LB:
		case Token.ID:
			value &= stmtWithoutIf();
			break;
		}
		
		return value;
	}

	private boolean stmtWithoutIf() {
		
		boolean value = true;
		
		switch (token.getTokenType()) {
		case Token.LB:
			value &= block();
			break;
		
		case Token.ID:
			value &= assignStmt();
			break;
			
		case Token.KW:
			if(token.getLexeme().equals("while"))
				value &= whileStmt();
			
			else if(token.getLexeme().equals("return"))
				value &= returnStmt();
			
			else
				value &= localVarDecl();
			
			break;
		}
		
		return value;
	}

	private boolean localVarDecl() {
		
		boolean value = type();
		
		value &= match(Token.KW);
		value &= match(Token.ID);
		value &= match(Token.SM);
		
		return value;
	}

	private boolean assignStmt() {
		
		boolean value = true;
		
		value &= match(Token.ID);
		value &= match(Token.AO);
		value &= expression();
		value &= match(Token.SM);
		
		return value;
	}
	
	private boolean whileStmt() {
		
		boolean value = true;
		
		value &= match(Token.KW);
		value &= match(Token.LP);
		value &= expression();
		value &= match(Token.RP);
		value &= statement();
		
		return value;
	}

	private boolean returnStmt() {
		
		boolean value = true;
		
		value &= match(Token.KW);
		value &= expression();
		value &= match(Token.SM);
		
		return value;
	}


	private boolean ifStmt() {
		
		boolean value = matchedStmt() || openStmt(); 
		
		return value;
	}
	
	private boolean openStmt() {
		
		boolean value = true;
		
		value &= match(Token.KW);
		value &= match(Token.LP);
		value &= expression();
		value &= match(Token.RP);
		
		switch (token.getTokenType()) {
		
		case Token.KW:
			if(token.getLexeme().equals("if"))
				value &= ifStmt();
			
			else{
				value &= matchedStmt();
				switch (token.getTokenType()) {
				
				case Token.KW:
					if(token.getLexeme().equals("else"))
						value &= openStmt();
					else
						return false;
					}
				}
		}
		
		return value;
	}

	private boolean matchedStmt() {
		
		boolean value = true;
		
		value &= match(Token.KW);
		value &= match(Token.LP);
		value &= expression();
		value &= match(Token.RP);
		
		switch (token.getTokenType()) {
		
		case Token.KW:
			if(token.getLexeme().equals("if")){
				
				value &= matchedStmt();
				switch (token.getTokenType()) {
				
				case Token.KW:
					if(token.getLexeme().equals("else"))
						value &= matchedStmt();
					else
						return false;
					}
				}
			
			else
				value &= stmtWithoutIf();
		}
		
		return value;
	}

	private boolean expression() {
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
}

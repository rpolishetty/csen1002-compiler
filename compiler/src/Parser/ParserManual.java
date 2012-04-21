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
public class ParserManual {

	private LexerManual lexer; // lexical analyzer
	private Token token; // look ahead token
	
	public ParserManual(LexerManual lex) {
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
		
		
		if(token.getLexeme().equals("class")){
	
			value &= match(Token.KW);
			value &= match(Token.ID);
			value &= match(Token.LB);
			value &= methodDecls();
			value &= match(Token.RB);
			
			return value;
		}
		
		return false;
	}
	
	private boolean methodDecls() {
		
		boolean value = true;
		
		while(true){
			
			if(token.getLexeme().equals("static"))
				value &= methodDecl();
			else
				return value;
			
		}
		
	}

	private boolean methodDecl() {
		
		boolean value = true;
		
		value &= match(Token.KW);
		value &= type();
		value &= match(Token.KW);
		value &= match(Token.ID);				
		value &= match(Token.LP);
		value &= formalParams();
		value &= match(Token.RP);
		value &= block();
			
		return value;
	}

	private boolean type() {
		
		if(token.getLexeme().equals("int") || token.getLexeme().equals("float") 
				|| token.getLexeme().equals("boolean") || token.getLexeme().equals("String"))
			return true;
		else
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
				value &= statement();
			case Token.KW:
				if(token.getLexeme().equals("while") || token.getLexeme().equals("return")
						|| token.getLexeme().equals("if") || type()){
					value &= statement();
					break;
				}
			default:
				return value;
			}			
		}
	}
	
	private boolean statement() {
		
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
			
			else if(token.getLexeme().equals("if"))
				value &= ifStmt();
			
			else
				value &= localVarDecl();
			break;
		default:
			return false;
		}
		
		return value;
	}

	private boolean localVarDecl() {
		
		boolean value = true;
		
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
		
		boolean value = true;
		
			value &= match(Token.KW);
			value &= match(Token.LP);
			value &= expression();
			value &= match(Token.RP);
			value &= statement();
			
			if(token.getLexeme().equals("else")){
				value &= match(Token.KW);
				value &= statement();
			}
			return value;		
	}
	
	private boolean expression() {
		
		boolean value = conditionalAndExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.LO:
				value &= match(token.getTokenType());
				value &= conditionalAndExpr();
				break;
				
			default:
				return value;	
			}
		}
	}

	private boolean conditionalAndExpr() {

		boolean value = equalityExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.LA:
				value &= match(token.getTokenType());
				value &= equalityExpr();
				break;
				
			default:
				return value;	
			}
		}
	}

	private boolean equalityExpr() {
		
		boolean value = additiveExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.EQ:
			case Token.NE:
				value &= match(token.getTokenType());
				value &= additiveExpr();
				break;
				
			default:
				return value;	
			}
		}
		
	}

	private boolean additiveExpr() {
		
		boolean value = multiplicativeExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.PO:
			case Token.MO:
				value &= match(token.getTokenType());
				value &= multiplicativeExpr();
				break;
				
			default:
				return value;	
			}
		}
	}

	private boolean multiplicativeExpr() {

		boolean value = primaryExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.TO:
			case Token.DO:
			case Token.MD:
				value &= match(token.getTokenType());
				value &= primaryExpr();
				break;
				
			default:
				return value;	
			}
		}
	}

	private boolean primaryExpr() {
		
		boolean value = true;
		
		switch (token.getTokenType()) {
		case Token.NM:
		case Token.BL:
		case Token.ST:
			value &= match(token.getTokenType());
			break;
			
		case Token.ID:
			value &= match(token.getTokenType());
			
			if(token.getTokenType() == Token.LP)
				value &= callExpr();
				
			break;
			
		case Token.LP:
			value &= match(Token.LP);
			value &= expression();
			value &= match(Token.RP);
			break;
			
		default: 
			return false;
		}
		
		return value;
	}

	private boolean callExpr() {
		
		boolean value = true;
		
		value &= match(Token.LP);
		value &= actualParams();
		value &= match(Token.RP);
		
		return value;
	}

	private boolean actualParams() {
		
		boolean value = true;
		
		switch (token.getTokenType()) {
		case Token.NM:
		case Token.BL:
		case Token.ST:
		case Token.ID:
		case Token.LP:
			value &= properActualParams();
			break;
		}
		return value;
	}

	private boolean properActualParams() {
		
		boolean value = expression();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.FA:
				value &= match(token.getTokenType());
				value &= expression();
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

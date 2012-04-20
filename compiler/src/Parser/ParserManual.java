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
		
		switch (token.getTokenType()) {
		case Token.KW:
			
			if(token.getLexeme().equals("class")){
		
				value &= match(Token.KW);
				value &= match(Token.ID);
				value &= match(Token.LB);
				value &= methodDecls();
				value &= match(Token.RB);
				
				if(!value)
					System.out.println("class");
				
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
				if(!value)
					System.out.println("methods");
				return value;
			}			
		}
		
	}

	private boolean methodDecl() {
		
		boolean value = true;
		
		switch (token.getTokenType()) {
		case Token.KW:
			
			value &= match(Token.KW);
			value &= type();
			value &= match(Token.KW);
			value &= match(Token.ID);				
			value &= match(Token.LP);
			value &= formalParams();
			value &= match(Token.RP);
			value &= block();
				
			if(!value)
				System.out.println("method");
				
				return value;
			
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
			
			if(!value)
				System.out.println("fparams");
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
				if(!value)
					System.out.println("pfparams");
				return value;	
			}
		}
	}

	private boolean formalParam() {
		
		boolean value = true;
		
		value &= match(Token.KW);
		value &= match(Token.ID);
		
		if(!value)
			System.out.println("fparam");
		
		return value;
	}
	

	private boolean block() {
		
		boolean value = true;
				
		value &= match(Token.LB);
		value &= statements();
		value &= match(Token.RB);
		
		if(!value)
			System.out.println("block");
		return value;
	}

	private boolean statements() {
		
		boolean value = true;
		
		while(true){
			switch (token.getTokenType()) {
			case Token.LB:
			case Token.ID:
			case Token.KW:
				value &= statement();
				break;

			default:
				if(!value)
					System.out.println("stmts");
			
				return value;
			}			
		}
	}

	private boolean statement() {
		
		boolean value = true; //openStmt();// || matchedStmt(); //|| ; 
		
		if(!value)
			System.out.println("stmt");
		return value;
	}
	
	private boolean openStmt() {
		
		boolean value = true;
		System.out.println(token.getLexeme());
		if(token.getLexeme().equals("if")){
			value &= match(Token.KW);
			value &= match(Token.LP);
			value &= expression();
			value &= match(Token.RP);
		
			switch (token.getTokenType()) {
		
			case Token.KW:
				if(token.getLexeme().equals("if"))
					value &= statement();
				
				else{
					value &= matchedStmt();
				
					if(token.getLexeme().equals("else")){
						value &= match(Token.KW);
						value &= openStmt();
					}
				
					else{
				
						System.out.println("os");
						return false;
						}
					}	
			}
		}
		
		else{
			System.out.println("os2");
			return false;
		}
		
		if(!value)
			System.out.println("os1");
		return value;
	}
	
	private boolean matchedStmt() {
		
		boolean value = true;
		
		switch (token.getTokenType()) {
		
		case Token.KW:
			if(token.getLexeme().equals("if")){
				
				value &= match(Token.KW);
				value &= match(Token.LP);
				value &= expression();
				value &= match(Token.RP);
				
				value &= matchedStmt();
				
				if(token.getLexeme().equals("else")){
					value &= match(Token.KW);
					value &= matchedStmt();
				}
				
				else{
					
					System.out.println("ms");
					return false;
					}
				}
			
			else
				value &= stmtWithoutIf();
		}
		
		if(!value)
			System.out.println("ms1");
		
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
		
		if(!value)
			System.out.println("swif");
		
		return value;
	}

	private boolean localVarDecl() {
		
		boolean value = true;
		
		value &= match(Token.KW);
		value &= match(Token.ID);
		value &= match(Token.SM);
		
		if(!value)
			System.out.println("local");
		
		return value;
	}

	private boolean assignStmt() {
		
		boolean value = true;
		
		value &= match(Token.ID);
		value &= match(Token.AO);
		value &= expression();
		value &= match(Token.SM);
		
		if(!value)
			System.out.println("assign");
		
		return value;
	}
	
	private boolean whileStmt() {
		
		boolean value = true;
		
		value &= match(Token.KW);
		value &= match(Token.LP);
		value &= expression();
		value &= match(Token.RP);
		value &= statement();
		
		if(!value)
			System.out.println("while");
		
		return value;
	}

	private boolean returnStmt() {
		
		boolean value = true;
		
		value &= match(Token.KW);
		value &= expression();
		value &= match(Token.SM);
		
		
		if(!value)
			System.out.println("return");
		
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
				if(!value)
					System.out.println("exp");
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
				if(!value)
					System.out.println("condexp");
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
				if(!value)
					System.out.println("eqexp");
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
				if(!value)
					System.out.println("addexp");
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
				if(!value)
					System.out.println("multexp");
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
			
			System.out.println("prexp");
			return false;
		}
		
		if(!value)
			System.out.println("prexp1");
		
		return value;
	}

	private boolean callExpr() {
		
		boolean value = true;
		
		value &= match(Token.LP);
		value &= actualParams();
		value &= match(Token.RP);
		
		if(!value)
			System.out.println("callexp");
		
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
		
		if(!value)
			System.out.println("actParams");
		
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
				if(!value)
					System.out.println("pactparams");
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

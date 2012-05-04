package Parser;

import java.util.ArrayList;

import Lexer.LexerManual;
import Lexer.Token;
import ParserObjects.*;

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
	
	public ClassDecl parse() throws SyntacticException {
		
		token = lexer.nextToken();
		
		ClassDecl cd = new ClassDecl();
		
		while(token.getTokenType() != Token.EOF) {
			cd = classDecl();
		}
		return cd;
	}
	
	private ClassDecl classDecl() throws SyntacticException{
		
		MethodDecls mds;
		
		if(token.getLexeme().equals("class")){
			match(Token.KW);
			match(Token.ID);
			match(Token.LB);
			mds = methodDecls();
			match(Token.RB);
			
			return new ClassDecl(mds);
		}
		
		throw new SyntacticException("Error");
	}
	
	private MethodDecls methodDecls() throws SyntacticException {
		
		ArrayList<MethodDecl> md = new ArrayList<MethodDecl>();
		
		while(true){
			
			if(token.getLexeme().equals("static"))
				md.add(methodDecl());
			else
				return new MethodDecls(md);
		}
		
	}

	private MethodDecl methodDecl() throws SyntacticException {
		
		match(Token.KW);
		Type t = type();
		match(Token.KW);
		String id = token.getLexeme();
		match(Token.ID);				
		match(Token.LP);
		FormalParams fps = formalParams();
		match(Token.RP);
		Block b = block();
			
		return new MethodDecl(t,id,fps,b);
	}

	private Type type() {
		
		if(token.getLexeme().equals("int") || token.getLexeme().equals("float") 
				|| token.getLexeme().equals("boolean") || token.getLexeme().equals("String"))
			return true;
		else
			return false;
	}

	private FormalParams formalParams() {
		
		boolean value = true;
		
		if(type()){
			value &= properFormalParams();
			
		}
		
		return value;
	}
	
	private boolean properFormalParams() throws SyntacticException {
		
		boolean value = formalParam();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.FA:
				match(token.getTokenType());
				value &= formalParam();
				break;
				
			default:
				return value;	
			}
		}
	}

	private boolean formalParam() {
		
		boolean value = true;
		
		match(Token.KW);
		match(Token.ID);
		
		return value;
	}
	

	private Block block() {
		
		boolean value = true;
				
		match(Token.LB);
		value &= statements();
		match(Token.RB);
		
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

	private void match(int t) throws SyntacticException {
		if (token.getTokenType() == t) {
			token = lexer.nextToken();
			
		} else {
			throw new SyntacticException("Error");
		}
	}
}

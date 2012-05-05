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
		
		Type t;
		String id;
		FormalParams fps;
		Block b;
		
		match(Token.KW);
		t = type();
		match(Token.KW);
		id = token.getLexeme();
		match(Token.ID);				
		match(Token.LP);
		fps = formalParams();
		match(Token.RP);
		b = block();
			
		return new MethodDecl(t,id,fps,b);
	}

	private Type type() throws SyntacticException {
		
		if(token.getLexeme().equals("int"))
			return new Type(Type.INT);
		
		else if(token.getLexeme().equals("float"))
			return new Type(Type.FL);
			
		else if(token.getLexeme().equals("boolean"))
			return new Type(Type.BL);
		
		else if(token.getLexeme().equals("String"))
			return new Type(Type.ST);
		
		else
			return null;
	}

	private FormalParams formalParams() throws SyntacticException {
		
		Type t = type();
		
		if(t != null)
			return new FormalParams(properFormalParams());
		
		return new FormalParams();
	}
	
	private ProperFormalParams properFormalParams() throws SyntacticException {
		
		ArrayList<FormalParam> fp = new ArrayList<FormalParam>();
		
		fp.add(formalParam());
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.FA:
				match(token.getTokenType());
				fp.add(formalParam());
				break;
				
			default:
				return new ProperFormalParams(fp);	
			}
		}
	}

	private FormalParam formalParam() throws SyntacticException {
	
		String t = token.getLexeme();
		match(Token.KW);
		
		String id = token.getLexeme();
		match(Token.ID);
		
		return new FormalParam(new Type(t) , id);
	}
	

	private Block block() throws SyntacticException {
	
		Statements sts;
		
		match(Token.LB);
		sts = statements();
		match(Token.RB);
		
		return new Block(sts);
	}

	private Statements statements() throws SyntacticException {
		
		ArrayList<Statement> stmt = new ArrayList<Statement>();
		
		while(true){
			switch (token.getTokenType()) {
			case Token.LB:
			case Token.ID:
				stmt.add(statement());
			case Token.KW:
				if(token.getLexeme().equals("while") || token.getLexeme().equals("return")
						|| token.getLexeme().equals("if") || token.getLexeme().equals("int")
						|| token.getLexeme().equals("float") || token.getLexeme().equals("boolean")
						|| token.getLexeme().equals("String")){
					stmt.add(statement());
					break;
				}
			default:
				return new Statements(stmt);
			}
		}
	}
	
	private Statement statement() throws SyntacticException {
		
		Statement stmt;
		
		switch (token.getTokenType()) {
		case Token.LB:
			stmt = new Statement(block());
			break;
		
		case Token.ID:
			stmt = new Statement(assignStmt());
			break;
			
		case Token.KW:
			if(token.getLexeme().equals("while"))
				stmt = new Statement(whileStmt());
			
			else if(token.getLexeme().equals("return"))
				stmt = new Statement(returnStmt());
			
			else if(token.getLexeme().equals("if"))
				stmt = new Statement(ifStmt());
			
			else
				stmt = new Statement(localVarDecl());
			break;
		default:
			throw new SyntacticException("Error");
		}
		
		return stmt;
	}

	private LocalVarDecl localVarDecl() throws SyntacticException {
	
		String t = token.getLexeme();
		match(Token.KW);
		
		String id = token.getLexeme();
		match(Token.ID);
		match(Token.SM);
		
		return new LocalVarDecl(new Type(t), id);
	}

	private AssignStmt assignStmt() throws SyntacticException {

		String id = token.getLexeme();
		Expression exp;
		
		match(Token.ID);
		match(Token.AO);
		exp = expression();
		match(Token.SM);
		
		return new AssignStmt(id, exp);
	}
	
	private WhileStmt whileStmt() throws SyntacticException {
		
		Expression exp;
		Statement stmt;
		
		match(Token.KW);
		match(Token.LP);
		exp = expression();
		match(Token.RP);
		stmt = statement();

		return new WhileStmt(exp, stmt);
	}

	private ReturnStmt returnStmt() throws SyntacticException {
		
		Expression exp;
		
		match(Token.KW);
		exp = expression(); 
		match(Token.SM);
		
		return new ReturnStmt(exp);
	}
	
	private IfStmt ifStmt() throws SyntacticException {
		
		Expression exp;
		Statement ifStmt;
		Statement elseStmt;
		
		match(Token.KW);
		match(Token.LP);
		exp = expression();
		match(Token.RP);
		ifStmt = statement();
		
		if(token.getLexeme().equals("else")){
			match(Token.KW);
			elseStmt = statement();
			
			return new IfStmt(exp, ifStmt, elseStmt);
		}
		
		else
			return new IfStmt(exp, ifStmt);		
	}
	
	private Expression expression() throws SyntacticException {
		
		Expression exp = conditionalAndExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.LO:
				match(token.getTokenType());
				exp = new Expression(conditionalAndExpr(), Expression.LO, exp);
				break;
				
			default:
				return exp;	
			}
		}
	}

	private ConditionalAndExpr conditionalAndExpr() throws SyntacticException {

		ConditionalAndExpr exp = equalityExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.LA:
				match(token.getTokenType());
				exp = new ConditionalAndExpr(equalityExpr(), ConditionalAndExpr.LA, exp);
				break;
				
			default:
				return exp;	
			}
		}
	}

	private EqualityExpr equalityExpr() throws SyntacticException {
		
		EqualityExpr exp = additiveExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.EQ:
				match(token.getTokenType());
				exp = new EqualityExpr(additiveExpr(), EqualityExpr.EQ, exp);
				break;
			case Token.NE:
				match(token.getTokenType());
				exp = new EqualityExpr(additiveExpr(), EqualityExpr.NE, exp);
				break;
				
			default:
				return exp;	
			}
		}
		
	}

	private AdditiveExpr additiveExpr() throws SyntacticException {
		
		AdditiveExpr exp = multiplicativeExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.PO:
				match(token.getTokenType());
				exp = new AdditiveExpr(multiplicativeExpr(), AdditiveExpr.PO, exp);
				break;
			case Token.MO:
				match(token.getTokenType());
				exp = new AdditiveExpr(multiplicativeExpr(), AdditiveExpr.MO, exp);
				break;
				
			default:
				return exp;	
			}
		}
	}

	private MultiplicativeExpr multiplicativeExpr() throws SyntacticException {

		MultiplicativeExpr exp = primaryExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.TO:
				match(token.getTokenType());
				exp = new MultiplicativeExpr(primaryExpr(), MultiplicativeExpr.TO, exp);
				break;
			case Token.DO:
				match(token.getTokenType());
				exp = new MultiplicativeExpr(primaryExpr(), MultiplicativeExpr.DO, exp);
				break;
			case Token.MD:
				match(token.getTokenType());
				exp = new MultiplicativeExpr(primaryExpr(), MultiplicativeExpr.MD, exp);
				break;
				
			default:
				return exp;	
			}
		}
	}

	private PrimaryExpr primaryExpr() throws SyntacticException {
		
		PrimaryExpr exp;
		
		String tk = token.getLexeme();
		
		switch (token.getTokenType()) {
		case Token.NM:
			match(token.getTokenType());
			
			if(tk.contains("."))
				exp = new PrimaryExpr(Float.parseFloat(tk));
			
			else
				exp = new PrimaryExpr(Integer.parseInt(tk));
			break;
		case Token.BL:
			match(token.getTokenType());
			exp = new PrimaryExpr(Boolean.parseBoolean(tk));
			break;
		case Token.ST:
			match(token.getTokenType());
			exp = new PrimaryExpr(tk);
			break;
			
		case Token.ID:
			match(token.getTokenType());
			
			if(token.getTokenType() == Token.LP)
				exp = new PrimaryExpr(callExpr(tk));
			
			else
				exp = new PrimaryExpr(tk);
				
				
			break;
			
		case Token.LP:
			match(Token.LP);
			exp = new PrimaryExpr(expression());
			match(Token.RP);
			break;
			
		default: 
			throw new SyntacticException("Error");
		}
		
		return exp;
	}

	private CallExpr callExpr(String id) throws SyntacticException {
		
		ActualParams aps;
		
		match(Token.LP);
		aps = actualParams();
		match(Token.RP);
		
		return new CallExpr(id, aps);
	}

	private ActualParams actualParams() throws SyntacticException {
		
		ActualParams aps = new ActualParams();
		
		switch (token.getTokenType()) {
		case Token.NM:
		case Token.BL:
		case Token.ST:
		case Token.ID:
		case Token.LP:
			aps = new ActualParams(properActualParams());
			break;
				
		}
		return aps;
	}

	private ProperActualParams properActualParams() throws SyntacticException {
		
		ArrayList<Expression> ap = new ArrayList<Expression>();
		
		ap.add(expression());
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.FA:
				match(token.getTokenType());
				ap.add(expression());
				break;
				
			default:
				return new ProperActualParams(ap);	
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

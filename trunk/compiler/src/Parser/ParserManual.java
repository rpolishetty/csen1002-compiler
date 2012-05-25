package Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	int lineNumber = 0;
	
	public ParserManual(LexerManual lex) {
		lexer = lex;
	}
	
	public ClassDecl parse() throws SyntacticException, SemanticException {
		
		token = lexer.nextToken();
		
		ClassDecl cd = new ClassDecl();
		
		while(token.getTokenType() != Token.EOF) {
			cd = classDecl();
		}
		return cd;
	}
	
	private ClassDecl classDecl() throws SyntacticException, SemanticException{
		
		String id;
		MethodDecls mds;
		int line;
		int charN;
		
		if(token.getLexeme().equals("class")){
			line = lineNumber;
			match(Token.KW, "#class");
			id = token.getLexeme();
			charN = token.charNumber;
			match(Token.ID, "ID");
			match(Token.LB, "");
			mds = methodDecls();
			match(Token.RB, "");
			
			return new ClassDecl(id,mds,line,charN);
		}
		errorReport(Token.KW, "#class");
		return null;
	}
	
	private MethodDecls methodDecls() throws SyntacticException, SemanticException {
		
		int line;
		ArrayList<MethodDecl> md = new ArrayList<MethodDecl>();
		
		while(true){
			
			if(token.getLexeme().equals("static"))
				md.add(methodDecl());
			else
				return new MethodDecls(md,0);
		}
		
	}

	private MethodDecl methodDecl() throws SyntacticException, SemanticException {
		
		Type t;
		String id;
		FormalParams fps;
		Block b;
		int line;
		int charN;
		line = lineNumber;
		match(Token.KW, "#static");
		t = type();
		match(Token.KW, "type");
		charN = token.charNumber;
		id = token.getLexeme();
		match(Token.ID, "ID");				
		match(Token.LP, "");
		fps = formalParams();
		match(Token.RP, "");
		b = block();
			
		return new MethodDecl(t,id,fps,b,line,charN);
	}

	private Type type() throws SyntacticException {
		int line;
		
		line = lineNumber;
		if(token.getLexeme().equals("int"))
			return new Type(Type.INT,line);
		
		else if(token.getLexeme().equals("float"))
			return new Type(Type.FLOAT,line);
			
		else if(token.getLexeme().equals("boolean"))
			return new Type(Type.BOOL,line);
		
		else if(token.getLexeme().equals("String"))
			return new Type(Type.STRING,line);
		
		else
			return null;
	}

	private FormalParams formalParams() throws SyntacticException {
		
		Type t = type();
		int line;
		
		line = lineNumber;
		if(t != null)
			return new FormalParams(properFormalParams(),line);
		
		return new FormalParams();
	}
	
	private ProperFormalParams properFormalParams() throws SyntacticException {
		
		ArrayList<FormalParam> fp = new ArrayList<FormalParam>();
		
		fp.add(formalParam());
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.FA:
				match(token.getTokenType(), "");
				fp.add(formalParam());
				break;
				
			default:
				return new ProperFormalParams(fp,0);	
			}
		}
	}

	private FormalParam formalParam() throws SyntacticException {
		
		int line;
		
		line = lineNumber;
		String t = token.getLexeme();
		match(Token.KW, "type");
		int charN;
		charN = token.charNumber;
		String id = token.getLexeme();
		match(Token.ID, "ID");
		
		int t2;
		if(t.equals("int"))
			t2 = 1;
		else if(t.equals("float"))
			t2 = 2;
		else if(t.equals("boolean"))
			t2 = 3;
		else
			t2 = 4;
		return new FormalParam(new Type(t2,line) , id,line, charN);
	}
	

	private Block block() throws SyntacticException, SemanticException {
	
		Statements sts;
		int line;
		
		line = lineNumber;
		match(Token.LB, "");
		sts = statements();
		match(Token.RB, "");
		
		return new Block(sts,line);
	}

	private Statements statements() throws SyntacticException, SemanticException {
		
		ArrayList<Statement> stmt = new ArrayList<Statement>();
		int line;
		
		line = lineNumber;
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
				return new Statements(stmt,line);
			}
		}
	}
	
	private Statement statement() throws SyntacticException, SemanticException {
		int line;
		int charN;
		line = lineNumber;
		Statement stmt = null;
		
		switch (token.getTokenType()) {
		case Token.LB:
			charN = token.charNumber;
			stmt = new Statement(block(),line,charN);
			break;
		
		case Token.ID:
			stmt = new Statement(assignStmt(),line);
			break;
			
		case Token.KW:
			if(token.getLexeme().equals("while"))
				stmt = new Statement(whileStmt(),line);
			
			else if(token.getLexeme().equals("return"))
				stmt = new Statement(returnStmt(),line);
			
			else if(token.getLexeme().equals("if"))
				stmt = new Statement(ifStmt(),line);
			
			else
				stmt = new Statement(localVarDecl(),line);
			break;
		default:
			errorReport(-1, "Statement");
		}
		
		return stmt;
	}

	private LocalVarDecl localVarDecl() throws SyntacticException {
		int line;
		int charN;
		
		line = lineNumber;
		String t = token.getLexeme();
		match(Token.KW, "type");
		
		charN = token.charNumber;
		String id = token.getLexeme();
		match(Token.ID, "ID");
		match(Token.SM, "");
		
		int t2;
		if(t.equals("int"))
			t2 = 1;
		else if(t.equals("float"))
			t2 = 2;
		else if(t.equals("boolean"))
			t2 = 3;
		else
			t2 = 4;
		
		return new LocalVarDecl(new Type(t2,line), id,line, charN);
	}

	private AssignStmt assignStmt() throws SyntacticException, SemanticException {
		int line;
		int charN;
		
		line = lineNumber;
		String id = token.getLexeme();
		Expression exp;
		charN = token.charNumber;
		match(Token.ID, "ID");
		match(Token.AO, "");
		exp = expression();
		match(Token.SM, "");
		
		return new AssignStmt(id, exp,line,charN);
	}
	
	private WhileStmt whileStmt() throws SyntacticException, SemanticException {
		int line;
		int charN;
		line = lineNumber;
		Expression exp;
		Statement stmt;
		
		match(Token.KW, "#while");
		charN = token.charNumber;
		match(Token.LP, "");
		exp = expression();
		match(Token.RP, "");
		stmt = statement();

		return new WhileStmt(exp, stmt,line,charN);
	}

	private ReturnStmt returnStmt() throws SyntacticException, SemanticException {
		int line;
		int charN;
		
		line = lineNumber;
		Expression exp;
		charN = token.charNumber;
		match(Token.KW, "#return");
		exp = expression(); 
		match(Token.SM, "");
		
		return new ReturnStmt(exp,line,charN);
	}
	
	private IfStmt ifStmt() throws SyntacticException, SemanticException {
		int line;
		int charN;
		
		line = lineNumber;
		Expression exp;
		Statement ifStmt;
		Statement elseStmt;
		
		match(Token.KW, "#if");
		charN = token.charNumber;
		match(Token.LP, "");
		exp = expression();
		match(Token.RP, "");
		ifStmt = statement();
		
		if(token.getLexeme().equals("else")){
			match(Token.KW, "#else");
			elseStmt = statement();
			
			return new IfStmt(exp, ifStmt, elseStmt,line, charN);
		}
		
		else
			return new IfStmt(exp, ifStmt,line, charN);		
	}
	
	private Expression expression() throws SyntacticException, SemanticException {
		int line;
		int charN;
		charN = token.charNumber;
		line = lineNumber;
		Expression exp = conditionalAndExpr();
		while (true) {
			switch (token.getTokenType()) {	
			case Token.LO:
				match(token.getTokenType() , "");
				exp = new Expression(conditionalAndExpr(), Expression.LO, exp,line,charN);
				break;
				
			default:
				return exp;	
			}
		}
	}

	private ConditionalAndExpr conditionalAndExpr() throws SyntacticException, SemanticException {
		int line;
		
		line = lineNumber;
		ConditionalAndExpr exp = equalityExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.LA:
				match(token.getTokenType(), "");
				exp = new ConditionalAndExpr(equalityExpr(), Expression.LA, exp,line);
				break;
				
			default:
				return exp;	
			}
		}
	}

	private EqualityExpr equalityExpr() throws SyntacticException, SemanticException {
		int line;
		
		line = lineNumber;
		EqualityExpr exp = additiveExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.EQ:
				match(token.getTokenType(), "");
				exp = new EqualityExpr(additiveExpr(), Expression.EQ, exp,line);
				break;
			case Token.NE:
				match(token.getTokenType(), "");
				exp = new EqualityExpr(additiveExpr(), Expression.NE, exp,line);
				break;
				
			default:
				return exp;	
			}
		}
		
	}

	private AdditiveExpr additiveExpr() throws SyntacticException, SemanticException {
		int line;
		
		line = lineNumber;
		AdditiveExpr exp = multiplicativeExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.PO:
				match(token.getTokenType(), "");
				exp = new AdditiveExpr(multiplicativeExpr(), Expression.PO, exp,line);
				break;
			case Token.MO:
				match(token.getTokenType(), "");
				exp = new AdditiveExpr(multiplicativeExpr(), Expression.MO, exp,line);
				break;
				
			default:
				return exp;	
			}
		}
	}

	private MultiplicativeExpr multiplicativeExpr() throws SyntacticException, SemanticException {
		int line;
		
		line = lineNumber;
		MultiplicativeExpr exp = primaryExpr();
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.TO:
				match(token.getTokenType(), "");
				exp = new MultiplicativeExpr(primaryExpr(), Expression.TO, exp,line);
				break;
			case Token.DO:
				match(token.getTokenType(), "");
				exp = new MultiplicativeExpr(primaryExpr(), Expression.DO, exp,line);
				break;
			case Token.MD:
				match(token.getTokenType(), "");
				exp = new MultiplicativeExpr(primaryExpr(), Expression.MD, exp,line);
				break;
				
			default:
				return exp;	
			}
		}
	}

	private PrimaryExpr primaryExpr() throws SyntacticException, SemanticException {
		int line;
		int charN;
		
		line = lineNumber;
		PrimaryExpr exp = null;
		
		String tk = token.getLexeme();
		
		switch (token.getTokenType()) {
		case Token.NM:
			match(token.getTokenType(), "a number");
			
			if(tk.contains("."))
				exp = new PrimaryExpr(Float.parseFloat(tk),line);
			
			else
				exp = new PrimaryExpr(Integer.parseInt(tk),line);
			break;
		case Token.BL:
			match(token.getTokenType(), "a boolean");
			exp = new PrimaryExpr(Boolean.parseBoolean(tk),line);
			break;
		case Token.ST:
			match(token.getTokenType(), "a string");
			exp = new PrimaryExpr(tk,line);
			break;
			
		case Token.ID:
			charN = token.charNumber;
			match(token.getTokenType(), "ID");
			
			if(token.getTokenType() == Token.LP)
				exp = callExpr(tk);
			
			else
				exp = new PrimaryExpr("id",tk,line,charN);
				
				
			break;
			
		case Token.LP:
			match(Token.LP, "");
			exp = new PrimaryExpr(expression(),line);
			match(Token.RP, "");
			break;
			
		default: 
			errorReport(Token.KW, "Expression");
		}
		
		return exp;
	}

	private CallExpr callExpr(String id) throws SyntacticException, SemanticException {
		int line;
		int charN = token.charNumber;
		line = lineNumber;
		ActualParams aps;
		
		match(Token.LP, "");
		aps = actualParams();
		match(Token.RP, "");
		
		return new CallExpr(id, aps,line, charN);
	}

	private ActualParams actualParams() throws SyntacticException, SemanticException {
		int line;
		
		line = lineNumber;
		ActualParams aps = new ActualParams();
		
		switch (token.getTokenType()) {
		case Token.NM:
		case Token.BL:
		case Token.ST:
		case Token.ID:
		case Token.LP:
			aps = new ActualParams(properActualParams(),line);
			break;
				
		}
		return aps;
	}

	private ProperActualParams properActualParams() throws SyntacticException, SemanticException {
		int line;
		
		line = lineNumber;
		ArrayList<Expression> ap = new ArrayList<Expression>();
		
		ap.add(expression());
		
		while (true) {
			switch (token.getTokenType()) {	
			case Token.FA:
				match(token.getTokenType(), "");
				ap.add(expression());
				break;
				
			default:
				return new ProperActualParams(ap,line);	
			}
		}
	}
	
	private void errorReport(int t, String message) throws SyntacticException {
		String inFile = "/Users/michaelmkamal/Documents/workspace/compiler-3/src/Lexer/Algebra.decaf";
		try {
			String error = "";
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(inFile));
			String line = "";
			for(int i = 1; i <= token.lineNumber; i++)
				line = reader.readLine().trim();
			
			error += "\nSyntax Error at line " + token.lineNumber + " char " + token.charNumber + ":\n";
			if(message.isEmpty())	
				error += "Excpected '" + Token.returnLexeme(t) + "' \nFound '" + token.getLexeme() + "'\n";
			else if(message.startsWith("#"))
				error += "Excpected '" + message.substring(1)  + "' \nFound '" + token.getLexeme() + "'\n";
			else
				error += "Cannot match '" + token.getLexeme() + "' to " + message + "\n";
			
			error += "In: " + line;
			
			throw new SyntacticException(error);
			} catch (IOException e) {
				e.printStackTrace();
			} 
	}

	private void match(int t, String message) throws SyntacticException {
		if (token.getTokenType() == t) {
			token = lexer.nextToken();
			lineNumber = token.lineNumber;
		} else {
			errorReport(t, message);
		}
	}
}

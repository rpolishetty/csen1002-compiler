package Parser;

import java.util.ArrayList;

import Lexer.Lexer;
import Lexer.LexerManual;
import Lexer.LexerSample;
import Lexer.Token;
import ParserObjects.Expr;
import ParserObjects.SyntacticException;
import ParserObjects.Term;

/*
 * class Parser
 * 
 * Parses the tokens returned from the lexical analyzer.
 * 
 * Update the parser implementation to parse the Decaf
 * language according to the grammar provided. 
 */
public class ParserSample {

	private LexerSample lexer; // lexical analyzer
	private Token token; // look ahead token
	
	public ParserSample(LexerSample lex) {
		lexer = lex;
	}
	
	public ArrayList<Expr> parse() throws SyntacticException {
		
		token = lexer.nextToken();
		
		ArrayList<Expr> exprs = new ArrayList<Expr>();
		
		while(token.getTokenType() != Token.EOF) {
			exprs.add(expr());
			match(Token.SM);
		}
		
		return exprs;
	}
	
	private Expr expr() throws SyntacticException{
		Expr value = term();

		while (true) {
			switch (token.getTokenType()) {
			case Token.MO:
				match(token.getTokenType());
				value = new Expr(term(), Expr.MO, value);
				break;

			case Token.PO:
				match(token.getTokenType());
				value = new Expr(term(), Expr.PO, value);
				break;

			default:
				return value;
			}
		}
	}

	private Term term() throws SyntacticException{
		String tk = token.getLexeme();
		match(Token.NM);
		Term value = new Term(Integer.parseInt(tk), 0, null);

		while (true) {
			switch (token.getTokenType()) {
			
			case Token.DO:
				match(token.getTokenType());
				tk = token.getLexeme();
				match(Token.NM);
				value = new Term(Integer.parseInt(tk), Term.DO, value);
				break;

			case Token.TO:
				match(token.getTokenType());
				tk = token.getLexeme();
				match(Token.NM);
				value = new Term(Integer.parseInt(tk), Term.TO, value);
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

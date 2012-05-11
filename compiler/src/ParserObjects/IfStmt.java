package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class IfStmt {

	Expression exp;
	Statement thenStmt;
	Statement elseStmt;
	public static SymbolTable symTable;
	
	public IfStmt() {

	}
	public IfStmt(Expression exp, Statement thenStmt) {
		this.exp = exp;
		this.thenStmt = thenStmt;
	}
	
	public IfStmt(Expression exp, Statement thenStmt, Statement elseStmt) {
		this.exp = exp;
		this.thenStmt = thenStmt;
		this.elseStmt = elseStmt;
	}

	public String toString() {
		String ret = "IfStmt\n";
		
		ret += "| " + exp.toString() + "\n";
		ret += "| ThenStmt\n";
		ret += "| " + thenStmt.toString() + "\n";
		
		if(elseStmt != null){
			ret += "| ElseStmt\n";
			ret += "| " + elseStmt.toString() + "\n";
			}
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		exp.check();
		
		if(exp.returnType != Expression.BOOLEAN)
			throw new SemanticException("Expression in if statement must be of type Boolean");
		
		thenStmt.check();
		
		if(elseStmt != null){
			elseStmt.check();
		}
	}
}

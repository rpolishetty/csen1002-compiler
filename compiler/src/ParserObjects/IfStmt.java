package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class IfStmt {

	Expression exp;
	Statement thenStmt;
	Statement elseStmt;
	int lineNumber;
	int charNumber;
	public static SymbolTable symTable;
	
	public IfStmt() {

	}
	public IfStmt(Expression exp, Statement thenStmt, int lineNumber, int charNumber) {
		this.exp = exp;
		this.thenStmt = thenStmt;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
	}
	
	public IfStmt(Expression exp, Statement thenStmt, Statement elseStmt, int lineNumber, int charNumber) {
		this.exp = exp;
		this.thenStmt = thenStmt;
		this.elseStmt = elseStmt;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
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
			ClassDecl.returnError("Expression in if statement must be of type Boolean", lineNumber, charNumber);
		
		thenStmt.check();
		
		if(elseStmt != null){
			elseStmt.check();
		}
	}
}

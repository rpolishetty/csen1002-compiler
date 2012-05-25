package ParserObjects;

import Parser.SymbolTable;

public class WhileStmt {

	Expression exp;
	Statement stmt;
	int lineNumber;
	int charNumber;
	public static SymbolTable symTable;
	
	public WhileStmt() {

	}
	public WhileStmt(Expression exp, Statement stmt, int lineNumber, int charNumber) {
		this.exp = exp;
		this.stmt = stmt;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
	}

	public String toString() {
		String ret = "WhileStmt\n";
		
		ret += "| " + exp.toString() + "\n";
		ret += "| " + stmt.toString() + "\n";
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		exp.check();
		
		if(exp.returnType != Expression.BOOLEAN)
			ClassDecl.returnError("Expression in while statement must be of type Boolean", lineNumber, charNumber );
		
		stmt.check();
	}
	
	
}

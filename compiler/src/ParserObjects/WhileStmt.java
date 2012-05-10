package ParserObjects;

import Parser.SymbolTable;

public class WhileStmt {

	Expression exp;
	Statement stmt;
	public static SymbolTable symTable;
	
	public WhileStmt() {

	}
	public WhileStmt(Expression exp, Statement stmt) {
		this.exp = exp;
		this.stmt = stmt;
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
			throw new SemanticException("Expression in while statement must be of type Boolean");
		
		stmt.check();
	}
	
	
}

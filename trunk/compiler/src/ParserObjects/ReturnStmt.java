package ParserObjects;

import Parser.SymbolTable;

public class ReturnStmt {

	Expression exp;
	public static SymbolTable symTable;
	
	public ReturnStmt() {

	}
	public ReturnStmt(Expression exp) {
		this.exp = exp;
	}

	public String toString() {
		String ret = "ReturnStmt\n";
		
		ret += "| " + exp.toString() + "\n";
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		exp.check();
	}
}

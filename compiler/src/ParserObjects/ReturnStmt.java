package ParserObjects;

import Parser.SymbolTable;

public class ReturnStmt {

	Expression exp;
	public static SymbolTable symTable;
	int lineNumber;
	int charNumber;
	public ReturnStmt() {

	}
	public ReturnStmt(Expression exp, int lineNumber, int charNumber) {
		this.exp = exp;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
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

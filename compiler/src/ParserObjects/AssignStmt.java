package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class AssignStmt {

	String id;
	Expression exp;
	public static SymbolTable symTable;
	public AssignStmt() {
	}
	
	public AssignStmt(String id, Expression exp) {
		this.id = id;
		this.exp = exp;
	}

	public String toString() {
		String ret = "AssignStmt\n";
		
		ret += "| " + "ID\n" + "| | " + id + "\n";
		ret += "| " + exp.toString() + "\n";
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		if(!symTable.contains(id))
			throw new SemanticException("Local Variable \"" + id + "\" is not declared in the current scope");
		
		exp.check();
			
	}
	
	
	
	
}

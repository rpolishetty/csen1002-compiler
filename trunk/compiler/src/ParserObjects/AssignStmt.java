package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class AssignStmt {

	String id;
	Expression exp;
	int lineNumber;
	int idCharNumber;
	public static SymbolTable symTable;
	public AssignStmt() {
	}
	
	public AssignStmt(String id, Expression exp, int lineNumber, int idCharNumber) {
		this.id = id;
		this.exp = exp;
		this.lineNumber = lineNumber;
		this.idCharNumber = idCharNumber;
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
			ClassDecl.returnError("Local Variable '" + id + "' is not declared in the current scope", lineNumber, idCharNumber );
		
		exp.check();
		
		Entry e = symTable.get(id);
		int t;
		
		try {
			FormalParam o = (FormalParam) e.object;
			t = o.t.type;
		} catch (Exception e2) {
			LocalVarDecl o = (LocalVarDecl) e.object;
			t = o.t.type;
		}
		
		if(t != exp.returnType)
			ClassDecl.returnError("Expression assgined to local variable '" + id + "' " +
					"should be the same as the variable type", lineNumber);
			
	}
	
	
	
	
}

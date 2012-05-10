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
			throw new SemanticException("Expression assgined to local variable \"" + id + "\" " +
					"should be the same as the variable type");
			
	}
	
	
	
	
}

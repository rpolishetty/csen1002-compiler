package ParserObjects;

import Parser.*;

public class ClassDecl {
	
	public String id;
	public MethodDecls mds;
	
	public static SymbolTable symTable;
	
	public ClassDecl() {
		
	}
	
	public ClassDecl(String id, MethodDecls mds) {
		this.id = id;
		this.mds = mds;
		
	}

	public static void throwException() throws SyntacticException {
		
		throw new SyntacticException("Error");
	}
	
	public String toString(){
		String ret = "ClassDecl " + id + "\n";
		
		ret += "| " + mds.toString();
		
		return ret;
	}
	
	public void check() throws SemanticException {

		symTable = SymbolTable.getInstance();
		symTable.add(new Entry(id));
		symTable.openScope();
		mds.check();
		symTable.closeScope();
		
	}
}

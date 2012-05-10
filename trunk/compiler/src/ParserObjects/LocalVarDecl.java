package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class LocalVarDecl {

	Type t;
	String id;
	public static SymbolTable symTable;
	public LocalVarDecl() {
	
	}
	public LocalVarDecl(Type t, String id) {

		this.t = t;
		this.id = id;
	}

	public String toString() {
		String ret = "LocalVarDecl\n";
		
		String s = "";
		s += t.toString();
		s += "ID\n| " + id + "\n";
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		if(symTable.contains(id))
			
			if(symTable.get(id).level == 0)
				throw new SemanticException("Local Variable name \"" + id + "\" cannot be the same as the class name");
			
			else if(symTable.get(id).level == 1)
				throw new SemanticException("Local Variable name \"" + id + "\" cannot be the same as the method name");
			
			else
				throw new SemanticException("Local Variable \"" + id + "\" is previously defined in the current scope");
		
			
		symTable.add(new Entry(id), this);
		
	}
	
}

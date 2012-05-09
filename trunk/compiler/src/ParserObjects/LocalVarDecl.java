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
			throw new SemanticException("bbb");
			
		symTable.add(new Entry(id));
		
	}
	
}

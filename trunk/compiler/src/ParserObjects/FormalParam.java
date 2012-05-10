package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class FormalParam {

	Type t;
	String id;
	public static SymbolTable symTable;
	public FormalParam() {
	
	}
	public FormalParam(Type t, String id) {

		this.t = t;
		this.id = id;
	}

	public String toString() {
		String ret = "FormalParam\n";
		
		String s = "";
		s += t.toString();
		s += "ID\n| " + id + "\n";
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		if(symTable.contains(id)){
	
			if(symTable.get(id).level == 0)
				throw new SemanticException("Formal Param name \"" + id + "\" cannot be the same as the class name");
			
			else if(symTable.get(id).level == 1)
				throw new SemanticException("Formal Param name \"" + id + "\" cannot be the same as the method name");
			
			else
				throw new SemanticException("Formal Param \"" + id + "\" is previously defined in the current scope");
		}
		
			
		symTable.add(new Entry(id));
		
	}
	
	
}

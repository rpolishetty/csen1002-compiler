package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class LocalVarDecl {

	Type t;
	String id;
	int lineNumber;
	int charNumber;
	public static SymbolTable symTable;
	public LocalVarDecl() {
	
	}
	public LocalVarDecl(Type t, String id, int lineNumber, int charNumber) {

		this.t = t;
		this.id = id;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
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
				ClassDecl.returnError("Local Variable name '" + id + "' cannot be the same as the class name", lineNumber, charNumber);
			
			else if(symTable.get(id).level == 1)
				ClassDecl.returnError("Local Variable name '" + id + "' cannot be the same as the method name", lineNumber, charNumber);
			
			else
				ClassDecl.returnError("Local Variable '" + id + "' is previously defined in the current scope", lineNumber, charNumber);
		
			
		symTable.add(new Entry(id), this);
		
	}
	
}

package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class MethodDecl {

	Type t;
	String id;
	FormalParams fps;
	Block b;
	
	public static SymbolTable symTable;
	
	
	public MethodDecl() {
		// TODO Auto-generated constructor stub
	}


	public MethodDecl(Type t, String id, FormalParams fps, Block b) {
		this.t = t;
		this.id = id;
		this.fps = fps;
		this.b = b;
	}


	public String toString() {
		String ret = "MethodDecl\n";
		
		String s = "";
		s += t.toString();
		s += "ID\n| " + id + "\n";
		s += fps.toString();
		s += b.toString();
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
		
		return ret;
	}


	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		symTable.openScope();
		
		if(symTable.contains(id))
			throw new SemanticException("bbb");
			
		symTable.add(new Entry(id));
		fps.check();
		b.check();
		symTable.closeScope();
		
	}
	
	
}

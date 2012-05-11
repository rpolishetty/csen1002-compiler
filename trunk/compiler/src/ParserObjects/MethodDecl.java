package ParserObjects;

import java.util.ArrayList;

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
	
	public ArrayList<Integer> getParamatersTypes(){
		return fps.getParamatersTypes();
	}


	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		if(symTable.contains(id)){
			
			if(symTable.get(id).level == 0)
				throw new SemanticException("Method name '" + id + "' cannot be the same as the class name");
			
			else 
				throw new SemanticException("Method name '" + id + "' cannot be the same as the name of another method");
		}
		
		symTable.add(new Entry(id),this);
		symTable.openScope();	
		
		fps.check();
		b.check();
				
		if(!hasReturnStmt(t.type, id))
			throw new SemanticException("Method '" + id + "' should have a reachable return statement");
		
		
		symTable.closeScope();
		
	}
	
	public boolean hasReturnStmt(int type, String md) throws SemanticException{
		
		return b.hasReturnStmt(type, md);
	}
	
	
}

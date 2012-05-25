package ParserObjects;

import java.util.ArrayList;

import Parser.Entry;
import Parser.SymbolTable;

public class MethodDecl {

	Type t;
	String id;
	FormalParams fps;
	Block b;
	int lineNumber;
	int charNumber;
	
	public static SymbolTable symTable;
	
	
	public MethodDecl() {
		// TODO Auto-generated constructor stub
	}


	public MethodDecl(Type t, String id, FormalParams fps, Block b, int lineNumber, int charNumber) {
		this.t = t;
		this.id = id;
		this.fps = fps;
		this.b = b;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
	}


	public String toString() {
		String ret = "MethodDecl\n";
		
		String s = "";
		s += t.toString();
		s += "ID\n| " + id + "\n";
		if(fps != null)
			s += fps.toString();
		s += b.toString();
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
		
		return ret;
	}
	
	public ArrayList<Integer> getParamatersTypes(){
		if(fps != null)
			return fps.getParamatersTypes();
		else
			return  new ArrayList<Integer>();
	}


	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		symTable.openScope();	
		if(fps != null)
			fps.check();
		b.check();
				
		if(!hasReturnStmt(t.type, id))
			ClassDecl.returnError("Method '" + id + "' should have a reachable return statement", lineNumber, charNumber);
		
		
		symTable.closeScope();
		
	}
	
	public boolean hasReturnStmt(int type, String md) throws SemanticException{
		
		return b.hasReturnStmt(type, md);
	}
	
	
}

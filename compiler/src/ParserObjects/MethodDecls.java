package ParserObjects;

import java.util.ArrayList;

import Parser.Entry;
import Parser.SymbolTable;

public class MethodDecls {
	
	public ArrayList<MethodDecl> mdList;
	public static SymbolTable symTable;
	public MethodDecls() {
		
	}
	
	public MethodDecls(ArrayList<MethodDecl> mdList) {
		this.mdList = mdList;
	}
	
	public String toString(){
		
		String ret = "MethodDecls\n";
		
		if(mdList.isEmpty())
			return ret;
		
		for(MethodDecl md: mdList)
			ret += "| " + md.toString() + "\n";
		
		return ret;
	}

	public void check() throws SemanticException {
		symTable = SymbolTable.getInstance();
		
		for(MethodDecl md: mdList)
			md.check();
		
	}
}

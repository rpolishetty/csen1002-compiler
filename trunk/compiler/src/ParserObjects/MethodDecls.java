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
		
		for(MethodDecl md: mdList){
			symTable = SymbolTable.getInstance();
			
			if(symTable.contains(md.id)){
				
				if(symTable.get(md.id).level == 0)
					throw new SemanticException("Method name '" + md.id + "' cannot be the same as the class name");
				
				else 
					throw new SemanticException("Method name '" + md.id + "' cannot be the same as the name of another method");
			}
			
			symTable.add(new Entry(md.id),md);
		}
		
		for(MethodDecl md: mdList)
			md.check();
		
	}
}

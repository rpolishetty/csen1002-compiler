package ParserObjects;

import java.util.ArrayList;

import Parser.Entry;
import Parser.SymbolTable;

public class MethodDecls {
	int lineNumber;
	public ArrayList<MethodDecl> mdList;
	public static SymbolTable symTable;
	public MethodDecls() {
		
	}
	
	public MethodDecls(ArrayList<MethodDecl> mdList, int lineNumber) {
		this.mdList = mdList;
		this.lineNumber = lineNumber;
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
					ClassDecl.returnError("Method name '" + md.id + "' cannot be the same as the class name", md.lineNumber, md.charNumber);
				
				else 
					ClassDecl.returnError("Method name '" + md.id + "' cannot be the same as the name of another method", md.lineNumber, md.charNumber);
			}
			
			symTable.add(new Entry(md.id),md);
		}
		
		for(MethodDecl md: mdList)
			md.check();
		
	}
}

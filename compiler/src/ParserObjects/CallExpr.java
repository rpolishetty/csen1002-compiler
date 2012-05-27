package ParserObjects;

import java.util.ArrayList;

import Parser.Entry;
import Parser.SymbolTable;

public class CallExpr extends PrimaryExpr {

	String id;
	ActualParams aps;
	int lineNumber;
	int charNumber;
	public static SymbolTable symTable;
	public CallExpr() {
	}
	
	public CallExpr(String id, ActualParams aps, int lineNumber, int charNumber) throws SemanticException {
		this.id = id;
		this.aps = aps;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
		symTable = SymbolTable.getInstance();
		
	}


	public String toString() {
		String ret = "CallExpr " + id + "\n";
		if(aps != null)
			ret += "| " + aps.toString() + "\n";
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		ArrayList<Integer> parametersList = new ArrayList<Integer>();
		if(!symTable.contains(id))
			throw new SemanticException("Method '" + id + "' is not declared in the class");
		
		if(aps != null){
			aps.check();
			parametersList = aps.getParamatersTypes();
		}
		Entry e = symTable.get(id);
		
		MethodDecl md = (MethodDecl) e.object;
		
		returnType = md.t.type;
		if(md.getParamatersTypes().size() != parametersList.size()){
			ClassDecl.returnError("Method '" + id + "' with the same number of parameters is not declared in the current scope", lineNumber, charNumber);
		}
		
		for(int i = 0; i < md.getParamatersTypes().size(); i++){
			int t = aps.getParamatersTypes().get(i);
			if(md.getParamatersTypes().get(i) != t){
				ClassDecl.returnError("Method '" + id + "' with the same parameter types is not declared in the current scope", lineNumber, charNumber);
			}
				
		}
			
	}
	
	
}

package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class CallExpr extends PrimaryExpr {

	String id;
	ActualParams aps;
	public static SymbolTable symTable;
	public CallExpr() {
	}
	
	public CallExpr(String id, ActualParams aps) throws SemanticException {
		this.id = id;
		this.aps = aps;
		symTable = SymbolTable.getInstance();
		
	}


	public String toString() {
		String ret = "CallExpr " + id + "\n";
		
		ret += "| " + aps.toString() + "\n";
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		if(!symTable.contains(id))
			throw new SemanticException("Method \"" + id + "\" is not declared in the class");
		
		aps.check();
		
		Entry e = symTable.get(id);
		
		MethodDecl md = (MethodDecl) e.object;
		
		returnType = md.t.type;
		
		if(md.getParamatersTypes().size() != aps.getParamatersTypes().size()){
			throw new SemanticException("Method \"" + id + "\" with the same parameter types is not declared in the current scope");
		}
		
		for(int i = 0; i < md.getParamatersTypes().size(); i++){
			int t = aps.getParamatersTypes().get(i);
			
			if(md.getParamatersTypes().get(i) != t){
				throw new SemanticException("Method \"" + id + "\" with the same parameter types is not declared in the current scope");
			}
				
		}
			
	}
	
	
}

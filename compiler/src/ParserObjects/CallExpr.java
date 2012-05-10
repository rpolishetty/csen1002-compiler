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
		String ret = "CallExpr\n";
		
		ret += "| " + "ID\n| " + id + "\n";
		ret += "| " + aps.toString() + "\n";
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		if(!symTable.contains(id))
			throw new SemanticException("Method \"" + id + "\" is not declared in the class");
		
		Entry e = symTable.get(id);
		MethodDecl md = (MethodDecl) e.object;
		returnType = md.t.type;
		
		for(int i = 0; i < md.getParamatersTypes().size(); i++){
			if(!md.getParamatersTypes().get(i).equals(aps.getParamatersTypes().get(i))){
				System.out.println(md.getParamatersTypes().get(i) + " " + aps.getParamatersTypes().get(i));
				throw new SemanticException("Method \"" + id + "\" with the same Parameter types is not declared in the current scope");
			}
				
		}
			
	}
	
	
}

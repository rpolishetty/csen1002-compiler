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
			String t = aps.getParamatersTypes().get(i);
			if(!t.equals("int") 
				&& !t.equals("float") 
				&& !t.equals("boolean") 
				&& !t.equals("String")){
				
				Entry e1 = symTable.get(aps.getParamatersTypes().get(i));
				
				try {
					FormalParam o = (FormalParam) e1.object;
					t = o.t.type;
				} catch (Exception e2) {
					LocalVarDecl o = (LocalVarDecl) e1.object;
					t = o.t.type;
				}
				aps.getParamatersTypes().set(i, t);
				
			}
			if(!md.getParamatersTypes().get(i).equals(t)){
				throw new SemanticException("Method \"" + id + "\" with the same Parameter types is not declared in the current scope");
			}
				
		}
			
	}
	
	
}

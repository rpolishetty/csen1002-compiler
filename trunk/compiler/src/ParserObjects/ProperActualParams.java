package ParserObjects;

import java.util.ArrayList;

import Parser.SymbolTable;

public class ProperActualParams {

	public ArrayList<Expression> eList;
	public static SymbolTable symTable;
	int lineNumber;
	public ProperActualParams() {
	
	}

	public ProperActualParams(ArrayList<Expression> eList, int lineNumber) {

		this.eList = eList;
		this.lineNumber = lineNumber;
	}

	public String toString() {
		 
		String ret = "ProperActualParams\n";
		
		 for(Expression e: eList)
				ret += "| " + e.toString() + "\n";
			
		return ret;
	}
	
	public ArrayList<Integer> getParamatersTypes(){
		ArrayList<Integer> types = new ArrayList<Integer>();
		for(Expression e: eList)
			types.add(e.getType());
		return types;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		for(Expression e: eList)
			e.check();
	}
	
	
}

package ParserObjects;

import java.util.ArrayList;

import Parser.SymbolTable;

public class ProperActualParams {

	public ArrayList<Expression> eList;
	public static SymbolTable symTable;

	public ProperActualParams() {
	
	}

	public ProperActualParams(ArrayList<Expression> eList) {

		this.eList = eList;
	}

	public String toString() {
		 
		String ret = "ProperActualParams\n";
		
		if(eList.isEmpty())
			return ret;
		 
		 for(Expression e: eList)
				ret += "| " + e.toString() + "\n";
			
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		for(Expression e: eList)
			e.check();
	}
	
	
}

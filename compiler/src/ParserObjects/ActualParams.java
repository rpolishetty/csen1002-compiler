package ParserObjects;

import java.util.ArrayList;

import Parser.SymbolTable;

public class ActualParams {

	ProperActualParams pap;
	int lineNumber;
	public static SymbolTable symTable;

	public ActualParams() {
	}

	public ActualParams(ProperActualParams pap, int lineNumber) {
		this.pap = pap;
		this.lineNumber = lineNumber;
	}
	
	public String toString() {
		
		String ret = "ActualParams\n";
		
		if(pap != null)
			ret += "| " + pap.toString() + "\n";
		
		return ret;
	}
	
	public ArrayList<Integer> getParamatersTypes(){
		if(pap != null)
			return pap.getParamatersTypes();
		else
			return new ArrayList<Integer>();
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		if(pap != null)
			pap.check();
	}
	
}

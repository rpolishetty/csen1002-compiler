package ParserObjects;

import Parser.SymbolTable;

public class ActualParams {

	ProperActualParams pap;
	public static SymbolTable symTable;

	public ActualParams() {
	}

	public ActualParams(ProperActualParams pap) {
		this.pap = pap;
	}
	
	public String toString() {
		
		String ret = "ActualParams\n";
		
		if(pap != null)
			ret += "| " + pap.toString() + "\n";
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		if(pap != null)
			pap.check();
	}
	
}

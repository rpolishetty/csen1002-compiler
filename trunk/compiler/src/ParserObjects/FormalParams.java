package ParserObjects;

import java.util.ArrayList;

import Parser.Entry;
import Parser.SymbolTable;

public class FormalParams {

	ProperFormalParams pfp;
	int lineNumber;
	public static SymbolTable symTable;
	
	public FormalParams() {

	}

	public FormalParams(ProperFormalParams pfp, int lineNumber) {

		this.pfp = pfp;
		this.lineNumber = lineNumber;
	}


	public String toString() {
		
		String ret = "FormalParams\n";
		
		if(pfp != null)
			ret += "| " + pfp.toString() + "\n";
		
		return ret;
	}
	
	public ArrayList<Integer> getParamatersTypes(){
		if(pfp != null)
			return pfp.getParamatersTypes();
		else
			return  new ArrayList<Integer>();
	}

	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		if(pfp != null)
			pfp.check();
		
	}
	
	
	
	
}

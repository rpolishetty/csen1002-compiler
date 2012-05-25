package ParserObjects;

import java.util.ArrayList;

import Parser.Entry;
import Parser.SymbolTable;

public class ProperFormalParams {

	public ArrayList<FormalParam> fpList;
	public static SymbolTable symTable;
	public ProperFormalParams() {
	
	}

	public ProperFormalParams(ArrayList<FormalParam> fpList) {

		this.fpList = fpList;
	}

	public String toString() {
		 
		String ret = "ProperFormalParams\n";
		
		if(fpList.isEmpty())
			return ret;
	
		 for(FormalParam fp: fpList)
				ret += "| " + fp.toString() + "\n";
			
		return ret;
	}
	
	public ArrayList<Integer> getParamatersTypes(){
		ArrayList<Integer> types = new ArrayList<Integer>();
		for(FormalParam fp: fpList)
			types.add(fp.t.type);
		return types;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		for(FormalParam fp: fpList)
			fp.check();
		
	}
	
	
}

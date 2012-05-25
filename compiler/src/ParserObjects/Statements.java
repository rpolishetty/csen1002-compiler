package ParserObjects;

import java.util.ArrayList;

import Parser.Entry;
import Parser.SymbolTable;

public class Statements {
	int lineNumber;
	public ArrayList<Statement> stList;
	public static SymbolTable symTable;
	public Statements() {
	
	}

	public Statements(ArrayList<Statement> stList, int lineNumber) {

		this.stList = stList;
		this.lineNumber = lineNumber;
	}

	public String toString() {
		
		String ret = "Statements\n";
		
		if(stList.isEmpty())
			return ret;
		
		for(Statement st: stList)
			ret += "| " + st.toString() + "\n";
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		for(Statement st: stList)
			st.check();
		
	}

	public boolean hasReturnStmt(int type, String id) throws SemanticException {
		
		for(Statement st: stList){
			
			if(st.hasReturnStmt(type, id))
				return true;
		}
		
		return false;
	}
	
	
	
	
	
}

package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class Block {

	Statements sts;
	int lineNumber;
	public static SymbolTable symTable;
	public Block() {

	}

	public Block(Statements sts, int lineNumber) {
		
		this.sts = sts;
		this.lineNumber = lineNumber;
	}

	public String toString() {
		String ret = "Block\n";
		
		ret += "| " + sts.toString() + "\n";
		
		return ret;
	}

	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		symTable.openScope();
		sts.check();
		symTable.closeScope();
		
	}

	public boolean hasReturnStmt(int type, String id) throws SemanticException {

		return sts.hasReturnStmt(type, id);
	}
	
	
	
	
}

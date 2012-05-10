package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class Statement {
	
	Block b;
	LocalVarDecl lcd;
	AssignStmt assignSt;
	IfStmt ifStmt;
	WhileStmt whileStmt;
	ReturnStmt returnStmt;
	public static SymbolTable symTable;
	
	int type;
	
	public static final int BLOCK = 1;
	public static final int LOCALVAR = 2;
	public static final int ASSIGNSTMT = 3;
	public static final int IFSTMT = 4;
	public static final int WHILESTMT = 5;
	public static final int RETUTNSTMT = 6;
	
	public Statement() {
	}

	public Statement(Block b) {
		this.b = b;
		type = BLOCK;
	}

	public Statement(LocalVarDecl lcd) {
		this.lcd = lcd;
		type = LOCALVAR;
	}

	public Statement(AssignStmt assignSt) {
		this.assignSt = assignSt;
		type = ASSIGNSTMT;
	}

	public Statement(IfStmt ifStmt) {
		this.ifStmt = ifStmt;
		type = IFSTMT;
	}

	public Statement(WhileStmt whileStmt) {
		this.whileStmt = whileStmt;
		type = WHILESTMT;
	}

	public Statement(ReturnStmt returnStmt) {
		this.returnStmt = returnStmt;
		type = RETUTNSTMT;
	}

	public String toString() {
		String ret = "Statement\n";
		
		switch(type){
		
		case BLOCK: 
			ret += "| " + b.toString() + "\n";
			break;
			
		case LOCALVAR: 
			ret += "| " + lcd.toString() + "\n";
			break;
			
		case ASSIGNSTMT: 
			ret += "| " + assignSt.toString() + "\n";
			break;
			
		case IFSTMT: 
			ret += "| " + ifStmt.toString() + "\n";
			break;
			
		case WHILESTMT: 
			ret += "| " + whileStmt.toString() + "\n";
			break;
			
		case RETUTNSTMT: 
			ret += "| " + returnStmt.toString() + "\n";
			break;
		}
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		switch(type){
			case BLOCK: 
				b.check();
				break;
				
			case LOCALVAR: 
				lcd.check();
				break;
				
			case ASSIGNSTMT: 
				assignSt.check();
				break;
				
			case IFSTMT: 
				ifStmt.check();
				break;
				
			case WHILESTMT: 
				whileStmt.check();
				break;
				
			case RETUTNSTMT: 
				returnStmt.check();
				break;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

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
	
	public Statement() {
	}

	public Statement(Block b) {
		this.b = b;
		type = 1;
	}

	public Statement(LocalVarDecl lcd) {
		this.lcd = lcd;
		type = 2;
	}

	public Statement(AssignStmt assignSt) {
		this.assignSt = assignSt;
		type = 3;
	}

	public Statement(IfStmt ifStmt) {
		this.ifStmt = ifStmt;
		type = 4;
	}

	public Statement(WhileStmt whileStmt) {
		this.whileStmt = whileStmt;
		type = 5;
	}

	public Statement(ReturnStmt returnStmt) {
		this.returnStmt = returnStmt;
		type = 6;
	}

	public String toString() {
		String ret = "Statement\n";
		
		switch(type){
		
		case 1: 
			ret += "| " + b.toString() + "\n";
			break;
			
		case 2: 
			ret += "| " + lcd.toString() + "\n";
			break;
			
		case 3: 
			ret += "| " + assignSt.toString() + "\n";
			break;
			
		case 4: 
			ret += "| " + ifStmt.toString() + "\n";
			break;
			
		case 5: 
			ret += "| " + whileStmt.toString() + "\n";
			break;
			
		case 6: 
			ret += "| " + returnStmt.toString() + "\n";
			break;
		}
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		symTable = SymbolTable.getInstance();
		
		switch(type){
			case 1: 
				b.check();
				break;
				
			case 2: 
				lcd.check();
				break;
				
			case 3: 
				assignSt.check();
				break;
				
			case 4: 
				ifStmt.check();
				break;
				
			case 5: 
				whileStmt.check();
				break;
				
			case 6: 
				returnStmt.check();
				break;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

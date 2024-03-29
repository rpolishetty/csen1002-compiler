package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class Statement {
	int lineNumber;
	int charNumber;
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

	public boolean hasReturnStmt(int type1, String id) throws SemanticException {
		
		if(type == RETUTNSTMT){
			
			if(returnStmt.exp.returnType != type1)
				ClassDecl.returnError("Return expression of method '" + id + "' " +
						"should be of same type as method return type", returnStmt.lineNumber, returnStmt.charNumber);
			
			return true;
		}
		
		else if(type == IFSTMT){
			
			boolean flag1 = ifStmt.thenStmt.hasReturnStmt(type1, id);
			
			boolean flag2 = false;
			
			if(ifStmt.elseStmt != null)
				flag2 = ifStmt.elseStmt.hasReturnStmt(type1, id);
			
			return flag1 && flag2;
					
		}
		
		else if(type == BLOCK)
			return b.hasReturnStmt(type1, id);
		
		else if(type == WHILESTMT)
			return whileStmt.stmt.hasReturnStmt(type1, id);
			
			
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

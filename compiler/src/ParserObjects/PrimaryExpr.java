package ParserObjects;

import Parser.Entry;
import Parser.SymbolTable;

public class PrimaryExpr extends MultiplicativeExpr{

	int in;
	float fl;
	boolean bl;
	String st;
	CallExpr cExp;
	Expression exp;
	int type;
	int lineNumber;
	int charNumber;
	public static SymbolTable symTable;

	public PrimaryExpr() {

	}
	
	public PrimaryExpr(int in, int lineNumber, int charNumber) {
		this.in = in;
		type = INT;
		returnType = INT;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
	}
	
	public PrimaryExpr(float fl, int lineNumber, int charNumber) {
		this.fl = fl;
		type = FLOAT;
		returnType = FLOAT;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
	}
	
	public PrimaryExpr(boolean bl, int lineNumber, int charNumber) {
		this.bl = bl;
		type = BOOLEAN;
		returnType = BOOLEAN;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
	}
	
	public PrimaryExpr(String st, int lineNumber, int charNumber) {
		this.st = st;
		type = STRING;
		returnType = STRING;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
	}
	
	public PrimaryExpr(String idType, String st, int lineNumber, int charNumber) {
		this.st = st;
		type = ID;
		idName = st;
		returnType = ID;
		this.charNumber = charNumber;
		this.lineNumber = lineNumber;
	}
	
	public PrimaryExpr(CallExpr ce, int lineNumber, int charNumber) {
		this.cExp = ce;
		type = CE;
		returnType = ce.getType();
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
	}
	
	public PrimaryExpr(Expression e, int lineNumber, int charNumber) {
		this.exp = e;
		type = E;
		returnType = e.getType();
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
	}
	
	public String toString() {
		String ret = "PrimaryExpression\n";
		
		switch(type){
		
		case INT: 
			ret += "| " + in + "\n";
			break;
			
		case FLOAT: 
			ret += "| " + fl + "\n";
			break;
			
		case BOOLEAN: 
			ret += "| " + bl + "\n";
			break;
			
		case STRING: 
			ret += "| " + st + "\n";
			break;
			
		case ID: 
			ret += "| " + st + "\n";
			break;
			
		case CE: 
			ret += "| " + cExp.toString() + "\n";
			break;
			
		case E: 
			ret += "| " + exp.toString() + "\n";
			break;
	
		}
		
		return ret;
	}
	
	public void check() throws SemanticException {
		symTable = SymbolTable.getInstance();
		
		switch(type){
			case ID: 

				if(!symTable.contains(idName))
					ClassDecl.returnError("Local Variable '" + idName + "' is not declared in the current scope", lineNumber, charNumber);
				
				Entry e = symTable.get(st);
				int t;
				try {
					FormalParam o = (FormalParam) e.object;
					t = o.t.type;
				} catch (Exception e2) {
					LocalVarDecl o = (LocalVarDecl) e.object;
					t = o.t.type;
				}
				
				returnType = t;
				break;
			
			case CE: 
				cExp.check();
				break;
				
			case E: 
				exp.check();
				break;
			default:
				break;
		}
	}
	
}

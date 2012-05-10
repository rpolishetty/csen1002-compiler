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
	
	public static SymbolTable symTable;

	public PrimaryExpr() {

	}
	
	public PrimaryExpr(int in) {
		this.in = in;
		type = INT;
		returnType = INT;
	}
	
	public PrimaryExpr(float fl) {
		this.fl = fl;
		type = FLOAT;
		returnType = FLOAT;
	}
	
	public PrimaryExpr(boolean bl) {
		this.bl = bl;
		type = BOOLEAN;
		returnType = BOOLEAN;
	}
	
	public PrimaryExpr(String st) {
		this.st = st;
		type = STRING;
		returnType = STRING;
	}
	
	public PrimaryExpr(String idType, String st) {
		this.st = st;
		type = ID;
		idName = st;
		returnType = ID;
	}
	
	public PrimaryExpr(CallExpr ce) {
		this.cExp = ce;
		type = CE;
		returnType = ce.getType();
	}
	
	public PrimaryExpr(Expression e) {
		this.exp = e;
		type = E;
		returnType = e.getType();
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
					throw new SemanticException("Local Variable \"" + idName + "\" is not declared in the current scope");
				
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

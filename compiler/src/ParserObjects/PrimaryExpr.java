package ParserObjects;

public class PrimaryExpr extends MultiplicativeExpr{

	int in;
	float fl;
	boolean bl;
	String st;
	CallExpr cExp;
	Expression exp;
	
	int type;
	
	public static final int INT = 1;
	public static final int FLOAT = 2;
	public static final int BOOL = 3;
	public static final int STRING = 4;
	public static final int CE = 5;
	public static final int E = 6;
	
	public PrimaryExpr() {

	}
	
	public PrimaryExpr(int in) {
		this.in = in;
		type = INT;
	}
	
	public PrimaryExpr(float fl) {
		this.fl = fl;
		type = FLOAT;
	}
	
	public PrimaryExpr(boolean bl) {
		this.bl = bl;
		type = BOOL;
	}
	
	public PrimaryExpr(String st) {
		this.st = st;
		type = STRING;
	}
	
	public PrimaryExpr(CallExpr ce) {
		this.cExp = ce;
		type = CE;
	}
	
	public PrimaryExpr(Expression e) {
		this.exp = e;
		type = E;
	}
	
	public String toString() {
		String ret = "PrimaryExpr\n";
		
		switch(type){
		
		case INT: 
			ret += "| " + in + "\n";
			break;
			
		case FLOAT: 
			ret += "| " + fl + "\n";
			break;
			
		case BOOL: 
			ret += "| " + bl + "\n";
			break;
			
		case STRING: 
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
		
		switch(type){
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

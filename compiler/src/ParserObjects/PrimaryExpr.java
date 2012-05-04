package ParserObjects;

public class PrimaryExpr extends MultiplicativeExpr{

	int nm;
	boolean bl;
	String st;
	CallExpr cExp;
	Expression exp;
	
	int type;
	
	public PrimaryExpr() {

	}
	
	public PrimaryExpr(int nm) {
		this.nm = nm;
		type = 1;
	}
	
	public PrimaryExpr(boolean bl) {
		this.bl = bl;
		type = 2;
	}
	
	public PrimaryExpr(String st) {
		this.st = st;
		type = 3;
	}
	
	public PrimaryExpr(CallExpr ce) {
		this.cExp = ce;
		type = 4;
	}
	
	public PrimaryExpr(Expression e) {
		this.exp = e;
		type = 5;
	}
	
	public String toString() {
		String ret = "PrimaryExpr\n";
		
		switch(type){
		
		case 1: 
			ret += "| " + nm + "\n";
			break;
			
		case 2: 
			ret += "| " + bl + "\n";
			break;
			
		case 3: 
			ret += "| " + st + "\n";
			break;
			
		case 4: 
			ret += "| " + cExp.toString() + "\n";
			break;
			
		case 5: 
			ret += "| " + exp.toString() + "\n";
			break;
	
		}
		
		return ret;
	}
	
}

package ParserObjects;

public class PrimaryExpr extends MultiplicativeExpr{

	int in;
	float fl;
	boolean bl;
	String st;
	CallExpr cExp;
	Expression exp;
	
	int type;
	
	public PrimaryExpr() {

	}
	
	public PrimaryExpr(int in) {
		this.in = in;
		type = 1;
	}
	
	public PrimaryExpr(float fl) {
		this.fl = fl;
		type = 2;
	}
	
	public PrimaryExpr(boolean bl) {
		this.bl = bl;
		type = 3;
	}
	
	public PrimaryExpr(String st) {
		this.st = st;
		type = 4;
	}
	
	public PrimaryExpr(CallExpr ce) {
		this.cExp = ce;
		type = 5;
	}
	
	public PrimaryExpr(Expression e) {
		this.exp = e;
		type = 6;
	}
	
	public String toString() {
		String ret = "PrimaryExpr\n";
		
		switch(type){
		
		case 1: 
			ret += "| " + in + "\n";
			break;
			
		case 2: 
			ret += "| " + fl + "\n";
			break;
			
		case 3: 
			ret += "| " + bl + "\n";
			break;
			
		case 4: 
			ret += "| " + st + "\n";
			break;
			
		case 5: 
			ret += "| " + cExp.toString() + "\n";
			break;
			
		case 6: 
			ret += "| " + exp.toString() + "\n";
			break;
	
		}
		
		return ret;
	}
	
}

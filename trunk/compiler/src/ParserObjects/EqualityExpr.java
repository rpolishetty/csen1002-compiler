package ParserObjects;

public class EqualityExpr {

	public AdditiveExpr addExp;
	public int op;
	public EqualityExpr eqExp;
	
	public static final int EQ = 1;
	public static final int NE = 2;
	
	public EqualityExpr() {
		
	}
	
	public EqualityExpr(AdditiveExpr ae, int o, EqualityExpr ee) {
		addExp = ae;
		op = o;
		eqExp = ee;
	}
	
	public String toString(){
		String ret = "EqualityExpression\n";
		
		String s = "";
		
		if(eqExp !=null) {
			
			s += eqExp.toString();
			if(op == EQ)
				s += "==\n";
			else
				s += "!=\n";
		}
		
		s += addExp.toString();
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
	
		
		return ret;
	}

}

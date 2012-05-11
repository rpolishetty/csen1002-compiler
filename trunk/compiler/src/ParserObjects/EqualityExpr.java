package ParserObjects;

public class EqualityExpr extends ConditionalAndExpr{

	public AdditiveExpr addExp;
	public int op;
	public EqualityExpr eqExp;
	
	public static final int EQ = 12;
	public static final int NE = 13;
	
	public EqualityExpr() {
		
	}
	
	public EqualityExpr(AdditiveExpr ae, int o, EqualityExpr ee) {
		addExp = ae;
		op = o;
		eqExp = ee;
		returnType = Expression.BOOLEAN;
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

	public void check() throws SemanticException {
		
		addExp.check();
		
		if(eqExp !=null) {
			eqExp.check();
			System.out.println(addExp.returnType + " " + eqExp.returnType);
			if(addExp.returnType != eqExp.returnType)
				throw new SemanticException("Both sides of equality expression must be of the same type");
		}
	}
}

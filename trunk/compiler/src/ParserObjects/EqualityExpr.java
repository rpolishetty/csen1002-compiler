package ParserObjects;

public class EqualityExpr extends ConditionalAndExpr{

	public AdditiveExpr addExp;
	public int op;
	public EqualityExpr eqExp;
	
	
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
			if(addExp.returnType != eqExp.returnType)
				throw new SemanticException("All elements of equality expression must be of same type");
		}
	}
}

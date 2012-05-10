package ParserObjects;

public class ConditionalAndExpr extends Expression{

	public EqualityExpr eqExp;
	public int op;
	public ConditionalAndExpr condExp;
	
	public static final int LA = 11;
	
	public ConditionalAndExpr() {
		
	}
	
	public ConditionalAndExpr(EqualityExpr ee, int o, ConditionalAndExpr ce) {
		eqExp = ee;
		op = o;
		condExp = ce;
		returnType = Expression.BOOLEAN;
	}
	
	public String toString(){
		String ret = "ConditionalAndExpression\n";
		
		String s = "";
		
		if(condExp !=null) {
			
			s += condExp.toString();
			if(op == LA)
				s += "&&\n";
	
		}
		s += eqExp.toString();
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
	
		
		return ret;
	}
	
	public void check() throws SemanticException {
		eqExp.check();
		
		if(eqExp.returnType != Expression.BOOLEAN)
			throw new SemanticException("Conditional Expression must be of type Boolean");
		
		
		if(condExp !=null) {
			condExp.check();
			
			if(condExp.returnType != Expression.BOOLEAN)
				throw new SemanticException("Conditional Expression must be of type Boolean");
		}
		
	}

}

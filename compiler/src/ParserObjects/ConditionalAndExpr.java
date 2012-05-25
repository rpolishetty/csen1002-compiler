package ParserObjects;

public class ConditionalAndExpr extends Expression{

	public EqualityExpr eqExp;
	public int op;
	public ConditionalAndExpr condExp;
//	int lineNumber;
//	int charNumber;
	public ConditionalAndExpr() {
		
	}
	
	public ConditionalAndExpr(EqualityExpr ee, int o, ConditionalAndExpr ce) {
		eqExp = ee;
		op = o;
		condExp = ce;
		returnType = Expression.BOOLEAN;
	//	this.lineNumber = ee.lineNumber;
	//	this.charNumber = ee.charNumber;
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
			ClassDecl.returnError("All elements of a conditional expression must be of type Boolean", eqExp.lineNumber, eqExp.charNumber);
		
		
		if(condExp !=null) {
			condExp.check();
			
			if(condExp.returnType != Expression.BOOLEAN)
				ClassDecl.returnError("All elements of a conditional expression must be of type Boolean", condExp.lineNumber, condExp.charNumber);
		}
		
	}

}

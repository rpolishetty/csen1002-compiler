package ParserObjects;

public class EqualityExpr extends ConditionalAndExpr{

	public AdditiveExpr addExp;
	public int op;
	public EqualityExpr eqExp;
	//int lineNumber;
	
	public EqualityExpr() {
		
	}
	
	public EqualityExpr(AdditiveExpr ae, int o, EqualityExpr ee) {
		addExp = ae;
		op = o;
		eqExp = ee;
		returnType = Expression.BOOLEAN;
	//	this.lineNumber = ae.lineNumber;
	//	this.charNumber = ae.charNumber;
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
				ClassDecl.returnError("All elements of an equality expression must be of same type", addExp.lineNumber, addExp.charNumber);
		}
	}
}

package ParserObjects;

public class MultiplicativeExpr extends AdditiveExpr{

	public PrimaryExpr primExp;
	public int op;
	public MultiplicativeExpr multExp;
	
	
	public MultiplicativeExpr() {
		
	}
	
	public MultiplicativeExpr(PrimaryExpr pe, int o, MultiplicativeExpr me) {
		primExp = pe;
		op = o;
		multExp = me;
	}
	
	public String toString(){
		String ret = "MultiplicativeExpression\n";
		
		String s = "";
		
		if(multExp !=null) {
			
			s += multExp.toString();
			if(op == TO)
				s += "*\n";
			else if(op == DO)
				s += "/\n";
			else
				s += "%\n";
		}
		
		s += primExp.toString();
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
	
		
		return ret;
	}
	
	public void check() throws SemanticException {
		primExp.check();
		returnType = primExp.getType();
		if((primExp.returnType != Expression.INT) && (primExp.returnType != Expression.FLOAT))
			throw new SemanticException("Multiplicative expression must be of type int or float");
				
		if(multExp !=null) {
			multExp.check();
			
			if(multExp.returnType != primExp.returnType)
				throw new SemanticException("All elements of multiplicative expression must be of same type");
			
			else if((multExp.returnType != Expression.INT) && (multExp.returnType != Expression.FLOAT))
				throw new SemanticException("Multiplicative expression must be of type int or float");
			
		}
	}

	
}

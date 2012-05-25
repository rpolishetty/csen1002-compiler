package ParserObjects;

public class MultiplicativeExpr extends AdditiveExpr{

	public PrimaryExpr primExp;
	public int op;
	public MultiplicativeExpr multExp;
	//int lineNumber;
	//int charNumber;
	
	public MultiplicativeExpr() {
		
	}
	
	public MultiplicativeExpr(PrimaryExpr pe, int o, MultiplicativeExpr me) {
		primExp = pe;
		op = o;
		multExp = me;
		//this.lineNumber = pe.lineNumber;
		//this.charNumber = pe.charNumber;
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
		//lineNumber = primExp.lineNumber;
		//charNumber = primExp.charNumber;
		returnType = primExp.getType();
		if((primExp.returnType != Expression.INT) && (primExp.returnType != Expression.FLOAT))
			ClassDecl.returnError("All elements of a multiplicative expression must be of type int or float", lineNumber, charNumber);
				
		if(multExp !=null) {
			multExp.check();
			
			if(multExp.returnType != primExp.returnType)
				ClassDecl.returnError("All elements of a multiplicative expression must be of same type", lineNumber, charNumber);
			
			else if((multExp.returnType != Expression.INT) && (multExp.returnType != Expression.FLOAT))
				ClassDecl.returnError("All elements of a multiplicative expression must be of type int or float", lineNumber, charNumber);
			
		}
	}

	
}

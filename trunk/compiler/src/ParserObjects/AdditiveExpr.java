package ParserObjects;

public class AdditiveExpr extends EqualityExpr{

	public MultiplicativeExpr multExp;
	public int op;
	public AdditiveExpr addExp;
	int lineNumber;
	int charNumber;
	
	public AdditiveExpr() {
		
	}
	
	public AdditiveExpr(MultiplicativeExpr me, int o, AdditiveExpr ae) {
		multExp = me;
		op = o;
		addExp = ae;
		this.lineNumber = me.lineNumber;
		this.charNumber = me.charNumber;
	}
	
	public String toString(){
		String ret = "AdditiveExpression\n";
		
		String s = "";
		
		if(addExp !=null) {
			
			s += addExp.toString();
			if(op == PO)
				s += "+\n";
			else
				s += "-\n";
		}
		
		s += multExp.toString();
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
	
		
		return ret;
	}
	public void check() throws SemanticException {
		
		multExp.check();
		returnType = multExp.getType();
		if((multExp.returnType != Expression.INT) && (multExp.returnType != Expression.FLOAT))
			ClassDecl.returnError("All elements of an additive expression must be of type int or float", lineNumber, charNumber);
				
		if(addExp !=null) {
			addExp.check();
			
			if(multExp.returnType != addExp.returnType)
				ClassDecl.returnError("All elements of an additive expression must be of same type", lineNumber, charNumber);
			
			else if((addExp.returnType != Expression.INT) && (addExp.returnType != Expression.FLOAT))
				ClassDecl.returnError("All elements of an additive expression must be of type int or float", lineNumber, charNumber);
			
			
		}
		
	}
}

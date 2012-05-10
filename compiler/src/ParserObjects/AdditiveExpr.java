package ParserObjects;

public class AdditiveExpr extends EqualityExpr{

	public MultiplicativeExpr multExp;
	public int op;
	public AdditiveExpr addExp;
	
	public static final int PO = 14;
	public static final int MO = 15;
	
	public AdditiveExpr() {
		
	}
	
	public AdditiveExpr(MultiplicativeExpr me, int o, AdditiveExpr ae) {
		multExp = me;
		op = o;
		addExp = ae;
		returnType = me.returnType;
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
		
		if((multExp.returnType != Expression.INT) && (multExp.returnType != Expression.FLOAT))
			throw new SemanticException("Additive expression must be of type int or float");
				
		if(addExp !=null) {
			addExp.check();
			
			if(multExp.returnType != addExp.returnType)
				throw new SemanticException("Both sides of additive expression must be of the same type");
			
			else if((addExp.returnType != Expression.INT) && (addExp.returnType != Expression.FLOAT))
				throw new SemanticException("Additive expression must be of type int or float");
			
			
		}
		
	}
}

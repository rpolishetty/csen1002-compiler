package ParserObjects;

public class MultiplicativeExpr extends AdditiveExpr{

	public PrimaryExpr primExp;
	public int op;
	public MultiplicativeExpr multExp;
	
	public static final int TO = 16;
	public static final int DO = 17;
	public static final int MD = 19;
	
	public MultiplicativeExpr() {
		
	}
	
	public MultiplicativeExpr(PrimaryExpr pe, int o, MultiplicativeExpr me) {
		primExp = pe;
		op = o;
		multExp = me;
		switch(primExp.type){
		
		case INT: 
			returnType = Expression.INT;
			break;
			
		case FLOAT: 
			returnType = Expression.FLOAT;
			break;
			
		case BOOLEAN: 
			returnType = Expression.BOOLEAN;
			break;
			
		case STRING: 
			returnType = Expression.STRING;
			break;
			
		case CE: 
			returnType = primExp.getType();
			break;
			
		case E: 
			returnType = primExp.getType();
			break;
	
		}
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
		
		if((primExp.returnType != Expression.INT) && (primExp.returnType != Expression.FLOAT))
			throw new SemanticException("Multiplicative expression must be of type int or float");
				
		if(multExp !=null) {
			multExp.check();
			
			if((multExp.returnType != Expression.INT) && (multExp.returnType != Expression.FLOAT))
				throw new SemanticException("Multiplicative expression must be of type int or float");
			
		}
	}

	
}

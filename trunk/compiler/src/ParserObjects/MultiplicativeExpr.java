package ParserObjects;

public class MultiplicativeExpr extends AdditiveExpr{

	public PrimaryExpr primExp;
	public int op;
	public MultiplicativeExpr multExp;
	
	public static final int TO = 1;
	public static final int DO = 2;
	public static final int MD = 3;
	
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

	
}

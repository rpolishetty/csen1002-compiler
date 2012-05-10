package ParserObjects;

public class AdditiveExpr extends EqualityExpr{

	public MultiplicativeExpr multExp;
	public int op;
	public AdditiveExpr addExp;
	
	public static final int PO = 1;
	public static final int MO = 2;
	
	public AdditiveExpr() {
		
	}
	
	public AdditiveExpr(MultiplicativeExpr me, int o, AdditiveExpr ae) {
		multExp = me;
		op = o;
		addExp = ae;
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
		addExp.check();
	}
}

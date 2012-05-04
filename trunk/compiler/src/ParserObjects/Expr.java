package ParserObjects;


public class Expr {
	
	public Term tr;
	public int op;
	public Expr ex;
	
	public static final int PO = 1;
	public static final int MO = 2;
	
	public Expr() {
		
	}
	
	public Expr(Term t, int o, Expr e) {
		tr = t;
		op = o;
		ex = e;
	}
	
	public String toString(){
		String ret = "Expression\n";
		
		String s = "";
		
		if(ex !=null) {
			s += ex.toString();
			if(op == PO)
				s += "+\n";
			else
				s += "-\n";
		}
		
		s += tr.toString();
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
	
		
		return ret;
	}
	
	/*
	 * Evaluates the expression and return it's value.
	 */
	public int evaluate() throws SemanticException {
		if(ex == null)
			return tr.evaluate();
		
		int exv = ex.evaluate();
		int tv = tr.evaluate();
		
		if(op == PO)
			return exv + tv;
		else
			return exv - tv;
	}
}

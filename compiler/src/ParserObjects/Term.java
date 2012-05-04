package ParserObjects;


public class Term extends Expr {
	
	public int num;
	public int op;
	public Term tr;
	
	public static final int TO = 1;
	public static final int DO = 2;
	
	public Term(){}
	
	public Term(int n, int o, Term t) {
		num = n;
		op = o;
		tr = t;
	}
	
	public String toString() {
		String ret = "Term\n";
		
		String s = "";
		if(tr!=null) {
			s += tr.toString();
			if(op == TO)
				s += "*\n";
			else
				s += "/\n";
		}
		
		s += num;
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
		
		return ret;
	}
	
	/*
	 * Evaluates the term and return it's value.
	 */
	public int evaluate() throws SemanticException {
		if(tr == null)
			return num;
		
		if(op == DO && num == 0)
			throw new SemanticException("Cannot divide by zero");
		
		int tv = tr.evaluate();
		
		if(op == TO)
			return tv * num;
		else
			return tv / num;
	}
}

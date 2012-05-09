package ParserObjects;

import Parser.SymbolTable;

public class Expression {

	public ConditionalAndExpr condExp;
	public int op;
	public Expression exp;
	
	public static final int LO = 1;
	
	public Expression() {
		
	}
	
	public Expression(ConditionalAndExpr ce, int o, Expression e) {
		condExp = ce;
		op = o;
		exp = e;
	}
	
	public String toString(){
		String ret = "Expression\n";
		
		String s = "";
		
		if(exp !=null) {
			
			s += exp.toString();
			if(op == LO)
				s += "||\n";
	
		}
		
		s += condExp.toString();
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
	
		
		return ret;
	}
	
	public void check() throws SemanticException {
		
		
	
		
	}
	
	
}

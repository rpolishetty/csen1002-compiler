package ParserObjects;

public class Expression {

	public ConditionalAndExpr condExp;
	public int op;
	public Expression exp;
	
	int returnType;
	String idName;
		
	public static final int INT = 1;
	public static final int FLOAT = 2;
	public static final int BOOLEAN = 3;
	public static final int STRING = 4;
	public static final int ID = 5;
	public static final int CE = 6;
	public static final int E = 7;
	
	public static final int LO = 10;
	
	public Expression() {
		
	}
	
	public Expression(ConditionalAndExpr ce, int o, Expression e) {
		condExp = ce;
		op = o;
		exp = e;
		returnType = BOOLEAN;
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
	
	public int getType(){
		return returnType;
	}
	
	public void check() throws SemanticException {
		
		condExp.check();
		
		System.out.println(condExp.returnType);

		if(condExp.returnType != Expression.BOOLEAN)
			throw new SemanticException("Expression must be of type Boolean");
		
		if(exp !=null) {
			exp.check();

			if(exp.returnType != Expression.BOOLEAN)
				throw new SemanticException("Expression must be of type Boolean");
		}
	
		
	}
	
	
}

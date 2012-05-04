package ParserObjects;

public class ReturnStmt {

	Expression exp;
	
	public ReturnStmt() {

	}
	public ReturnStmt(Expression exp) {
		this.exp = exp;
	}

	public String toString() {
		String ret = "ReturnStmt\n";
		
		ret += "| " + exp.toString() + "\n";
		
		return ret;
	}
	
}

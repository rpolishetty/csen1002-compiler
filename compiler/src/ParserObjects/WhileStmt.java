package ParserObjects;

public class WhileStmt {

	Expression exp;
	Statement stmt;
	
	public WhileStmt() {

	}
	public WhileStmt(Expression exp, Statement stmt) {
		this.exp = exp;
		this.stmt = stmt;
	}

	public String toString() {
		String ret = "WhileStmt\n";
		
		ret += "| " + exp.toString() + "\n";
		ret += "| " + stmt.toString() + "\n";
		
		return ret;
	}
	
	
	
	
}

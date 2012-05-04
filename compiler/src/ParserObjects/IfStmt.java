package ParserObjects;

public class IfStmt {

	Expression exp;
	Statement ifStmt;
	Statement elseStmt;
	
	public IfStmt() {

	}
	public IfStmt(Expression exp, Statement ifStmt) {
		this.exp = exp;
		this.ifStmt = ifStmt;
	}
	
	public IfStmt(Expression exp, Statement ifStmt, Statement elseStmt) {
		this.exp = exp;
		this.ifStmt = ifStmt;
		this.elseStmt = elseStmt;
	}

	public String toString() {
		String ret = "IfStmt\n";
		
		ret += "| " + exp.toString() + "\n";
		ret += "| " + ifStmt.toString() + "\n";
		
		if(elseStmt != null){
			ret += "| ELSE\n";
			ret += "| " + elseStmt.toString() + "\n";
			}
		
		return ret;
	}
}

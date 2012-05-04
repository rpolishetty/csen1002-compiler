package ParserObjects;

public class AssignStmt {

	String id;
	Expression exp;
	
	public AssignStmt() {
	}
	
	public AssignStmt(String id, Expression exp) {
		this.id = id;
		this.exp = exp;
	}

	public String toString() {
		String ret = "AssignStmt\n";
		
		ret += "| " + id + "\n";
		ret += "| " + exp.toString() + "\n";
		
		return ret;
	}
	
	
	
	
}

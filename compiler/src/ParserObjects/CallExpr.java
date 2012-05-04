package ParserObjects;

public class CallExpr {

	String id;
	ActualParams aps;
	public CallExpr() {
	}
	
	public CallExpr(String id, ActualParams aps) {
		this.id = id;
		this.aps = aps;
	}


	public String toString() {
		String ret = "CallExpr\n";
		
		ret += "| " + id + "\n";
		ret += "| " + aps.toString() + "\n";
		
		return ret;
	}
	
	
	
	
}

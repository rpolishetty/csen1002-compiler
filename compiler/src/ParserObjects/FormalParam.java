package ParserObjects;

public class FormalParam {

	Type t;
	String id;
	public FormalParam() {
	
	}
	public FormalParam(Type t, String id) {

		this.t = t;
		this.id = id;
	}

	public String toString() {
		String ret = "FormalParam\n";
		
		String s = "";
		s += t.toString();
		s += id + "\n";
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
		
		return ret;
	}
	
	
	
	
}

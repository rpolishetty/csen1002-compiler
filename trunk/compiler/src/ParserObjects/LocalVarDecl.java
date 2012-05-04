package ParserObjects;

public class LocalVarDecl {

	Type t;
	String id;
	
	public LocalVarDecl() {
	
	}
	public LocalVarDecl(Type t, String id) {

		this.t = t;
		this.id = id;
	}

	public String toString() {
		String ret = "LocalVarDecl\n";
		
		String s = "";
		s += t.toString();
		s += id + "\n";
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";
		
		return ret;
	}
}

package ParserObjects;

public class Type {
	
	public String type;

	public static String INT = "int";
	public static String FL = "float";
	public static String BL = "boolean";
	public static String ST = "String";
	
	public Type() {
	
	}

	public Type(String t) {

		this.type = t;
	}

	public String toString() {
		
		String ret = "Type\n";
		
		ret += "| " + type + "\n";
		
		return ret;
		
	}
	
	
	

}

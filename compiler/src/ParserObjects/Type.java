package ParserObjects;

public class Type {
	
	public int type;
	int lineNumber;
	public static final int INT = 1;
	public static final int FLOAT = 2;
	public static final int BOOL = 3;
	public static final int STRING = 4;
	
	public Type() {
	
	}

	public Type(int t, int lineNumber) {

		this.type = t;
		this.lineNumber = lineNumber;
	}

	public String toString() {
		
		String ret = "Type\n";
		
		switch(type){
		
			case INT: 
				ret += "| int\n";
				break;
				
			case FLOAT: 
				ret += "| float\n";
				break;
				
			case BOOL: 
				ret += "| boolean\n";
				break;
				
			case STRING: 
				ret += "| String\n";
				break;
		}
		
		return ret;
		
	}
	
	
	

}

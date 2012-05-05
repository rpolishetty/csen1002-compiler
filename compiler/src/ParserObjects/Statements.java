package ParserObjects;

import java.util.ArrayList;

public class Statements {

	ArrayList<Statement> stList;

	public Statements() {
	
	}

	public Statements(ArrayList<Statement> stList) {

		this.stList = stList;
	}

	public String toString() {
		
		String ret = "Statements\n";
		
		if(stList.isEmpty())
			return ret;
		
		for(Statement st: stList)
			ret += "| " + st.toString() + "\n";
		
		return ret;
	}
	
	
	
	
}

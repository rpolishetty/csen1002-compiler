package ParserObjects;

import java.util.ArrayList;

public class ProperActualParams {

	ArrayList<Expression> eList;

	public ProperActualParams() {
	
	}

	public ProperActualParams(ArrayList<Expression> eList) {

		this.eList = eList;
	}

	public String toString() {
		 String ret = "ProperActualParams\n";
		 
		 for(Expression e: eList)
				ret += "| " + e.toString() + "\n";
			
		return ret;
	}
	
	
}

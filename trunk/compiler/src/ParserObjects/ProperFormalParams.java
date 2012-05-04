package ParserObjects;

import java.util.ArrayList;

public class ProperFormalParams {

	ArrayList<FormalParam> fpList;

	public ProperFormalParams() {
	
	}

	public ProperFormalParams(ArrayList<FormalParam> fpList) {

		this.fpList = fpList;
	}

	public String toString() {
		 String ret = "ProperFormalParams\n";
		 
		 for(FormalParam fp: fpList)
				ret += "| " + fp.toString() + "\n";
			
		return ret;
	}
	
	
	
	
}

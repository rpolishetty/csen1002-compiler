package ParserObjects;

public class FormalParams {

	ProperFormalParams pfp;

	
	public FormalParams() {

	}

	public FormalParams(ProperFormalParams pfp) {

		this.pfp = pfp;
	}


	public String toString() {
		
		String ret = "FormalParams\n";
		
		if(pfp != null)
			ret += "| " + pfp.toString() + "\n";
		
		return ret;
	}
	
	
	
	
}

package ParserObjects;

public class ActualParams {

	ProperActualParams pap;

	public ActualParams() {
	}

	public ActualParams(ProperActualParams pap) {
		this.pap = pap;
	}
	
	public String toString() {
		
		String ret = "ActualParams\n";
		
		if(pap != null)
			ret += "| " + pap.toString() + "\n";
		
		return ret;
	}
	
	
	
}

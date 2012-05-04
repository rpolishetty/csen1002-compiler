package ParserObjects;

public class Block {

	Statements sts;

	public Block() {

	}

	public Block(Statements sts) {
		
		this.sts = sts;
	}

	public String toString() {
		String ret = "Block\n";
		
		ret += "| " + sts.toString() + "\n";
		
		return ret;
	}
	
	
	
	
}

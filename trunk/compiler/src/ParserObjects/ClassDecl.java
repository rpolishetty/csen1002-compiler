package ParserObjects;


public class ClassDecl {
	
	public MethodDecls mds;
	
	public ClassDecl() {
		
	}
	
	public ClassDecl(MethodDecls mds) {
		this.mds=mds;
	}
	
	public String toString(){
		String ret = "ClassDecl\n";
		
		ret += mds.toString();
		
		return ret;
	}
}

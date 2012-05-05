package ParserObjects;

import java.util.ArrayList;

public class MethodDecls {
	
	ArrayList<MethodDecl> mdList;
	
	public MethodDecls() {
		
	}
	
	public MethodDecls(ArrayList<MethodDecl> mdList) {
		this.mdList = mdList;
	}
	
	public String toString(){
		
		String ret = "MethodDecls\n";
		
		if(mdList.isEmpty())
			return ret;
		
		for(MethodDecl md: mdList)
			ret += "| " + md.toString() + "\n";
		
		return ret;
	}
}

package ParserObjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Parser.*;

public class ClassDecl {
	
	public String id;
	public MethodDecls mds;
	static String inFile;
	int lineNumber;
	int charNumber;
	public static SymbolTable symTable;
	
	public ClassDecl(String inFile) {
		this.inFile = inFile;
	}
	
	public ClassDecl(String id, MethodDecls mds, int lineNumber, int charNumber, String inFile) {
		this.id = id;
		this.mds = mds;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
		this.inFile = inFile;
	}

	public static void throwException() throws SyntacticException {
		
		throw new SyntacticException("Error");
	
	}
	

	
	public static void returnError(String message, int lineNumber, int charNumber) throws SemanticException{
		
		try {
			String error = "";
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(inFile));
			String line = "";
			for(int i = 1; i <= lineNumber; i++)
				line = reader.readLine().trim();
			
			error += "\nSemantic Error at line " + lineNumber + " char " + charNumber + ":\n";	
			error += message + "\n";
			error += "In: " + line;
			
			throw new SemanticException(error);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	public String toString(){
		String ret = "ClassDecl " + id + "\n";
		
		ret += "| " + mds.toString();
		
		return ret;
	}
	
	public void check() throws SemanticException {

		symTable = SymbolTable.getInstance();
		symTable.add(new Entry(id),this);
		symTable.openScope();
		mds.check();
		symTable.closeScope();
		
	}
}

package Parser;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import Lexer.Lexer;
import ParserObjects.ClassDecl;
import ParserObjects.Expr;

import java_cup.runtime.Symbol;

public class AP {

	public static void main(String[] args) {
		
		String inFile = "";
		//String inFile = "/Users/michaelmkamal/Documents/workspace/compiler-3/src/Lexer/Sample.in";

		if (args.length > 0) {
			inFile = args[0];
		}
		
		try {
			FileInputStream fis = new FileInputStream(inFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);
	
			parser parser = new parser(new Lexer(dis));
			parser.infile = inFile;
			Symbol res = parser.parse();

	
			ClassDecl c = (ClassDecl) res.value;
			c.check();
			//for(Expr e: expressions) {
				System.out.print(c);
				
			fis.close();
			bis.close();
			dis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

package Parser;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import Lexer.Lexer;

import java_cup.runtime.Symbol;

public class AP {

	public static void main(String[] args) {
		
		String inFile = "/Users/michaelmkamal/Documents/workspace/compiler-3/src/Lexer/Algebra.decaf";

		if (args.length > 1) {
			inFile = args[0];
		}
		
		try {
			FileInputStream fis = new FileInputStream(inFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);
	
			parser parser = new parser(new Lexer(dis));
			Symbol res = parser.parse();
			boolean value = ((Boolean)res.value).booleanValue();
			
			if(value)
				System.out.println("File: " + inFile + " parsed successfully.");
			else
				System.out.println("Error in parsing file: " + inFile);
	
			fis.close();
			bis.close();
			dis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

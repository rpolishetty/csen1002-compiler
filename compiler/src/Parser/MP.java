package Parser;

import java.util.ArrayList;

import Lexer.LexerManual;
import Lexer.LexerSample;
import ParserObjects.ClassDecl;
import ParserObjects.Expr;
import ParserObjects.SemanticException;
import ParserObjects.SyntacticException;

/*
 * Class MP
 * 
 * Parses a specified input file and prints whether the
 * file was successfully printed or not.
 * 
 * Input file can be given as command line argument.
 * If no arguments are given, a hard coded file name
 * will be used.
 * 
 */
public class MP {
	public static void main(String[] args) throws SyntacticException, SemanticException {
		//String inFile = "/Users/Magued/Documents/sheelMaayaaWorkspace/Compiler/src/Lexer/Algebra.decaf";
		String inFile = "";
		//String inFile = "/Users/michaelmkamal/Documents/workspace/compiler-3/src/Lexer/Sample.in";

		if (args.length > 0) {
			inFile = args[0];
		}

		//Manual
		LexerManual lexer = new LexerManual(inFile);
		ParserManual parser = new ParserManual(lexer);
		
		//Sample
		//LexerSample lexer = new LexerSample(inFile);
		//ParserSample parser = new ParserSample(lexer);
		
		
		

		
//		boolean value = parser.parse();
		
//		if(value)
//			System.out.println("File: " + inFile + " parsed successfully.");
//		else
//			System.out.println("Error in parsing file: " + inFile);
		
//		ArrayList<Expr> expressions = parser.parse();
		ClassDecl c = parser.parse();
		c.check();
		//for(Expr e: expressions) {
			System.out.print(c);
			//System.out.println("Value = " + e.evaluate() + "\n");
		//}
	}
}

package Lexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * Class Lexer
 * 
 * Update the method nextToken() such to the provided
 * specifications of the Decaf Programming Language.
 * 
 * You are not allowed to use any built it in tokenizer
 * in Java. You are only allowed to scan the input file
 * one character at a time.
 */

public class Lexer {

	private BufferedReader reader; // Reader
	private char curr; // The current character being scanned

	private static final char EOF = (char) (-1);

	// End of file character

	public Lexer(String file) {
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Read the first character
		curr = read();
	}

	private char read() {
		try {
			return (char) (reader.read());
		} catch (IOException e) {
			e.printStackTrace();
			return EOF;
		}
	}

	// Checks if a character is a digit
	private boolean isNumeric(char c) {
		if (c >= '0' && c <= '9')
			return true;

		return false;
	}

	// Checks if a character is a character
	private boolean isChar(char c) {
		if ((c >= 'a' && c <= 'z') || (c >= 'a' && c <= 'z'))
			return true;

		return false;
	}

	public Token nextToken() {

		int state = 1; // Initial state
		int decimalPoint = 0;
		int intBuffer = 0;
		float numBuffer = 0; // A buffer for number literals
		String symbolBuffer = "";
		String charBuffer = "";
		String stringBuffer = "";
		String stringErrorBuffer = "";

		while (true) {
			if (curr == EOF) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			switch (state) {
			// Controller
			case 1:
				switch (curr) {
				case ' ': // Whitespaces
					curr = read();
					continue;
				
				case '\n':
				case '\b':
				case '\t':
				case '\r':
				case '\f':
					curr = read();
					continue;
					
				case ';':
					curr = read();
					return new Token("SM", ";");

				case ',':
					curr = read();
					return new Token("FA", ",");

				case '(':
					curr = read();
					return new Token("LP", "(");

				case ')':
					curr = read();
					return new Token("RP", ")");

				case '{':

					curr = read();
					return new Token("LB", "{");

				case '}':
					curr = read();
					return new Token("RB", "}");

				case '+':
					curr = read();
					return new Token("PO", "+");

				case '-':
					curr = read();
					return new Token("MO", "-");

				case '*':
					curr = read();
					return new Token("TO", "*");

				case '/':
					curr = read();
					if(curr == '*'){
						state = 6;
						curr = read();
						continue;
					}else if(curr == '/'){
						state = 7;
						curr = read();
						continue;
					}else return new Token("DO", "/");
				case '%':
					curr = read();
					return new Token("MD", "%");

				case '=':
					if (symbolBuffer == "=") {
						curr = read();
						return new Token("EQ", "==");
					} else if (symbolBuffer == "!") {
						curr = read();
						return new Token("NE", "!=");
					} else {
						symbolBuffer += curr;
						curr = read();
						if (curr != '=') {
							symbolBuffer = "";
							return new Token("AO", "=");
						} else
							continue;
					}
				case '!':
					symbolBuffer += curr;
					curr = read();
					continue;
				case '|':
					curr = read();
					if (curr == '|') {
						curr = read();
						return new Token("LO", "||");
					}
					continue;
				case '&':
					curr = read();
					if (curr == '&') {
						curr = read();
						return new Token("LA", "&&");
					}
					continue;
				case '"':
					stringBuffer = "";
					stringBuffer += curr;
					state = 4;
					curr = read();
					continue;

					// look ahead

				default:
					state = 2; // Check the next possibility
					continue;
				}

				// char - Start
			case 2:
				if (isChar(curr)  || curr == '_') {
					charBuffer = ""; // Reset the buffer.
					charBuffer += curr;

					state = 3;
					curr = read();
				} else if (isNumeric(curr)) {
					intBuffer = 0; // Reset the buffer.
					intBuffer += (curr - '0');
					
					state = 5;
					curr = read();
				}else {
					return null;
				}
				continue;
			case 3:
				if (isChar(curr) || isNumeric(curr) || curr == '_') {
					charBuffer += curr;

					curr = read();
				} else {
					if (charBuffer.equals("class") || charBuffer.equals("else")
							|| charBuffer.equals("if")
							|| charBuffer.equals("int")
							|| charBuffer.equals("float")
							|| charBuffer.equals("boolean")
							|| charBuffer.equals("String")
							|| charBuffer.equals("return")
							|| charBuffer.equals("static")
							|| charBuffer.equals("while"))
						return new Token("KW", "" + charBuffer);
					else if(charBuffer.equals("true") || charBuffer.equals("false"))
						return new Token("BL", "" + charBuffer);
					else  return new Token("ID", "" + charBuffer);
					
				}
				continue;
			case 4:
				if (curr == '\\') {
					stringBuffer += curr;
					stringErrorBuffer = "";
					stringErrorBuffer += curr;
					curr = read();
					if(curr == 'n' || curr == 'r'){
						stringErrorBuffer += curr;
						//return new Token("ERROR", "" + stringErrorBuffer); // next entrance in the method will enter state 1
					}
				}
				if (curr != '"') {
					stringBuffer += curr;
					curr = read();
				}else {
					return new Token("ST", "" + stringBuffer);
				}
				continue;
				// number - Start
			case 5:
				if (!isNumeric(curr) || curr != '.'){
					//return new Token("ERROR", "" + numBuffer); //next entrance will be
				}
				if (curr == '.'){
					numBuffer = 0;
					numBuffer += intBuffer;
					decimalPoint = 10;
				}
				if (isNumeric(curr) && decimalPoint < 10) {
					intBuffer *= 10; // Reset the buffer.
					intBuffer += (curr-0);
					curr = read();
				}else if(isNumeric(curr)){
					numBuffer += ((curr-0)/decimalPoint);
					decimalPoint *= 10;
					curr = read();
				}else if(decimalPoint < 10){
					return new Token("NUM", "" + intBuffer);
				}else {
					return new Token("NUM", "" + numBuffer);
				}
				continue;
				
			//multiline comments
			case 6:
				if (curr == '*'){
					curr = read();
					if(curr == '/'){
						state = 1;
					}
				}
				curr = read();
				continue;
			//singleline comments
			case 7:
				if (curr == '\n'){
					state = 1;
				}
				curr = read();
				continue;
			}
			
		}
	}
}

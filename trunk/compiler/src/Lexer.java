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

	public Token nextToken() {

		int state = 1; // Initial state
		int numBuffer = 0; // A buffer for number literals
		String symbolBuffer = "";

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
					return new Token("DO", "/");

				case '%':
					curr = read();
					return new Token("MD", "%");
					
				case '=':
					if(symbolBuffer == "="){
						curr = read();
						return new Token("EQ", "==");
					}else if(symbolBuffer == "!"){
						curr = read();
						return new Token("NE", "!=");
					}else{
						symbolBuffer += curr;
						curr = read();
						if(curr != '='){
							symbolBuffer = "";
							return new Token("AO", "=");
						}else continue;
					}
				case '!':
					symbolBuffer += curr;
					curr = read();
					continue;
				case '|':
					curr = read();
					if(curr == '|'){
						curr = read();
						return new Token("LO", "||");
					}
					continue;
				case '&':
					curr = read();
					if(curr == '&'){
						curr = read();
						return new Token("LA", "&&");
					}
					continue;
					
					//look ahead
					

				default:
					state = 2; // Check the next possibility
					continue;
				}

				// Integer - Start
			case 2:
				if (isNumeric(curr)) {
					numBuffer = 0; // Reset the buffer.
					numBuffer += (curr - '0');

					state = 3;
					curr = read();
				} else {
					return null;
				}
				continue;

				// Integer - Body
			case 3:
				if (isNumeric(curr)) {
					numBuffer *= 10;
					numBuffer += (curr - '0');

					curr = read();
				} else {
					return new Token("NUM", "" + numBuffer);
				}
				continue;
			}
		}
	}
}

package Lexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class LexerManual {

	private BufferedReader reader; // Reader
	private char curr; // The current character being scanned
	int lineNumber = 1;
	int charNumber = 0;
	String file;

	private static final char EOF = (char) (-1);

	// End of file character

	public LexerManual(String file) {
		this.file = file;
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
			char newChar = (char) (reader.read());
			if (curr == '\n'){
				charNumber = 0;
				lineNumber++;
			}else if (curr == '\r'){
				charNumber = 0;
				lineNumber = 1;
			}
			charNumber++;
			return newChar;
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

	// Checks if a character is a char
	private boolean isChar(char c) {
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
			return true;

		return false;
	}

	public Token nextToken() {

		int state = 1; 
		float decimalPoint = 0;
		int intBuffer = 0;
		float numBuffer = 0; 
		char symbolBuffer = 0;
		String charBuffer = "";
		String stringBuffer = "";
		char invalidBuffer;

		while (true) {
			if (curr == EOF) {
				try {
					reader.close();
					return new Token(Token.EOF, "", lineNumber, charNumber);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			switch (state) {
			// Controller
			case 1:
				switch (curr) {
				case '\n':
				case '\r':
				case ' ':
				case '\b':
				case '\t':
				case '\f':
					curr = read();
					continue;
				case ';':
					curr = read();
					return new Token(Token.SM, ";", lineNumber, charNumber);

				case ',':
					curr = read();
					return new Token(Token.FA, ",", lineNumber, charNumber);

				case '(':
					curr = read();
					return new Token(Token.LP, "(", lineNumber, charNumber);

				case ')':
					curr = read();
					return new Token(Token.RP, ")", lineNumber, charNumber);

				case '{':

					curr = read();
					return new Token(Token.LB, "{", lineNumber, charNumber);

				case '}':
					curr = read();
					return new Token(Token.RB, "}", lineNumber, charNumber);

				case '+':
					curr = read();
					return new Token(Token.PO, "+", lineNumber, charNumber);

				case '-':
					curr = read();
					return new Token(Token.MO, "-", lineNumber, charNumber);

				case '*':
					curr = read();
					return new Token(Token.TO, "*", lineNumber, charNumber);

				case '/':
					curr = read();
					if(curr == '*'){ //multiline comments
						state = 6;
						curr = read();
						continue;
					}
					else if(curr == '/'){ //single line comment
						state = 7;
						curr = read();
						continue;
					}
					else return new Token(Token.DO, "/", lineNumber, charNumber);
				
				case '%':
					curr = read();
					return new Token(Token.MD, "%", lineNumber, charNumber);

				case '=':
					if (symbolBuffer == '=') {
						curr = read();
						return new Token(Token.EQ, "==", lineNumber, charNumber);
					} 
					else if (symbolBuffer == '!') {
						curr = read();
						return new Token(Token.NE, "!=", lineNumber, charNumber);
					} 
					else {
						symbolBuffer = curr;
						curr = read();
						if (curr != '=') {
							symbolBuffer = 0;
							return new Token(Token.AO, "=", lineNumber, charNumber);
						} else
							continue;
					}
				
				case '!':
					symbolBuffer = curr;
					curr = read();
					continue;
				
				case '|':
					curr = read();
					if (curr == '|') {
						curr = read();
						return new Token(Token.LO, "||", lineNumber, charNumber);
					}else{
						warningReport("|", "||", 1,1);
						return new Token(Token.LO, "||", lineNumber, charNumber+1);
					}
				
				case '&':
					curr = read();
					if (curr == '&') {
						curr = read();
						return new Token(Token.LA, "&&", lineNumber, charNumber);
					}else{
						warningReport("&", "&&", 1,1);
						return new Token(Token.LA, "&&", lineNumber, charNumber+1);
					}
						
				case '"': // string = Start
					stringBuffer = "";
					stringBuffer += curr;
					state = 4;
					curr = read();
					continue;

				default:
					state = 2; // Check the next possibility
					continue;
				}

				// char or number - Start
			case 2:
				if (isChar(curr)  || curr == '_') {
					charBuffer = ""; // Reset the buffer.
					charBuffer += curr;

					state = 3;
					curr = read();
				} 
				
				else if (isNumeric(curr)) {
					intBuffer = 0; // Reset the buffer.
					intBuffer += (curr - '0');
					
					state = 5;
					curr = read();
				}
				
				else if (curr != '\u0000') {
					invalidBuffer = curr;
					curr = read();
					state = 1;
					warningReport("" + invalidBuffer, "", 2,1);
					continue;
				}
				
				else{
					return null;
				}
				continue;
			
			case 3: //char - Body
				
				if (isChar(curr) || isNumeric(curr) || curr == '_') {
					charBuffer += curr;

					curr = read();
				} 
				
				else {
					if (charBuffer.equals("class") || charBuffer.equals("else")
							|| charBuffer.equals("if")
							|| charBuffer.equals("int")
							|| charBuffer.equals("float")
							|| charBuffer.equals("boolean")
							|| charBuffer.equals("String")
							|| charBuffer.equals("return")
							|| charBuffer.equals("static")
							|| charBuffer.equals("while"))
						return new Token(Token.KW, "" + charBuffer, lineNumber, charNumber);
					
					else if(charBuffer.equals("true") || charBuffer.equals("false"))
						return new Token(Token.BL, "" + charBuffer, lineNumber, charNumber);
					
					else  return new Token(Token.ID, "" + charBuffer, lineNumber, charNumber);
					
				}
				continue;
			
			case 4: //strings - Body
				
				if (curr == '\n' || curr == '\r' ) {
					warningReport(stringBuffer, "", 3,1);
					curr = read();
					return new Token(Token.ST, "" + stringBuffer, lineNumber, charNumber);

				}
				
				if (curr != '"') {
					stringBuffer += curr;
					curr = read();
				}
				else {
					stringBuffer += curr;
					curr = read();
					return new Token(Token.ST, "" + stringBuffer, lineNumber, charNumber);
				}
				continue;
				
			case 5: // number - Body
				
				if (curr == '.'){
					numBuffer =  0;
					numBuffer = intBuffer;
					decimalPoint = 10;
					curr = read();
					
					if (!isNumeric(curr)){
						decimalPoint = 0;
						warningReport(".", "", 2, 1);
					}
				}
				
				if (isNumeric(curr) && decimalPoint < 10) {
					intBuffer *= 10; 
					intBuffer += (curr-'0');
					curr = read();
				}
				
				else if(isNumeric(curr)){
					numBuffer += ((curr-'0')/decimalPoint);
					decimalPoint *= 10;
					curr = read();
				}
				
				else if(decimalPoint < 10){
					
					return new Token(Token.NM, "" + intBuffer, lineNumber, charNumber);
				}
				
				else {
					return new Token(Token.NM, "" + numBuffer, lineNumber, charNumber);
				}
				continue;
				
			
			case 6: //multiline comments
				
				if (curr == '*'){
					curr = read();
					if(curr == '/'){ //end of comment
						state = 1;
					}
				}
				curr = read();
				continue;
			
			case 7: //single line comments
				if (curr == '\n'){
					state = 1;
				}
				curr = read();
				continue;
			}
			
		}
	}
	
	private void warningReport(String found, String expected, int c, int lexemeLength){
		
		
		try {
			String error = "";
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			for(int i = 1; i <= lineNumber; i++)
				line = reader.readLine().trim();
			
			error += "Warning at line " + lineNumber + " char " + (charNumber-lexemeLength) + ":\n";
			
			switch(c){
			
			case 1: 
				error += found + " converted to " + expected;
				break;
				
			case 2:
				error += "Invalid Input: '" + found + "' is ignored";
				break;
				
			case 3:
				error += "Missing \" in string: " + found + ", is added";
				break;
			}
				
			error += "\nIn: " + line + "\n";
			
			System.out.println(error);
			} catch (IOException e) {
				e.printStackTrace();
			} 
	}

}
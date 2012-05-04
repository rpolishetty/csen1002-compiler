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

public class LexerSample {

	private BufferedReader reader; // Reader
	private char curr; // The current character being scanned

	private static final char EOF = (char) (-1);

	// End of file character

	public LexerSample(String file) {
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

		while (true) {
			if (curr == EOF) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return new Token(Token.EOF, null);
			}

			switch (state) {
			// Controller
			case 1:
				switch (curr) {
				case ' ': // Whitespaces
				case '\n':
					curr = read();
					continue;

				case ';':
					curr = read();
					return new Token(Token.SM, ";");

				case '+':
					curr = read();
					return new Token(Token.PO, "+");

				case '-':
					curr = read();
					return new Token(Token.MO, "-");

				case '*':
					curr = read();
					return new Token(Token.TO, "*");

				case '/':
					curr = read();
					return new Token(Token.DO, "/");

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
					return new Token(-1, null);
				}
				continue;

				// Integer - Body
			case 3:
				if (isNumeric(curr)) {
					numBuffer *= 10;
					numBuffer += (curr - '0');

					curr = read();
				} else {
					return new Token(Token.NM, "" + numBuffer);
				}
				continue;
			}
		}
	}
}

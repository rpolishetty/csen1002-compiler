package Lexer;
import Parser.sym;
import java_cup.runtime.Symbol;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Lexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

  	int charNumber = 0;
  	public String inFile;
  	private void warningReport(String found, String expected, int c, int lexeme){
		try {
			String error = "";
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(inFile));
			String line = "";
			for(int i = 1; i <= yyline+1; i++)
				line = reader.readLine().trim();
			error += "Warning at line " + (yyline+1) + " char " + (yychar-charNumber-lexeme+1) + ":\n";
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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Lexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public Lexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int STRING = 3;
	private final int MANYLINECOMMENTS = 2;
	private final int YYINITIAL = 0;
	private final int ONELINECOMMENTS = 1;
	private final int yy_state_dtrans[] = {
		0,
		56,
		58,
		62
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NOT_ACCEPT,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NOT_ACCEPT,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NOT_ACCEPT,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NOT_ACCEPT,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"5:8,4:2,25,5,4,26,5:18,4,39,1,5:2,38,41,6,29,30,36,34,28,35,7,37,3:10,5,27," +
"5,33,5:3,2:18,19,2:7,5,6,5:2,2,5,10,18,8,2,12,14,21,24,13,2:2,9,2,15,17,2:2" +
",20,11,16,22,2,23,2:3,31,40,32,5:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,102,
"0,1,2,3,4,5,1:9,6,1:3,7,1,8,9,1,3,1:6,10,3:11,1:5,10,11,12,13,14:2,2,15,16," +
"17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41," +
"42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61")[0];

	private int yy_nxt[][] = unpackFromString(62,42,
"1,2,3,4,5,6:3,90,3:2,97,80,50,91,3,81,3,101,98,99,3:2,92,3,7,8,9,10,11,12,1" +
"3,14,15,16,17,18,19,20,51,21,22,-1:43,23,2,49,2,-1,2,-1,2:17,-1:2,2:8,-1:2," +
"2:5,-1:2,3:2,-1:4,3:17,-1:20,4,-1:3,48,-1:38,5,-1:70,25,-1:44,26,27,-1:44,2" +
"9,-1:42,30,-1:3,31,-1:39,52,54,49,54,53,54,53,54:17,-1:2,54:8,53:2,54:5,-1:" +
"2,3:2,-1:4,3:6,24,55,3:9,-1:50,28,-1:11,54,-1:40,3:2,-1:4,3:8,32,3:8,-1:17," +
"1,43:24,44,43:16,-1:2,3:2,-1:4,3:4,33,3:12,-1:17,1,45:24,46,45:10,60,-1,45:" +
"4,-1:2,3:2,-1:4,3:4,34,3:12,-1:54,47,-1:6,3:2,-1:4,3:3,35,3:13,-1:17,1,-1:4" +
"3,3:2,-1:4,3:8,36,3:8,-1:19,3:2,-1:4,3:4,37,3:12,-1:19,3:2,-1:4,3:4,38,3:12" +
",-1:19,3:2,-1:4,39,3:16,-1:19,3:2,-1:4,3:13,40,3:3,-1:19,3:2,-1:4,3:7,41,3:" +
"9,-1:19,3:2,-1:4,3:7,42,3:9,-1:19,3:2,-1:4,3:3,57,3:13,-1:19,3:2,-1:4,3:14," +
"59,3:2,-1:19,3:2,-1:4,3:3,61,3:13,-1:19,3:2,-1:4,3:2,63,3:14,-1:19,3:2,-1:4" +
",3:3,64,3:13,-1:19,3:2,-1:4,3,65,3:15,-1:19,3:2,-1:4,3:5,66,3:11,-1:19,3:2," +
"-1:4,3:7,67,3:9,-1:19,3:2,-1:4,3:12,68,3:4,-1:19,3:2,-1:4,3:2,69,3:14,-1:19" +
",3:2,-1:4,3,70,3:15,-1:19,3:2,-1:4,3:12,71,3:4,-1:19,3:2,-1:4,3:2,72,3:14,-" +
"1:19,3:2,-1:4,3:9,73,3:7,-1:19,3:2,-1:4,3,74,3:15,-1:19,3:2,-1:4,3:5,75,3:1" +
"1,-1:19,3:2,-1:4,3:8,76,3:8,-1:19,3:2,-1:4,3:5,77,3:11,-1:19,3:2,-1:4,3:14," +
"78,3:2,-1:19,3:2,-1:4,3:4,79,3:12,-1:19,3:2,-1:4,3,82,3:15,-1:19,3:2,-1:4,3" +
",83,84,3:14,-1:19,3:2,-1:4,3:16,85,-1:19,3:2,-1:4,3:2,86,3:14,-1:19,3:2,-1:" +
"4,3:12,87,3:4,-1:19,3:2,-1:4,3:8,88,3:8,-1:19,3:2,-1:4,3,89,3:15,-1:19,3:2," +
"-1:4,3:8,93,3:8,-1:19,3:2,-1:4,3:8,94,3:8,-1:19,3:2,-1:4,3:4,95,3:12,-1:19," +
"3:2,-1:4,3:9,96,3:7,-1:19,3:2,-1:4,3:9,100,3:7,-1:17");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	return new Symbol(sym.EOF, null);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ 
   warningReport(yytext(), "", 3,2-yytext().length());
   return new Symbol(sym.ST,(yyline+1), (yychar-charNumber), yytext());
}
					case -3:
						break;
					case 3:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -4:
						break;
					case 4:
						{ 
  int number = 0;
	for(int k=yy_buffer_start;k < yy_buffer_end; k++){
		number *= 10;
		number += yy_buffer[k]-'0';
	}
  return new Symbol(sym.NM,(yyline+1), (yychar-charNumber),  "" + number);
}
					case -5:
						break;
					case 5:
						{ }
					case -6:
						break;
					case 6:
						{
  warningReport(yytext(), "", 2,1);
}
					case -7:
						break;
					case 7:
						{ 
  charNumber = yychar;
}
					case -8:
						break;
					case 8:
						{ 
  yychar = 0;
  yyline = 0;
}
					case -9:
						break;
					case 9:
						{ 
  return new Symbol(sym.SM,(yyline+1), (yychar-charNumber), ";");
}
					case -10:
						break;
					case 10:
						{ 
  return new Symbol(sym.FA,(yyline+1), (yychar-charNumber), ","); 
}
					case -11:
						break;
					case 11:
						{ 
  return new Symbol(sym.LP,(yyline+1), (yychar-charNumber), "(");
}
					case -12:
						break;
					case 12:
						{ 
  return new Symbol(sym.RP,(yyline+1), (yychar-charNumber), ")"); 
}
					case -13:
						break;
					case 13:
						{ 
  return new Symbol(sym.LB,(yyline+1), (yychar-charNumber), "{");
}
					case -14:
						break;
					case 14:
						{ 
  return new Symbol(sym.RB,(yyline+1), (yychar-charNumber), "}");
}
					case -15:
						break;
					case 15:
						{ 
  return new Symbol(sym.AO,(yyline+1), (yychar-charNumber), "="); 
}
					case -16:
						break;
					case 16:
						{ 
  return new Symbol(sym.PO,(yyline+1), (yychar-charNumber), "+"); 
}
					case -17:
						break;
					case 17:
						{ 
  return new Symbol(sym.MO,(yyline+1), (yychar-charNumber), "-");
}
					case -18:
						break;
					case 18:
						{ 
  return new Symbol(sym.TO,(yyline+1), (yychar-charNumber), "*");
}
					case -19:
						break;
					case 19:
						{ 
  return new Symbol(sym.DO,(yyline+1), (yychar-charNumber), "/");
}
					case -20:
						break;
					case 20:
						{ 
  return new Symbol(sym.MD,(yyline+1), (yychar-charNumber), "%");
}
					case -21:
						break;
					case 21:
						{ 
  warningReport("|", "||", 1,1);
  return new Symbol(sym.LO,(yyline+1), (yychar-charNumber+1), "||"); 
}
					case -22:
						break;
					case 22:
						{ 
  warningReport("&", "&&", 1,1);
  return new Symbol(sym.LA,(yyline+1), (yychar-charNumber+1), "&&"); 
}
					case -23:
						break;
					case 23:
						{ 
  return new Symbol(sym.ST,(yyline+1), (yychar-charNumber), yytext());
}
					case -24:
						break;
					case 24:
						{ 
  return new Symbol(sym.IF,(yyline+1), (yychar-charNumber), yytext());
}
					case -25:
						break;
					case 25:
						{ 
  return new Symbol(sym.EQ,(yyline+1), (yychar-charNumber), "==");
}
					case -26:
						break;
					case 26:
						{
  yybegin(MANYLINECOMMENTS);
}
					case -27:
						break;
					case 27:
						{
  yybegin(ONELINECOMMENTS);
}
					case -28:
						break;
					case 28:
						{ 
  return new Symbol(sym.NE,(yyline+1), (yychar-charNumber), "!=");
}
					case -29:
						break;
					case 29:
						{ 
  return new Symbol(sym.LO,(yyline+1), (yychar-charNumber), "||"); 
}
					case -30:
						break;
					case 30:
						{ 
  return new Symbol(sym.LA,(yyline+1), (yychar-charNumber), "&&");
}
					case -31:
						break;
					case 31:
						{
	float floatNumber = 0;
	float decimalPoint = 0;
	for(int k=yy_buffer_start;k < yy_buffer_end; k++){
		if(yy_buffer[k] == '.'){
			decimalPoint=10;
			continue;
		}else if(decimalPoint>=10){
			floatNumber += (yy_buffer[k]-'0')/decimalPoint;
			decimalPoint*=10;
		}else{
			floatNumber *= 10;
			floatNumber += yy_buffer[k]-'0';
		}
	}
  return new Symbol(sym.NM,(yyline+1), (yychar-charNumber),  "" + floatNumber);
}
					case -32:
						break;
					case 32:
						{ 
  return new Symbol(sym.INT,(yyline+1), (yychar-charNumber), yytext());
}
					case -33:
						break;
					case 33:
						{ 
  return new Symbol(sym.ELSE,(yyline+1), (yychar-charNumber), yytext());
}
					case -34:
						break;
					case 34:
						{ 
  return new Symbol(sym.BL,(yyline+1), (yychar-charNumber), yytext());
}
					case -35:
						break;
					case 35:
						{ 
  return new Symbol(sym.CLASS,(yyline+1), (yychar-charNumber), yytext());
}
					case -36:
						break;
					case 36:
						{ 
  return new Symbol(sym.FLOAT,(yyline+1), (yychar-charNumber), yytext());
}
					case -37:
						break;
					case 37:
						{ 
  return new Symbol(sym.BL,(yyline+1), (yychar-charNumber), yytext());
}
					case -38:
						break;
					case 38:
						{ 
  return new Symbol(sym.WHILE,(yyline+1), (yychar-charNumber), yytext());
}
					case -39:
						break;
					case 39:
						{ 
  return new Symbol(sym.STATIC,(yyline+1), (yychar-charNumber), yytext());
}
					case -40:
						break;
					case 40:
						{ 
  return new Symbol(sym.STRING,(yyline+1), (yychar-charNumber), yytext());
}
					case -41:
						break;
					case 41:
						{ 
  return new Symbol(sym.RETURN,(yyline+1), (yychar-charNumber), yytext());
}
					case -42:
						break;
					case 42:
						{ 
  return new Symbol(sym.BOOLEAN,(yyline+1), (yychar-charNumber), yytext());
}
					case -43:
						break;
					case 43:
						{
}
					case -44:
						break;
					case 44:
						{
  charNumber = yychar;
  yybegin(YYINITIAL);
}
					case -45:
						break;
					case 45:
						{
}
					case -46:
						break;
					case 46:
						{
	charNumber = yychar;
}
					case -47:
						break;
					case 47:
						{
  yybegin(YYINITIAL);
}
					case -48:
						break;
					case 49:
						{ 
   warningReport(yytext(), "", 3,2-yytext().length());
   return new Symbol(sym.ST,(yyline+1), (yychar-charNumber), yytext());
}
					case -49:
						break;
					case 50:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -50:
						break;
					case 51:
						{
  warningReport(yytext(), "", 2,1);
}
					case -51:
						break;
					case 52:
						{ 
  return new Symbol(sym.ST,(yyline+1), (yychar-charNumber), yytext());
}
					case -52:
						break;
					case 54:
						{ 
   warningReport(yytext(), "", 3,2-yytext().length());
   return new Symbol(sym.ST,(yyline+1), (yychar-charNumber), yytext());
}
					case -53:
						break;
					case 55:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -54:
						break;
					case 57:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -55:
						break;
					case 59:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -56:
						break;
					case 61:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -57:
						break;
					case 63:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -58:
						break;
					case 64:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -59:
						break;
					case 65:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -60:
						break;
					case 66:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -61:
						break;
					case 67:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -62:
						break;
					case 68:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -63:
						break;
					case 69:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -64:
						break;
					case 70:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -65:
						break;
					case 71:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -66:
						break;
					case 72:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -67:
						break;
					case 73:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -68:
						break;
					case 74:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -69:
						break;
					case 75:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -70:
						break;
					case 76:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -71:
						break;
					case 77:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -72:
						break;
					case 78:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -73:
						break;
					case 79:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -74:
						break;
					case 80:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -75:
						break;
					case 81:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -76:
						break;
					case 82:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -77:
						break;
					case 83:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -78:
						break;
					case 84:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -79:
						break;
					case 85:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -80:
						break;
					case 86:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -81:
						break;
					case 87:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -82:
						break;
					case 88:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -83:
						break;
					case 89:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -84:
						break;
					case 90:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -85:
						break;
					case 91:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -86:
						break;
					case 92:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -87:
						break;
					case 93:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -88:
						break;
					case 94:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -89:
						break;
					case 95:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -90:
						break;
					case 96:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -91:
						break;
					case 97:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -92:
						break;
					case 98:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -93:
						break;
					case 99:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -94:
						break;
					case 100:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -95:
						break;
					case 101:
						{ 
  return new Symbol(sym.ID,(yyline+1), (yychar-charNumber), yytext());
}
					case -96:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}

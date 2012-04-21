package Lexer;

import Parser.sym;
import java_cup.runtime.Symbol;


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
		50,
		53,
		57
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
		/* 41 */ YY_NOT_ACCEPT,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NOT_ACCEPT,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NOT_ACCEPT,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NOT_ACCEPT,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NOT_ACCEPT,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
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
		/* 90 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"5:8,4:2,24,5,4,40,5:18,4,37,1,5:2,36,39,6,27,28,34,32,26,33,5,35,3:10,5,25," +
"5,31,5:3,2:18,18,2:7,5,6,5:2,2,5,9,17,7,2,11,13,20,23,12,2:2,8,2,14,16,2:2," +
"19,10,15,21,2,22,2:3,29,38,30,5:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,91,
"0,1,2,3,4,5,1:7,6,1:3,7,1:2,3,1:6,8,3:9,1:4,8,9,10,11,12:2,2,13,14,15,16,17" +
",18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42" +
",43,44,45,46,47,48,49,50,51,52,53,54,55")[0];

	private int yy_nxt[][] = unpackFromString(56,41,
"1,2,3,4,5,6:2,79,3:2,86,71,43,80,3:3,90,87,88,3:2,81,3,5,7,8,9,10,11,12,13," +
"14,15,16,17,18,44,49,52,5,-1:42,19,2,42,2,-1,2:18,-1,2:8,-1:2,2:5,-1:3,3:2," +
"-1:3,3:17,-1:18,41:2,4,41:20,-1,41:15,-1:5,5,-1:19,5,-1:15,5,-1:31,21,-1:43" +
",22,23,-1:8,27,-1:38,45,47,42,47,46,47:18,-1,47:8,46:2,47:5,-1:3,3:2,-1:3,3" +
":6,20,48,3:9,-1:48,24,-1:12,47,-1:39,3:2,-1:3,3:8,28,3:8,-1:55,25,-1:2,1,37" +
":23,38,37:16,-1:2,3:2,-1:3,3:4,29,3:12,-1:56,26,-1,1,39:33,55,-1,39:5,-1:2," +
"3:2,-1:3,3:3,30,3:13,-1:52,40,-1:7,3:2,-1:3,3:8,31,3:8,-1:17,1,-1:42,3:2,-1" +
":3,3:4,32,3:12,-1:19,3:2,-1:3,33,3:16,-1:19,3:2,-1:3,3:13,34,3:3,-1:19,3:2," +
"-1:3,3:7,35,3:9,-1:19,3:2,-1:3,3:7,36,3:9,-1:19,3:2,-1:3,3:3,51,3:13,-1:19," +
"3:2,-1:3,3:3,54,3:13,-1:19,3:2,-1:3,3:2,56,3:14,-1:19,3:2,-1:3,3,58,3:15,-1" +
":19,3:2,-1:3,3:5,59,3:11,-1:19,3:2,-1:3,3:7,60,3:9,-1:19,3:2,-1:3,3:12,61,3" +
":4,-1:19,3:2,-1:3,3:2,62,3:14,-1:19,3:2,-1:3,3,63,3:15,-1:19,3:2,-1:3,3:2,6" +
"4,3:14,-1:19,3:2,-1:3,3:9,65,3:7,-1:19,3:2,-1:3,3:5,66,3:11,-1:19,3:2,-1:3," +
"3:8,67,3:8,-1:19,3:2,-1:3,3:5,68,3:11,-1:19,3:2,-1:3,3:14,69,3:2,-1:19,3:2," +
"-1:3,3:4,70,3:12,-1:19,3:2,-1:3,3,72,3:15,-1:19,3:2,-1:3,3,73,3:15,-1:19,3:" +
"2,-1:3,3:16,74,-1:19,3:2,-1:3,3:2,75,3:14,-1:19,3:2,-1:3,3:12,76,3:4,-1:19," +
"3:2,-1:3,3:8,77,3:8,-1:19,3:2,-1:3,3,78,3:15,-1:19,3:2,-1:3,3:8,82,3:8,-1:1" +
"9,3:2,-1:3,3:8,83,3:8,-1:19,3:2,-1:3,3:4,84,3:12,-1:19,3:2,-1:3,3:9,85,3:7," +
"-1:19,3:2,-1:3,3:9,89,3:7,-1:17");

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
  return new Symbol(sym.error, yytext());
}
					case -3:
						break;
					case 3:
						{ 
  return new Symbol(sym.ID, yytext());
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
  return new Symbol(sym.NM,  "" + number);
}
					case -5:
						break;
					case 5:
						{ }
					case -6:
						break;
					case 6:
						{
  return new Symbol(sym.error, yytext());
}
					case -7:
						break;
					case 7:
						{ 
  return new Symbol(sym.SM, ";");
}
					case -8:
						break;
					case 8:
						{ 
  return new Symbol(sym.FA, ","); 
}
					case -9:
						break;
					case 9:
						{ 
  return new Symbol(sym.LP, "(");
}
					case -10:
						break;
					case 10:
						{ 
  return new Symbol(sym.RP, ")"); 
}
					case -11:
						break;
					case 11:
						{ 
  return new Symbol(sym.LB, "{");
}
					case -12:
						break;
					case 12:
						{ 
  return new Symbol(sym.RB, "}");
}
					case -13:
						break;
					case 13:
						{ 
  return new Symbol(sym.AO, "="); 
}
					case -14:
						break;
					case 14:
						{ 
  return new Symbol(sym.PO, "+"); 
}
					case -15:
						break;
					case 15:
						{ 
  return new Symbol(sym.MO, "-");
}
					case -16:
						break;
					case 16:
						{ 
  return new Symbol(sym.TO, "*");
}
					case -17:
						break;
					case 17:
						{ 
  return new Symbol(sym.DO, "/");
}
					case -18:
						break;
					case 18:
						{ 
  return new Symbol(sym.MD, "%");
}
					case -19:
						break;
					case 19:
						{ 
  return new Symbol(sym.ST, yytext());
}
					case -20:
						break;
					case 20:
						{ 
  return new Symbol(sym.IF, yytext());
}
					case -21:
						break;
					case 21:
						{ 
  return new Symbol(sym.EQ, "==");
}
					case -22:
						break;
					case 22:
						{
  yybegin(MANYLINECOMMENTS);
}
					case -23:
						break;
					case 23:
						{
  yybegin(ONELINECOMMENTS);
}
					case -24:
						break;
					case 24:
						{ 
  return new Symbol(sym.NE, "!=");
}
					case -25:
						break;
					case 25:
						{ 
  return new Symbol(sym.LO, "||"); 
}
					case -26:
						break;
					case 26:
						{ 
  return new Symbol(sym.LA, "&&");
}
					case -27:
						break;
					case 27:
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
  return new Symbol(sym.NM,  "" + floatNumber);
}
					case -28:
						break;
					case 28:
						{ 
  return new Symbol(sym.INT, yytext());
}
					case -29:
						break;
					case 29:
						{ 
  return new Symbol(sym.ELSE, yytext());
}
					case -30:
						break;
					case 30:
						{ 
  return new Symbol(sym.CLASS, yytext());
}
					case -31:
						break;
					case 31:
						{ 
  return new Symbol(sym.FLOAT, yytext());
}
					case -32:
						break;
					case 32:
						{ 
  return new Symbol(sym.WHILE, yytext());
}
					case -33:
						break;
					case 33:
						{ 
  return new Symbol(sym.STATIC, yytext());
}
					case -34:
						break;
					case 34:
						{ 
  return new Symbol(sym.STRING, yytext());
}
					case -35:
						break;
					case 35:
						{ 
  return new Symbol(sym.RETURN, yytext());
}
					case -36:
						break;
					case 36:
						{ 
  return new Symbol(sym.BOOLEAN, yytext());
}
					case -37:
						break;
					case 37:
						{
}
					case -38:
						break;
					case 38:
						{
  yybegin(YYINITIAL);
}
					case -39:
						break;
					case 39:
						{
}
					case -40:
						break;
					case 40:
						{
  yybegin(YYINITIAL);
}
					case -41:
						break;
					case 42:
						{ 
  return new Symbol(sym.error, yytext());
}
					case -42:
						break;
					case 43:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -43:
						break;
					case 44:
						{
  return new Symbol(sym.error, yytext());
}
					case -44:
						break;
					case 45:
						{ 
  return new Symbol(sym.ST, yytext());
}
					case -45:
						break;
					case 47:
						{ 
  return new Symbol(sym.error, yytext());
}
					case -46:
						break;
					case 48:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -47:
						break;
					case 49:
						{
  return new Symbol(sym.error, yytext());
}
					case -48:
						break;
					case 51:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -49:
						break;
					case 52:
						{
  return new Symbol(sym.error, yytext());
}
					case -50:
						break;
					case 54:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -51:
						break;
					case 56:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -52:
						break;
					case 58:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -53:
						break;
					case 59:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -54:
						break;
					case 60:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -55:
						break;
					case 61:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -56:
						break;
					case 62:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -57:
						break;
					case 63:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -58:
						break;
					case 64:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -59:
						break;
					case 65:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -60:
						break;
					case 66:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -61:
						break;
					case 67:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -62:
						break;
					case 68:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -63:
						break;
					case 69:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -64:
						break;
					case 70:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -65:
						break;
					case 71:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -66:
						break;
					case 72:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -67:
						break;
					case 73:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -68:
						break;
					case 74:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -69:
						break;
					case 75:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -70:
						break;
					case 76:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -71:
						break;
					case 77:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -72:
						break;
					case 78:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -73:
						break;
					case 79:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -74:
						break;
					case 80:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -75:
						break;
					case 81:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -76:
						break;
					case 82:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -77:
						break;
					case 83:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -78:
						break;
					case 84:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -79:
						break;
					case 85:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -80:
						break;
					case 86:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -81:
						break;
					case 87:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -82:
						break;
					case 88:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -83:
						break;
					case 89:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -84:
						break;
					case 90:
						{ 
  return new Symbol(sym.ID, yytext());
}
					case -85:
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

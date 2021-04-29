import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LexicalAnalyzer{
	
	static int charClass;
	static char[] lexeme = new char[100];
	static char nextChar;
	static int lexLength;
	static int token;
	static int nextToken;
	static FileReader input;
	
	//character classes
	final static int LETTER = 0;
	final static int DIGIT = 1;
	final static int UNKNOWN = 99;
	
	//token codes
	final static int BEGIN = 2;
	final static int END = 3;
	final static int IDENT = 11;
	final static int INT_CONST = 10;
	final static int ASSIGN_OP = 20;
	final static int ADD_OP = 21;
	final static int SUB_OP = 22;
	final static int MUL_OP = 23;
	final static int DIV_OP = 24;
	final static int GNE_OP = 25;
	final static int LNE_OP = 26;
	final static int LEFT_PAREN = 27;
	final static int RIGHT_PAREN = 28;
	final static int SEMICOL = 29;
	final static int IF = 30;
	final static int LOOP = 31;
	final static int THEN = 32;
	final static int SPACE = 33;
	
	LexicalAnalyzer(File code) throws IOException{
		input = new FileReader(code);
		nextChar = (char) input.read();
		lex();
	}
	
	public static void getChar() throws IOException{
		if((nextChar = (char) input.read()) != -1) {
			if(Character.isLetter(nextChar)) {
				charClass = LETTER;
			}
			else if(Character.isDigit(nextChar)) {
				charClass = DIGIT;
			}
			else {
				charClass = UNKNOWN;
			}
		}	
	}
	
	public static void addChar() {
		if(lexLength <= 98) {
			lexeme[lexLength] = nextChar;
			lexLength++;
			lexeme[lexLength] = 0;
		}
		else {
			System.out.println("Error- lexeme is too long!");
		}
	}
	
	public static void getNonBlank() throws IOException {
		while(Character.isWhitespace(nextChar)) {
			getChar();
		}
	}
	
	public static int lex() throws IOException {
		lexLength = 0;
		lexeme = new char[100];
		getNonBlank();
		
		switch(charClass) {
			case LETTER:
				addChar();
				getChar();
				while(charClass == LETTER || charClass == DIGIT) {
					addChar();
					getChar();
				}
				nextToken = IDENT;
				specialTerminals();
				break;
			case DIGIT:
				addChar();
				getChar();
				while(charClass == DIGIT) {
					addChar();
					getChar();
				}
				nextToken = INT_CONST;
				break;	
			case UNKNOWN:
				lookup(nextChar);
				getChar();
				break;
		}
		
		System.out.println("Next token is: " + nextToken + ", Next lexeme is: " + String.valueOf(lexeme));
		return nextToken;
	}
	
	public static int lookup(char c) {
		switch(c) {
			case '(':
				addChar();
				nextToken = LEFT_PAREN;
				break;
			case ')':
				addChar();
				nextToken = RIGHT_PAREN;
				break;
			case '+':
				addChar();
				nextToken = ADD_OP;
				break;
			case '-':
				addChar();
				nextToken = SUB_OP;
				break;
			case '*':
				addChar();
				nextToken = MUL_OP;
				break;
			case '/':
				addChar();
				nextToken = DIV_OP;
				break;
			case '<':
				addChar();
				nextToken = LNE_OP;
				break;
			case '>':
				addChar();
				nextToken = GNE_OP;
				break;
			case '=':
				addChar();
				nextToken = ASSIGN_OP;
				break;
			case ';':
				addChar();
				nextToken = SEMICOL;
				break;
		}
		
		return nextToken;
	}
	
	public static void specialTerminals() throws IOException {
		if(String.valueOf(lexeme, 0, lexLength).equals("program")) {
			lexLength = 0;
			lexeme = new char[100];
			getNonBlank();
			while(charClass == LETTER) {
				addChar();
				getChar();
			}
			if(String.valueOf(lexeme, 0, lexLength).equals("begin")) {
				nextToken = BEGIN;
			}
		}
		if(String.valueOf(lexeme, 0, lexLength).equals("end")) {
			nextToken = END;
		}
		if(String.valueOf(lexeme, 0, lexLength).equals("if")) {
			nextToken = IF;
		}
		if(String.valueOf(lexeme, 0, lexLength).equals("loop")) {
			nextToken = LOOP;
		}
	}

}

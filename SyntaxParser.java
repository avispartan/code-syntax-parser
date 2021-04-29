import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SyntaxParser {

	public static void main(String[] args) throws IOException {
		
		System.out.println("Enter the file to parse: ");
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		File program = new File(input.nextLine());
		new LexicalAnalyzer(program);
		program();

	}
	
	public static void program() throws IOException {
		if(LexicalAnalyzer.nextToken == LexicalAnalyzer.BEGIN) {
			LexicalAnalyzer.lex();
			statementList();
		}
		
		if(LexicalAnalyzer.nextToken == LexicalAnalyzer.END) {
			System.out.println("Program is valid!");
		}
		else {
			error();
		}
	}

	public static void statementList() throws IOException {
		statement();
		while(LexicalAnalyzer.nextToken == LexicalAnalyzer.SEMICOL) {
			LexicalAnalyzer.lex();
			statement();
		}
	}
	
	private static void statement() throws IOException {
		if(LexicalAnalyzer.nextToken == LexicalAnalyzer.IDENT) {
			LexicalAnalyzer.lex();
			assignStatement();
		}
		else if(LexicalAnalyzer.nextToken == LexicalAnalyzer.IF) {
			LexicalAnalyzer.lex();
			ifStatement();
		}
		else if(LexicalAnalyzer.nextToken == LexicalAnalyzer.LOOP) {
			LexicalAnalyzer.lex();
			loopStatement();
		}
		else {
			error();
		}
	}

	private static void loopStatement() throws IOException {
		if(LexicalAnalyzer.nextToken == LexicalAnalyzer.LEFT_PAREN) {
			LexicalAnalyzer.lex();
			logicExpression();
			if(LexicalAnalyzer.nextToken == LexicalAnalyzer.RIGHT_PAREN) {
				LexicalAnalyzer.lex();
				statement();
			}
		}
		
	}

	private static void logicExpression() throws IOException {
		variable();
		if(LexicalAnalyzer.nextToken == LexicalAnalyzer.ASSIGN_OP) {
			LexicalAnalyzer.lex();
			expression();
		}
		
	}

	private static void variable() throws IOException {
		if(LexicalAnalyzer.nextToken == LexicalAnalyzer.IDENT) {
			LexicalAnalyzer.lex();
		}
	}

	private static void ifStatement() throws IOException {
		if(LexicalAnalyzer.nextToken == LexicalAnalyzer.LEFT_PAREN) {
			LexicalAnalyzer.lex();
			logicExpression();
			if(LexicalAnalyzer.nextToken == LexicalAnalyzer.RIGHT_PAREN) {
				LexicalAnalyzer.lex();
				if(LexicalAnalyzer.nextToken == LexicalAnalyzer.THEN) {
					LexicalAnalyzer.lex();
					statement();
				}
			}
		}
		
	}

	private static void assignStatement() throws IOException {
		if(LexicalAnalyzer.nextToken == LexicalAnalyzer.ASSIGN_OP) {
			LexicalAnalyzer.lex();
			expression();
		}
		
	}

	private static void expression() throws IOException {
		term();
		while(LexicalAnalyzer.nextToken == LexicalAnalyzer.ADD_OP || LexicalAnalyzer.nextToken == LexicalAnalyzer.SUB_OP) {
			LexicalAnalyzer.lex();
			term();
		}
		
	}

	private static void term() throws IOException {
		factor();
		while(LexicalAnalyzer.nextToken == LexicalAnalyzer.MUL_OP || LexicalAnalyzer.nextToken == LexicalAnalyzer.DIV_OP) {
			LexicalAnalyzer.lex();
			factor();
		}
		
	}

	private static void factor() throws IOException {
		if(LexicalAnalyzer.nextToken == LexicalAnalyzer.IDENT) {
			LexicalAnalyzer.lex();
		}
		else if(LexicalAnalyzer.nextToken == LexicalAnalyzer.INT_CONST) {
			LexicalAnalyzer.lex();
		}
		else if(LexicalAnalyzer.nextToken == LexicalAnalyzer.LEFT_PAREN) {
			LexicalAnalyzer.lex();
			expression();
			if(LexicalAnalyzer.nextToken == LexicalAnalyzer.RIGHT_PAREN) {
				LexicalAnalyzer.lex();
			}
		}
		else {
			error();
		}
	}

	private static void error() {
		System.out.println("Program has errors!");
	}

}

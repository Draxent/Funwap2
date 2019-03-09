package org.draxent.funwap.lexicalanalysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ScannerTest {

	@Test
	public void test_tokenize() {
		String source =
			"func sum(a int, b int) int\n" + 
			"{\n" + 
			"	return a + b;\n" + 
			"}";
		Scanner scanner = new Scanner(source);
		List<Token> tokens = scanner.tokenize();
		assertEquals(17, tokens.size());
		assertEquals(TokenType.DECLFUNC, tokens.get(0).getType());
		assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
		assertEquals(TokenType.ROUNDBR_OPEN, tokens.get(2).getType());
		assertEquals(TokenType.IDENTIFIER, tokens.get(3).getType());
		assertEquals(TokenType.TYPEINT, tokens.get(4).getType());
		assertEquals(TokenType.COMMA, tokens.get(5).getType());
		assertEquals(TokenType.IDENTIFIER, tokens.get(6).getType());
		assertEquals(TokenType.TYPEINT, tokens.get(7).getType());
		assertEquals(TokenType.ROUNDBR_CLOSE, tokens.get(8).getType());
		assertEquals(TokenType.TYPEINT, tokens.get(9).getType());
		assertEquals(TokenType.CURLYBR_OPEN, tokens.get(10).getType());
		assertEquals(TokenType.RETURN, tokens.get(11).getType());
		assertEquals(TokenType.IDENTIFIER, tokens.get(12).getType());
		assertEquals(TokenType.PLUS, tokens.get(13).getType());
		assertEquals(TokenType.IDENTIFIER, tokens.get(14).getType());
		assertEquals(TokenType.SEMICOLONS, tokens.get(15).getType());
		assertEquals(TokenType.CURLYBR_CLOSE, tokens.get(16).getType());
	}
	
	@Test
	public void test_skipWhiteSpaces() {
		test_skipWhiteSpaces("   ", 3, 1, 4);
		test_skipWhiteSpaces("   \n", 4, 2, 1);
		test_skipWhiteSpaces("   \n\t\t\n\t \n \r\n ", 14, 5, 2);
	}

	private void test_skipWhiteSpaces(String source, int index, int row, int column) {
		Scanner scanner = new Scanner(source);
		scanner.skipWhiteSpaces();		
		checkIndexRowColumn(scanner, index, row, column);
	}
	
	@Test
	public void test_checkCharLookingAhead() {
		Scanner scanner = new Scanner("test");
		assertTrue(scanner.checkCharLookingAhead('t', 0));
		assertTrue(scanner.checkCharLookingAhead('e', 1));
		assertTrue(scanner.checkCharLookingAhead('s', 2));
		assertTrue(scanner.checkCharLookingAhead('t', 3));
		assertFalse(scanner.checkCharLookingAhead('x', 0));
		assertFalse(scanner.checkCharLookingAhead('x', 1));
		assertFalse(scanner.checkCharLookingAhead('x', 4));
	}
	
	@Test
	public void test_skipComment() {
		test_skipLineComment("//test\n", 6, 1, 7);
		test_skipLineComment("//test comment\n", 14, 1, 15);
		test_skipLineComment("/*comment*/", 11, 1, 12);
		test_skipLineComment("/*comment*/other", 11, 1, 12);
		test_skipLineComment("/* comment */", 13, 1, 14);
		test_skipLineComment("/* comment */ other /* comment2 */", 13, 1, 14);
		test_skipLineComment("/* first line\n * second line*/", 30, 2, 17);
		test_skipLineComment("/* first line\n * second line\n * third line */", 45, 3, 17);
	}
	
	private void test_skipLineComment(String source, int index, int row, int column) {
		Scanner scanner = new Scanner(source);
		scanner.skipComment();
		checkIndexRowColumn(scanner, index, row, column);	
	}

	private void checkIndexRowColumn(Scanner scanner, int index, int row, int column) {
		assertEquals(index, scanner.getIndex());
		assertEquals(row, scanner.getRow());
		assertEquals(column, scanner.getColumn());
	}
	
	@Test
	public void test_notRegEx_matchToken() {
		checkStringEqualTokenTypeUsingMatchToken("{", TokenType.CURLYBR_OPEN);
		checkStringEqualTokenTypeUsingMatchToken("}", TokenType.CURLYBR_CLOSE);
		checkStringEqualTokenTypeUsingMatchToken("(", TokenType.ROUNDBR_OPEN);
		checkStringEqualTokenTypeUsingMatchToken(")", TokenType.ROUNDBR_CLOSE);	
		checkStringEqualTokenTypeUsingMatchToken(";", TokenType.SEMICOLONS);
		checkStringEqualTokenTypeUsingMatchToken(",", TokenType.COMMA);
		checkStringEqualTokenTypeUsingMatchToken("++", TokenType.INCR);
		checkStringEqualTokenTypeUsingMatchToken("--", TokenType.DECR);
		checkStringEqualTokenTypeUsingMatchToken("+=", TokenType.ASSIGN_PLUS);
		checkStringEqualTokenTypeUsingMatchToken("-=", TokenType.ASSIGN_MINUS);
		checkStringEqualTokenTypeUsingMatchToken("if", TokenType.IF);
		checkStringEqualTokenTypeUsingMatchToken("else", TokenType.ELSE);
		checkStringEqualTokenTypeUsingMatchToken("while", TokenType.WHILE);
		checkStringEqualTokenTypeUsingMatchToken("for", TokenType.FOR);
		checkStringEqualTokenTypeUsingMatchToken("return", TokenType.RETURN);
		checkStringEqualTokenTypeUsingMatchToken("println", TokenType.PRINTLN);
		checkStringEqualTokenTypeUsingMatchToken("readln", TokenType.READLN);
		checkStringEqualTokenTypeUsingMatchToken("=", TokenType.ASSIGN);
		checkStringEqualTokenTypeUsingMatchToken("||", TokenType.OR);
		checkStringEqualTokenTypeUsingMatchToken("&&", TokenType.AND);
		checkStringEqualTokenTypeUsingMatchToken("==", TokenType.EQUAL);
		checkStringEqualTokenTypeUsingMatchToken("!=", TokenType.INEQUAL);
		checkStringEqualTokenTypeUsingMatchToken(">=", TokenType.GREATEREQ);
		checkStringEqualTokenTypeUsingMatchToken("<=", TokenType.LESSEQ);
		checkStringEqualTokenTypeUsingMatchToken("!", TokenType.NOT);
		checkStringEqualTokenTypeUsingMatchToken(">", TokenType.GREATER);
		checkStringEqualTokenTypeUsingMatchToken("<", TokenType.LESS);
		checkStringEqualTokenTypeUsingMatchToken("+", TokenType.PLUS);
		checkStringEqualTokenTypeUsingMatchToken("-", TokenType.MINUS);
		checkStringEqualTokenTypeUsingMatchToken("*", TokenType.MUL);
		checkStringEqualTokenTypeUsingMatchToken("/", TokenType.DIV);
		checkStringEqualTokenTypeUsingMatchToken("var", TokenType.DECLVAR);
		checkStringEqualTokenTypeUsingMatchToken("func", TokenType.DECLFUNC);
		checkStringEqualTokenTypeUsingMatchToken("int", TokenType.TYPEINT);
		checkStringEqualTokenTypeUsingMatchToken("bool", TokenType.TYPEBOOL);
		checkStringEqualTokenTypeUsingMatchToken("char", TokenType.TYPECHAR);
		checkStringEqualTokenTypeUsingMatchToken("string", TokenType.TYPESTRING);
		checkStringEqualTokenTypeUsingMatchToken("fun", TokenType.TYPEFUN);
		checkStringEqualTokenTypeUsingMatchToken("true", TokenType.TRUE);
		checkStringEqualTokenTypeUsingMatchToken("false", TokenType.FALSE);
		checkStringEqualTokenTypeUsingMatchToken("Main", TokenType.MAIN);
	}
	
	@Test
	public void test_RegEx_matchToken() {
		checkStringEqualTokenTypeUsingMatchToken("1", TokenType.NUMBER);
		checkStringEqualTokenTypeUsingMatchToken("3789", TokenType.NUMBER);
		checkStringEqualTokenTypeUsingMatchToken("'a'", TokenType.CHAR);
		checkStringEqualTokenTypeUsingMatchToken("'%'", TokenType.CHAR);
		checkStringEqualTokenTypeUsingMatchToken("\"\"", TokenType.STRING);
		checkStringEqualTokenTypeUsingMatchToken("\"test\"", TokenType.STRING);
		checkStringEqualTokenTypeUsingMatchToken("x", TokenType.IDENTIFIER);
		checkStringEqualTokenTypeUsingMatchToken("_x_y_z3", TokenType.IDENTIFIER);
		//TODO: Add test for URL		
	}
	
	private void checkStringEqualTokenTypeUsingMatchToken(String s, TokenType type) {
		assertEquals(type, new Scanner(s).matchToken().getType());		
	}
	
	@Test
	public void test_unknown_matchToken() {
		assertEquals(null, new Scanner("'ab'").matchToken());
		assertEquals(null, new Scanner("\"test").matchToken());
	}
	
	@Test
	public void test_getSourceSubSequence() {
		Scanner scanner = new Scanner("test");
		assertEquals("test", scanner.getSourceSubSequence(0, 4));
		assertEquals("est", scanner.getSourceSubSequence(1, 4));
		assertEquals("", scanner.getSourceSubSequence(4, 4));
		assertEquals("", scanner.getSourceSubSequence(5, 4));
		assertEquals("", scanner.getSourceSubSequence(4, 5));
		assertEquals("test", scanner.getSourceSubSequence(0, 4));
		assertEquals("e", scanner.getSourceSubSequence(1, 2));
		assertEquals("es", scanner.getSourceSubSequence(1, 3));
		assertEquals("st", scanner.getSourceSubSequence(2, 4));
	}
}

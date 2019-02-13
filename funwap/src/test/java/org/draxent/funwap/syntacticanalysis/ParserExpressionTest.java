package org.draxent.funwap.syntacticanalysis;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.ast.expression.ConstantNode;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.expression.VarNode;
import org.draxent.funwap.ast.expression.operation.BinaryOperationNode;
import org.draxent.funwap.ast.expression.operation.UnaryOperationNode;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ParserExpressionTest {
	private static final String VAR_X = "x";
	private static final String VAR_Y = "y";
	private static final String VAR_Z = "z";
	private static final String VAR_W = "w";
	private static final Integer[] NUM = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	
	private ParserTestUtils utils = new ParserTestUtils();

	@Test
	public void testConstantNumber() {
		ExpressionNode n = parseExpression(Arrays.asList(createTokenNumber(NUM[1])));
		checkNode(n, ConstantNode.class, NUM[1].toString());
	}

	@Test
	public void testConstantIdentifier() {
		ExpressionNode n = parseExpression(Arrays.asList(createTokenIdentifier(VAR_X)));
		checkNode(n, VarNode.class, VAR_X);
	}

	@Test
	public void testUnaryExpression() {
		ExpressionNode n = parseExpression(Arrays.asList(utils.createToken(TokenType.MINUS), createTokenIdentifier(VAR_X)));
		checkNode(n, UnaryOperationNode.class, "-");
		checkNode(((UnaryOperationNode) n).getChild(), VarNode.class, VAR_X);
	}

	@Test
	public void testTerm1() {
		ExpressionNode n = parseExpression(
				Arrays.asList(createTokenNumber(NUM[3]), utils.createToken(TokenType.MUL), createTokenNumber(NUM[7])));
		checkNode(n, BinaryOperationNode.class, TokenType.MUL.toString());
		checkNode(((BinaryOperationNode) n).getLeftExpression(), ConstantNode.class, NUM[3].toString());
		checkNode(((BinaryOperationNode) n).getRightExpression(), ConstantNode.class, NUM[7].toString());
	}
	
	@Test
	public void testTerm2() {
		ExpressionNode n = parseExpression(Arrays.asList(createTokenNumber(NUM[3]), utils.createToken(TokenType.MUL),
				createTokenNumber(NUM[7]), utils.createToken(TokenType.DIV), createTokenNumber(NUM[2])));
		checkNode(n, BinaryOperationNode.class, TokenType.DIV.toString());
		checkNode(((BinaryOperationNode) n).getLeftExpression(), BinaryOperationNode.class, "*");
		checkNode(((BinaryOperationNode) n).getRightExpression(), ConstantNode.class, NUM[2].toString());
		ExpressionNode leftNode = ((BinaryOperationNode) n).getLeftExpression();
		checkNode(((BinaryOperationNode) leftNode).getLeftExpression(), ConstantNode.class, NUM[3].toString());
		checkNode(((BinaryOperationNode) leftNode).getRightExpression(), ConstantNode.class, NUM[7].toString());
	}
	
	@Test
	public void testSumExpression() {
		ExpressionNode n = parseExpression(
				Arrays.asList(createTokenNumber(NUM[3]), utils.createToken(TokenType.PLUS), createTokenNumber(NUM[7])));
		checkNode(n, BinaryOperationNode.class, TokenType.PLUS.toString());
		checkNode(((BinaryOperationNode) n).getLeftExpression(), ConstantNode.class, NUM[3].toString());
		checkNode(((BinaryOperationNode) n).getRightExpression(), ConstantNode.class, NUM[7].toString());
	}
	
	@Test
	public void testRelationalExpression() {
		ExpressionNode n = parseExpression(
				Arrays.asList(createTokenNumber(NUM[3]), utils.createToken(TokenType.LESS), createTokenNumber(NUM[7])));
		checkNode(n, BinaryOperationNode.class, TokenType.LESS.toString());
		checkNode(((BinaryOperationNode) n).getLeftExpression(), ConstantNode.class, NUM[3].toString());
		checkNode(((BinaryOperationNode) n).getRightExpression(), ConstantNode.class, NUM[7].toString());
	}
	
	@Test
	public void testComplexExpression() {
		ExpressionNode n = parseExpression(Arrays.asList(
				createTokenIdentifier(VAR_X),
				utils.createToken(TokenType.AND),
				createTokenIdentifier(VAR_Y),
				utils.createToken(TokenType.OR),
				createTokenIdentifier(VAR_Z),
				utils.createToken(TokenType.GREATER),
				createTokenIdentifier(VAR_W)
		));
		checkNode(n, BinaryOperationNode.class, TokenType.OR.toString());
		checkNode(((BinaryOperationNode) n).getLeftExpression(), BinaryOperationNode.class, TokenType.AND.toString());
		checkNode(((BinaryOperationNode) n).getRightExpression(), BinaryOperationNode.class, TokenType.GREATER.toString());
		ExpressionNode leftNode = ((BinaryOperationNode) n).getLeftExpression();
		checkNode(((BinaryOperationNode) leftNode).getLeftExpression(), VarNode.class, VAR_X);
		checkNode(((BinaryOperationNode) leftNode).getRightExpression(), VarNode.class, VAR_Y);
		ExpressionNode rightNode = ((BinaryOperationNode) n).getRightExpression();
		checkNode(((BinaryOperationNode) rightNode).getLeftExpression(), VarNode.class, VAR_Z);
		checkNode(((BinaryOperationNode) rightNode).getRightExpression(), VarNode.class, VAR_W);
	}
	
	private ExpressionNode parseExpression(List<Token> tokenList) {
		TokenReader tokenReader = new TokenReader(tokenList);
		return new ParserExpression(tokenReader).parse();		
	}
	
	private Token createTokenIdentifier(String value) {
		return utils.createToken(TokenType.IDENTIFIER, value);
	}
	
	private Token createTokenNumber(int value) {
		return utils.createToken(TokenType.NUMBER, String.valueOf(value));
	}
	
	private <T> void checkNode(ExpressionNode expressionNode, Class<T> classType, String value) {
		assertEquals(classType, expressionNode.getClass());
		assertEquals(value, expressionNode.getToken().getValue());
	}
}

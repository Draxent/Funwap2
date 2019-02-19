package org.draxent.funwap.syntacticanalysis;

import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.ast.expression.ConstantNode;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.expression.VarNode;
import org.draxent.funwap.ast.expression.operation.BinaryOperationNode;
import org.draxent.funwap.ast.expression.operation.OperationNode;
import org.draxent.funwap.ast.statement.AssignNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.CallNode;
import org.draxent.funwap.ast.statement.StatementNode;
import org.draxent.funwap.ast.statement.command.ForNode;
import org.draxent.funwap.ast.statement.command.IfNode;
import org.draxent.funwap.ast.statement.command.PrintNode;
import org.draxent.funwap.ast.statement.command.ReadNode;
import org.draxent.funwap.ast.statement.command.ReturnNode;
import org.draxent.funwap.ast.statement.command.WhileNode;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class ParserBlockTest {
	
	ParserTestUtils utils;
	private ParserExpression parseExpression;
	private ParserDeclarationList parserDeclarationList;

	@Before
    public void before() {
		utils = new ParserTestUtils();
		parseExpression = mock(ParserExpression.class);
		parserDeclarationList = mock(ParserDeclarationList.class);
	}
	
	@Test
	public void testCall() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.CURLYBR_OPEN,
				TokenType.IDENTIFIER,
				TokenType.ROUNDBR_OPEN,
				TokenType.ROUNDBR_CLOSE,
				TokenType.SEMICOLONS,
				TokenType.CURLYBR_CLOSE
		);
		CallNode callnode = (CallNode) testStatement(tokenTypes, null, 1, 0, CallNode.class);
		assertEquals(0, callnode.numActualParameters());
	}
	
	@Test
	public void testReadln() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.CURLYBR_OPEN,
				TokenType.IDENTIFIER,
				TokenType.ASSIGN,
				TokenType.READLN,
				TokenType.ROUNDBR_OPEN,
				TokenType.ROUNDBR_CLOSE,
				TokenType.CURLYBR_CLOSE
		);
		AssignNode assignNode = (AssignNode) testStatement(tokenTypes, null, 1, 0, AssignNode.class);
		assertEquals(ReadNode.class, assignNode.getAssignedValue().getClass());
		ReadNode readNode = (ReadNode) assignNode.getAssignedValue();
		assertEquals(TokenType.IDENTIFIER, readNode.getContainerVariable().getType());
	}
	
	@Test
	public void testStatementIdentifierPlusEqual() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.CURLYBR_OPEN,
				TokenType.IDENTIFIER,
				TokenType.ASSIGN_PLUS,
				TokenType.CURLYBR_CLOSE
		);
		ConstantNode n = new ConstantNode(utils.createToken(TokenType.NUMBER));
		AssignNode assignNode = (AssignNode) testStatement(tokenTypes, n, 1, 1, AssignNode.class);
		assertEquals(BinaryOperationNode.class, assignNode.getAssignedValue().getClass());
		BinaryOperationNode opNode = (BinaryOperationNode) assignNode.getAssignedValue();
		assertEquals(OperationNode.OperationType.PLUS, opNode.getOperationType());
		assertEquals(VarNode.class, opNode.getLeftExpression().getClass());
		assertEquals(ConstantNode.class, opNode.getRightExpression().getClass());
	}
	
	@Test
	public void testIfElse() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.CURLYBR_OPEN,
				TokenType.IF,
				TokenType.ROUNDBR_OPEN,
				TokenType.ROUNDBR_CLOSE,
				TokenType.CURLYBR_OPEN,
				TokenType.CURLYBR_CLOSE,
				TokenType.ELSE,
				TokenType.CURLYBR_OPEN,
				TokenType.CURLYBR_CLOSE,
				TokenType.CURLYBR_CLOSE
		);
		ConstantNode n = new ConstantNode(utils.createToken(TokenType.TRUE));
		IfNode ifNode = (IfNode) testStatement(tokenTypes, n, 3, 1, IfNode.class);
		assertEquals(TokenType.TRUE, ifNode.getCondition().getToken().getType());
		assertNotEquals(null, ifNode.getThenBlock());
		assertNotEquals(null, ifNode.getElseBlock());
	}
	
	@Test
	public void testWhile() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.CURLYBR_OPEN,
				TokenType.WHILE,
				TokenType.ROUNDBR_OPEN,
				TokenType.ROUNDBR_CLOSE,
				TokenType.CURLYBR_OPEN,
				TokenType.CURLYBR_CLOSE,
				TokenType.CURLYBR_CLOSE
		);
		ConstantNode n = new ConstantNode(utils.createToken(TokenType.TRUE));
		WhileNode whileNode = (WhileNode) testStatement(tokenTypes, n, 2, 1, WhileNode.class);
		assertEquals(TokenType.TRUE, whileNode.getCondition().getToken().getType());
		assertNotEquals(null, whileNode.getBody());
	}
	
	@Test
	public void testFor() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.CURLYBR_OPEN,
				TokenType.FOR,
				TokenType.ROUNDBR_OPEN,
				TokenType.IDENTIFIER,
				TokenType.ASSIGN,
				TokenType.SEMICOLONS,
				TokenType.SEMICOLONS,
				TokenType.IDENTIFIER,
				TokenType.INCR,
				TokenType.ROUNDBR_CLOSE,
				TokenType.CURLYBR_OPEN,
				TokenType.CURLYBR_CLOSE,
				TokenType.CURLYBR_CLOSE
		);
		ConstantNode n = new ConstantNode(utils.createToken(TokenType.TRUE));
		ForNode forNode = (ForNode) testStatement(tokenTypes, n, 2, 2, ForNode.class);
		assertNotEquals(null, forNode.getFirstStatement());
		assertEquals(TokenType.TRUE, forNode.getCondition().getToken().getType());
		assertNotEquals(null, forNode.getSecondStatement());
		assertNotEquals(null, forNode.getBody());
	}
	
	@Test
	public void testReturn() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.CURLYBR_OPEN,
				TokenType.RETURN,
				TokenType.SEMICOLONS,
				TokenType.CURLYBR_CLOSE
		);
		ConstantNode n = new ConstantNode(utils.createToken(TokenType.NUMBER));
		ReturnNode returnNode = (ReturnNode) testStatement(tokenTypes, n, 1, 1, ReturnNode.class);
		assertNotEquals(null, returnNode.getReturnedValue());
	}
	
	@Test
	public void testPrintln() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.CURLYBR_OPEN,
				TokenType.PRINTLN,
				TokenType.ROUNDBR_OPEN,
				TokenType.ROUNDBR_CLOSE,
				TokenType.SEMICOLONS,
				TokenType.CURLYBR_CLOSE
		);
		ConstantNode n = new ConstantNode(utils.createToken(TokenType.STRING, "test"));
		PrintNode printNode = (PrintNode) testStatement(tokenTypes, n, 1, 1, PrintNode.class);
		assertEquals(1, printNode.numExpressions());
		assertEquals(TokenType.STRING, printNode.getExpression(0).getToken().getType());
	}
	
	private <T> StatementNode testStatement(
			List<TokenType> tokenTypes,
			ExpressionNode parseExpressionReturn,
			int timesParseDeclaration,
			int timesParseExpression,
			Class<T> classType) {
		// Arrange
		List<Token> tokens = utils.createTokenList(tokenTypes);
		TokenReader tokenReader = new TokenReader(tokens);
		when(parseExpression.parse()).thenReturn(parseExpressionReturn);
		// Act
		BlockNode n = new ParserBlock(tokenReader, parseExpression, parserDeclarationList).parse(BlockNode.BlockType.BODY);
		// Assert
		verify(parserDeclarationList, times(timesParseDeclaration)).parse(any());
		verify(parseExpression, times(timesParseExpression)).parse();
		assertEquals(1, n.numChildren());
		assertEquals(classType, n.getChild(0).getClass());	
		return n.getChild(0);
	}
}

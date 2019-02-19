package org.draxent.funwap.syntacticanalysis;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.ast.expression.ConstantNode;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.DeclarationNode;
import org.draxent.funwap.ast.statement.FunctionNode;
import org.draxent.funwap.ast.statement.StatementNode;
import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ParserDeclarationListTest {
	
	ParserTestUtils utils;
	private ParserExpression parseExpression;
	private ParserBlock parserBlock;

	@Before
    public void before() {
		utils = new ParserTestUtils();
		parseExpression = mock(ParserExpression.class);
		parserBlock = mock(ParserBlock.class);
	}
	
	@Test
	public void testVariableDeclaration() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.DECLVAR,
				TokenType.IDENTIFIER,
				TokenType.TYPEINT,
				TokenType.SEMICOLONS
		);
		DeclarationNode declarationNode = testGeneralVariableDeclaration(tokenTypes, null, 0);
		assertEquals(Eval.Type.INT, declarationNode.getVariableType().getType());
		assertEquals(null, declarationNode.getValue());
	}
	
	@Test
	public void testVariableDeclarationWithAssignment() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.DECLVAR,
				TokenType.IDENTIFIER,
				TokenType.ASSIGN,
				TokenType.TYPEINT,
				TokenType.SEMICOLONS
		);
		ConstantNode exp = new ConstantNode(utils.createToken(TokenType.NUMBER, "1"));
		DeclarationNode declarationNode = testGeneralVariableDeclaration(tokenTypes, exp, 1);
		assertEquals(Eval.Type.INT, declarationNode.getVariableType().getType());
		assertEquals(ConstantNode.class, declarationNode.getValue().getClass());
	}
	
	@Test
	public void testFunctionDeclaration() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.DECLFUNC,
				TokenType.IDENTIFIER,
				TokenType.ROUNDBR_OPEN,
				TokenType.ROUNDBR_CLOSE,
				TokenType.TYPEINT
		);
		FunctionNode functionNode = testFunctionDeclaration(tokenTypes);
		assertEquals(0, functionNode.numFormalParameters());
		assertEquals(Eval.Type.INT, functionNode.getReturnType().getType());
	}
	
	@Test
	public void testFunctionDeclarationWithFormalParameters() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.DECLFUNC,
				TokenType.IDENTIFIER,
				TokenType.ROUNDBR_OPEN,
				TokenType.IDENTIFIER,
				TokenType.TYPECHAR,
				TokenType.COMMA,
				TokenType.IDENTIFIER,
				TokenType.TYPEBOOL,
				TokenType.ROUNDBR_CLOSE,
				TokenType.TYPEINT
		);
		FunctionNode functionNode = testFunctionDeclaration(tokenTypes);
		assertEquals(2, functionNode.numFormalParameters());
		assertEquals(Eval.Type.CHAR, functionNode.getFormalParameter(0).getType());
		assertEquals(Eval.Type.BOOL, functionNode.getFormalParameter(1).getType());
		assertEquals(Eval.Type.INT, functionNode.getReturnType().getType());
	}
	
	@Test
	public void testFunctionDeclarationWithFunctionReturnType() {
		List<TokenType> tokenTypes = Arrays.asList(
				TokenType.DECLFUNC,
				TokenType.IDENTIFIER,
				TokenType.ROUNDBR_OPEN,
				TokenType.ROUNDBR_CLOSE,
				TokenType.TYPEFUN,
				TokenType.ROUNDBR_OPEN,
				TokenType.TYPECHAR,
				TokenType.COMMA,
				TokenType.TYPEBOOL,
				TokenType.ROUNDBR_CLOSE,
				TokenType.TYPEINT
				
		);
		FunctionNode functionNode = testFunctionDeclaration(tokenTypes);
		assertEquals(0, functionNode.numFormalParameters());
		assertEquals(Eval.Type.FUN, functionNode.getReturnType().getType());
		assertEquals(2, functionNode.getReturnType().numFunctionParameterTypes());
		assertEquals(Eval.Type.CHAR, functionNode.getReturnType().getFunctionParameterTypes(0));
		assertEquals(Eval.Type.BOOL, functionNode.getReturnType().getFunctionParameterTypes(1));
		assertEquals(Eval.Type.INT, functionNode.getReturnType().getFunctionReturnType().getType());
	}
	
	private DeclarationNode testGeneralVariableDeclaration(List<TokenType> tokenTypes, 
			ExpressionNode parseExpressionReturn, int timesParseExpression) {
		return (DeclarationNode) testDeclaration(tokenTypes, parseExpressionReturn, timesParseExpression, 0, DeclarationNode.class);
	}
	
	private FunctionNode testFunctionDeclaration(List<TokenType> tokenTypes) {
		return (FunctionNode) testDeclaration(tokenTypes, null, 0, 1, FunctionNode.class);
	}
	
	private <T> StatementNode testDeclaration(
			List<TokenType> tokenTypes,
			ExpressionNode parseExpressionReturn,
			int timesParseExpression,
			int timesParseBlock,
			Class<T> classType) {
		// Arrange
		List<Token> tokens = utils.createTokenList(tokenTypes);
		TokenReader tokenReader = new TokenReader(tokens);
		BlockNode block = new BlockNode(null, BlockNode.BlockType.BODY);
		when(parseExpression.parse()).thenReturn(parseExpressionReturn);
		when(parserBlock.parse(BlockNode.BlockType.BODY)).thenReturn(block);
		BlockNode n = new BlockNode(null, BlockNode.BlockType.PROGRAM);
		// Act
		new ParserDeclarationList(tokenReader, parseExpression, parserBlock).parse(n);
		// Assert
		verify(parseExpression, times(timesParseExpression)).parse();
		verify(parserBlock, times(timesParseBlock)).parse(any());
		assertEquals(1, n.numChildren());
		assertEquals(classType, n.getChild(0).getClass());	
		return n.getChild(0);
	}
}

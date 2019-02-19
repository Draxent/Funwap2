package org.draxent.funwap.syntacticanalysis;

import java.util.Arrays;

import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.FunctionNode;
import org.draxent.funwap.lexicalanalysis.TokenType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class ParserTest {
	
	private ParserTestUtils utils = new ParserTestUtils();
	private ParserDeclarationList parserDeclarationList;
	private ParserBlock parserBlock;

	@Test
	public void testMain() {
		// Arrange
		parserDeclarationList = mock(ParserDeclarationList.class);
		parserBlock = mock(ParserBlock.class);
		when(parserBlock.parse(BlockNode.BlockType.MAIN)).thenReturn(new BlockNode(null, BlockNode.BlockType.MAIN));
		// Act
		BlockNode n = new Parser(Arrays.asList(
				utils.createToken(TokenType.DECLFUNC),
				utils.createToken(TokenType.MAIN),
				utils.createToken(TokenType.ROUNDBR_OPEN),
				utils.createToken(TokenType.ROUNDBR_CLOSE),
				utils.createToken(TokenType.CURLYBR_OPEN),
				utils.createToken(TokenType.CURLYBR_CLOSE)
		), parserDeclarationList, parserBlock).parse();
		// Assert
		verify(parserDeclarationList).parse(any());
		verify(parserBlock).parse(BlockNode.BlockType.MAIN);
		assertEquals(1, n.numChildren());
		assertEquals(FunctionNode.class, n.getChild(0).getClass());
		BlockNode childBlock = ((FunctionNode) n.getChild(0)).getBody();
		assertEquals(BlockNode.BlockType.MAIN, childBlock.getBlockType());
	}
}

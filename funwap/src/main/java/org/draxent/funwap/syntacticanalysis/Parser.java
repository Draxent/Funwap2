package org.draxent.funwap.syntacticanalysis;

import java.util.List;

import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.FunctionNode;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;

// It is the class performing the syntactic analysis,
// that is the process of analyzing the list of tokens according to the rules of a formal grammar.
public class Parser {

	private TokenReader tokenReader;
	private ParserDeclarationList parserDeclarationList;
	private ParserBlock parserBlock;

	public Parser(List<Token> tokens) {
		initTokenReader(tokens);
		ParserExpression parserExpression = new ParserExpression(tokenReader);
		parserDeclarationList = new ParserDeclarationList(tokenReader, parserExpression, parserBlock);
		parserBlock = new ParserBlock(tokenReader, parserExpression, parserDeclarationList);	
	}

	public Parser(List<Token> tokens, ParserDeclarationList parserDeclarationList, ParserBlock parserBlock) {
		initTokenReader(tokens);
		this.parserDeclarationList = parserDeclarationList;
		this.parserBlock = parserBlock;	
	}

	public BlockNode parse() {
		BlockNode programNode = new BlockNode(null, BlockNode.Type.PROGRAM);

		parserDeclarationList.parse(programNode);
		parseMain(programNode);

		return programNode;
	}

	private void parseMain(BlockNode blockNode) {
		tokenReader.matchTokenAndMoveOn(TokenType.DECLFUNC);
		Token main = tokenReader.matchTokenAndMoveOn(TokenType.MAIN);
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_OPEN);
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_CLOSE);

		BlockNode bodyNode = parserBlock.parse(BlockNode.Type.MAIN);

		FunctionNode functionNode = new FunctionNode(main, bodyNode);
		blockNode.addChild(functionNode);
	}
	
	private void initTokenReader(List<Token> tokens) {
		tokenReader = new TokenReader(tokens);
	}
}

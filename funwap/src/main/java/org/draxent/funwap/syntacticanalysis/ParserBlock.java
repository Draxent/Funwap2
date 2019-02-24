package org.draxent.funwap.syntacticanalysis;

import java.util.ArrayList;
import java.util.List;

import org.draxent.funwap.FunwapException;
import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.ast.expression.ConstantNode;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.expression.VarNode;
import org.draxent.funwap.ast.expression.operation.BinaryOperationNode;
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

public class ParserBlock {	
	private TokenReader tokenReader;
	private ParserExpression parserExpression;
	private ParserDeclarationList parserDeclarationList;
	
	public ParserBlock(TokenReader tokenReader, ParserExpression parserExpression, ParserDeclarationList parserDeclarationList) {
		this.tokenReader = tokenReader;
		this.parserExpression = parserExpression;
		this.parserDeclarationList = parserDeclarationList;
	}
	
	public BlockNode parse(BlockNode.BlockType blockType) {
		Token token = tokenReader.matchTokenAndMoveOn(TokenType.CURLYBR_OPEN);
		BlockNode blockNode = new BlockNode(token, blockType);

		parserDeclarationList.parse(blockNode);
		parseStatementList(blockNode);

		tokenReader.matchTokenAndMoveOn(TokenType.CURLYBR_CLOSE);
		return blockNode;
	}
	
	private void parseStatementList(BlockNode blockNode) {
		if (tokenReader.getCurrent().isStatement()) {
			blockNode.addStatement(parseStatement());
			parseStatementList(blockNode);
		}
	}
	
	private StatementNode parseStatement() {
		switch(tokenReader.getCurrent().getType()) {
		case IDENTIFIER:
			return parseStatementStartingWithIdentifier();
		case IF:
			return parseStatementIf();
		case WHILE:
			return parseStatementWhile();
		case FOR:
			return parseStatementFor();
		case RETURN:
			return parseStatementReturn();
		case PRINTLN:
			return parseStatementPrintln();
		default:
			throw new FunwapException("Invalid statement.", tokenReader.getCurrent());
		}
	}
	
	private StatementNode parseStatementStartingWithIdentifier() {
		Token identifier = tokenReader.matchTokenAndMoveOn(TokenType.IDENTIFIER);
		StatementNode statementNode;
		if (tokenReader.isCurrentOfType(TokenType.ROUNDBR_OPEN)) {
			statementNode = parseCall(identifier, true);
		} else if (tokenReader.isCurrentOfType(TokenType.ASSIGN) && tokenReader.checkNextTokenType(TokenType.READLN)) {
			statementNode = parseStatementReadln(identifier);
		} else {
			statementNode = parseStatementIdentifier(identifier);
		}
		tokenReader.matchTokenAndMoveOn(TokenType.SEMICOLONS);
		return statementNode;
	}
	
	private CallNode parseCall(Token identifier, boolean isStatement) {
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_OPEN);
		List<ExpressionNode> actualParameters = parseActualParameters();
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_CLOSE);

		return new CallNode(identifier, actualParameters, isStatement);
	}
	
	private List<ExpressionNode> parseActualParameters() {
		List<ExpressionNode> actualParameters = new ArrayList<>();
		if (!tokenReader.isCurrentOfType(TokenType.ROUNDBR_CLOSE))
		{
			actualParameters.add(parserExpression.parse());
			while (tokenReader.isCurrentOfType(TokenType.COMMA))
			{
				tokenReader.moveNext();
				actualParameters.add(parserExpression.parse());
			}
		}
		return actualParameters;
	}
	
	private StatementNode parseStatementReadln(Token identifier) {
		tokenReader.moveNext();
		ReadNode readNode = new ReadNode(tokenReader.getCurrent(), identifier);
		tokenReader.moveNext();
		
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_OPEN);
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_CLOSE);
		
		return new AssignNode(identifier, readNode);
	}
	
	private StatementNode parseStatementIf() {
		Token tokenIf = tokenReader.getCurrent();
		tokenReader.moveNext();
		
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_OPEN);
		ExpressionNode conditionNode = parserExpression.parse();
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_CLOSE);
		
		BlockNode thenNode = this.parse(BlockNode.BlockType.THEN);
		BlockNode elseNode = null;
		if (tokenReader.isCurrentOfType(TokenType.ELSE)) {
			tokenReader.moveNext();
			elseNode = this.parse(BlockNode.BlockType.ELSE);
		}
		return new IfNode(tokenIf, conditionNode, thenNode, elseNode);
	}
	
	private StatementNode parseStatementWhile() {
		Token tokenWhile = tokenReader.getCurrent();
		tokenReader.moveNext();
		
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_OPEN);
		ExpressionNode conditionNode = parserExpression.parse();
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_CLOSE);
		
		BlockNode bodyNode = this.parse(BlockNode.BlockType.BLOCK);
		return new WhileNode(tokenWhile, conditionNode, bodyNode);
	}
	
	// for(IDE = Exp; Exp; IDE StmtIDE) Block
	private StatementNode parseStatementFor() {
		Token tokenFor = tokenReader.getCurrent();
		tokenReader.moveNext();
		
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_OPEN);
		
		Token identifier1 = tokenReader.matchTokenAndMoveOn(TokenType.IDENTIFIER);
		tokenReader.matchTokenAndMoveOn(TokenType.ASSIGN);
		StatementNode stm1 = new AssignNode(identifier1, parserExpression.parse());
		tokenReader.matchTokenAndMoveOn(TokenType.SEMICOLONS);
		
		ExpressionNode condition = parserExpression.parse();
		tokenReader.matchTokenAndMoveOn(TokenType.SEMICOLONS);
		
		Token identifier2 = tokenReader.matchTokenAndMoveOn(TokenType.IDENTIFIER);
		StatementNode stm2 = parseStatementIdentifier(identifier2);
		
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_CLOSE);
		
		BlockNode bodyNode = this.parse(BlockNode.BlockType.BLOCK);
		return new ForNode(tokenFor, stm1, condition, stm2, bodyNode);
	}
	
	// Parse StmtIDE = Exp ; | += Exp ; | -= Exp ; | ++ ; | -- ;
	private AssignNode parseStatementIdentifier(Token identifier) {
		switch (tokenReader.getCurrent().getType()) {
		case ASSIGN:
			tokenReader.moveNext();
			return new AssignNode(identifier, parserExpression.parse());
		case ASSIGN_MINUS: case ASSIGN_PLUS:
			return new AssignNode(identifier, new BinaryOperationNode(tokenReader.getCurrentAndMoveNext(), new VarNode(identifier), parserExpression.parse()));
		case INCR: case DECR:
			Token oneToken = new Token(TokenType.NUMBER, "1", tokenReader.getCurrent().getIndex(), tokenReader.getCurrent().getRow(), tokenReader.getCurrent().getColumn());
			ConstantNode one = new ConstantNode(oneToken);
			return new AssignNode(identifier, new BinaryOperationNode(tokenReader.getCurrentAndMoveNext(), new VarNode(identifier), one));
		default:
			throw new FunwapException("Invalid statement.", tokenReader.getCurrent());
		}
	}
	
	private ReturnNode parseStatementReturn() {
		Token tokenReturn = tokenReader.getCurrent();
		tokenReader.moveNext();

		SyntacticNode rightNode = (tokenReader.isCurrentOfType(TokenType.DECLFUNC)
				? parserDeclarationList.parseFunctionDeclaration(tokenReader.getCurrent())
				: parserExpression.parse());
		ReturnNode returnNode = new ReturnNode(tokenReturn, rightNode);

		tokenReader.matchTokenAndMoveOn(TokenType.SEMICOLONS);
		return returnNode;
	}
	
	private PrintNode parseStatementPrintln() {
		Token tokenPrint = tokenReader.getCurrent();
		tokenReader.moveNext();
		
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_OPEN);

		List<ExpressionNode> expList = new ArrayList<>();
		expList.add(parserExpression.parse());
		
		while (tokenReader.isCurrentOfType(TokenType.COMMA)){
			tokenReader.moveNext();
			expList.add(parserExpression.parse());
		}

		PrintNode printNode = new PrintNode(tokenPrint, expList);

		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_CLOSE);
		tokenReader.matchTokenAndMoveOn(TokenType.SEMICOLONS);
		
		return printNode;
	}
}

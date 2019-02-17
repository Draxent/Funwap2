package org.draxent.funwap.syntacticanalysis;

import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.FunwapException;
import org.draxent.funwap.ast.expression.ConstantNode;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.expression.VarNode;
import org.draxent.funwap.ast.expression.operation.BinaryOperationNode;
import org.draxent.funwap.ast.expression.operation.UnaryOperationNode;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;

public class ParserExpression {
	
	private TokenReader tokenReader;
	
	public ParserExpression(TokenReader tokenReader) {
		this.tokenReader = tokenReader;
	}
	
	public ExpressionNode parse() {
		ExpressionNode expressionNode = parseAndExpression();
		return parseMoreAndExpressions(expressionNode);
	}

	private ExpressionNode parseMoreAndExpressions(ExpressionNode expressionNode) {
		if (!isCurrentOneOfType(Arrays.asList(TokenType.OR))) {
			return expressionNode;
		}
		Token operator = tokenReader.getCurrentAndMoveNext();

		ExpressionNode rightNode = parseAndExpression();
		BinaryOperationNode operationNode = new BinaryOperationNode(operator, expressionNode, rightNode);
		ExpressionNode moreElements = parseMoreAndExpressions(operationNode);

		return moreElements;
	}

	private ExpressionNode parseAndExpression() {
		ExpressionNode expressionNode = parseUnaryRelationalExpression();
		return parseMoreUnaryRelationalExpressions(expressionNode);
	}

	private ExpressionNode parseMoreUnaryRelationalExpressions(ExpressionNode expressionNode) {
		if (!isCurrentOneOfType(Arrays.asList(TokenType.AND))) {
			return expressionNode;
		}
		Token operator = tokenReader.getCurrentAndMoveNext();

		ExpressionNode rightNode = parseUnaryRelationalExpression();
		BinaryOperationNode operationNode = new BinaryOperationNode(operator, expressionNode, rightNode);
		ExpressionNode moreElements = parseMoreUnaryRelationalExpressions(operationNode);

		return moreElements;
	}

	private ExpressionNode parseUnaryRelationalExpression() {
		if (tokenReader.isCurrentOfType(TokenType.NOT)) {
			Token operator = tokenReader.getCurrent();
			tokenReader.moveNext();

			ExpressionNode expNode = parseRelationalExpression();
			UnaryOperationNode operationNode = new UnaryOperationNode(operator, expNode);

			return operationNode;
		} else {
			return parseRelationalExpression();
		}
	}

	private ExpressionNode parseRelationalExpression() {
		ExpressionNode expressionNode = parseSumExpression();
		return parseMoreSumExpressions(expressionNode);
	}

	private ExpressionNode parseMoreSumExpressions(ExpressionNode expressionNode) {		
		if (!isCurrentOneOfType(Arrays.asList(TokenType.EQUAL, TokenType.INEQUAL, TokenType.GREATER,
				TokenType.GREATEREQ, TokenType.LESS, TokenType.LESSEQ))) {
			return expressionNode;
		}
		Token operator = tokenReader.getCurrentAndMoveNext();

		ExpressionNode rightNode = parseSumExpression();
		BinaryOperationNode operationNode = new BinaryOperationNode(operator, expressionNode, rightNode);
		ExpressionNode moreElements = parseMoreSumExpressions(operationNode);

		return moreElements;
	}

	private ExpressionNode parseSumExpression() {
		ExpressionNode expressionNode = parseTerm();
		return parseMoreTerms(expressionNode);
	}

	private ExpressionNode parseMoreTerms(ExpressionNode expressionNode) {
		if (!isCurrentOneOfType(Arrays.asList(TokenType.PLUS, TokenType.MINUS))) {
			return expressionNode;
		}
		Token operator = tokenReader.getCurrentAndMoveNext();

		ExpressionNode rightNode = parseTerm();
		BinaryOperationNode operationNode = new BinaryOperationNode(operator, expressionNode, rightNode);
		ExpressionNode moreElements = parseMoreTerms(operationNode);

		return moreElements;
	}

	private ExpressionNode parseTerm() {
		ExpressionNode expressionNode = parseUnaryExpression();
		return parseMoreUnaryExpressions(expressionNode);
	}

	private ExpressionNode parseMoreUnaryExpressions(ExpressionNode expressionNode) {
		if (!isCurrentOneOfType(Arrays.asList(TokenType.MUL, TokenType.DIV))) {
			return expressionNode;
		}
		Token operator = tokenReader.getCurrentAndMoveNext();

		ExpressionNode rightNode = parseUnaryExpression();
		BinaryOperationNode operationNode = new BinaryOperationNode(operator, expressionNode, rightNode);
		ExpressionNode moreElements = parseMoreUnaryExpressions(operationNode);

		return moreElements;
	}

	private ExpressionNode parseUnaryExpression() {
		if (tokenReader.isCurrentOfType(TokenType.MINUS)) {
			Token operator = tokenReader.getCurrent();
			tokenReader.moveNext();

			ExpressionNode expNode = parseFactor();
			UnaryOperationNode operationNode = new UnaryOperationNode(operator, expNode);

			return operationNode;
		} else {
			return parseFactor();
		}
	}

	// Parse Factor → IDE | ( Exp ) | Call | Const
	private ExpressionNode parseFactor() {
		switch (tokenReader.getCurrent().getType()) {
		case ROUNDBR_OPEN:
			return parseFactorExpressionInsideBrakets();
		case IDENTIFIER:
			return parseIdentifier();
		case NUMBER:
		case CHAR:
		case STRING:
		case URL:
		case TRUE:
		case FALSE:
			return parseConstant();
		default:
			throw new FunwapException("Invalid factor.", tokenReader.getCurrent());
		}
	}

	private ExpressionNode parseFactorExpressionInsideBrakets() {
		tokenReader.moveNext();
		ExpressionNode expNode = this.parse();
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_CLOSE);
		return expNode;
	}

	private VarNode parseIdentifier() {
		Token identifier = tokenReader.matchTokenAndMoveOn(TokenType.IDENTIFIER);
		// TODO: if (current.Type == TokenType.ROUNDBR_OPEN) Call(identifier);
		return new VarNode(identifier);
	}

	private ConstantNode parseConstant() {
		ConstantNode constantNode = new ConstantNode(tokenReader.getCurrent());
		tokenReader.moveNext();
		return constantNode;
	}
	
	private boolean isCurrentOneOfType(List<TokenType> operationTypeList) {
		return !tokenReader.isEOF() && operationTypeList.contains(tokenReader.getCurrent().getType());
	}
}

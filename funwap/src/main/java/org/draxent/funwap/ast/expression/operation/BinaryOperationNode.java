package org.draxent.funwap.ast.expression.operation;

import java.util.Stack;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.lexicalanalysis.Token;

public class BinaryOperationNode extends OperationNode {

	private ExpressionNode leftNode;
	private ExpressionNode rightNode;
	
	public BinaryOperationNode(Token token, ExpressionNode leftNode, ExpressionNode rightNode) {
		super(token);
		
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}

    public ExpressionNode getLeftExpression() {
		return leftNode;
	}

	public ExpressionNode getRightExpression() {
		return rightNode;
	}
}

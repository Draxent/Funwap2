package org.draxent.funwap.ast.expression.operation;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class BinaryOperationNode extends OperationNode {
	private ExpressionNode leftNode;
	private ExpressionNode rightNode;
	
	public BinaryOperationNode(Token token, ExpressionNode leftNode, ExpressionNode rightNode) {
		super(token);
		
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		addChildIfNotNull(leftNode);
		addChildIfNotNull(rightNode);
	}

    public ExpressionNode getLeftExpression() {
		return leftNode;
	}

	public ExpressionNode getRightExpression() {
		return rightNode;
	}

	@Override
	public void compile(StringBuilder sb, int numTab) {
		sb.append(ROUNDBR_OPEN);
		leftNode.compile(sb, numTab);
		sb.append(SPACE);
		sb.append(getOperationType());
		sb.append(SPACE);
		rightNode.compile(sb, numTab);
		sb.append(ROUNDBR_CLOSE);
	}
}

package org.draxent.funwap.ast.expression.operation;

import java.util.Stack;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.lexicalanalysis.Token;

public class UnaryOperationNode extends OperationNode {

	private ExpressionNode child;
	
	public UnaryOperationNode(Token token, ExpressionNode child) {
		super(token);
		
		this.child = child;
	}
	
	public ExpressionNode getChild() {
		return child;
	}

}

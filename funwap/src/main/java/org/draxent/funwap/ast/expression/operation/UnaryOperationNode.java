package org.draxent.funwap.ast.expression.operation;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.compiler.CompilerHelper;
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

	@Override
	public void compile(StringBuilder sb, int numTab) {
		sb.append(CompilerHelper.ROUNDBR_OPEN);
		sb.append(getOperationType());
		child.compile(sb, numTab);
		sb.append(CompilerHelper.ROUNDBR_CLOSE);
	}
}

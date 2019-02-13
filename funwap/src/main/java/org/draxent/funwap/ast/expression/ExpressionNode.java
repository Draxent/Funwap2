package org.draxent.funwap.ast.expression;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.lexicalanalysis.Token;

public abstract class ExpressionNode extends SyntacticNode {

	public ExpressionNode(Token token) {
		super(token);
	}

}

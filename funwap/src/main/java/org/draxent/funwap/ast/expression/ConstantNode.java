package org.draxent.funwap.ast.expression;

import java.util.Stack;

import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.lexicalanalysis.Token;

public class ConstantNode extends ExpressionNode {

	public ConstantNode(Token token) {
		super(token);
	}

}

package org.draxent.funwap.ast.expression;

import java.util.Stack;

import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.lexicalanalysis.Token;

public class VarNode extends ExpressionNode {

	public VarNode(Token token) {
		super(token);
	}

	public NodeType getNodeType() {
		return NodeType.VARIABLE;
	}

}

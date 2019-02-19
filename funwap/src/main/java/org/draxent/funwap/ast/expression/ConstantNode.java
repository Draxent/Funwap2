package org.draxent.funwap.ast.expression;

import java.util.Stack;

import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;

public class ConstantNode extends ExpressionNode {

	public ConstantNode(Token token) {
		super(token);
	}
	
	public NodeType getNodeType() {
		if (getToken().getType().equals(TokenType.STRING)) {
			return NodeType.STRING;	
		} else {
			return NodeType.NUMBER;	
		}
	}
}

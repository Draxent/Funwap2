package org.draxent.funwap.ast.expression;

import java.awt.Font;
import java.util.Stack;

import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;

public class ConstantNode extends ExpressionNode {
	private static final Font STRING_FONT = new Font(SANS_SERIF, Font.BOLD, 20);
	private static final Font NUMBER_FONT = new Font(SANS_SERIF, Font.BOLD, 20);
	
	public ConstantNode(Token token) {
		super(token);
	}
	
	public NodeType getNodeType() {
		return NodeType.CONSTANT;
	}
	
	public Font getFont() {
		if (getToken().getType().equals(TokenType.STRING)) {
			return STRING_FONT;
		} else {
			return NUMBER_FONT;
		}
	}
	
	public String getTitle() {
		return getToken().getValue();
	}
}

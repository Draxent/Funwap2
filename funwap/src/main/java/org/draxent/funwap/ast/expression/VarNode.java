package org.draxent.funwap.ast.expression;

import java.awt.Font;
import java.util.Stack;

import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.lexicalanalysis.Token;

public class VarNode extends ExpressionNode {
	private static final Font VAR_FONT = new Font(SANS_SERIF, Font.BOLD, 20);
	
	public VarNode(Token token) {
		super(token);
	}

	public NodeType getNodeType() {
		return NodeType.VARIABLE;
	}

	public Font getFont() { 
		return VAR_FONT;
	}
	
	public String getTitle() {
		return getToken().getValue();
	}
}

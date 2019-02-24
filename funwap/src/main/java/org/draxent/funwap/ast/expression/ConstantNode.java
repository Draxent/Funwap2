package org.draxent.funwap.ast.expression;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.draxent.funwap.Useful;
import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;

public class ConstantNode extends ExpressionNode {
	private static final Font STRING_FONT = new Font(Useful.SANS_SERIF, Font.ITALIC, 20);
	private static final Font NUMBER_FONT = new Font(Useful.SANS_SERIF, Font.BOLD, 20);
	
	public ConstantNode(Token token) {
		super(token);
	}
	
	public NodeType getNodeType() {
		return NodeType.CONSTANT;
	}
	
	public List<GraphicText> getTitle() {
		if (getToken().getType().equals(TokenType.STRING)) {
			return Arrays.asList(new GraphicText("\"" + getToken().getValue() + "\"", STRING_FONT, Color.RED));
		} else {
			return Arrays.asList(new GraphicText(getToken().getValue(), NUMBER_FONT, Color.ORANGE));
		}
	}
}

package org.draxent.funwap.ast.expression;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.Useful;
import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;

public class VarNode extends ExpressionNode {
	private static final Font VAR_FONT = new Font(Useful.SANS_SERIF, Font.PLAIN, 20);
	private static final Color VAR_COLOR = Color.getHSBColor(0.271978f, 0.994536f, 0.717647f);
	
	public VarNode(Token token) {
		super(token);
	}

	public NodeType getNodeType() {
		return NodeType.VARIABLE;
	}

	public Font getFont() { 
		return VAR_FONT;
	}
	
	public List<GraphicText> getTitle() {
		return Arrays.asList(new GraphicText(getToken().getValue(), VAR_FONT, VAR_COLOR));
	}
	
	@Override
	public void compile(StringBuilder sb, int numTab) {
		sb.append(getToken().getValue());
	}
}

package org.draxent.funwap.ast.expression;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.FunwapException;
import org.draxent.funwap.Useful;
import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;

public class ConstantNode extends ExpressionNode {
	private static final Font STRING_FONT = new Font(Useful.SANS_SERIF, Font.ITALIC, 20);
	private static final Font NUMBER_FONT = new Font(Useful.SANS_SERIF, Font.BOLD, 20);
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	
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
	
	@Override
	public void compile(StringBuilder sb, int numTab) {
		sb.append(getCompiledString());
	}
	
	private String getCompiledString() {
		switch (getToken().getType())
		{
			case NUMBER:
			case CHAR:
			case STRING:
				return getToken().getValue();
			case TRUE: return TRUE;
			case FALSE: return FALSE;
			default:
				throw new FunwapException("Impossible convert token into constant type.", getToken());
		}	
	}
}

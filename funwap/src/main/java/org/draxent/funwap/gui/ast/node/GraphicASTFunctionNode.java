package org.draxent.funwap.gui.ast.node;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.FunctionNode;

public class GraphicASTFunctionNode extends GraphicASTNode {
	
	private FunctionNode functionNode;
	
	public GraphicASTFunctionNode(Graphics g, int x, int y, FunctionNode functionNode) {
		super(g, x, y);
		this.functionNode = functionNode;
	}

	public Rectangle draw() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(functionNode.getToken().getValue());
		stringBuilder.append(" (");
		for (int i = 0; i < functionNode.numFormalParameters(); i++) {
			if (i > 0) {
				stringBuilder.append(", ");
			}
			stringBuilder.append(functionNode.getFormalParameter(i).getType());
		}
		stringBuilder.append(")");
		String title = stringBuilder.toString();
		g.setFont(FUNCTION_FONT); 
		FontMetrics metrics = g.getFontMetrics();
		int height = metrics.getHeight();
		int width = metrics.stringWidth(title);
		g.setColor(Color.WHITE);
		Rectangle r = new Rectangle(x - width/2 - PADDING, y, width + 2*PADDING, height + 2*PADDING);
		g.fillRect(r.x, r.y, r.width, r.height);
		g.setColor(Color.BLACK);
		g.drawRect(r.x, r.y, r.width, r.height);
		g.drawString(title, x - width/2, y + height + PADDING/2);
		
		return r;
	}
}

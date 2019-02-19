package org.draxent.funwap.gui.ast.node;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.FunctionNode;

public class GraphicASTBlockNode extends GraphicASTNode {
	
	private BlockNode blockNode;
	
	public GraphicASTBlockNode(Graphics g, int x, int y, BlockNode blockNode) {
		super(g, x, y);
		this.blockNode = blockNode;
	}

	public Rectangle draw() {
		String title = blockNode.getBlockType().name();
		g.setFont(BLOCK_FONT); 
		FontMetrics metrics = g.getFontMetrics();
		int height = metrics.getHeight();
		int width = metrics.stringWidth(title);
		g.setColor(Color.WHITE);
		Rectangle r = new Rectangle(x - width/2 - PADDING, y, width + 2*PADDING, height + 2*PADDING);
		g.fillRect(r.x, r.y, r.width, r.height);
		g.setColor(Color.BLACK);
		g.drawString(title, x - width/2, y + height + PADDING/2);
		return r;
	}
}

package org.draxent.funwap.gui.ast;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import org.draxent.funwap.ast.SyntacticNode;

public class GraphicASTNode {
	private static final int PADDING = 10;
	private static final int PADDING_X2 = PADDING * 2;
	private static final int PADDING_HALF = PADDING / 2;
	
	private Graphics g;
	private SyntacticNode node;
	private Dimension dimension;
	
	public GraphicASTNode(Graphics g, SyntacticNode node) {
		this.g = g;
		this.node = node;
		this.dimension = null;
	}
	
	public void computeDimension() {
		g.setFont(node.getFont());
		FontMetrics metrics = g.getFontMetrics();		
		int width = metrics.stringWidth(node.getTitle());
		int height = metrics.getHeight();
		dimension = new Dimension(width + PADDING_X2, height + PADDING_X2);
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public void draw(int x, int y) {
		switch(node.getNodeType()) {
		case BLOCK:
		case COMMAND:
		case DECLARATION:
		case ASSIGN:
			drawRectangleNode(x, y);
			break;
		case FUNCTION:
			drawOutlinedRectangleNode(x, y);
			break;
		case OPERATION:
		case VARIABLE:
		case CONSTANT:
			drawOvalNode(x, y);
			break;
		default:
			break;
		}
	}
	
	private void drawRectangleNode(int x, int y) {
		g.setFont(node.getFont()); 
		g.setColor(Color.WHITE);
		g.fillRect(x - dimension.width/2 - PADDING, y, dimension.width, dimension.height);
		drawString(x, y);	
	}
	
	private void drawOutlinedRectangleNode(int x, int y) {
		drawRectangleNode(x, y);
		g.setColor(Color.BLACK);
		g.drawRect(x - dimension.width/2 - PADDING, y, dimension.width, dimension.height);
	}
	
	private void drawOvalNode(int x, int y) {
		g.setFont(node.getFont()); 
		g.setColor(Color.WHITE);
		g.fillOval(x - dimension.width/2 - PADDING, y, dimension.width, dimension.height);
		drawString(x, y);
	}
	
	private void drawString(int x, int y) {
		g.setColor(Color.BLACK);
		Point baseline = getStringBaselinePoint(x, y);
		g.drawString(node.getTitle(), baseline.x, baseline.y);			
	}
	
	private Point getStringBaselinePoint(int x, int y) {
		return new Point(x - dimension.width/2, y + dimension.height - PADDING_X2 + PADDING_HALF);
	}
}

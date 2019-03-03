package org.draxent.funwap.gui.ast;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.draxent.funwap.ast.SyntacticNode;

public class GraphicASTNode {
	private static final int PADDING = 10;
	private static final int PADDING_X2 = PADDING * 2;
	private static final int PADDING_HALF = PADDING / 2;
	private static final int TEXT_SPACE = 5;
	private static final int BASELINE_POSX_INCR = 2;
	
	private Graphics2D g;
	private SyntacticNode node;
	private Dimension dimension;
	private List<Integer> textSizes;
	
	public GraphicASTNode(Graphics2D g, SyntacticNode node) {
		this.g = g;
		this.node = node;
		this.dimension = null;
		this.textSizes = new ArrayList<>();
	}
	
	public void computeDimension() {
		dimension = new Dimension(0, 0);
		for (GraphicText gText : node.getTitle()) {
			FontMetrics metrics = g.getFontMetrics(gText.getFont());
			int textWidth = metrics.stringWidth(gText.getText());
			int textHeight = metrics.getHeight();
			textSizes.add(textWidth);
			dimension.setSize(dimension.getWidth() + textWidth + TEXT_SPACE, Math.max(dimension.getHeight(), textHeight));
		}
		dimension.setSize(dimension.getWidth() + PADDING_X2, dimension.getHeight() + PADDING_X2);
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
		case CALL:
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
		g.setColor(Color.WHITE);
		g.fillRect(x - dimension.width/2, y, dimension.width, dimension.height);
		drawString(x, y);	
	}
	
	private void drawOutlinedRectangleNode(int x, int y) {
		drawRectangleNode(x, y);
		g.setColor(Color.BLACK);
		g.drawRect(x - dimension.width/2, y, dimension.width, dimension.height);
	}
	
	private void drawOvalNode(int x, int y) {
		g.setColor(Color.WHITE);
		g.fillOval(x - dimension.width/2, y, dimension.width, dimension.height);
		drawString(x, y);
	}
	
	private void drawString(int x, int y) {
		Point baseline = getStringBaselinePoint(x, y);
		
		int i = 0, posX = baseline.x;
		for (GraphicText gText : node.getTitle()) {
			g.setFont(gText.getFont());
			g.setColor(gText.getColor());
			g.drawString(gText.getText(), posX, baseline.y);
			
			posX += textSizes.get(i) + TEXT_SPACE;
			i++;
		}
	}
	
	private Point getStringBaselinePoint(int x, int y) {
		return new Point(x - dimension.width/2 + PADDING + BASELINE_POSX_INCR, y + dimension.height - PADDING_X2 + PADDING_HALF);
	}
}

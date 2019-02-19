package org.draxent.funwap.gui.ast.node;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.FunctionNode;

public abstract class GraphicASTNode {
	protected static final int PADDING = 10;
	
	private static final String SANS_SERIF = "sans-serif";
	protected static final Font BLOCK_FONT = new Font(SANS_SERIF, Font.BOLD, 20);
	protected static final Font FUNCTION_FONT = new Font(SANS_SERIF, Font.ITALIC, 20);
	
	protected Graphics g;
	protected int x;
	protected int y;
	
	public GraphicASTNode(Graphics g, int x, int y) {
		this.g = g;
		this.x = x;
		this.y = y;
	}
	
	public abstract Rectangle draw();
}

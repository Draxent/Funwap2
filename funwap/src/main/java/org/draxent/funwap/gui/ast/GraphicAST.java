package org.draxent.funwap.gui.ast;

import java.awt.Graphics;
import java.awt.Rectangle;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.FunctionNode;
import org.draxent.funwap.ast.statement.StatementNode;
import org.draxent.funwap.gui.ast.node.GraphicASTBlockNode;
import org.draxent.funwap.gui.ast.node.GraphicASTFunctionNode;
import org.draxent.funwap.gui.ast.node.GraphicASTNode;

public class GraphicAST {
    // Space to skip horizontally and vertically between siblings and between generations
    private final int HOFFSET = 5;
    private final int VOFFSET = 10;
    
	private Graphics g;
	private int x;
	private int y;
	private SyntacticNode node;
    
    public GraphicAST(Graphics g, int x, int y, SyntacticNode node) {
    	this.g = g;
    	this.x = x;
    	this.y = y;
    	this.node = node;
    }
    
	public Rectangle draw() {
		switch(node.getNodeType()) {
		case BLOCK:
			return drawASTWithRootBlockNode();
		case FUNCTION:
			return drawASTWithRootFunctionalNode();
		default:
			return null;
		}
	}
	
	private void drawLine(Rectangle r1, Rectangle r2) {
		g.drawLine(r1.x + r1.width/2, r1.y + r1.height, r2.x + r2.width/2, r2.y);
	}	
	
	private Rectangle drawASTWithRootBlockNode() {
		BlockNode blockNode = (BlockNode) node;
		Rectangle r1 = new GraphicASTBlockNode(g, x, y, blockNode).draw();
		for (int i = 0; i < blockNode.numChildren(); i++) {
			StatementNode child = blockNode.getChild(i);
			Rectangle r2 = new GraphicAST(g, x, y + 100, child).draw();
			drawLine(r1, r2);
		}		
		return r1;
	}
	
	private Rectangle drawASTWithRootFunctionalNode() {
		FunctionNode functionNode = (FunctionNode) node;
		Rectangle r1 = new GraphicASTFunctionNode(g, x, y, functionNode).draw();
		return r1;
	}
}

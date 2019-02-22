package org.draxent.funwap.gui.ast;

import java.awt.Graphics;

import javax.swing.JComponent;

import org.draxent.funwap.ast.statement.BlockNode;

public class GraphicASTPanel extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private BlockNode programNode;
	
	public GraphicASTPanel(BlockNode programNode) {
		this.programNode = programNode;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		GraphicAST ast = new GraphicAST(g, programNode);
		ast.computeTreeStructure();
		ast.moveTree((getWidth() - ast.getWidth()) / 2, 0);
		ast.draw();
	}
}

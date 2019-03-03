package org.draxent.funwap.gui.ast;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import org.draxent.funwap.ast.statement.BlockNode;

public class GraphicASTPanel extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private GraphicASTDialog graphicASTDialog;
	private BlockNode programNode;
	private boolean isPaintedFirstTime;
	
	public GraphicASTPanel(GraphicASTDialog graphicASTDialog, BlockNode programNode) {
		this.graphicASTDialog = graphicASTDialog;
		this.programNode = programNode;
		this.isPaintedFirstTime = false;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GraphicAST ast = new GraphicAST(g2, programNode);
		ast.computeTreeStructure();
		ast.moveTree((getWidth() - ast.getWidth()) / 2, 0);
		ast.draw();

		ifFirstTimeSetParentSizeAndLocation(ast);
	}
	
	private void ifFirstTimeSetParentSizeAndLocation(GraphicAST ast) {
		if (!isPaintedFirstTime) {
			graphicASTDialog.setSize(ast.getWidth() + 100, ast.getHeight() + 100);
			graphicASTDialog.setLocationRelativeTo(null);
			isPaintedFirstTime = true;
		}		
	}
}

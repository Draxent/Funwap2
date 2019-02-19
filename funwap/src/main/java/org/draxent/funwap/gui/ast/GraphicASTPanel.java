package org.draxent.funwap.gui.ast;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.FontMetrics;

import javax.swing.JComponent;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.FunctionNode;
import org.draxent.funwap.ast.statement.StatementNode;

public class GraphicASTPanel extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private BlockNode programNode;
	
	public GraphicASTPanel(BlockNode programNode) {
		this.programNode = programNode;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		new GraphicAST(g, getWidth()/2, 10, programNode).draw();
	}


}

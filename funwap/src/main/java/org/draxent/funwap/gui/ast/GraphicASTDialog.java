package org.draxent.funwap.gui.ast;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.draxent.funwap.ast.statement.BlockNode;

public class GraphicASTDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	public GraphicASTDialog(JFrame parent, BlockNode programBlock) {
		super(parent, "Abstract Syntax Tree", true);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new GraphicASTPanel(this, programBlock), BorderLayout.CENTER);

		setSize(1200, 800);
		setLocationRelativeTo(null);
	}
}
 
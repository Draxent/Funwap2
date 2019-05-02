package org.draxent.funwap.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.gui.ast.GraphicASTDialog;
import org.draxent.funwap.lexicalanalysis.Token;

public class ActionListenerAST implements ActionListener {
	private JFrame frame;
	private JTextArea textAreaConsole;
	private ActionListenerUtils utils;
		
	public ActionListenerAST(JFrame frame, JTextArea textAreaCode, JTextArea textAreaConsole) {
		this.frame = frame;
		this.textAreaConsole = textAreaConsole;
		this.utils = new ActionListenerUtils(textAreaCode, textAreaConsole);
	}
	
	public void actionPerformed(ActionEvent ev) {
		if (utils.isTextAreaCodeEmpty()) {
			return;
		}
		List<Token> tokens = utils.scannerPhase();
		BlockNode programBlock = utils.parserPhase(tokens);
		
		if (programBlock != null) {
			GraphicASTDialog dialog = new GraphicASTDialog(frame, programBlock);
			textAreaConsole.append("Abstract Syntax Tree generated.\r\n");
			dialog.setModal(true);
			dialog.setVisible(true);		
		}
	}
}

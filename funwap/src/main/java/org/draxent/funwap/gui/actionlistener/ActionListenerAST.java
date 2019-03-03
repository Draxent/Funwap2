package org.draxent.funwap.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.draxent.funwap.FunwapException;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.gui.ast.GraphicASTDialog;
import org.draxent.funwap.lexicalanalysis.Scanner;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.syntacticanalysis.Parser;

public class ActionListenerAST implements ActionListener {
	private JFrame frame;
	private JTextArea textAreaCode;
	private JTextArea textAreaConsole;
	
	public ActionListenerAST(JFrame frame, JTextArea textAreaCode, JTextArea textAreaConsole) {
		this.frame = frame;
		this.textAreaCode = textAreaCode;
		this.textAreaConsole = textAreaConsole;
	}
	
	public void actionPerformed(ActionEvent ev) {
		if (textAreaCode.getText().isEmpty()) {
			textAreaConsole.append("Cannot scan empty text!\r\n");
			return;
		}
		
		List<Token> tokens = new Scanner(textAreaCode.getText()).tokenize();
		textAreaConsole.append("Scanner phase proceduced the following tokens:\r\n");
		for (Token token : tokens) {
			textAreaConsole.append("  - " + token.toString() + "\r\n");
		}
		textAreaConsole.append("Scanner phase compleated.\r\n");
		try {
			BlockNode programBlock = new Parser(tokens).parse();
			textAreaConsole.append("Parser phase compleated.\r\n");
			GraphicASTDialog dialog = new GraphicASTDialog(frame, programBlock);
			textAreaConsole.append("Abstract Syntax Tree generated.\r\n");
			dialog.setModal(true);
			dialog.setVisible(true);
		} catch (FunwapException e) {
			textAreaConsole.append("Error during the parser phase:\r\n");
			textAreaConsole.append("  - " + e.getMessage() + "\r\n");
			textAreaConsole.append("  - " + e.getToken() + "\r\n");
		}
	}
}

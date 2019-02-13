package org.draxent.funwap.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

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
	}
}

package org.draxent.funwap.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

public class ActionListenerClearConsole implements ActionListener {
	
	private JTextArea textAreaConsole;
	
	public ActionListenerClearConsole(JTextArea textAreaConsole) {
		this.textAreaConsole = textAreaConsole;
	}
	
	public void actionPerformed(ActionEvent ev) {
		textAreaConsole.setText("");
	}
}

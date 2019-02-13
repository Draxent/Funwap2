package org.draxent.funwap.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ActionListenerExit implements ActionListener {
	private JFrame frame;
	
	public ActionListenerExit(JFrame frame) {
		this.frame = frame;
	}
	
	public void actionPerformed(ActionEvent ev) {
		frame.dispose();
		System.exit(0);
	}
}

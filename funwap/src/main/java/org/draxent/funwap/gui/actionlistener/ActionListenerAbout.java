package org.draxent.funwap.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.draxent.funwap.gui.SimpleDialog;

public class ActionListenerAbout implements ActionListener {
	private JFrame frame;
	
	public ActionListenerAbout(JFrame frame) {
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent ev) {
		List<String> text = Arrays.asList(
			"Program written by Federico Conte.", 
			"Info: fconte90@gmail.com", 
			"Date: 01/01/2019", 
			"Release: 2.0"
		);
		SimpleDialog dialog = new SimpleDialog(frame, "About", text, 350, 250);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
}

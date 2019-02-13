package org.draxent.funwap.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.draxent.funwap.gui.SimpleDialog;

public class ActionListenerHelp implements ActionListener {
	private JFrame frame;
	
	public ActionListenerHelp(JFrame frame) {
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent ev) {
		List<String> text = Arrays.asList(
			"In order to have your code file interpetered or compiled,",
			"do the following steps:",
            "  1) Click on File -> Open File.",
			"  2) Select a text file with the same format of the files that",
			"       you can find in the Examples folder.",
			"  3) Click on Execute -> Compile, if you want to compile the code",
			"       or click on Execute -> Interpret, if you want to interpret it.",
			" ",
			"For more information, read the file documentation.pdf!"
		);
		SimpleDialog dialog = new SimpleDialog(frame, "Help", text, 570, 350);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
}

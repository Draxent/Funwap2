package org.draxent.funwap.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ActionListenerSave implements ActionListener {
	private static final String UTF_8 = "utf-8";
	
	private JFrame frame;
	private JTextArea textAreaCode;
	private JTextArea textAreaConsole;
	
	public ActionListenerSave(JFrame frame, JTextArea textAreaCode, JTextArea textAreaConsole) {
		this.frame = frame;
		this.textAreaCode = textAreaCode;
		this.textAreaConsole = textAreaConsole;
	}
	
	public void actionPerformed(ActionEvent ev) {
		TxtFileChooser fileChooser = new TxtFileChooser(frame);
		fileChooser.showSaveDialog();
		File selectedFile = fileChooser.getSelectedFile();
		if (selectedFile == null) {
			return;
		}
		
        try {
			textAreaCode.write(new OutputStreamWriter(new FileOutputStream(selectedFile), UTF_8));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Cannot save file\n " + selectedFile.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		textAreaConsole.append("The file \"" + selectedFile.getName() + "\" has been saved correctly!\r\n");
	}
}

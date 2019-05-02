package org.draxent.funwap.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.draxent.funwap.gui.Cache;

public class ActionListenerOpen implements ActionListener {
	private JFrame frame;
	private JTextArea textAreaCode;
	private JTextArea textAreaConsole;
	
	public ActionListenerOpen(JFrame frame, JTextArea textAreaCode, JTextArea textAreaConsole) {
		this.frame = frame;
		this.textAreaCode = textAreaCode;
		this.textAreaConsole = textAreaConsole;
	}
	
	public void actionPerformed(ActionEvent ev) {
		TxtFileChooser fileChooser = new TxtFileChooser(frame);
		fileChooser.showOpenDialog();
		File selectedFile = fileChooser.getSelectedFile();
		if (selectedFile == null) {
			return;
		}

		Cache.getCache().setOpenedFile(selectedFile);
		textAreaCode.setText(readFileContent(selectedFile));
		textAreaConsole.append("The file \"" + selectedFile.getName() + "\" has been loaded correctly!\r\n");
	}

	private String readFileContent(File selectedFile) {
		try {
			String fileContent = new String(Files.readAllBytes(selectedFile.toPath()), StandardCharsets.UTF_8);
			return fileContent;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Cannot open file\n " + selectedFile.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
}

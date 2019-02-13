package org.draxent.funwap.gui.actionlistener;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TxtFileChooser {
	private static final String TXT = "txt";
	private static final String TXT_EXT = "." + TXT;
	private static final String FILTER_TITLE = "Text Files (*" + TXT_EXT + ")";
	private static final String EXAMPLES_DIR = "./src/main/resources/examples";
	
	private JFrame frame;
	private JFileChooser fileChooser;
	private boolean error;
	
	public TxtFileChooser(JFrame frame) {
		this.frame = frame;
		
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(EXAMPLES_DIR));
		FileFilter filter = new FileNameExtensionFilter(FILTER_TITLE, TXT);
		fileChooser.setFileFilter(filter);
	}
	
	public void showOpenDialog() {
		int result = fileChooser.showOpenDialog(frame);
		setErrorIfResultNotApproved(result);
	}
	
	public void showSaveDialog() {
		int result = fileChooser.showSaveDialog(frame);
		setErrorIfResultNotApproved(result);
	}
	
	public boolean isOperationFailed() {
		return error;
	}
	
	private void setErrorIfResultNotApproved(int result) {
		if (result != JFileChooser.APPROVE_OPTION) {
			error = true;
		}
	}
	
	public File getSelectedFile() {
		if (isOperationFailed()) {
			return null;
		}
		
		File selectedFile = fileChooser.getSelectedFile();
		boolean isNotTxtFile = !selectedFile.getName().toLowerCase().endsWith(TXT_EXT);
		if (isNotTxtFile) {
	    	JOptionPane.showMessageDialog(frame, "Can select only files with extension *" + TXT_EXT, "Error", JOptionPane.ERROR_MESSAGE);
	    	return null;
		} else {
			return selectedFile;
		}
	}
}

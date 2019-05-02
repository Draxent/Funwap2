package org.draxent.funwap.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JTextArea;

import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class ActionListenerCompile implements ActionListener {
	private JTextArea textAreaConsole;
	private ActionListenerUtils utils;
	private PrintWriter printWriter;
	private static final String extention = ".java";
	private static final String fileName = "Main";
	private static final String fileNameWithExtention = fileName + extention;
	
	public ActionListenerCompile(JTextArea textAreaCode, JTextArea textAreaConsole) {
		this.textAreaConsole = textAreaConsole;
		this.utils = new ActionListenerUtils(textAreaCode, textAreaConsole);
	}
	
	public void actionPerformed(ActionEvent ev) {
		if (utils.isTextAreaCodeEmpty()) {
			return;
		}
		initPrintWriter();
		List<Token> tokens = utils.scannerPhase();
		BlockNode programBlock = utils.parserPhase(tokens);
		
		StringBuilder sb = new StringBuilder();
		programBlock.compile(sb, 0);
		printWriter.println(sb.toString());
		printWriter.close();
		textAreaConsole.append("Code translated with success into java file \"" + fileNameWithExtention + "\".\r\n");
		compileProgram();
	}
	
	private void initPrintWriter() {
		try {
			printWriter = new PrintWriter(fileNameWithExtention);
		} catch (FileNotFoundException e) {
			textAreaConsole.append("Cannot write into file \"" + fileNameWithExtention + "\".\r\n");
		}		
	}
	
    private void compileProgram() {
		try {
			Process p = Runtime.getRuntime().exec("javac " + fileNameWithExtention, null, new File("C:\\Users\\feder\\Desktop\\Funwap2\\funwap"));
	        redirectStdOutput(p.getInputStream());
	        int numErrors = redirectStdOutput(p.getErrorStream());
			p.waitFor();
			if (numErrors > 0) {
				textAreaConsole.append("Error compiling \"" + fileNameWithExtention + "\".\r\n");
			} else {
				textAreaConsole.append("File \"" + fileNameWithExtention + "\" successfully compiled.\r\n");
				textAreaConsole.append("You can run it using the command \"java " + fileName + "\".\r\n");				
			}
		} catch (IOException e) {
			textAreaConsole.append("Error running process javac.\r\n");
			textAreaConsole.append(e.getMessage() + "\r\n");
		} catch (InterruptedException e) {
			textAreaConsole.append("Error compiling \"" + fileNameWithExtention + "\".\r\n");
			textAreaConsole.append(e.getMessage() + "\r\n");
		}
    }
    
    private int redirectStdOutput(InputStream in) throws IOException {
    	int numLine = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
        	textAreaConsole.append(line + "\r\n");
        	numLine++;
        }
        return numLine;
    }
}

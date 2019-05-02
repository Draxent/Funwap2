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
import org.draxent.funwap.gui.Cache;
import org.draxent.funwap.lexicalanalysis.Token;

public class ActionListenerCompile implements ActionListener {
	private static final String DEFAULT_FILENAME = "Main";
	private static final String EXT = ".java";
	
	private JTextArea textAreaConsole;
	private ActionListenerUtils utils;

	public ActionListenerCompile(JTextArea textAreaCode, JTextArea textAreaConsole) {
		this.textAreaConsole = textAreaConsole;
		this.utils = new ActionListenerUtils(textAreaCode, textAreaConsole);
	}
	
	public void actionPerformed(ActionEvent ev) {
		if (utils.isTextAreaCodeEmpty()) {
			return;
		}
		String programName = getProgramName();
		Cache.getCache().setProgramName(programName);
		String outputFileName = programName + EXT;
		PrintWriter printWriter = initPrintWriter(outputFileName);
		List<Token> tokens = utils.scannerPhase();
		BlockNode programBlock = utils.parserPhase(tokens);
		
		StringBuilder sb = new StringBuilder();
		programBlock.compile(sb, 0);
		printWriter.println(sb.toString());
		printWriter.close();
		textAreaConsole.append("Code translated with success into java file \"" + outputFileName + "\".\r\n");
		compileProgram(outputFileName, programName);
	}
	
	private String getProgramName() {
		File openedFile = Cache.getCache().getOpenedFile();
		if (openedFile != null) {
			return capitilize(removeSpecialCharacters(removeExtension(openedFile.getName())));
		} else {
			return DEFAULT_FILENAME;
		}
	}
	
	private PrintWriter initPrintWriter(String outputFileName) {
		try {
			return new PrintWriter(outputFileName);
		} catch (FileNotFoundException e) {
			textAreaConsole.append("Cannot write into file \"" + outputFileName + "\".\r\n");
			return null;
		}		
	}
	
    private void compileProgram(String outputFileName, String programName) {
		try {
			Process p = Runtime.getRuntime().exec("javac " + outputFileName, null, new File("C:\\Users\\feder\\Desktop\\Funwap2\\funwap"));
	        redirectStdOutput(p.getInputStream());
	        int numErrors = redirectStdOutput(p.getErrorStream());
			p.waitFor();
			if (numErrors > 0) {
				textAreaConsole.append("Error compiling \"" + outputFileName + "\".\r\n");
			} else {
				textAreaConsole.append("File \"" + outputFileName + "\" successfully compiled.\r\n");
				textAreaConsole.append("You can run it using the command \"java " + programName + "\".\r\n");				
			}
		} catch (IOException e) {
			textAreaConsole.append("Error running process javac.\r\n");
			textAreaConsole.append(e.getMessage() + "\r\n");
		} catch (InterruptedException e) {
			textAreaConsole.append("Error compiling \"" + outputFileName + "\".\r\n");
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
    
	private String removeExtension(String str) {
		return str.substring(0, str.lastIndexOf('.'));
	}
	
	private String removeSpecialCharacters(String str) {
		return str.replaceAll("[^a-zA-Z0-9]", "");
	}
	
	private String capitilize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}

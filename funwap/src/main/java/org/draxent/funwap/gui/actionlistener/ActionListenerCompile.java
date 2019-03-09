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
	private static final String fileName = "Main.java";
	
	public ActionListenerCompile(JTextArea textAreaCode, JTextArea textAreaConsole) {
		this.textAreaConsole = textAreaConsole;
		this.utils = new ActionListenerUtils(textAreaCode, textAreaConsole);
		
		try {
			printWriter = new PrintWriter(fileName);
		} catch (FileNotFoundException e) {
			textAreaConsole.append("Cannot write into file \"" + fileName + "\".\r\n");
		}
	}
	
	public void actionPerformed(ActionEvent ev) {
		if (utils.isTextAreaCodeEmpty()) {
			return;
		}	
		List<Token> tokens = utils.scannerPhase();
		BlockNode programBlock = utils.parserPhase(tokens);
		
		StringBuilder sb = new StringBuilder();
		programBlock.compile(sb, 0);
		printWriter.println(sb.toString());
		printWriter.close();
		textAreaConsole.append("Code translated with success into java file \"" + fileName + "\".\r\n");
		//compileProgram();
		runProgram();
	}
	
    private void compileProgram() {
		try {
			Process p = Runtime.getRuntime().exec("javac " + fileName, null, new File("C:\\Users\\feder\\Desktop\\Funwap2\\funwap"));
			p.waitFor();
			textAreaConsole.append("File \"" + fileName + "\" successfully compiled.\r\n");
			//runProgram();
		} catch (IOException e) {
			textAreaConsole.append("Error running process javac.\r\n");
			textAreaConsole.append(e.getMessage() + "\r\n");
		} catch (InterruptedException e) {
			textAreaConsole.append("Error compiling \"" + fileName + "\".\r\n");
			textAreaConsole.append(e.getMessage() + "\r\n");
		}
    }
    
    private void runProgram() {
		try {
			Process p = Runtime.getRuntime().exec("java Main", null, new File("C:\\Users\\feder\\Desktop\\Funwap2\\funwap"));
	        output("Std.In", p.getInputStream());
	        output("Std.Out", p.getErrorStream());
			p.waitFor();
			textAreaConsole.append("File \"" + fileName + "\" successfully executed.\r\n");
		} catch (IOException e) {
			textAreaConsole.append("Error running process java.\r\n");
			textAreaConsole.append(e.getMessage() + "\r\n");
		} catch (InterruptedException e) {
			textAreaConsole.append("Error running \"" + fileName + "\".\r\n");
			textAreaConsole.append(e.getMessage() + "\r\n");
		}
    }

    private void output(String stream, InputStream in) throws IOException {      
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
        	textAreaConsole.append(String.format("%s: %s", stream, line));
        }
    }
    
}

package org.draxent.funwap.gui.actionlistener;

import java.util.List;

import javax.swing.JTextArea;

import org.draxent.funwap.FunwapException;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.lexicalanalysis.Scanner;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.syntacticanalysis.Parser;

public class ActionListenerUtils {

	private JTextArea textAreaCode;
	private JTextArea textAreaConsole;
	
	public ActionListenerUtils(JTextArea textAreaCode, JTextArea textAreaConsole) {
		this.textAreaCode = textAreaCode;
		this.textAreaConsole = textAreaConsole;		
	}
	
	public boolean isTextAreaCodeEmpty() {
		if (textAreaCode.getText().isEmpty()) {
			textAreaConsole.append("Cannot scan empty text!\r\n");
			return true;
		} else {
			return false;
		}
	}
	
	public List<Token> scannerPhase() {
		List<Token> tokens = new Scanner(textAreaCode.getText()).tokenize();
		textAreaConsole.append("Scanner phase proceduced the following tokens:\r\n");
		for (Token token : tokens) {
			textAreaConsole.append("  - " + token.toString() + "\r\n");
		}
		textAreaConsole.append("Scanner phase compleated.\r\n");	
		return tokens;
	}
	
	public BlockNode parserPhase(List<Token> tokens) {
		try {
			BlockNode programBlock = new Parser(tokens).parse();
			textAreaConsole.append("Parser phase compleated.\r\n");
			return programBlock;
		} catch (FunwapException e) {
			textAreaConsole.append("Error during the parser phase:\r\n");
			textAreaConsole.append("  - " + e.getMessage() + "\r\n");
			textAreaConsole.append("  - " + e.getToken() + "\r\n");
			return null;
		}
	}
}

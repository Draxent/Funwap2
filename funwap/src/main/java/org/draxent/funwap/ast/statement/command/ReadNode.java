package org.draxent.funwap.ast.statement.command;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;

public class ReadNode extends CommandNode {
	private static final String BUFFER_READER = "BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));" + NEW_LINE;
	private static final String TRY = "try {" + NEW_LINE;
	private static final String CATCH_NUMBER_FORMAT_EXCEPTION = "} catch(Exception numberFormatException) {" + NEW_LINE;
	private static final String PRINT_INVALID_FORMAT = "System.out.println(\"Impossible to convert the text '%s' into a %s type.\");" + NEW_LINE;
	private static final String PARSE_INT = "Integer.parseInt";
	private static final String READ_LINE = "bufferedReader.readLine()";
	private static final String ZERO = "0";
	
	// Token of the variable involved into the reading operation.
	private Token containerVariable;

	public ReadNode(Token token, Token containerVariable) {
		super(token);
		
		this.containerVariable = containerVariable;
	}
	
	public Token getContainerVariable() {
		return containerVariable;
	}
	
	@Override
	public List<GraphicText> getTitle() {
		return Arrays.asList(
				new GraphicText(containerVariable.getValue(), COMMAND_FONT, Color.BLACK),
				new GraphicText(String.valueOf(ASSIGN), COMMAND_FONT, Color.BLACK),
				new GraphicText(getToken().getValue(), COMMAND_FONT, Color.BLUE)
		);
	}
	
	@Override
	public void compile(StringBuilder sb, int numTab) {
		appendTabs(sb, numTab);
		sb.append(BUFFER_READER);
		appendTabs(sb, numTab);
		sb.append(TRY);
		compileReadLine(sb, numTab);
		compileCatchBlock(sb, numTab);
	}
	
	private void compileReadLine(StringBuilder sb, int numTab) {
		appendTabs(sb, numTab + 1);
		sb.append(containerVariable.getValue());
		sb.append(SPACE);
		sb.append(ASSIGN);
		sb.append(SPACE);
		sb.append(PARSE_INT);
		sb.append(ROUNDBR_OPEN);
		sb.append(READ_LINE);
		sb.append(ROUNDBR_CLOSE);
		sb.append(SEMICOLON);
		sb.append(NEW_LINE);
	}
	
	private void compileCatchBlock(StringBuilder sb, int numTab) {
		appendTabs(sb, numTab);
		sb.append(CATCH_NUMBER_FORMAT_EXCEPTION);
		compileNeutralAssignment(sb, numTab);
		appendTabs(sb, numTab + 1);
		sb.append(PRINT_INVALID_FORMAT);
		appendTabs(sb, numTab);
		sb.append(CURLYBR_CLOSE);
		sb.append(NEW_LINE);		
	}
	
	private void compileNeutralAssignment(StringBuilder sb, int numTab) {
		appendTabs(sb, numTab + 1);
		sb.append(containerVariable.getValue());
		sb.append(SPACE);
		sb.append(ASSIGN);
		sb.append(SPACE);
		sb.append(ZERO);
		sb.append(SEMICOLON);
		sb.append(NEW_LINE);	
	}
}

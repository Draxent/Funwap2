package org.draxent.funwap.gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import org.draxent.funwap.Useful;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class ContainerBuilder {
	private RSyntaxTextArea textAreaCode;
	private JTextArea textAreaConsole;
	
	public ContainerBuilder() {
	}
	
	public JPanel build() {
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		JSplitPane splitPane = buildSplitPane();
		container.add(splitPane);
		return container;
	}
	
	public RSyntaxTextArea getTextAreaCode() {
		return textAreaCode;
	}
	
	public JTextArea getTextAreaConsole() {
		return textAreaConsole;
	}

	private JSplitPane buildSplitPane() {
		JScrollPane scrollPaneCode = buildScrollPaneCode();
		JScrollPane scrollPaneConsole = buildsSrollPaneConsole();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneCode, scrollPaneConsole);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.5);
		return splitPane;
	}

	private JScrollPane buildScrollPaneCode() {
		textAreaCode = new RSyntaxTextArea();
		textAreaCode.setFont(Useful.DEFAULT_FONT);
		textAreaCode.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textAreaCode.setCodeFoldingEnabled(true);
		RTextScrollPane scrollPaneCode = new RTextScrollPane(textAreaCode);
		return scrollPaneCode;
	}
	
	private JScrollPane buildsSrollPaneConsole() {
		textAreaConsole = new JTextArea();
		textAreaConsole.setEditable(false);
		JScrollPane scrollPaneConsole = new JScrollPane(textAreaConsole);
		scrollPaneConsole.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(" Console "),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			), scrollPaneConsole.getBorder()
		));
		return scrollPaneConsole;
	}
}

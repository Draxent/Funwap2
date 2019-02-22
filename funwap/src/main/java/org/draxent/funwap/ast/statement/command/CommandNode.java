package org.draxent.funwap.ast.statement.command;

import java.awt.Font;

import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.ast.statement.StatementNode;
import org.draxent.funwap.lexicalanalysis.Token;

public abstract class CommandNode extends StatementNode {
	private static final Font COMMAND_FONT = new Font(SANS_SERIF, Font.BOLD, 20);
	
	public CommandNode(Token token) {
		super(token);
	}

	public NodeType getNodeType() {
		return NodeType.COMMAND;
	}
	
	public Font getFont() { 
		return COMMAND_FONT;
	}
	
	public String getTitle() {
		return getToken().getValue();
	}
}

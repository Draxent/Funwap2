package org.draxent.funwap.ast.statement.command;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.Useful;
import org.draxent.funwap.ast.statement.StatementNode;
import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;

public abstract class CommandNode extends StatementNode {
	protected static final Font COMMAND_FONT = new Font(Useful.SANS_SERIF, Font.BOLD, 20);
	
	public CommandNode(Token token) {
		super(token);
	}

	public NodeType getNodeType() {
		return NodeType.COMMAND;
	}
	
	public List<GraphicText> getTitle() {
		return Arrays.asList(new GraphicText(getToken().getValue(), COMMAND_FONT, Color.BLUE));
	}
}

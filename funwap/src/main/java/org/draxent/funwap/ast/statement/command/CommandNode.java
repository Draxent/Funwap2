package org.draxent.funwap.ast.statement.command;

import org.draxent.funwap.ast.statement.StatementNode;
import org.draxent.funwap.lexicalanalysis.Token;

public abstract class CommandNode extends StatementNode {

	public CommandNode(Token token) {
		super(token);
	}

}

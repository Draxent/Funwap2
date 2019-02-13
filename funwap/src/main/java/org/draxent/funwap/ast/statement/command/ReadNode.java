package org.draxent.funwap.ast.statement.command;

import org.draxent.funwap.lexicalanalysis.Token;

public class ReadNode extends CommandNode {

	// Token of the variable involved into the reading operation.
	private Token containerVariable;

	public ReadNode(Token token, Token containerVariable) {
		super(token);
		
		this.containerVariable = containerVariable;
	}
	
	public Token getContainerVariable() {
		return containerVariable;
	}
}

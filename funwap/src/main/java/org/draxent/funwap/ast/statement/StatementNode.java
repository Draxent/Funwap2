package org.draxent.funwap.ast.statement;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.lexicalanalysis.Token;

public abstract class StatementNode extends SyntacticNode {

	public StatementNode(Token token) {
		super(token);
	}

}

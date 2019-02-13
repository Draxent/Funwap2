package org.draxent.funwap.ast.statement.command;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class ReturnNode extends CommandNode {

	private SyntacticNode returnedValueNode;
	
	public ReturnNode(Token token, SyntacticNode returnedValueNode) {
		super(token);
		
		this.returnedValueNode = returnedValueNode;
	}
	
	public SyntacticNode getReturnedValue() {
		return returnedValueNode;
	}
}

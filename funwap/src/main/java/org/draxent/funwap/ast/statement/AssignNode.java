package org.draxent.funwap.ast.statement;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class AssignNode extends StatementNode {

	private SyntacticNode assignedValueNode;
	
	public AssignNode(Token token, SyntacticNode assignedValueNode) {
		super(token);
		
		this.assignedValueNode = assignedValueNode;
	}

	public SyntacticNode getAssignedValue() {
		return assignedValueNode;
	}
}

package org.draxent.funwap.ast.statement;

import java.awt.Font;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.lexicalanalysis.Token;

public class AssignNode extends StatementNode {
	private static final Font ASSIGN_FONT = new Font(SANS_SERIF, Font.BOLD, 20);
	
	private SyntacticNode assignedValueNode;
	
	public AssignNode(Token token, SyntacticNode assignedValueNode) {
		super(token);
		
		this.assignedValueNode = assignedValueNode;
		addChildIfNotNull(assignedValueNode);
	}
	
	public NodeType getNodeType() {
		return NodeType.ASSIGN;
	}
	
	public Font getFont() { 
		return ASSIGN_FONT;
	}
	
	public String getTitle() {
		return getToken().getValue();
	}

	public SyntacticNode getAssignedValue() {
		return assignedValueNode;
	}
}

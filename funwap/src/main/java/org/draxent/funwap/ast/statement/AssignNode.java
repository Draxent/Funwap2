package org.draxent.funwap.ast.statement;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.Useful;
import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;

public class AssignNode extends StatementNode {
	private static final Font ASSIGN_FONT = new Font(Useful.SANS_SERIF, Font.PLAIN, 20);
	
	private SyntacticNode assignedValueNode;
	
	public AssignNode(Token token, SyntacticNode assignedValueNode) {
		super(token);
		
		this.assignedValueNode = assignedValueNode;
		addChildIfNotNull(assignedValueNode);
	}
	
	public NodeType getNodeType() {
		return NodeType.ASSIGN;
	}
	
	public List<GraphicText> getTitle() {
		return Arrays.asList(new GraphicText(getToken().getValue(), ASSIGN_FONT, Color.BLACK));
	}

	public SyntacticNode getAssignedValue() {
		return assignedValueNode;
	}
}

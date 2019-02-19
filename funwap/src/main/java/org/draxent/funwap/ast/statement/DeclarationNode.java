package org.draxent.funwap.ast.statement;

import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.environment.VariableType;
import org.draxent.funwap.lexicalanalysis.Token;

public class DeclarationNode extends StatementNode  {

	private VariableType type;
	private ExpressionNode valueNode;
	
	public DeclarationNode(Token token, VariableType type, ExpressionNode valueNode) {
		super(token);
		
		this.type = type;
		this.valueNode = valueNode;
	}
	
	public NodeType getNodeType() {
		return NodeType.DECLARATION;
	}
	
	public DeclarationNode(Token token, VariableType type) {
		this(token, type, null);
	}

	public VariableType getVariableType() {
		return type;
	}

	public ExpressionNode getValue() {
		return valueNode;
	}
}

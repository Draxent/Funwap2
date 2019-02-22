package org.draxent.funwap.ast.statement;

import java.awt.Font;

import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.environment.VariableType;
import org.draxent.funwap.lexicalanalysis.Token;

public class DeclarationNode extends StatementNode  {
	private static final Font DECLARATION_FONT = new Font(SANS_SERIF, Font.BOLD, 20);
	
	private VariableType type;
	private ExpressionNode valueNode;
	
	public DeclarationNode(Token token, VariableType type, ExpressionNode valueNode) {
		super(token);
		
		this.type = type;
		this.valueNode = valueNode;
	}

	public DeclarationNode(Token token, VariableType type) {
		this(token, type, null);
	}
	
	public NodeType getNodeType() {
		return NodeType.DECLARATION;
	}
	
	public Font getFont() { 
		return DECLARATION_FONT;
	}
	
	public String getTitle() {
		return getToken().getValue();
	}
	
	public VariableType getVariableType() {
		return type;
	}

	public ExpressionNode getValue() {
		return valueNode;
	}
}

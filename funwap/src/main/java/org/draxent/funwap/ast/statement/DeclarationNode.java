package org.draxent.funwap.ast.statement;

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
	
	public DeclarationNode(Token token, VariableType type) {
		this(token, type, null);
	}

}

package org.draxent.funwap.syntacticanalysis;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class DeclarationVariable {
	
	private Token identifier;
	private ExpressionNode nodeValue;
	
	public DeclarationVariable(Token identifier, ExpressionNode nodeValue) {
		this.identifier = identifier;
		this.nodeValue = nodeValue;
	}
	
	public DeclarationVariable(Token identifier) {
		this(identifier, null);
	}
	
	public Token getIdentifier() {
		return identifier;
	}

	public ExpressionNode getNodeValue() {
		return nodeValue;
	}
}

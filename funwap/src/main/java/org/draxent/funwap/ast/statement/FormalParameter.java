package org.draxent.funwap.ast.statement;

import org.draxent.funwap.environment.Eval;

public class FormalParameter {
	String identifier;
	Eval.Type type;
	
	public FormalParameter(String identifier, Eval.Type type) {
		this.identifier = identifier;
		this.type = type;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public Eval.Type getType() {
		return type;
	}
}

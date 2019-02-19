package org.draxent.funwap.ast;

import java.util.Stack;

import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.lexicalanalysis.Token;

public abstract class SyntacticNode {
	private Token token;
	
	public enum NodeType {
	    BLOCK,
	    FUNCTION,
	    DECLARATION,
	    COMMAND,
	    CALL,
	    ASSIGN,
	    VARIABLE,
	    OPERATION,
	    STRING,
	    NUMBER
	}
	
	public SyntacticNode(Token token)
    {
		this.token = token;
    }
	
	public Token getToken() {
		return token;
	}
	
	public abstract NodeType getNodeType();
	
	// Perform the type and environment checking
	//abstract public Eval check(Stack<Eval> envStack);
	

}

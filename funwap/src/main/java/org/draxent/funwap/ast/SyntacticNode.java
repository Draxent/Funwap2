package org.draxent.funwap.ast;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.lexicalanalysis.Token;

public abstract class SyntacticNode {
	protected static final String SANS_SERIF = "sans-serif";
	
	private Token token;
	protected List<SyntacticNode> children;
	
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
	
	public SyntacticNode(Token token) {
		this.token = token;
		this.children = new ArrayList<>();
    }
	
	public Token getToken() {
		return token;
	}
	
	public int numChildren() {
		return children.size();
    }
	
	public SyntacticNode getChild(int index) {
		return children.get(index);
    }
	
	public void addChildIfNotNull(SyntacticNode child) {
		if (child != null) {
			children.add(child);
		}	
	}
	
	public abstract NodeType getNodeType();
	public abstract Font getFont();
	public abstract String getTitle();
	
	// Perform the type and environment checking
	//abstract public Eval check(Stack<Eval> envStack);
}

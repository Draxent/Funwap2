package org.draxent.funwap.ast;

import java.util.ArrayList;
import java.util.List;

import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;

public abstract class SyntacticNode {
	protected static final char CURLYBR_OPEN = '{';
	protected static final char CURLYBR_CLOSE = '}';
	protected static final char ROUNDBR_OPEN = '(';
	protected static final char ROUNDBR_CLOSE = ')';
	protected static final char SPACE = ' ';
	protected static final char COMMA = ',';
	protected static final char TAB = '\t';
	protected static final char SEMICOLON = ';';
	protected static final char ASSIGN = '=';
	protected static final String NEW_LINE = "\r\n";
	
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
	    CONSTANT
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

	public abstract List<GraphicText> getTitle();
	
	abstract public void compile(StringBuilder sb, int numTab);
	
	protected static void appendTabs(StringBuilder sb, int numTab) {
		for (int i = 0; i < numTab; i++) {
			sb.append(TAB);
		}
	}

	// Perform the type and environment checking
	//abstract public Eval check(Stack<Eval> envStack);
}

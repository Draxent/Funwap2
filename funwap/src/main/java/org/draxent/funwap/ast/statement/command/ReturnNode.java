package org.draxent.funwap.ast.statement.command;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.compiler.CompilerHelper;
import org.draxent.funwap.lexicalanalysis.Token;

public class ReturnNode extends CommandNode {
	private static final String RETURN = "return";
	
	private SyntacticNode returnedValueNode;
	
	public ReturnNode(Token token, SyntacticNode returnedValueNode) {
		super(token);
		
		this.returnedValueNode = returnedValueNode;
		addChildIfNotNull(returnedValueNode);
	}
	
	public SyntacticNode getReturnedValue() {
		return returnedValueNode;
	}
	
	@Override
	public void compile(StringBuilder sb, int numTab) {
		appendTabs(sb, numTab);
		sb.append(RETURN);
		sb.append(CompilerHelper.SPACE);
		returnedValueNode.compile(sb, numTab);
		sb.append(CompilerHelper.SEMICOLON);
	}
}

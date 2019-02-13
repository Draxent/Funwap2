package org.draxent.funwap.ast.statement;

import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.environment.VariableType;
import org.draxent.funwap.lexicalanalysis.Token;

public class FunctionNode extends StatementNode {

	private VariableType returnType;
	private List<FormalParameter> formalParameters;
	private BlockNode bodyNode;

	public FunctionNode(Token token, VariableType returnType, List<FormalParameter> formalParameters, BlockNode bodyNode) {
		super(token);
		
		this.returnType = returnType;
		this.formalParameters = formalParameters;
		this.bodyNode = bodyNode;
	}
	
	public FunctionNode(Token token, BlockNode bodyNode) {
		this(token, null, Arrays.asList(), bodyNode);
	}
	
	public BlockNode getBody() {
		return bodyNode;
	}
}

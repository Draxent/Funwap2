package org.draxent.funwap.ast.statement;

import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.environment.VariableType;
import org.draxent.funwap.lexicalanalysis.Token;

public class FunctionNode extends StatementNode {
	private static final Font FUNCTION_FONT = new Font(SANS_SERIF, Font.ITALIC, 20);
	
	private VariableType returnType;
	private List<FormalParameter> formalParameters;
	private BlockNode bodyNode;

	public FunctionNode(Token token, VariableType returnType, List<FormalParameter> formalParameters, BlockNode bodyNode) {
		super(token);
		
		this.returnType = returnType;
		this.formalParameters = formalParameters;
		this.bodyNode = bodyNode;
		addChildIfNotNull(bodyNode);
	}
	
	public FunctionNode(Token token, BlockNode bodyNode) {
		this(token, null, Arrays.asList(), bodyNode);
	}
	
	public NodeType getNodeType() {
		return NodeType.FUNCTION;
	}
	
	public Font getFont() { 
		return FUNCTION_FONT;
	}
	
	public String getTitle() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getToken().getValue());
		stringBuilder.append(" (");
		for (int i = 0; i < numFormalParameters(); i++) {
			if (i > 0) {
				stringBuilder.append(", ");
			}
			stringBuilder.append(getFormalParameter(i).getType());
		}
		stringBuilder.append(")");
		return stringBuilder.toString();
	}
	
	public VariableType getReturnType() {
		return returnType;
	}
	
	public int numFormalParameters() {
		return formalParameters.size();
    }
	
	public FormalParameter getFormalParameter(int index) {
		return formalParameters.get(index);
    }

	public BlockNode getBody() {
		return bodyNode;
	}
}

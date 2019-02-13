package org.draxent.funwap.ast.statement;

import java.util.List;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class CallNode extends StatementNode {

	private List<ExpressionNode> actualParameters;
	private boolean isStatement;
	
	public CallNode(Token token, List<ExpressionNode> actualParameters, boolean isStatement) {
		super(token);
		
		this.actualParameters = actualParameters;
		this.isStatement = isStatement;
	}

	public int numActualParameters()
    {
		return actualParameters.size();
    }
	
	public ExpressionNode getctualParameter(int index)
    {
		return actualParameters.get(index);
    }
}

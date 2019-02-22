package org.draxent.funwap.ast.statement;

import java.awt.Font;
import java.util.List;

import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class CallNode extends StatementNode {
	private static final Font CALL_FONT = new Font(SANS_SERIF, Font.BOLD, 20);
	
	private List<ExpressionNode> actualParameters;
	private boolean isStatement;
	
	public CallNode(Token token, List<ExpressionNode> actualParameters, boolean isStatement) {
		super(token);
		
		this.actualParameters = actualParameters;
		this.isStatement = isStatement;
	}
	
	public NodeType getNodeType() {
		return NodeType.CALL;
	}
	
	public Font getFont() { 
		return CALL_FONT;
	}
	
	public String getTitle() {
		return getToken().getValue();
	}

	public int numActualParameters() {
		return actualParameters.size();
    }
	
	public ExpressionNode getctualParameter(int index) {
		return actualParameters.get(index);
    }
}

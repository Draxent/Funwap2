package org.draxent.funwap.ast.statement;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.Useful;
import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;

public class CallNode extends StatementNode {
	private static final Font CALL_FONT = new Font(Useful.SANS_SERIF, Font.PLAIN, 20);
	
	private List<ExpressionNode> actualParameters;
	private boolean isStatement;
	
	public CallNode(Token token, List<ExpressionNode> actualParameters, boolean isStatement) {
		super(token);
		
		this.actualParameters = actualParameters;
		this.isStatement = isStatement;
		for (ExpressionNode exp: actualParameters) {
			this.children.add(exp);
		}
	}
	
	public NodeType getNodeType() {
		return NodeType.CALL;
	}
	
	public List<GraphicText> getTitle() {
		return Arrays.asList(new GraphicText(getToken().getValue(), CALL_FONT, Color.BLACK));
	}

	public int numActualParameters() {
		return actualParameters.size();
    }
	
	public ExpressionNode getctualParameter(int index) {
		return actualParameters.get(index);
    }
}

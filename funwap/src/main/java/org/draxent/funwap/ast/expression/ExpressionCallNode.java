package org.draxent.funwap.ast.expression;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.Useful;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.compiler.CompilerHelper;
import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;

public class ExpressionCallNode extends ExpressionNode {
	private static final Font CALL_FONT = new Font(Useful.SANS_SERIF, Font.BOLD, 20);
	
	private List<ExpressionNode> actualParameters;
	
	public ExpressionCallNode(Token token, List<ExpressionNode> actualParameters) {
		super(token);
		
		this.actualParameters = actualParameters;
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
	
	@Override
	public void compile(StringBuilder sb, int numTab) {
		sb.append(getToken().getValue());
		sb.append(CompilerHelper.ROUNDBR_OPEN);
		compileActualParameters(sb);
		sb.append(CompilerHelper.ROUNDBR_CLOSE);
	}
	
	private void compileActualParameters(StringBuilder sb) {
		boolean firstIteration = true;
		for (ExpressionNode exp : actualParameters) {
			if (!firstIteration) {
				sb.append(CompilerHelper.COMMA);
			}
			exp.compile(sb, 0);
			firstIteration = false;
		}		
	}
}

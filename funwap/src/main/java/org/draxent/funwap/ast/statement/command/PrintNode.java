package org.draxent.funwap.ast.statement.command;

import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.statement.StatementNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class PrintNode extends CommandNode {

	private List<ExpressionNode> expressionNodes;
	
	public PrintNode(Token token, List<ExpressionNode> expressionNodes) {
		super(token);
		
		this.expressionNodes = expressionNodes;
		for (ExpressionNode exp: expressionNodes) {
			this.children.add(exp);
		}
	}
	
	public PrintNode(Token token, ExpressionNode expressionNode) {
		this(token, Arrays.asList(expressionNode));
	}
	
	public int numExpressions() {
		return expressionNodes.size();
    }
	
	public ExpressionNode getExpression(int index) {
		return expressionNodes.get(index);
    }
}

package org.draxent.funwap.ast.statement.command;

import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class PrintNode extends CommandNode {
	private static final String PRINT = "System.out.print";
	private static final String PRINTLN =  PRINT + "ln();";
	
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
	
	@Override
	public void compile(StringBuilder sb, int numTab) {
		for (ExpressionNode exp: expressionNodes) {
			appendTabs(sb, numTab);
			sb.append(PRINT);
			sb.append(ROUNDBR_OPEN);
			exp.compile(sb, 0);
			sb.append(ROUNDBR_CLOSE);
			sb.append(SEMICOLON);
			sb.append(NEW_LINE);
		}
		appendTabs(sb, numTab);
		sb.append(PRINTLN);
	}
}

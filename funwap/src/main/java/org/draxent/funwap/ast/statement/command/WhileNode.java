package org.draxent.funwap.ast.statement.command;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.compiler.CompilerHelper;
import org.draxent.funwap.lexicalanalysis.Token;

public class WhileNode extends CommandNode {
	private static final String WHILE = "while";
	
	private ExpressionNode conditionNode;
    private BlockNode bodyNode;
    
    public WhileNode(Token token, ExpressionNode conditionNode, BlockNode bodyNode) {
		super(token);
		
		this.conditionNode = conditionNode;
		this.bodyNode = bodyNode;
		addChildIfNotNull(conditionNode);
		addChildIfNotNull(bodyNode);
	}
    
	public ExpressionNode getCondition() {
		return conditionNode;
	}

	public BlockNode getBody() {
		return bodyNode;
	}
	
	@Override
	public void compile(StringBuilder sb, int numTab) {
		appendTabs(sb, numTab);
		sb.append(WHILE);
		sb.append(CompilerHelper.SPACE);
		sb.append(CompilerHelper.ROUNDBR_OPEN);
		conditionNode.compile(sb, 0);
		sb.append(CompilerHelper.ROUNDBR_CLOSE);
		sb.append(CompilerHelper.NEW_LINE);
		bodyNode.compile(sb, numTab);
	}
}

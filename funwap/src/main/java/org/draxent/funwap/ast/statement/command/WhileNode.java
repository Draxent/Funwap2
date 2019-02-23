package org.draxent.funwap.ast.statement.command;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class WhileNode extends CommandNode {

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
}

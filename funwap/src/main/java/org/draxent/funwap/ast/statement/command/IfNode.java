package org.draxent.funwap.ast.statement.command;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class IfNode extends CommandNode {
	
	private ExpressionNode conditionNode;
    private BlockNode thenNode;
    private BlockNode elseNode;
    
	public IfNode(Token token, ExpressionNode conditionNode, BlockNode thenNode, BlockNode elseNode) {
		super(token);
		
		this.conditionNode = conditionNode;
		this.thenNode = thenNode;
		this.elseNode = elseNode;
		addChildIfNotNull(conditionNode);
		addChildIfNotNull(thenNode);
		addChildIfNotNull(elseNode);
	}
	
	public IfNode(Token token, ExpressionNode conditionNode, BlockNode thenNode) {
		this(token, conditionNode, thenNode, null);
	}

    public ExpressionNode getCondition() {
		return conditionNode;
	}

	public BlockNode getThenBlock() {
		return thenNode;
	}

	public BlockNode getElseBlock() {
		return elseNode;
	}
}

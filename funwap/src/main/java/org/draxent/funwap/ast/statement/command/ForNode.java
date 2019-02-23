package org.draxent.funwap.ast.statement.command;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.StatementNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class ForNode extends CommandNode {

	// for (stm1; condition; stm2) { block }
	private StatementNode stmNode1;
	private ExpressionNode conditionNode;
	private StatementNode stmNode2;
	private BlockNode bodyNode;
	
	public ForNode(Token token, StatementNode stmNode1, ExpressionNode conditionNode, StatementNode stmNode2,
			BlockNode bodyNode) {
		super(token);

		this.stmNode1 = stmNode1;
		this.conditionNode = conditionNode;
		this.stmNode2 = stmNode2;
		this.bodyNode = bodyNode;
		addChildIfNotNull(stmNode1);
		addChildIfNotNull(conditionNode);
		addChildIfNotNull(stmNode2);
		addChildIfNotNull(bodyNode);
	}

	public StatementNode getFirstStatement() {
		return stmNode1;
	}

	public ExpressionNode getCondition() {
		return conditionNode;
	}

	public StatementNode getSecondStatement() {
		return stmNode2;
	}

	public BlockNode getBody() {
		return bodyNode;
	}
}

package org.draxent.funwap.ast.statement.command;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.compiler.CompilerHelper;
import org.draxent.funwap.lexicalanalysis.Token;

public class IfNode extends CommandNode {
	private static final String IF = "if";
	private static final String ELSE = "else";
	
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
	
	@Override
	public void compile(StringBuilder sb, int numTab) {
		appendTabs(sb, numTab);
		sb.append(IF);
		sb.append(CompilerHelper.SPACE);
		sb.append(CompilerHelper.ROUNDBR_OPEN);
		conditionNode.compile(sb, 0);
		sb.append(CompilerHelper.ROUNDBR_CLOSE);
		sb.append(CompilerHelper.NEW_LINE);
		thenNode.compile(sb, numTab);
		if (elseNode != null) {
			sb.append(CompilerHelper.NEW_LINE);
			appendTabs(sb, numTab);
			sb.append(ELSE);
			sb.append(CompilerHelper.NEW_LINE);
			elseNode.compile(sb, numTab);
		}	
	}
}

package org.draxent.funwap.ast.expression.operation;

import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.lexicalanalysis.Token;

public abstract class OperationNode extends ExpressionNode {
	public enum OperationType {
	    OR,
	    AND,
	    EQUAL,
	    INEQUAL,
	    GREATEREQ,
	    LESSEQ,
	    NOT,
	    GREATER,
	    LESS,
	    PLUS,
	    MINUS,
	    MUL,
	    DIV
	}

	private OperationType operationType;
	
	public OperationNode(Token token) {
		super(token);
		this.operationType = convertTokenIntoOperationType();
	}
	
	public NodeType getNodeType() {
		return NodeType.OPERATION;
	}

	public OperationType getOperationType() {
		return operationType;
	}
	
	private OperationType convertTokenIntoOperationType() {
		switch(getToken().getType()) {
		case OR: return OperationType.OR;
		case AND: return OperationType.AND;
		case EQUAL: return OperationType.EQUAL;
		case INEQUAL: return OperationType.INEQUAL;
		case GREATEREQ: return OperationType.GREATEREQ;
		case LESSEQ: return OperationType.LESSEQ;
		case NOT: return OperationType.NOT;
		case GREATER: return OperationType.GREATER;
		case LESS: return OperationType.LESS;
		case ASSIGN_PLUS: return OperationType.PLUS;
		case PLUS: return OperationType.PLUS;
		case ASSIGN_MINUS: return OperationType.MINUS;
		case MINUS: return OperationType.MINUS;
		case MUL: return OperationType.MUL;
		case DIV: return OperationType.DIV;
		default:
			return null;
		}
	}
}

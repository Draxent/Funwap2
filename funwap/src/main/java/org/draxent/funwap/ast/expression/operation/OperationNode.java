package org.draxent.funwap.ast.expression.operation;

import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.lexicalanalysis.Token;

public abstract class OperationNode extends ExpressionNode {
	public enum Type {
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

	private Type operationType;
	
	public OperationNode(Token token) {
		super(token);
		this.operationType = convertTokenIntoOperationType();
	}

	public Type getOperationType() {
		return operationType;
	}
	
	private Type convertTokenIntoOperationType() {
		switch(getToken().getType()) {
		case OR: return Type.OR;
		case AND: return Type.AND;
		case EQUAL: return Type.EQUAL;
		case INEQUAL: return Type.INEQUAL;
		case GREATEREQ: return Type.GREATEREQ;
		case LESSEQ: return Type.LESSEQ;
		case NOT: return Type.NOT;
		case GREATER: return Type.GREATER;
		case LESS: return Type.LESS;
		case ASSIGN_PLUS: return Type.PLUS;
		case PLUS: return Type.PLUS;
		case ASSIGN_MINUS: return Type.MINUS;
		case MINUS: return Type.MINUS;
		case MUL: return Type.MUL;
		case DIV: return Type.DIV;
		default:
			return null;
		}
	}
}

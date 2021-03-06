package org.draxent.funwap.ast.expression.operation;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.Useful;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;

public abstract class OperationNode extends ExpressionNode {
	private static final Font OPERATION_FONT = new Font(Useful.SANS_SERIF, Font.BOLD, 20);    
    
	public enum OperationType {
	    OR("||"),
	    AND("&&"),
	    EQUAL("=="),
	    INEQUAL("!="),
	    GREATEREQ(">="),
	    LESSEQ("<="),
	    NOT("!"),
	    GREATER(">"),
	    LESS("<"),
	    PLUS("+"),
	    MINUS("-"),
	    MUL("*"),
	    DIV("/");
		
		private String compiledValue;
		
		private OperationType(String compiledValue) {
			this.compiledValue = compiledValue;
		}
		
		@Override
		public String toString() {
			return compiledValue;
		}
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
	
	public List<GraphicText> getTitle() {
		return Arrays.asList(new GraphicText(getToken().getValue(), OPERATION_FONT, Color.BLACK));
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
		case INCR: return OperationType.PLUS;
		case PLUS: return OperationType.PLUS;
		case ASSIGN_MINUS: return OperationType.MINUS;
		case DECR: return OperationType.MINUS;
		case MINUS: return OperationType.MINUS;
		case MUL: return OperationType.MUL;
		case DIV: return OperationType.DIV;
		default:
			return null;
		}
	}
}

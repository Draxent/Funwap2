package org.draxent.funwap.ast.statement;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.Useful;
import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.environment.VariableType;
import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;

public class FunctionNode extends StatementNode {
	private static final Font FUNCTION_FONT = new Font(Useful.SANS_SERIF, Font.ITALIC, 20);
	private static final String PUBLIC_STATIC = "public static ";
	private static final String MAIN_PARAMETER = "String[] args";
	
	private VariableType returnType;
	private List<FormalParameter> formalParameters;
	private BlockNode bodyNode;

	public FunctionNode(Token token, VariableType returnType, List<FormalParameter> formalParameters, BlockNode bodyNode) {
		super(token);
		
		this.returnType = returnType;
		this.formalParameters = formalParameters;
		this.bodyNode = bodyNode;
		addChildIfNotNull(bodyNode);
	}
	
	public FunctionNode(Token token, BlockNode bodyNode) {
		this(token, new VariableType(Eval.Type.VOID), Arrays.asList(), bodyNode);
	}
	
	public NodeType getNodeType() {
		return NodeType.FUNCTION;
	}
	
	public Font getFont() { 
		return FUNCTION_FONT;
	}
	
	public List<GraphicText> getTitle() {
		List<GraphicText> graphicTexts = new ArrayList<>();
		graphicTexts.add(returnType.getTitle());
		graphicTexts.add(new GraphicText(getToken().getValue(), FUNCTION_FONT, Color.BLACK));
		graphicTexts.add(new GraphicText("(", FUNCTION_FONT, Color.BLACK));
		for (int i = 0; i < numFormalParameters(); i++) {
			if (i > 0) {
				graphicTexts.add(new GraphicText(",", FUNCTION_FONT, Color.BLACK));
			}
			graphicTexts.add(new GraphicText(getFormalParameter(i).getType().name().toLowerCase(), FUNCTION_FONT, Color.BLACK));
		}
		graphicTexts.add(new GraphicText(")", FUNCTION_FONT, Color.BLACK));
		return graphicTexts;
	}
	
	public VariableType getReturnType() {
		return returnType;
	}
	
	public int numFormalParameters() {
		return formalParameters.size();
    }
	
	public FormalParameter getFormalParameter(int index) {
		return formalParameters.get(index);
    }

	public BlockNode getBody() {
		return bodyNode;
	}
	
	@Override
	public void compile(StringBuilder sb, int numTab) {
		appendTabs(sb, numTab);
		if (numTab == 1) {
			sb.append(PUBLIC_STATIC);
		}
		sb.append(returnType.getCompiledValue());
		sb.append(SPACE);
		sb.append(getToken().getValue());
		sb.append(ROUNDBR_OPEN);
		if (getToken().getType().equals(TokenType.MAIN)) {
			sb.append(MAIN_PARAMETER);
		} else {
			compileFormalParameters(sb);			
		}
		sb.append(ROUNDBR_CLOSE);
		sb.append(NEW_LINE);
		bodyNode.compile(sb, numTab);
	}
	
	private void compileFormalParameters(StringBuilder sb) {
		boolean firstIteration = true;
		for (FormalParameter param : formalParameters) {
			if (!firstIteration) {
				sb.append(COMMA);
			}
			sb.append(param.getType().getCompiledValue());
			sb.append(SPACE);
			sb.append(param.getIdentifier());
			firstIteration = false;
		}		
	}
}

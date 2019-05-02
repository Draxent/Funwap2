package org.draxent.funwap.ast.statement;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.draxent.funwap.Useful;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.compiler.CompilerHelper;
import org.draxent.funwap.environment.VariableType;
import org.draxent.funwap.gui.ast.GraphicText;
import org.draxent.funwap.lexicalanalysis.Token;

public class DeclarationNode extends StatementNode  {
	private static final Font DECLARATION_FONT = new Font(Useful.SANS_SERIF, Font.PLAIN, 20);
	
	private VariableType type;
	private ExpressionNode valueNode;
	
	public DeclarationNode(Token token, VariableType type, ExpressionNode valueNode) {
		super(token);
		
		this.type = type;
		this.valueNode = valueNode;
		addChildIfNotNull(valueNode);
	}

	public DeclarationNode(Token token, VariableType type) {
		this(token, type, null);
	}
	
	public NodeType getNodeType() {
		return NodeType.DECLARATION;
	}
	
	public List<GraphicText> getTitle() {
		return Arrays.asList(
				type.getTitle(),
				new GraphicText(getToken().getValue(), DECLARATION_FONT, Color.BLACK)
		);
	}
	
	public VariableType getVariableType() {
		return type;
	}

	public ExpressionNode getValue() {
		return valueNode;
	}
	
	@Override
	public void compile(StringBuilder sb, int numTab) {
		appendTabs(sb, numTab);
		if (numTab == 1) {
			sb.append(CompilerHelper.PUBLIC_STATIC);
		}
		sb.append(type.getCompiledValue());
		sb.append(CompilerHelper.SPACE);
		sb.append(getToken().getValue());
		
		if (valueNode != null) {
			sb.append(CompilerHelper.SPACE);
			sb.append(CompilerHelper.ASSIGN);
			sb.append(CompilerHelper.SPACE);
			valueNode.compile(sb, 0);
		}
		sb.append(CompilerHelper.SEMICOLON);
	}
}

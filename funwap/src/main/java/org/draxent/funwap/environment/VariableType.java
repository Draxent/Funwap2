package org.draxent.funwap.environment;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import org.draxent.funwap.Useful;
import org.draxent.funwap.gui.ast.GraphicText;

public class VariableType {
	private static final Font VARIABLETYPE_FONT = new Font(Useful.SANS_SERIF, Font.ITALIC, 20);
	private static final Color VARIABLETYPE_COLOR = Color.getHSBColor(0.791237f, 0.760784f, 1.0f);

	private Eval.Type type;
	private List<Eval.Type> functionParameterTypes;
	private VariableType functionReturnType;

	public VariableType(Eval.Type type, List<Eval.Type> functionParameterTypes, VariableType functionReturnType) {
		this.type = type;
		this.functionParameterTypes = functionParameterTypes;
		this.functionReturnType = functionReturnType;
	}

	public VariableType(Eval.Type type) {
		this(type, null, null);
	}

	public Eval.Type getType() {
		return type;
	}
	
	public int numFunctionParameterTypes()
    {
		return functionParameterTypes.size();
    }
	
	public Eval.Type getFunctionParameterTypes(int index)
    {
		return functionParameterTypes.get(index);
    }

	public VariableType getFunctionReturnType() {
		return functionReturnType;
	}

	public GraphicText getTitle() {
		return new GraphicText(type.name().toLowerCase(), VARIABLETYPE_FONT, VARIABLETYPE_COLOR);
	}
}

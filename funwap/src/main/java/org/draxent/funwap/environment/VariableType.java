package org.draxent.funwap.environment;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.function.Function;

import org.draxent.funwap.Useful;
import org.draxent.funwap.compiler.CompilerHelper;
import org.draxent.funwap.gui.ast.GraphicText;

public class VariableType {
	private static final Font VARIABLETYPE_FONT = new Font(Useful.SANS_SERIF, Font.ITALIC, 20);
	private static final Color VARIABLETYPE_COLOR = Color.getHSBColor(0.791237f, 0.760784f, 1.0f);
	private static final char ANGULARBR_OPEN = '<';
	private static final char ANGULARBR_CLOSE = '>';
	
	private Eval.Type type;
	private List<Eval.Type> functionParameterTypes;
	private VariableType functionReturnType;
	private boolean parentIsFunctionType;

	public VariableType(Eval.Type type, List<Eval.Type> functionParameterTypes, 
			VariableType functionReturnType, boolean parentIsFunctionType) {
		this.type = type;
		this.functionParameterTypes = functionParameterTypes;
		this.functionReturnType = functionReturnType;
		this.parentIsFunctionType = parentIsFunctionType;
	}

	public VariableType(Eval.Type type, boolean parentIsFunctionType) {
		this(type, null, null, parentIsFunctionType);
	}
	
	public VariableType(Eval.Type type) {
		this(type, null, null, false);
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
	
    public String getCompiledValue() {
    	if (!type.equals(Eval.Type.FUN)) {
    		return parentIsFunctionType ? type.getObjectValue() : type.getCompiledValue();
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append(type.getCompiledValue());
    	sb.append(ANGULARBR_OPEN);
    	compileFunctionParameterTypes(sb);
    	sb.append(CompilerHelper.COMMA);
    	sb.append(CompilerHelper.SPACE);
    	sb.append(functionReturnType.getCompiledValue());
    	sb.append(ANGULARBR_CLOSE);
    	return sb.toString();
    }
    
	private void compileFunctionParameterTypes(StringBuilder sb) {
		CompilerHelper.compileCommaSeparatedList(sb, functionParameterTypes, new Function<Eval.Type, String>()  {
		    @Override
		    public String apply(Eval.Type funParamType) {
		    	return funParamType.getObjectValue();
		    }
		});	
	}
}

package org.draxent.funwap.environment;

import java.util.List;

public class VariableType {
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

	public String getTitle() {
		return type.name().toLowerCase();
	}
}

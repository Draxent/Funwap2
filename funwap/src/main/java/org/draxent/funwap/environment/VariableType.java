package org.draxent.funwap.environment;

import java.util.ArrayList;

public class VariableType {
	private Eval.Type type;
	private ArrayList<Eval.Type> functionParametersType;
	private VariableType functionReturnType;

	public VariableType(Eval.Type type, ArrayList<Eval.Type> functionParametersType, VariableType functionReturnType) {
		this.type = type;
		this.functionParametersType = functionParametersType;
		this.functionReturnType = functionReturnType;
	}

	public VariableType(Eval.Type type) {
		this(type, null, null);
	}

	public Eval.Type getType() {
		return type;
	}

	public ArrayList<Eval.Type> getFunctionParametersType() {
		return functionParametersType;
	}

	public VariableType getFunctionReturnType() {
		return functionReturnType;
	}

}

package org.draxent.funwap.environment;

import org.draxent.funwap.FunwapException;
import org.draxent.funwap.lexicalanalysis.Token;

public class Eval {
    public enum Type
    {
		VOID("void", "Void"),
        INT("int", "Integer"),
        BOOL("boolean", "Boolean"),
		CHAR("char", "Char"),
		STRING("String", "String"),
		FUN("Function", "Function"),
		ALL(null, null); // special type used for ReadNode in the type checking phase
    	
		private String compiledValue;
		private String objectValue;
    	Type(String compiledValue, String objectValue) {
    		this.compiledValue = compiledValue;
    		this.objectValue = objectValue;
    	}
    	
		public String getCompiledValue() {
			return compiledValue;
		}
		public String getObjectValue() {
			return objectValue;
		}
    }
    
    private Token token;
    private Object value;
    private Type type;
    
	public Eval(Token token, Type type)
	{
		init(token, null, type);
	}
	
	public Eval(Token token, Integer value)
	{
		init(token, value, Type.INT);
	}
	
	public Eval(Token token, Boolean value)
	{
		init(token, value, Type.BOOL);
	}
	
	public Eval(Token token, Character value)
	{
		init(token, value, Type.CHAR);
	}
	
	public Eval(Token token, String value)
	{
		init(token, value, Type.STRING);

	}
	
	private void init(Token token, Object value, Type type) {
		this.token = token;
		this.value = value;
		this.type = type;
	}
	
	public Integer getIntValue() {
        throwIfNotExpectedType(Type.INT);
        return (Integer) this.value;
	}
	
	public Boolean getBoolValue() {
        throwIfNotExpectedType(Type.BOOL);
        return (Boolean) this.value;
	}
	
	public Character getCharValue() {
        throwIfNotExpectedType(Type.CHAR);
        return (char) this.value;
	}
	
	public String getStringValue() {
        throwIfNotExpectedType(Type.STRING);
        return (String) this.value;
	}
	
	public Token getToken() {
		return token;
	}
	
	public Type getType() {
		return type;
	}
	
    public String getCompiledValue() {
    	return getType().getCompiledValue();	
    }
    
    public static Eval.Type convertToken2EvalType(Token token)
    {
        switch (token.getType())
        {
            case TYPEINT: return Eval.Type.INT;
            case TYPEBOOL: return Eval.Type.BOOL;
            case TYPECHAR: return Eval.Type.CHAR;
			case TYPESTRING: return Eval.Type.STRING;
			case TYPEFUN: return Eval.Type.FUN;
			case TYPEVOID: return Eval.Type.VOID;
            default:
                throw new FunwapException("Failed conversion.", token);
        }
    }
    
	private void throwIfNotExpectedType(Type expectedType) {
		if (this.type != expectedType) {
        	throw new FunwapException("Cannot return an " + expectedType + " value for this Eval object.", token);
        }
	}
}
package org.draxent.funwap.lexicalanalysis;

import java.util.regex.Pattern;

public enum TokenType {
    /* Symbols */
    CURLYBR_OPEN("{", false, 1),
    CURLYBR_CLOSE("}", false, 1),
    ROUNDBR_OPEN("(", false, 1),
    ROUNDBR_CLOSE(")", false, 1),
    SEMICOLONS(";", false, 1),
    COMMA(",", false, 1),

    /* Statements */
    INCR("++", false, 2),
    DECR("--", false, 2),
    ASSIGN_PLUS("+=", false, 2),
    ASSIGN_MINUS("-=", false, 2),
    IF("if", false, 2),
    ELSE("else", false, 2),
    WHILE("while", false, 2),
    FOR("for", false, 2),
    RETURN("return", false, 2),
    PRINTLN("println", false, 2),
    READLN("readln", false, 2),
    ASSIGN("=", false, 4),
    
	/* Operators */
    OR("||", false, 3),
    AND("&&", false, 3),
    EQUAL("==", false, 3),
    INEQUAL("!=", false, 3),
    GREATEREQ(">=", false, 3),
    LESSEQ("<=", false, 3),
    NOT("!", false, 4),
    GREATER(">", false, 4),
    LESS("<", false, 4),
    PLUS("+", false, 4),
    MINUS("-", false, 4),
    MUL("*", false, 4),
    DIV("/", false, 4),
    
	/* Declarations */
	DECLVAR("var", false, 5),
	DECLFUNC("func", false, 5),
	
	/* Types */
    TYPEINT("int", false, 6),
    TYPEBOOL("bool", false, 6),
    TYPECHAR("char", false, 6),
	TYPESTRING("string", false, 6),
    TYPEFUN("fun", false, 6),
    TYPEVOID("void", false, 6),

    /* Constants */
    TRUE("true", false, 7),
    FALSE("false", false, 7),
    NUMBER("^\\d+", true, 7), // Any integer number, e.g. 1	
    CHAR("^\\'.\\'", true, 7), // Any character delimited by single quotes, e.g. 'a
    STRING("^\\\"[^\\\"]*\\\"", true, 7), // Any sequence of characters delimited by double quotes, e.g. "string"
    IDENTIFIER("^[_a-zA-Z][_a-zA-Z0-9]*", true, 9), // Any identifier
    
    /* Others */
	MAIN("main", false, 8); // Identifier "main" is treated separately to ensure that the program always present the main function.
	
	private String matchString;
	private boolean isRegEx;
	private int priority;
	private Pattern pattern;
	
	private TokenType(String matchString, boolean isRegEx, int priority) {
		this.matchString = matchString;
		this.isRegEx = isRegEx;
		this.priority = priority;
		
		if (isRegEx) {
			pattern = Pattern.compile(matchString);
		}
	}
	
	@Override
	public String toString() {
		return matchString;
	}

	public String getMatchString() {
		return matchString;
	}

	public boolean isRegEx() {
		return isRegEx;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public Pattern getPattern() {
		return pattern;
	}
}

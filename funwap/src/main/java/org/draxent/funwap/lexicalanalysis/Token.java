package org.draxent.funwap.lexicalanalysis;

// This is a single atomic unit of the language.
public class Token {
	private TokenType type;
	private String value;
	private int index; // position inside the source text where this token was found
	private int row; // line position inside the source text where this token was found
	private int column; // column position inside the source text where this token was found.
	
    public Token(TokenType type, String value, int index, int row, int column)
    {
        this.type = type;
        this.value = value;
		this.index = index;
        this.row = row;
        this.column = column;
    }
    
    public boolean isVariableType() {
        return ((type == TokenType.TYPEINT)
            ||  (type == TokenType.TYPEBOOL)
            ||  (type == TokenType.TYPECHAR)
			||  (type == TokenType.TYPESTRING)
            ||  (type == TokenType.TYPEFUN)
       );
    }
    
    public boolean isStatement()
    {
        return ((type == TokenType.IDENTIFIER)
            ||  (type == TokenType.IF)
            ||  (type == TokenType.WHILE)
            ||  (type == TokenType.FOR)
            ||  (type == TokenType.RETURN)
			||	(type == TokenType.PRINTLN)
			||  (type == TokenType.READLN)
        );
    }
    
	@Override
	public String toString() {
		String valueToPrint = (this.type.isRegEx() ? String.format("(\"%s\")", this.value) : "");
		return String.format("Token %s%s at %d:%d", this.type.name(), valueToPrint, this.row, this.column);
	}

	public TokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public int getIndex() {
		return index;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}

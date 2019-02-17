package org.draxent.funwap.syntacticanalysis;

import java.util.List;

import org.draxent.funwap.FunwapException;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;

public class TokenReader {
	
	private List<Token> tokens;
	private int index;
	
	public TokenReader(List<Token> tokens) {
		this.tokens = tokens;
		this.index = 0;
	}
	
	public Token getCurrentAndMoveNext() {
		Token t = getCurrent();
		moveNext();
		return t;
	}
	
	public Token getCurrent() {
		return tokens.get(index);
	}
	
	public void moveNext() {
		index++;
	}
	
	public boolean isEOF() {
		return index >= tokens.size();
	}

	public boolean isCurrentOfType(TokenType tokenType) {
		return (getCurrent().getType() == tokenType);
	}

	public Token matchToken(TokenType tokenType) {
		if (!isCurrentOfType(tokenType)) {
			throw new FunwapException(getCurrent() + " found has not expected type " + tokenType.name() + ".", getCurrent());
		}
		return getCurrent();
	}

	public Token matchTokenAndMoveOn(TokenType tokenType) {
		matchToken(tokenType);
		Token token = getCurrent();
		index++;
		return token;
	}
	
	public boolean checkTokenTypeLookingAhead(TokenType tokenType, int ahead) {
		return (index + ahead < tokens.size() && tokens.get(index + ahead).getType() == tokenType);
	}
	
	public boolean checkNextTokenType(TokenType tokenType) {
		return checkTokenTypeLookingAhead(tokenType, 1);
	}
}

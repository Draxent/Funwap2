package org.draxent.funwap.syntacticanalysis;

import java.util.ArrayList;
import java.util.List;

import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;

public class ParserTestUtils {
	private int index;
	
	public ParserTestUtils() {
		this.index = -1;
	}
	
	public Token createToken(TokenType type, String value) {
		index++;
		return new Token(type, value, index, 0, 0);
	}
	
	public Token createToken(TokenType type) {
		return createToken(type, type.toString());
	}
	
	public List<Token> createTokenList(List<TokenType> tokenTypes) {
		List<Token> list = new ArrayList<>();
		for (TokenType type : tokenTypes) {
			list.add(createToken(type));
		}
		return list;
	}
}

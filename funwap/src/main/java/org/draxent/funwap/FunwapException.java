package org.draxent.funwap;

import org.draxent.funwap.lexicalanalysis.Token;

public class FunwapException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Token token;
	
	public FunwapException(String errorMessage, Token token) {
		super(errorMessage);
		this.token = token;
	}
	
	public FunwapException(String errorMessage, Throwable err, Token token) {
		super(errorMessage, err);
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
}

package org.draxent.funwap.syntacticanalysis;

import java.util.ArrayList;
import java.util.List;

import org.draxent.funwap.FunwapException;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.DeclarationNode;
import org.draxent.funwap.ast.statement.FormalParameter;
import org.draxent.funwap.ast.statement.FunctionNode;
import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.environment.VariableType;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;

public class ParserDeclarationList {
	
	private TokenReader tokenReader;
	private ParserExpression parserExpression;
	private ParserBlock parserBlock;
		
	public ParserDeclarationList(TokenReader tokenReader, ParserExpression parserExpression,
			ParserBlock parserBlock) {
		this.tokenReader = tokenReader;
		this.parserExpression = parserExpression;
		this.parserBlock = parserBlock;
	}

	public void parse(BlockNode blockNode) {
		boolean isDeclVar = !tokenReader.isEOF() && tokenReader.isCurrentOfType(TokenType.DECLVAR);
		boolean isDeclFuncNotMain = !tokenReader.isEOF() && tokenReader.isCurrentOfType(TokenType.DECLFUNC) && !tokenReader.checkTokenTypeLookingAhead(TokenType.MAIN, 1);
		if (isDeclVar || isDeclFuncNotMain) {
			parseDeclaration(blockNode);
			parse(blockNode);
		}
	}
	
	public FunctionNode parseFunctionDeclaration(Token functionName) {
		tokenReader.moveNext();
		
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_OPEN);
		List<FormalParameter> formalParameters = parseFormalParams();
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_CLOSE);

		VariableType returnType = parseVariableType();
		BlockNode bodyNode = parserBlock.parse(BlockNode.BlockType.BODY);

		return new FunctionNode(functionName, returnType, formalParameters, bodyNode);		
	}

	private void parseDeclaration(BlockNode blockNode) {
		if (tokenReader.isCurrentOfType(TokenType.DECLFUNC)) {
			tokenReader.moveNext();
			Token functionName = tokenReader.matchToken(TokenType.IDENTIFIER);
			blockNode.addChild(parseFunctionDeclaration(functionName));
		} else {
			parseNormalDeclaration(blockNode);
		}
	}
	
	private void parseNormalDeclaration(BlockNode blockNode) {
		tokenReader.moveNext();
		List<DeclarationVariable> variables = parseDeclarationVariables();
		VariableType type = parseVariableType();
		tokenReader.matchTokenAndMoveOn(TokenType.SEMICOLONS);

		for (DeclarationVariable variable : variables) {
			DeclarationNode declarationNode = new DeclarationNode(variable.getIdentifier(), type,
					variable.getNodeValue());
			blockNode.addChild(declarationNode);
		}
	}
	
	private List<DeclarationVariable> parseDeclarationVariables() {
		List<DeclarationVariable> variables = new ArrayList<>();
		variables.add(parseDeclarationVariable());
		while (tokenReader.isCurrentOfType(TokenType.COMMA)) {
			tokenReader.moveNext();
			variables.add(parseDeclarationVariable());
		}
		return variables;
	}
	
	private List<FormalParameter> parseFormalParams() {
		List<FormalParameter> formalParams = new ArrayList<>();
		
		if (tokenReader.isCurrentOfType(TokenType.IDENTIFIER)) {
			formalParams.add(parseFormalParam());
			
			while (tokenReader.isCurrentOfType(TokenType.COMMA)) {
				tokenReader.moveNext();
				formalParams.add(parseFormalParam());
			}
		}
		
		return formalParams;
	}
	
	private FormalParameter parseFormalParam() {
		String identifier = tokenReader.matchTokenAndMoveOn(TokenType.IDENTIFIER).getValue();
		
		if (!tokenReader.getCurrent().isVariableType()) {
			throw new FunwapException(tokenReader.getCurrent() + " is not a type token.", tokenReader.getCurrent());
		}
		
		Eval.Type type = Eval.convertToken2EvalType(tokenReader.getCurrent());
		tokenReader.moveNext();
		return new FormalParameter(identifier, type);
	}

	private DeclarationVariable parseDeclarationVariable() {
		Token identifier = tokenReader.matchTokenAndMoveOn(TokenType.IDENTIFIER);
		if (tokenReader.isCurrentOfType(TokenType.ASSIGN)) {
			tokenReader.moveNext();
			return new DeclarationVariable(identifier, parserExpression.parse());
		} else {
			return new DeclarationVariable(identifier);
		}
	}

	private VariableType parseVariableType() {
		Eval.Type type = Eval.convertToken2EvalType(tokenReader.getCurrent());
		tokenReader.moveNext();
		if (type != Eval.Type.FUN) {
			return new VariableType(type);
		} else {
			return parseFunctionType(type);
		}
	}

	private VariableType parseFunctionType(Eval.Type type) {
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_OPEN);
		List<Eval.Type> functionParameterTypes = parseFunctionParameterTypes();
		tokenReader.matchTokenAndMoveOn(TokenType.ROUNDBR_CLOSE);
		VariableType functionReturnType = parseVariableType();
		return new VariableType(type, functionParameterTypes, functionReturnType);
	}

	private List<Eval.Type> parseFunctionParameterTypes() {
		if (!tokenReader.getCurrent().isVariableType()) {
			return null;
		}

		List<Eval.Type> functionParameterTypes = new ArrayList<>();
		functionParameterTypes.add(Eval.convertToken2EvalType(tokenReader.getCurrent()));
		tokenReader.moveNext();

		while (tokenReader.isCurrentOfType(TokenType.COMMA)) {
			tokenReader.moveNext();

			if (tokenReader.getCurrent().isVariableType() && !tokenReader.isCurrentOfType(TokenType.TYPEFUN)) {
				functionParameterTypes.add(Eval.convertToken2EvalType(tokenReader.getCurrent()));
				tokenReader.moveNext();
			} else {
				throw new FunwapException(tokenReader.getCurrent() + " is not a valid type.", tokenReader.getCurrent());
			}
		}
		return functionParameterTypes;
	}
}

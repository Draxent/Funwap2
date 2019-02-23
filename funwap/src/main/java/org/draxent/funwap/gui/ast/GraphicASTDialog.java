package org.draxent.funwap.gui.ast;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.draxent.funwap.ast.SyntacticNode;
import org.draxent.funwap.ast.expression.ConstantNode;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.expression.VarNode;
import org.draxent.funwap.ast.expression.operation.BinaryOperationNode;
import org.draxent.funwap.ast.expression.operation.OperationNode;
import org.draxent.funwap.ast.statement.AssignNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.DeclarationNode;
import org.draxent.funwap.ast.statement.FunctionNode;
import org.draxent.funwap.ast.statement.command.ForNode;
import org.draxent.funwap.ast.statement.command.IfNode;
import org.draxent.funwap.ast.statement.command.PrintNode;
import org.draxent.funwap.environment.Eval;
import org.draxent.funwap.environment.VariableType;
import org.draxent.funwap.lexicalanalysis.Token;
import org.draxent.funwap.lexicalanalysis.TokenType;

public class GraphicASTDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	public GraphicASTDialog(JFrame parent) {
		super(parent, "Abstract Syntax Tree", true);
		
		BlockNode programNode = new BlockNode(null, BlockNode.BlockType.PROGRAM);
		BlockNode bodyNode = new BlockNode(null, BlockNode.BlockType.MAIN);
		FunctionNode functionNode = new FunctionNode(createToken(TokenType.MAIN), bodyNode);
		programNode.addStatement(functionNode);
		
		DeclarationNode d1 = new DeclarationNode(
				createToken(TokenType.IDENTIFIER, "a"), 
				createVariableType(Eval.Type.INT), 
				createNumberNode(0)
		);
		DeclarationNode d2 = new DeclarationNode(
				createToken(TokenType.IDENTIFIER, "b"), 
				createVariableType(Eval.Type.INT), 
				createNumberNode(0)
		);
		DeclarationNode d3 = new DeclarationNode(
				createToken(TokenType.IDENTIFIER, "i"), 
				createVariableType(Eval.Type.INT), 
				null
		);
		DeclarationNode d4 = new DeclarationNode(
				createToken(TokenType.IDENTIFIER, "isAeqB"), 
				createVariableType(Eval.Type.BOOL),
				createBinaryOperationNode(TokenType.EQUAL, createVarNode("a"), createVarNode("b"))
		);
		BlockNode blockFor = new BlockNode(null, BlockNode.BlockType.FOR);
		blockFor.addStatement(createAssignNode("a", createBinaryOperationNode(TokenType.PLUS, createVarNode("a"), createNumberNode(1))));
		ForNode forNode = new ForNode(
				createToken(TokenType.FOR),
				createAssignNode("i", createNumberNode(0)),
				createBinaryOperationNode(TokenType.LESS, createVarNode("i"), createNumberNode(32)),
				createAssignNode("i", createBinaryOperationNode(TokenType.PLUS, createVarNode("i"), createNumberNode(1))),
				blockFor
				);
		
		BlockNode blockThen = new BlockNode(null, BlockNode.BlockType.THEN);
		blockThen.addStatement(createPrintNode("msg1"));
		BlockNode blockElse = new BlockNode(null, BlockNode.BlockType.ELSE);
		blockElse.addStatement(createPrintNode("msg2"));
		IfNode ifNode = new IfNode(
				createToken(TokenType.IF),
				createBinaryOperationNode(TokenType.AND, 
						createVarNode("isAeqB"), 
						createBinaryOperationNode(TokenType.EQUAL, createVarNode("a"), createNumberNode(0))
				),
				blockThen,
				blockElse
		);
		
		bodyNode.addStatement(d1);
		bodyNode.addStatement(d2);
		bodyNode.addStatement(d3);
		bodyNode.addStatement(d4);
		bodyNode.addStatement(forNode);
		bodyNode.addStatement(ifNode);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new GraphicASTPanel(programNode), BorderLayout.CENTER);

		setSize(1000, 800);
		setLocationRelativeTo(null);
	}
	
	private Token createToken(TokenType type) {
		return createToken(type, type.getMatchString());
	}
	
	private Token createToken(TokenType type, String value) {
		return new Token(type, value, 0, 0, 0);
	}
	
	private VariableType createVariableType(Eval.Type type) {
		return new VariableType(type, null, null);
	}
	
	private VarNode createVarNode(String value) {
		return new VarNode(createToken(TokenType.IDENTIFIER, value));
	}
	
	private ConstantNode createNumberNode(int num) {
		return createConstantNode(TokenType.NUMBER, String.valueOf(num));
	}
	
	private ConstantNode createStringNode(String s) {
		return createConstantNode(TokenType.STRING, s);
	}
	
	private ConstantNode createConstantNode(TokenType type, String s) {
		return new ConstantNode(createToken(type, s));
	}
	
	private BinaryOperationNode createBinaryOperationNode(TokenType type, ExpressionNode n1, ExpressionNode n2) {
		return new BinaryOperationNode(createToken(type), n1, n2); 
	}
	
	private AssignNode createAssignNode(String ide, SyntacticNode value) {
		return new AssignNode(createToken(TokenType.IDENTIFIER, ide), value);
	}
	
	private PrintNode createPrintNode(String s) {
		return new PrintNode(createToken(TokenType.PRINTLN), Arrays.asList(createStringNode(s)));
	}
}
 
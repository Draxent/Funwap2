package org.draxent.funwap.gui.ast;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.draxent.funwap.ast.expression.ConstantNode;
import org.draxent.funwap.ast.expression.ExpressionNode;
import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.ast.statement.FunctionNode;
import org.draxent.funwap.ast.statement.command.PrintNode;
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
		
		ConstantNode c1 = new ConstantNode(createToken(TokenType.STRING, "hello"));
		ConstantNode c2 = new ConstantNode(createToken(TokenType.STRING, "hello"));
		ConstantNode c3 = new ConstantNode(createToken(TokenType.STRING, "hello"));
		ConstantNode c4 = new ConstantNode(createToken(TokenType.STRING, "hello"));
		ConstantNode c5 = new ConstantNode(createToken(TokenType.STRING, "hello"));
		PrintNode printNode1 = new PrintNode(createToken(TokenType.PRINTLN), Arrays.asList(c1));
		PrintNode printNode2 = new PrintNode(createToken(TokenType.PRINTLN), Arrays.asList(c2));
		PrintNode printNode3 = new PrintNode(createToken(TokenType.PRINTLN), Arrays.asList(c3));
		PrintNode printNode4 = new PrintNode(createToken(TokenType.PRINTLN), Arrays.asList(c4));
		PrintNode printNode5 = new PrintNode(createToken(TokenType.PRINTLN), Arrays.asList(c5));
		bodyNode.addStatement(printNode1);
		bodyNode.addStatement(printNode2);
		bodyNode.addStatement(printNode3);
		bodyNode.addStatement(printNode4);
		bodyNode.addStatement(printNode5);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new GraphicASTPanel(programNode), BorderLayout.CENTER);

		setSize(800, 800);
		setLocationRelativeTo(null);
	}
	
	private Token createToken(TokenType type) {
		return createToken(type, type.getMatchString());
	}
	
	private Token createToken(TokenType type, String value) {
		return new Token(type, value, 0, 0, 0);
	}
}
 
package org.draxent.funwap.ast.statement;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.lexicalanalysis.Token;

public class BlockNode extends StatementNode {
	private static final Font BLOCK_FONT = new Font(SANS_SERIF, Font.BOLD, 20);
	
	public enum BlockType
	{
		PROGRAM, // Used for the block of the entire program.
		MAIN, // Used for the block of the main function.
		THEN,
		ELSE,
		WHILE,
		FOR,
		BODY
	};
	
	private BlockType type;
	private List<StatementNode> statementList;
	
	public BlockNode(Token token, BlockType type) {
		super(token);
		
		this.type = type;
		this.statementList = new ArrayList<>();
	}
	
	public NodeType getNodeType() {
		return NodeType.BLOCK;
	}
	
	public Font getFont() { 
		return BLOCK_FONT;
	}
	
	public String getTitle() {
		return getBlockType().name();
	}
	
	public BlockType getBlockType() {
		return type;
    }
	
	public int numStatement() {
		return statementList.size();
    }
	
	public StatementNode getStatement(int index) {
		return statementList.get(index);
    }
	
	public void addStatement(StatementNode child) {
		statementList.add(child);
		children.add(child);
    }
}

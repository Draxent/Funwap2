package org.draxent.funwap.ast.statement;

import java.util.ArrayList;
import java.util.List;

import org.draxent.funwap.ast.SyntacticNode.NodeType;
import org.draxent.funwap.lexicalanalysis.Token;

public class BlockNode extends StatementNode {

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
	private List<StatementNode> children;
	
	public BlockNode(Token token, BlockType type) {
		super(token);
		
		this.type = type;
		this.children = new ArrayList<>();
	}
	
	public NodeType getNodeType() {
		return NodeType.BLOCK;
	}
	
	public BlockType getBlockType()
    {
		return type;
    }
	
	public int numChildren()
    {
		return children.size();
    }
	
	public StatementNode getChild(int index)
    {
		return children.get(index);
    }
	
	public void addChild(StatementNode child)
    {
		children.add(child);
    }
}

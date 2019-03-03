package org.draxent.funwap.gui.ast;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.draxent.funwap.ast.SyntacticNode;

public class GraphicAST {
    // Space to skip horizontally and vertically between siblings and between generations
    private final int HOFFSET = 5;
    private final int VOFFSET = 10;
    
    private Graphics2D g;
    private Rectangle treeScreenArea;
    private GraphicASTNode gRoot;
    private List<GraphicAST> gSubTrees;
	
    public GraphicAST(Graphics2D g, SyntacticNode root) {
    	this.g = g;
    	this.treeScreenArea = new Rectangle(0, 0, 0, 0);
    	this.gRoot = new GraphicASTNode(g, root);
    	
    	this.gSubTrees = new ArrayList<>();
		for (int i = 0; i < root.numChildren(); i++) {
			this.gSubTrees.add(new GraphicAST(g, root.getChild(i)));
		}	
    }
    
    public void moveTree(int transX, int transY)
	{
    	setX(getX() + transX);
    	setY(getY() + transY);
    	
    	for (GraphicAST gSubTree : gSubTrees) {
    		gSubTree.moveTree(transX, transY);
    	}
	}
    
    public void computeTreeStructure() {
    	gRoot.computeDimension();
    	Dimension nodeDimension = gRoot.getDimension();

        int posX = getX();
        for (GraphicAST gSubTree : gSubTrees) {
        	// Arrange this child's subtree
        	gSubTree.setX(posX);
        	gSubTree.setY(getY() + nodeDimension.height + VOFFSET);
        	gSubTree.computeTreeStructure();
        	
        	// See if this increases the height of the tree
        	if (getBottom() < gSubTree.getBottom()) {
        		setHeight(gSubTree.getBottom() - getY());
        	}
        	
        	 // Allow room before the next sibling
        	posX += gSubTree.getWidth() + HOFFSET;
        }
        
        if (gSubTrees.size() > 0)
        {
            // Remove the spacing after the last child
            posX -= HOFFSET;
            setWidth(posX - getX());
        }
        else
        {
            // It has no children so the treeArea is equal to the node's size
            setWidth(nodeDimension.width);
            setHeight(nodeDimension.height);
        }
        
        // See if this node is wider than the subtree under it
        if (nodeDimension.width > getWidth())
        {
            // Center the subtrees under this node moving all its children of translX
            int translX = (nodeDimension.width - getWidth()) / 2;
            for (GraphicAST gSubTree : gSubTrees) {
            	gSubTree.moveTree(translX, 0);
            }
            // The Width property of the TreeArea is equal to the node's width since it dominates over its subtree
            setWidth(nodeDimension.width);
        }
    }
    
    public void draw() {
        drawArcs();
        drawNodes();
    }
    
    public int getX() {
    	return treeScreenArea.x;
    }
    
    public void setX(int x) {
    	treeScreenArea.x = x;
    }
    
    public int getY() {
    	return treeScreenArea.y;
    }
    
    public void setY(int y) {
    	treeScreenArea.y = y;
    }
    
    public int getWidth() {
    	return treeScreenArea.width;
    }
    
    public void setWidth(int width) {
    	treeScreenArea.width = width;
    }

    public int getHeight() {
    	return treeScreenArea.height;
    }
    
    public void setHeight(int height) {
    	treeScreenArea.height = height;
    }
    
    public int getBottom() {
    	return getY() + getHeight();
    }
    
    public Point getDrawLocationForNodeCenter() {
        return new Point(getX() + getWidth()/2, getY() + gRoot.getDimension().height / 2);
    }
    
    private void drawArcs() {
    	for (GraphicAST gSubTree : gSubTrees) {
            drawLine(getDrawLocationForNodeCenter(), gSubTree.getDrawLocationForNodeCenter());
            gSubTree.drawArcs();
        }
    }
    
    private void drawNodes() {
    	Point center = getDrawLocationForNodeCenter();
    	gRoot.draw(center.x, center.y);
        for (GraphicAST gSubTree : gSubTrees) {
        	gSubTree.drawNodes();
        }
    }
	
	private void drawLine(Point p1, Point p2) {
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
	}
}

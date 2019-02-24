package org.draxent.funwap.gui.ast;

import java.awt.Color;
import java.awt.Font;

public class GraphicText {
	
	private String text;
	private Font font;
	private Color color;
	
	public GraphicText(String text, Font font, Color color) {
		this.text = text;
		this.font = font;
		this.color = color;
	}
	
	public String getText() {
		return text;
	}

	public Font getFont() {
		return font;
	}

	public Color getColor() {
		return color;
	}
}

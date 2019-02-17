package org.draxent.funwap.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.draxent.funwap.Useful;

public class IDE implements Runnable {
	private JFrame frame;

	public void run() {
		frame = new JFrame("JFunw@p Compiler");
		
		setDefaultFontSize();
		ContainerBuilder containerBuilder = new ContainerBuilder();
		JPanel container = containerBuilder.build();
		JMenuBar menuBar = new MenuBarBuilder(frame, containerBuilder.getTextAreaCode(), containerBuilder.getTextAreaConsole()).build();
	
		frame.setContentPane(container);
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1200, 900));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void setDefaultFontSize() {
		UIManager.put("Menu.font", Useful.defaultFont);
		UIManager.put("MenuItem.font", Useful.defaultFont);
		UIManager.put("TextArea.font", Useful.defaultFont);
		UIManager.put("TitledBorder.font", Useful.defaultFont);
		UIManager.put("Label.font", Useful.defaultFont);
		UIManager.put("Button.font", Useful.defaultFont);
	}
}
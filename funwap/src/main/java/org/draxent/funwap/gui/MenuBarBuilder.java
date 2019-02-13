package org.draxent.funwap.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import org.draxent.funwap.gui.actionlistener.ActionListenerAST;
import org.draxent.funwap.gui.actionlistener.ActionListenerAbout;
import org.draxent.funwap.gui.actionlistener.ActionListenerExit;
import org.draxent.funwap.gui.actionlistener.ActionListenerHelp;
import org.draxent.funwap.gui.actionlistener.ActionListenerOpen;
import org.draxent.funwap.gui.actionlistener.ActionListenerSave;

public class MenuBarBuilder {
	private static final String IMAGES_DIR = "./src/main/resources/images/";
	
	private JFrame frame;
	private JTextArea textAreaCode;
	private JTextArea textAreaConsole;
	
	public MenuBarBuilder(JFrame frame, JTextArea textAreaCode, JTextArea textAreaConsole) {
		this.frame = frame;
		this.textAreaCode = textAreaCode;
		this.textAreaConsole = textAreaConsole;
	}
	
	public JMenuBar build() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = buildMenuFile();
		JMenu menuExecute = buildMenuExecute();
		JMenu menuQuestionMark = buildMenuQuestionMark();
		menuBar.add(menuFile);
		menuBar.add(menuExecute);
		menuBar.add(menuQuestionMark);
		return menuBar;
	}

	private JMenu buildMenuFile() {
		JMenu menuFile = new JMenu("FILE");
		JMenuItem menuItemOpen = new JMenuItem("Open File");
		menuItemOpen.setIcon(new ImageIcon(IMAGES_DIR + "open.png"));
		menuItemOpen.addActionListener(new ActionListenerOpen(frame, textAreaCode, textAreaConsole));
		JMenuItem menuItemSave = new JMenuItem("Save File");
		menuItemSave.addActionListener(new ActionListenerSave(frame, textAreaCode, textAreaConsole));
		menuItemSave.setIcon(new ImageIcon(IMAGES_DIR + "save.png"));
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.setIcon(new ImageIcon(IMAGES_DIR + "close.png"));
		menuItemExit.addActionListener(new ActionListenerExit(frame));
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemSave);
		menuFile.add(menuItemExit);
		return menuFile;
	}

	private JMenu buildMenuExecute() {
		JMenu menuExecute = new JMenu("EXECUTE");
		JMenuItem menuItemShowAST = new JMenuItem("Show Abstract Syntax Tree");
		menuItemShowAST.setIcon(new ImageIcon(IMAGES_DIR + "tree.png"));
		menuItemShowAST.addActionListener(new ActionListenerAST(frame, textAreaCode, textAreaConsole));
		JMenuItem menuItemCompile = new JMenuItem("Compile");
		menuItemCompile.setIcon(new ImageIcon(IMAGES_DIR + "compile.png"));
		JMenuItem menuItemInterpret = new JMenuItem("Interpret");
		menuItemInterpret.setIcon(new ImageIcon(IMAGES_DIR + "interpret.png"));
		menuExecute.add(menuItemShowAST);
		menuExecute.add(menuItemCompile);
		menuExecute.add(menuItemInterpret);
		return menuExecute;
	}

	private JMenu buildMenuQuestionMark() {
		JMenu menuQuestionMark = new JMenu("?");
		JMenuItem menuItemHelp = new JMenuItem("Help");
		menuItemHelp.setIcon(new ImageIcon(IMAGES_DIR + "help.png"));
		menuItemHelp.addActionListener(new ActionListenerHelp(frame));
		JMenuItem menuItemAbout = new JMenuItem("About");
		menuItemAbout.setIcon(new ImageIcon(IMAGES_DIR + "info.png"));
		menuItemAbout.addActionListener(new ActionListenerAbout(frame));
		menuQuestionMark.add(menuItemHelp);
		menuQuestionMark.add(menuItemAbout);
		return menuQuestionMark;
	}
}

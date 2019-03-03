package org.draxent.funwap.gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import org.draxent.funwap.gui.actionlistener.ActionListenerAST;
import org.draxent.funwap.gui.actionlistener.ActionListenerAbout;
import org.draxent.funwap.gui.actionlistener.ActionListenerClearConsole;
import org.draxent.funwap.gui.actionlistener.ActionListenerExit;
import org.draxent.funwap.gui.actionlistener.ActionListenerHelp;
import org.draxent.funwap.gui.actionlistener.ActionListenerOpen;
import org.draxent.funwap.gui.actionlistener.ActionListenerSave;

public class MenuBarBuilder {
	private static final String IMAGES_DIR = "./src/main/resources/images/";
	private static final int SCALE_IMAGE_ICON = 30;
	
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
		menuItemOpen.setIcon(createScaledImageIcon(IMAGES_DIR + "open.png", SCALE_IMAGE_ICON));
		menuItemOpen.addActionListener(new ActionListenerOpen(frame, textAreaCode, textAreaConsole));
		JMenuItem menuItemSave = new JMenuItem("Save File");
		menuItemSave.addActionListener(new ActionListenerSave(frame, textAreaCode, textAreaConsole));
		menuItemSave.setIcon(createScaledImageIcon(IMAGES_DIR + "save.png", SCALE_IMAGE_ICON));
		JMenuItem menuItemExit = new JMenuItem("Exit");
		JMenuItem menuItemClearConsole = new JMenuItem("Clear Console");
		menuItemClearConsole.addActionListener(new ActionListenerClearConsole(textAreaConsole));
		menuItemClearConsole.setIcon(createScaledImageIcon(IMAGES_DIR + "clear.png", SCALE_IMAGE_ICON));
		menuItemExit.setIcon(createScaledImageIcon(IMAGES_DIR + "close.png", SCALE_IMAGE_ICON));
		menuItemExit.addActionListener(new ActionListenerExit(frame));
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemSave);
		menuFile.add(menuItemClearConsole);
		menuFile.add(menuItemExit);
		return menuFile;
	}

	private JMenu buildMenuExecute() {
		JMenu menuExecute = new JMenu("EXECUTE");
		JMenuItem menuItemShowAST = new JMenuItem("Show Abstract Syntax Tree");
		menuItemShowAST.setIcon(createScaledImageIcon(IMAGES_DIR + "tree.png", SCALE_IMAGE_ICON));
		menuItemShowAST.addActionListener(new ActionListenerAST(frame, textAreaCode, textAreaConsole));
		JMenuItem menuItemCompile = new JMenuItem("Compile");
		menuItemCompile.setIcon(createScaledImageIcon(IMAGES_DIR + "compile.png", SCALE_IMAGE_ICON));
		JMenuItem menuItemInterpret = new JMenuItem("Interpret");
		menuItemInterpret.setIcon(createScaledImageIcon(IMAGES_DIR + "interpret.png", SCALE_IMAGE_ICON));
		menuExecute.add(menuItemShowAST);
		menuExecute.add(menuItemCompile);
		menuExecute.add(menuItemInterpret);
		return menuExecute;
	}

	private JMenu buildMenuQuestionMark() {
		JMenu menuQuestionMark = new JMenu("?");
		JMenuItem menuItemHelp = new JMenuItem("Help");
		menuItemHelp.setIcon(createScaledImageIcon(IMAGES_DIR + "help.png", SCALE_IMAGE_ICON));
		menuItemHelp.addActionListener(new ActionListenerHelp(frame));
		JMenuItem menuItemAbout = new JMenuItem("About");
		menuItemAbout.setIcon(createScaledImageIcon(IMAGES_DIR + "info.png", SCALE_IMAGE_ICON));
		menuItemAbout.addActionListener(new ActionListenerAbout(frame));
		menuQuestionMark.add(menuItemHelp);
		menuQuestionMark.add(menuItemAbout);
		return menuQuestionMark;
	}
	
	private ImageIcon createScaledImageIcon(String path, int scaled){
		ImageIcon imageIcon = new ImageIcon(path);
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(scaled, scaled, java.awt.Image.SCALE_SMOOTH); 
		return new ImageIcon(newimg);  
	}
}

package org.draxent.funwap.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SimpleDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public SimpleDialog(JFrame parent, String title, List<String> text, int width, int height) {
		super(parent, title, true);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(buildContainer(text), BorderLayout.CENTER);

		setSize(width, height);
		setLocationRelativeTo(null);
	}

	private JPanel buildContainer(List<String> text) {
		JPanel container = new JPanel();
		container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		container.setLayout(new BorderLayout());

		Box textBox = buildTextBox(text);
		JPanel panelSouth = buildPanelSouth();
		container.add(textBox, BorderLayout.CENTER);
		container.add(panelSouth, BorderLayout.SOUTH);
		return container;
	}
	
	private Box buildTextBox(List<String> text) {
		Box box = Box.createVerticalBox();
		box.add(Box.createGlue());
		for (String line : text) {
			box.add(new JLabel(line));
		}
		box.add(Box.createGlue());
		return box;
	}
	
	private JPanel buildPanelSouth() {
		JPanel panelSouth = new JPanel();
		JButton buttonOk = new JButton("Ok");
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});
		panelSouth.add(buttonOk);
		return panelSouth;
	}
}

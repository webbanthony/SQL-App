/*
Name: Anthony Webb
Course: CNT 4714 Fall 2023
Assignment title: Project 3 – A Two-tier Client-Server Application
Date: October 29, 2023
Class: ButtonsPanel - Contains all buttons in the GUI.
*/

package sqlapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsPanel extends JPanel {
	Font customFont = new Font("Consolas", Font.BOLD, 13);
	RoundedButton connect, clear, execute, clearResult;
	private String connectText = "Connect";
	private String clearText = "Clear SQL Command";
	private String executeText = "Execute SQL Command";
	private String clearResultText = "Clear SQL Execution Result Window";
	GridBagLayout gridBagLayout = new GridBagLayout();
	SqlCommandPanel commandPanel;

	public ButtonsPanel() {
		setupPanel();
	}

	private void setupPanel() {
		this.setBackground(Color.BLUE);
		this.setForeground(Color.WHITE);
		int width = this.getWidth();
		this.setLayout(gridBagLayout);
		this.setPreferredSize(new Dimension(width, 100));
		this.setMinimumSize(new Dimension(width, 100));
		this.setMaximumSize(new Dimension(width, 100));

		Dimension btnDimension = new Dimension(100, 25);

		connect = new RoundedButton(connectText);
		connect.setMaximumSize(btnDimension);
		connect.setPreferredSize(btnDimension);
		connect.setFont(customFont);

		clear = new RoundedButton(clearText);
		clear.setMaximumSize(btnDimension);
		clear.setPreferredSize(btnDimension);
		clear.setFont(customFont);

		execute = new RoundedButton(executeText);
		execute.setMaximumSize(btnDimension);
		execute.setPreferredSize(btnDimension);
		execute.setFont(customFont);

		clearResult = new RoundedButton(clearResultText);
		clearResult.setFont(customFont);

		this.add(connect, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		this.add(clear, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		this.add(execute, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		this.add(clearResult, new GridBagConstraints(0, 1, 0, 0, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
	}
}

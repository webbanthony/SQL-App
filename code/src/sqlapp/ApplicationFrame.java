/*
Name: Anthony Webb
Course: CNT 4714 Fall 2023
Assignment title: Project 3 – A Two-tier Client-Server Application
Date: October 29, 2023
Class: ApplicationFrame - Contains the main panel.
*/

package sqlapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class ApplicationFrame extends JFrame {
	private GridBagLayout gridBagLayoutAppFrame;
	private MainPanel mainPanel;
	private static ApplicationFrame instance = null;
	public Connection connection;

	private ApplicationFrame() {
		setupGUI();
	}

	public static ApplicationFrame getInsance() {

		if (instance == null) {
			instance = new ApplicationFrame();
		}
		return instance;
	}

	private void setupGUI() {
		this.setBackground(Color.WHITE);
		this.setTitle("SQL Client Application - (ABW CNT 4714 Fall 2023 - Project 3");
		this.setMinimumSize(new Dimension(800, 900));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gridBagLayoutAppFrame = new GridBagLayout();
		mainPanel = new MainPanel();
		this.setLayout(gridBagLayoutAppFrame);
		this.getContentPane().add(mainPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
	}
}

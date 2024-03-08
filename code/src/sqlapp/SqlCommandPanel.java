/*
Name: Anthony Webb
Course: CNT 4714 Fall 2023
Assignment title: Project 3 – A Two-tier Client-Server Application
Date: October 29, 2023
Class: SqlCommandPanel - Area for user to type in commands.
*/

package sqlapp;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class SqlCommandPanel extends JPanel {
	JTextArea commandPanel;
	private GridBagLayout gridBagLayout;
	private String title = "Enter an SQL Command";
	private final int width = 30;
	private final int height = 10;

	public SqlCommandPanel() {
		setupPanel();
	}

	private void setupPanel() {
		commandPanel = new JTextArea(height, width);
		commandPanel.setLineWrap(true);
		commandPanel.setEditable(true);

		gridBagLayout = new GridBagLayout();

		this.setBorder(BorderFactory.createTitledBorder(title));
		this.setLayout(gridBagLayout);

		this.add(commandPanel, new GridBagConstraints(0, 0, 0, 0, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
	}

	public String getCommand() throws SQLException {
		String command = commandPanel.getText();
		commandPanel.setText(command);
		
		if (command.isEmpty()) {
			JOptionPane.showMessageDialog(commandPanel, "Please enter an SQL command.", "No Command Entered",
		            JOptionPane.ERROR_MESSAGE);
			throw new SQLException("No Command Entered.");
		}
		
		return command;
	}
}

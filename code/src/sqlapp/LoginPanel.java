/*
Name: Anthony Webb
Course: CNT 4714 Fall 2023
Assignment title: Project 3 – A Two-tier Client-Server Application
Date: October 29, 2023
Class: LoginPanel - Area where user inputs information. Getter methods to return info to the main panel.
*/

package sqlapp;

import javax.swing.*;

import com.mysql.cj.xdevapi.Statement;

import java.awt.*;
import java.util.Properties;

public class LoginPanel extends JPanel {
	private final int rows = 4;
	private final int columns = 2;
	private final int vGap = 7;
	private final int hGap = 1;

	private String titleLabel = "Connection Details";
	private String userLabel = "Username";
	private String passwordLabel = "Password";
	private String dbPropertiesLabel = "DB URL Properties";
	private String userPropertiesLabel = "User Properties";
	private static String dbProperties1 = "project3.properties";
	private static String dbProperties2 = "bikedb.properties";
	private static String userProperties1 = "client1.properties";
	private static String userProperties2 = "client2.properties";
	private static String userProperties3 = "project3rootuser.properties";
	private String connectionStatusLabel = "Connection Status: ";

	private JLabel dbProperties, userProperties, user, password, connectionStatus;
	private static JComboBox<String> dbPropertiesList;
	private static JComboBox<String> userPropertiesList;
	private JTextField userField;
	private JPasswordField passwordField;
	public JTextArea connectionStatusArea;

	public LoginPanel() {
		setupPanel();
	}

	void setupPanel() {
		Font customFont = new Font("Consolas", Font.BOLD, 13);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		setBorder(BorderFactory.createTitledBorder(titleLabel));

		dbProperties = new JLabel(dbPropertiesLabel);
		dbProperties.setFont(customFont);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		add(dbProperties, gbc);

		dbPropertiesList = new JComboBox<>();
		dbPropertiesList.setFont(customFont);
		dbPropertiesList.setBackground(Color.BLUE);
		dbPropertiesList.setForeground(Color.WHITE);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		dbPropertiesList.addItem(dbProperties1);
		dbPropertiesList.addItem(dbProperties2);
		add(dbPropertiesList, gbc);

		userProperties = new JLabel(userPropertiesLabel);
		userProperties.setFont(customFont);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		add(userProperties, gbc);

		userPropertiesList = new JComboBox<>();
		userPropertiesList.setFont(customFont);
		userPropertiesList.setBackground(Color.BLUE);
		userPropertiesList.setForeground(Color.WHITE);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		userPropertiesList.addItem(userProperties1);
		userPropertiesList.addItem(userProperties2);
		userPropertiesList.addItem(userProperties3);
		add(userPropertiesList, gbc);

		user = new JLabel(userLabel);
		user.setFont(customFont);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		add(user, gbc);

		userField = new JTextField(10);
		userField.setFont(customFont);
		userField.setBackground(Color.BLUE);
		userField.setForeground(Color.WHITE);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(userField, gbc);

		password = new JLabel(passwordLabel);
		password.setFont(customFont);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.EAST;
		add(password, gbc);

		passwordField = new JPasswordField(10);
		passwordField.setFont(customFont);
		passwordField.setBackground(Color.BLUE);
		passwordField.setForeground(Color.WHITE);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(passwordField, gbc);

		connectionStatus = new JLabel(connectionStatusLabel);
		connectionStatus.setFont(customFont);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.EAST;
		add(connectionStatus, gbc);

		connectionStatusArea = new JTextArea();
		connectionStatusArea.setFont(customFont);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		connectionStatusArea.setEditable(false);
		add(connectionStatusArea, gbc);
		connectionStatusArea.setText("Not Connected");
		connectionStatusArea.setBackground(Color.BLUE);
		connectionStatusArea.setForeground(Color.WHITE);
	}

	public static Object getDBPropertyFile() {
		return dbPropertiesList.getSelectedItem();
	} // END Get

	public static Object getUserPropertyFile() {
		return userPropertiesList.getSelectedItem();
	} // END Get

	public String getUsername() {
		return userField.getText();
	} // END Get

	public String getPassword() {
		return passwordField.getText();
	} // END Get

	public void setConnectionStatus(boolean status, Properties properties) {
		if (status) {
			connectionStatusArea.setText("Connected: " + properties.getProperty("MYSQL_DB_URL"));
			connectionStatusArea.setForeground(Color.GREEN);
		} else {
			connectionStatusArea.setText("Connection Error");
			connectionStatusArea.setForeground(Color.RED);
		}
	} // END setConnectionStatus

}// END LoginPanel

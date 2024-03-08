/*
Name: Anthony Webb
Course: CNT 4714 Fall 2023
Assignment title: Project 3 – A Two-tier Client-Server Application
Date: October 29, 2023
Class: MainPanel - Driver code for some functionality and contains GUI elements.
*/
package sqlapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MainPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LoginPanel loginPanel;
	private static SqlCommandPanel sqlCommandPanel;
	private static SqlOutputPanel sqlOutputPanel;
	private static ButtonsPanel buttonsPanel;
	private static GridBagLayout gridBagLayoutMain;
	private static Connection connection1;
	private static Connection connection2;
	private OperationsLog operationsLog;
	private int queryCount = 0;
	private int updateCount = 0;
	String operationLogProperties = "project3app.properties";

	public MainPanel() {
		setupGUI();
		try {
			operationsLog = new OperationsLog(operationLogProperties, connection2); // ensure to hold both connections
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setupGUI() {

		// Initialize all the panels
		loginPanel = new LoginPanel();
		sqlCommandPanel = new SqlCommandPanel();
		gridBagLayoutMain = new GridBagLayout();
		buttonsPanel = new ButtonsPanel();
		sqlOutputPanel = new SqlOutputPanel();

		// Add panels to GUI
		this.setLayout(gridBagLayoutMain);
		this.add(loginPanel, new GridBagConstraints(0, 0, 1, 2, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.add(sqlCommandPanel, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTHEAST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.add(buttonsPanel, new GridBagConstraints(0, 2, 2, 1, 1, 0, GridBagConstraints.SOUTH,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		this.add(sqlOutputPanel, new GridBagConstraints(0, 4, 2, 2, 1, 1, GridBagConstraints.SOUTH,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		buttonsPanel.clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlCommandPanel.commandPanel.setText("");
			}
		});// END Action

		buttonsPanel.connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = loginPanel.getUsername();
				String password = loginPanel.getPassword();
				Object dbFile = LoginPanel.getDBPropertyFile();
				Object userFile = LoginPanel.getUserPropertyFile();

				// DEBUG BLOCK
				// System.out.println("User: " + username + ", Pass: " + password);
				// System.out.println("DB File: " + dbFile + ", User File: " + userFile);

				try {
					connection1 = Load(dbFile, userFile, null);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});// END Action

		buttonsPanel.clearResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (sqlOutputPanel.tableModel.getRowCount() > 0) {
					sqlOutputPanel.tableModel.removeRow(0);
				}

				while (sqlOutputPanel.tableModel.getColumnCount() > 0) {
					sqlOutputPanel.tableModel.setColumnCount(0);
				}
			}
		});// END Action

		buttonsPanel.execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String command = sqlCommandPanel.getCommand();
					if (command != null) {
						Statement statement = connection1.createStatement();

						boolean isUpdate = false;

						System.out.println("isUpdate status before:" + isUpdate);

						if (command.trim().toLowerCase().startsWith("insert")
								|| command.trim().toLowerCase().startsWith("update")
								|| command.trim().toLowerCase().startsWith("delete")) {
							isUpdate = true;
						}

						System.out.println("isUpdate status after:" + isUpdate);

						System.out.println(command);

						if (isUpdate) {
							// This is an update statement.
							int rowsAffected = statement.executeUpdate(command);
							updateCount++;
							operationsLog.logUpdate(loginPanel.getUsername(), updateCount); // It's an update
							// Provide feedback to the user about the number of rows affected.
							JOptionPane.showMessageDialog(MainPanel.this, rowsAffected + " rows affected.",
									"Update Successful", JOptionPane.INFORMATION_MESSAGE);
						} else {
							// This is a query.
							ResultSet resultSet = statement.executeQuery(command);
							queryCount++;
							operationsLog.logQuery(loginPanel.getUsername(), queryCount);
							if (!resultSet.isBeforeFirst()) {
								JOptionPane.showMessageDialog(MainPanel.this, "No results found for the query.",
										"No Results", JOptionPane.INFORMATION_MESSAGE);
							} else {
								while (sqlOutputPanel.tableModel.getRowCount() > 0) {
									sqlOutputPanel.tableModel.removeRow(0);
								}
							}

							java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
							int columnCount = metaData.getColumnCount();
							String[] columnNames = new String[columnCount];

							for (int i = 1; i <= columnCount; i++) {
								columnNames[i - 1] = metaData.getColumnName(i);
							}

							sqlOutputPanel.tableModel.setColumnIdentifiers(columnNames);

							while (resultSet.next()) {
								Object[] rowData = new Object[columnCount];
								for (int i = 1; i <= columnCount; i++) {
									rowData[i - 1] = resultSet.getObject(i);
								}
								sqlOutputPanel.tableModel.addRow(rowData);
							}
						}
						statement.close();
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(MainPanel.this,
							"Command denied to user: " + loginPanel.getUsername() + "\n" + e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}// END setupGUI

	public static Connection Load(Object dbFile, Object userFile, String command)
			throws SQLException, ClassNotFoundException {
		String password = loginPanel.getPassword();
		String username = loginPanel.getUsername();

		boolean status = false;
		Connection connection = null;

		Properties dbProperties = new Properties();
		Properties userProperties = new Properties();
		FileInputStream dbFileIn = null;
		FileInputStream userFileIn = null;
		MysqlDataSource dataSource = null;

		try {
			dbFileIn = new FileInputStream((String) dbFile);
			userFileIn = new FileInputStream((String) userFile);
			dbProperties.load(dbFileIn);
			userProperties.load(userFileIn);
			dataSource = new MysqlDataSource();
			dataSource.setURL(dbProperties.getProperty("MYSQL_DB_URL"));
			dataSource.setUser(userProperties.getProperty("MYSQL_DB_USERNAME"));
			dataSource.setPassword(userProperties.getProperty("MYSQL_DB_PASSWORD"));
			connection = dataSource.getConnection();
			status = true;

			System.out.println("Username and Password from userProperties in Load: "
					+ userProperties.getProperty("MYSQL_DB_USERNAME") + " "
					+ userProperties.getProperty("MYSQL_DB_PASSWORD"));

			if (!loginPanel.getUsername().isEmpty() && !loginPanel.getPassword().isEmpty()) {
				if (!username.equals(userProperties.getProperty("MYSQL_DB_USERNAME"))
						|| !password.equals(userProperties.getProperty("MYSQL_DB_PASSWORD"))) {
					JOptionPane.showMessageDialog(loginPanel, "Username/Password is incorrect.", "Login Error",
							JOptionPane.ERROR_MESSAGE);
					throw new SQLException("Access denied. Invalid username/password.");
				}
			} else if (loginPanel.getUsername().isEmpty()) {
				JOptionPane.showMessageDialog(loginPanel, "Please enter a username.", "Login Error",
						JOptionPane.ERROR_MESSAGE);
				throw new SQLException("Access denied. Invalid username/password.");
			} else if (loginPanel.getPassword().isEmpty()) {
				JOptionPane.showMessageDialog(loginPanel, "Please enter a password.", "Login Error",
						JOptionPane.ERROR_MESSAGE);
				throw new SQLException("Access denied. Invalid username/password.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new SQLException("Error loading database properties.");
		}

		DatabaseMetaData dbMetaData = connection.getMetaData();

		// DEBUG BLOCK
		System.out.println("Database connected");
		System.out.println("JDBC Driver name " + dbMetaData.getDriverName());
		System.out.println("JDBC Driver version " + dbMetaData.getDriverVersion());
		System.out.println("Driver Major version " + dbMetaData.getDriverMajorVersion());
		System.out.println("Driver Minor version " + dbMetaData.getDriverMinorVersion());
		System.out.println();
		// END DEBUG BLOCK

		loginPanel.setConnectionStatus(status, dbProperties);

		if (command != null) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(command);
		}
		return connection;
	}// END Load
}// END MainPanel

/*
Name: Anthony Webb
Course: CNT 4714 Fall 2023
Assignment title: Project 3 – A Two-tier Client-Server Application
Date: October 29, 2023
Class: OperationsLog - Maintains database connection and log user activity in a seperate DB.
*/

package sqlapp;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

public class OperationsLog {
	private Connection connection2;

	public OperationsLog(String dbPropertiesFile, Connection connection2) throws SQLException {
		connection2 = establishConnection("project3app.properties");
	}

	private Connection establishConnection(String dbProperties) throws SQLException {
		Properties properties = new Properties();
		FileInputStream fileIn = null;
		String dbFile = dbProperties;
		MysqlDataSource dataSource = null;

		try {
			fileIn = new FileInputStream((String) dbFile);
			properties.load(fileIn);
			dataSource = new MysqlDataSource();
			dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
			dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
			dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
			connection2 = dataSource.getConnection();

			// DEBUG
			System.out.println("Operations Log Succesfully Connected: " + properties.getProperty("MYSQL_DB_URL"));

		} catch (IOException e) {
			e.printStackTrace();
			throw new SQLException("Error loading database properties.");
		}

		return connection2;

	}

	public void logQuery(String username, int numQueries) {
		String updateQuery = "UPDATE operationscount SET num_queries = ? WHERE login_username = ?";
		try (PreparedStatement preparedStatement = connection2.prepareStatement(updateQuery)) {
			preparedStatement.setInt(1, numQueries);
			preparedStatement.setString(2, username);

			int rowsUpdated = preparedStatement.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println("Query activity logged for user: " + username);
			} else {
				System.err.println("Failed to log query activity for user: " + username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error logging query activity for user: " + username);
		}
	}

	public void logUpdate(String username, int numUpdates) {
		String updateQuery = "UPDATE operationscount SET num_updates = ? WHERE login_username = ?";
		try (PreparedStatement preparedStatement = connection2.prepareStatement(updateQuery)) {
			preparedStatement.setInt(1, numUpdates);
			preparedStatement.setString(2, username);

			int rowsUpdated = preparedStatement.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println("Update activity logged for user: " + username);
			} else {
				System.err.println("Failed to log update activity for user: " + username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error logging update activity for user: " + username);
		}
	}
}

/*
Name: Anthony Webb
Course: CNT 4714 Fall 2023
Assignment title: Project 3 – A Two-tier Client-Server Application
Date: October 29, 2023
Class: Main - Driver code for project.
*/

package sqlapp;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationFrame app = ApplicationFrame.getInsance();
		app.setVisible(true);
	}

}

/*
Name: Anthony Webb
Course: CNT 4714 Fall 2023
Assignment title: Project 3 – A Two-tier Client-Server Application
Date: October 29, 2023
Class: SqlOutputPanel - Area for SQL output.
*/

package sqlapp;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SqlOutputPanel extends JPanel {
	
	DefaultTableModel tableModel;
	JTable resultTable;
	JScrollPane scrollPane;
	private String titleLabel = "SQL Execution Result Window";
	GridBagLayout gridBagLayout;
	
	
	public SqlOutputPanel() {
		setupPanel();
	}
	
	private void setupPanel() {
		this.setBackground(Color.WHITE);
		gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);
		setBorder(BorderFactory.createTitledBorder(titleLabel));
		tableModel = new DefaultTableModel();
		resultTable = new JTable(tableModel);
		scrollPane = new JScrollPane(resultTable);
		this.add(scrollPane, new GridBagConstraints(0, 0, 0, 0, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
	}
}

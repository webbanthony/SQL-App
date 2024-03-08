/*
Name: Anthony Webb
Course: CNT 4714 Fall 2023
Assignment title: Project 3 – A Two-tier Client-Server Application
Date: October 29, 2023
Class: RoundedButton - Button styling for ButtonsPanel
*/

package sqlapp;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

class RoundedButton extends JButton {
	public RoundedButton(String text) {
		super(text);
		setContentAreaFilled(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(getBackground());
		}
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
		super.paintComponent(g);
	}

	@Override
	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
	}
}

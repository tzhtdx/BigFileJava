package my;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class StatusBar extends JPanel
{
	private JLabel statusLabel;
	
	public StatusBar()
	{
		// TODO Auto-generated constructor stub
		initComponents();
	}
	
	private void initComponents()
	{
		statusLabel = new JLabel();
		setLayout(new MigLayout());
		setBackground(new Color(0, 122, 204));
		
		statusLabel.setText("×´Ì¬À¸£¨Î´Êµ×°£©");
		statusLabel.setForeground(Color.white);
		add(statusLabel);
		
	}
	
	
}

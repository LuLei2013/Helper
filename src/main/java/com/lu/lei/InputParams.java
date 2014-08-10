package com.lu.lei;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author lulei lei.a.lu@ericsson.com
 * @version Jul 18, 2014 10:37:02 AM
 */
@SuppressWarnings("serial")
public class InputParams extends JFrame implements ActionListener {
	// the numbers of cows
	private int cowNum;
	// the numbers of rows
	private int rowNum;
	Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 30);

	private JTextField cow = new JTextField();
	private JTextField row = new JTextField();
	private JLabel inputCowJLabel = new JLabel("cowsNum");
	private JLabel inputRowJLabel = new JLabel("rowsNum");
	private JButton okButton = new JButton("OK");
	private JButton resetButton = new JButton("RESET");
	public boolean isFinised = false;

	public InputParams() {
		super("Input the rows and cows");
		setSize(600, 400);
		cow.setFont(font);
		row.setFont(font);
		inputCowJLabel.setFont(font);
		inputRowJLabel.setFont(font);
		okButton.setFont(font);
		resetButton.setFont(font);

		getContentPane().setLayout(null);
		getContentPane().add(cow);
		getContentPane().add(row);
		getContentPane().add(inputCowJLabel);
		getContentPane().add(inputRowJLabel);
		getContentPane().add(okButton);
		getContentPane().add(resetButton);
		cow.setBounds(350, 80, 100, 50);
		row.setBounds(350, 150, 100, 50);
		inputCowJLabel.setBounds(100, 80, 200, 50);
		inputRowJLabel.setBounds(100, 150, 200, 50);
		resetButton.setBounds(250, 300, 150, 50);
		okButton.setBounds(400, 300, 120, 50);
		resetButton.addActionListener(this);
		okButton.addActionListener(this);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public int getCowNum() {
		return cowNum;
	}

	private void setCowNum(int cownum) {
		cowNum = cownum;
	}

	public int getRowNum() {
		return rowNum;
	}

	private void setRowNum(int rownum) {
		rowNum = rownum;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == okButton) {
			// verify the data and set it to the local variable.
			if (cow.getText().equals("") || row.getText().equals("")) {
				shakeScreen();
				return;
			} else {
				setCowNum(Integer.valueOf(cow.getText()));
				setRowNum(Integer.valueOf(row.getText()));
				setVisible(false);
				isFinised = true;
			}
		} else if (event.getSource() == resetButton) {
			// reset the data
			setCowNum(0);
			setRowNum(0);
			cow.setText("");
			row.setText("");
		}
	}

	private void shakeScreen() {
		new Thread() {
			public void run() {
				int i = 10;
				while (i-- > 0) {
					if (i % 2 == 0)
						setLocation(getLocation().x + 10, getLocation().y);
					else
						setLocation(getLocation().x - 10, getLocation().y);
					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

}

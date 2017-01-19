package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CreateTagButtonListener implements ActionListener {
	public JFrame createTagFrame;
	JFrame parentsFrame;
	JButton changeTagButton;
	public CreateTagButtonListener (JFrame changeTagFrame, JButton btnChangeTag){
		parentsFrame = changeTagFrame;
		changeTagButton = btnChangeTag;
	}
	public void actionPerformed(ActionEvent e) {
		JFrame createTagFrame = new JFrame();
		
		createTagFrame.setTitle("PhotoRenamer");
		createTagFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		createTagFrame.setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		createTagFrame.setContentPane(contentPane);

		JTextField textField = new JTextField();
		textField.setEditable(true);
		contentPane.add(textField, BorderLayout.CENTER);
		textField.setColumns(4);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JButton confirmButton = new JButton("Confirm");
		confirmButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		confirmButton.setBackground(Color.PINK);
		ActionListener confirm = new ConfirmButtonListener(textField, createTagFrame, parentsFrame, changeTagButton);
		confirmButton.addActionListener(confirm);
		panel.add(confirmButton);

		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);

		JButton cancellButton1 = new JButton("Cancel");
		cancellButton1.setFont(new Font("Tahoma", Font.BOLD, 20));
		cancellButton1.setBackground(Color.PINK);
		ActionListener cancell = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createTagFrame.dispose();
			}
		};
		cancellButton1.addActionListener(cancell);
		panel.add(cancellButton1);

		JLabel lblTypeInThe = new JLabel("Type in the new tag");
		contentPane.add(lblTypeInThe, BorderLayout.NORTH);
		createTagFrame.pack();
		createTagFrame.setVisible(true);

	}

}

package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import Backend.*;

public class ConfirmButtonListener implements ActionListener {
	
	JTextField textfield;
	JFrame tagFrame;
	JFrame parentFrame;
	JButton changeTagButton;
	public ConfirmButtonListener(JTextField textField, JFrame createTagFrame, JFrame ParentFrame, JButton btnChangeTag) {
		textfield = textField;
		tagFrame = createTagFrame;
		parentFrame = ParentFrame;
		changeTagButton = btnChangeTag;
	}
	public void actionPerformed(ActionEvent e) {
		String text = textfield.getText();
		TagCollection.addTag(new Tag(text));
		// close the current window
		tagFrame.dispose();
		// close its parent's window
		parentFrame.dispose();
		// use doClick to simulate a button click and create a new window
		// with all the changes
		changeTagButton.doClick();
		

		
	}

}

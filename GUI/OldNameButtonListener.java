package GUI;

import Backend.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class OldNameButtonListener implements ActionListener {

	public JFrame oldnameFrame;
	public Image currentImage;
	public JList<String> imagelistShow;
	public File directory;

	public OldNameButtonListener(Image currentImage, JList<String> imageList, File dir) {
		this.directory = dir;
		this.currentImage = currentImage;
		this.oldnameFrame = new JFrame();
		this.imagelistShow = imageList;
	}

	public void actionPerformed(ActionEvent arg0) {
		oldnameFrame.setTitle("PhotoRenamer");
		// oldnameFrame.setAlwaysOnTop(true);
		oldnameFrame.setLocationRelativeTo(null);
		oldnameFrame.setVisible(true);
		oldnameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		oldnameFrame.setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		oldnameFrame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JList<String> namelist = new JList<String>();
		contentPane.add(new JScrollPane(namelist), BorderLayout.CENTER);
		DefaultListModel<String> listModel = new DefaultListModel<String>();

		for (String n : currentImage.allNames) {
			listModel.addElement(n);

		}
		namelist.setModel(listModel);
		Font font2 = new Font("Monospaced", Font.BOLD, 20);
		namelist.setFont(font2);
		ListSelectionListener nameSeclet = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		};
		namelist.addListSelectionListener(nameSeclet);

		JPanel buttonpanel = new JPanel();
		contentPane.add(buttonpanel, BorderLayout.SOUTH);

		JButton btnChangeBack = new JButton("Change Back");
		btnChangeBack.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnChangeBack.setBackground(Color.PINK);
		buttonpanel.add(btnChangeBack);

		ActionListener changeBack = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentImage.goBackToName(namelist.getSelectedValue());

				ImageFileExplorer ife = new ImageFileExplorer(directory);
				DefaultListModel<String> listModel = new DefaultListModel<>();
				for (File f : ife.listOfImages) {
					listModel.addElement(f.getAbsolutePath());
				}
				imagelistShow.setModel(listModel);
				// we also want to close the current frame after we go back to
				// the previous name
				oldnameFrame.dispose();
			}
		};
		btnChangeBack.addActionListener(changeBack);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnCancel.setBackground(Color.PINK);
		buttonpanel.add(btnCancel);
		ActionListener cancell = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oldnameFrame.dispose();
			}
		};
		btnCancel.addActionListener(cancell);
		oldnameFrame.pack();
	}

}

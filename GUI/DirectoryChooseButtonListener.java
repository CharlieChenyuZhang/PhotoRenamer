package GUI;

import Backend.*;
import photo_renamer.PhotoRenamer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class DirectoryChooseButtonListener implements ActionListener {
	private JFrame directoryFrame;
	private JFileChooser fileChooser;
	private JLabel directoryLabel;
	private JList<String> imagefileList;
	private JFrame filechooseFrame;
	private JSplitPane splitPane;

	/**
	 * An action listener for window dirFrame, using fileChooser to choose a
	 * file.
	 *
	 * @param dirFrame
	 *            the main window
	 * @param fileChooser
	 *            the file chooser to use
	 * @return
	 */
	public DirectoryChooseButtonListener(JFrame dirFrame, JFileChooser fileChooser, JLabel directoryLabel) {
		this.directoryFrame = dirFrame;
		this.fileChooser = fileChooser;
		this.directoryLabel = directoryLabel;
		this.filechooseFrame = new JFrame();
		this.imagefileList = new JList<String>();
	}

	public void actionPerformed(ActionEvent e) {
		int returnVal = fileChooser.showOpenDialog(directoryFrame.getContentPane());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();

			if (file.exists()) {
				// set up the split pane
				splitPane = new JSplitPane();
				filechooseFrame.setTitle("PhotoRenamer");
				filechooseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				filechooseFrame.setResizable(true);
				filechooseFrame.setPreferredSize(new Dimension(450, 300));
				JPanel contentPane = new JPanel();

				filechooseFrame.getContentPane().add(splitPane);
				JPanel imagePane = new JPanel();
				// configure the splitPane
				splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
				splitPane.setDividerLocation(150);

				splitPane.setLeftComponent(contentPane);
				splitPane.setRightComponent(imagePane);

				JLabel lblNewLabel = new JLabel("Choose an image file:");
				JLabel imageNewLabel = new JLabel("Image Preview");
				lblNewLabel.setFont(new Font("STXinwei", Font.PLAIN, 18));
				imageNewLabel.setFont(new Font("STXinwei", Font.PLAIN, 18));

				contentPane.add(lblNewLabel, BorderLayout.NORTH);
				imagePane.add(imageNewLabel, BorderLayout.NORTH);

				JPanel panel = new JPanel();
				JPanel imagePanel = new JPanel();

				contentPane.add(panel);
				imagePane.add(imagePanel);
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));

				ImageFileExplorer ife = new ImageFileExplorer(file);
				DefaultListModel<String> listModel = new DefaultListModel<>();
				for (File f : ife.listOfImages) {
					listModel.addElement(f.getAbsolutePath());
				}
				imagefileList.setModel(listModel);
				ImageIcon icon = new ImageIcon();
				JLabel imageLabel = new JLabel(null, icon, JLabel.CENTER);
				imagePanel.add(imageLabel);

				ListSelectionListener imageListSeclet = new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent event) {
						if (imagefileList.getValueIsAdjusting()) {

							BufferedImage img = null;
							try {
								img = ImageIO.read(new File(imagefileList.getSelectedValue()));
								icon.setImage(img);
								filechooseFrame.pack();
								// update the frame
								imagePanel.revalidate();
								imagePanel.repaint();

							} catch (IOException e) {
							}

						}
					}
				};
				imagefileList.addListSelectionListener(imageListSeclet);

				JScrollPane fileListPanel = new JScrollPane(imagefileList);
				panel.add(fileListPanel);

				JButton openFileButton = new JButton("Open File");
				openFileButton.setBackground(Color.PINK);
				openFileButton.setForeground(Color.BLACK);
				openFileButton.setFont(new Font("Tahoma", Font.BOLD, 21));
				filechooseFrame.add(openFileButton, BorderLayout.SOUTH);
				
				
				
				
				
				
				
				ActionListener openFileActionListener = new OpenFileButtonListener(imagefileList, file);
				openFileButton.addActionListener(openFileActionListener);

				
				
				
				filechooseFrame.pack();
				filechooseFrame.setVisible(true);

				filechooseFrame.addWindowListener(new WindowListener() {

					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowClosing(WindowEvent e) {
						// we want to set free the parent resources
						// and reopen parent's window
						filechooseFrame.dispose();
						//directoryFrame.dispose();
						PhotoRenamer window = new PhotoRenamer();
						window.frame.setVisible(true);
					}

					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub

					}

				});
			} else {
				directoryLabel.setText("No Path Selected");
			}
		}
	}
}
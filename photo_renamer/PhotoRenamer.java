package photo_renamer;

import Backend.*;
import GUI.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PhotoRenamer {

	public JFrame frame;
	private JTextArea textArea;

	/**
	 * Create the application.
	 */
	public PhotoRenamer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		// frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// define a panel to store the text area and initialize it
		JPanel textPanel = new JPanel();

		textArea = new JTextArea(20, 20);
		textArea.setFont(new Font("serif", Font.PLAIN, 15));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		textPanel.add(textArea, BorderLayout.AFTER_LINE_ENDS);
		// frame.getContentPane().add(textPanel);
		textArea.setEditable(false);
		// define the text Area and add this component to textPanel

		// add a scroll pane to the textArea
		JScrollPane areaScrollPane = new JScrollPane(textArea);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 250));
		frame.getContentPane().add(areaScrollPane);

		// define a panel to store the button
		JPanel panel = new JPanel();
		// set up the panel, add the panel to the frame
		frame.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		// A JFileChooser used to choose the directory
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// set the title of the frame
		frame.setTitle("PhotoRenamer");
		JLabel lblChooseADirectory = new JLabel("Choose a directory " + "OR read the naming history log");
		frame.getContentPane().add(lblChooseADirectory, BorderLayout.NORTH);
		// set up the button
		JButton btnChooseDirectory = new JButton("Choose A Directory");
		btnChooseDirectory.setBackground(Color.PINK);
		btnChooseDirectory.setForeground(Color.BLACK);
		btnChooseDirectory.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(btnChooseDirectory);

		// add the glue
		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);

		// set up the button to read the log
		JButton logButton = new JButton("Read Log File");
		logButton.setBackground(Color.PINK);
		logButton.setForeground(Color.BLACK);
		logButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.add(logButton);

		// define read log button listener
		ActionListener readLog = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// we always clean the previous content
				textArea.setText(null);

				// check whether the file exist or not
				// if doesn't exist, we write a line which say "No Log Founded,
				// please choose a directory to get started"
				// O.W. read the log text file and append each line to textField
				File imageLog = new File("./ImageNameLogs.txt");
				if (!imageLog.exists()) {

					textArea.append("You haven't changed the name of any images yet.");
					textArea.update(textArea.getGraphics());
				} else {
					// open up the file, read it line by line and append each
					// line
					// to the text filed
					BufferedReader br;
					try {
						br = new BufferedReader(new FileReader("ImageNameLogs.txt"));
						String line;
						try {
							line = br.readLine();
							while (line != null) {
								textArea.append(line);
								line = br.readLine();
							} // update the graphic
							textArea.update(textArea.getGraphics());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				}
			}
		};

		ActionListener dcbListener = new DirectoryChooseButtonListener(frame, fileChooser, lblChooseADirectory);
		btnChooseDirectory.addActionListener(dcbListener);

		// add the read log action listener to the read log file button
		logButton.addActionListener(readLog);

		// we also want to serialize both ImageManager and TagCollectioni
		// when the user click on the close button to close the window
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// check ImagManager
				// check if serializeImageManager.ser exists, if yes, we
				// deserialize it
				File imageManagerFile = new File("./serializeImageManager.ser");
				if (imageManagerFile.exists()) {
					try {
						ImageManager.deserializeImage();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				// check TagCollection
				// we also check whether the file
				// exist or not, if yes, we deserialize it create an instange of
				// tagCollection,
				File tagCollectionFile = new File("./serializeTagCollectioni.ser");
				if (tagCollectionFile.exists()) {
					try {
						TagCollection.deserializeTagCollection();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// serialize the data
				// serialize ImageManager
				try {
					ImageManager.serializeImage();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// serialize TagCollection
				try {
					TagCollection.serializeTagCollection();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

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

		// pack all the components
		frame.pack();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PhotoRenamer window = new PhotoRenamer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}

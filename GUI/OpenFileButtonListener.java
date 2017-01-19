package GUI;

import Backend.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class OpenFileButtonListener implements ActionListener {

	public JFrame imagePresentFrame;
	public JList<String> imageList;
	public Image currentImage;
	public File directory;
	public boolean findImage = false;

	public OpenFileButtonListener(JList<String> imagefileList, File file) {
		this.directory = file;
		this.imageList = imagefileList;
		this.imagePresentFrame = new JFrame();
	}

	public void actionPerformed(ActionEvent arg) {
		String path = imageList.getSelectedValue();
		
		
		
		if (path != null) {
			imagePresentFrame.setTitle("PhotoRenamer");
			imagePresentFrame.setLocationRelativeTo(null);
			// imagePresentFrame.setAlwaysOnTop(true);
			imagePresentFrame.setVisible(true);
			imagePresentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			imagePresentFrame.setBounds(100, 100, 450, 300);
			JPanel contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			imagePresentFrame.setContentPane(contentPane);

			File f = new File(path);

			
			findImage = ImageManager.checkExistance(f);

			System.out.println(findImage);

			if (!findImage) {
				try {
					currentImage = new Image(f);
				} catch (SecurityException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				boolean stop = false;
				for (Image each : ImageManager.getImages()) {
					if ((each.allNames.get(each.allNames.size() - 1) == f.getName()) && !stop) {
						currentImage = each;
						System.out.println(currentImage);
						stop = true;
					}

				}
			}


			
			
			
			
			JPanel buttonpanel = new JPanel();
			buttonpanel.setBorder(null);
			contentPane.add(buttonpanel, BorderLayout.SOUTH);

			JButton btnOldName = new JButton("Old Name");
			btnOldName.setBackground(Color.PINK);
			btnOldName.setFont(new Font("Tahoma", Font.BOLD, 18));
			buttonpanel.add(btnOldName);
			ActionListener oldnameActionListener = new OldNameButtonListener(currentImage, imageList, directory);
			btnOldName.addActionListener(oldnameActionListener);

			JButton btnChangeTag = new JButton("Change Tag");
			btnChangeTag.setBackground(Color.PINK);
			btnChangeTag.setFont(new Font("Tahoma", Font.BOLD, 18));
			buttonpanel.add(btnChangeTag);
			ActionListener changetagActionListener = new ChangeTagButtonListener(currentImage, btnChangeTag, imageList,
					directory);
			btnChangeTag.addActionListener(changetagActionListener);
			JTextArea tagArea = new JTextArea();
			tagArea.setEditable(false);
			tagArea.setToolTipText("Tags:");
			tagArea.setBackground(new Color(255, 255, 255));
			contentPane.add(new JScrollPane(tagArea), BorderLayout.EAST);
			
			
			
			
			System.out.println(currentImage);
			
			
			
			
			for (Tag t : currentImage.tags) {
				tagArea.append((String) t.getContent() + "\n");
			}

			JPanel imagepanel = new JPanel();
			contentPane.add(imagepanel, BorderLayout.CENTER);
			BufferedImage img = null;
			try {
				img = ImageIO.read(f);
			} catch (IOException e) {
			}
			ImageIcon icon = new ImageIcon(img);
			JLabel imageLabel = new JLabel(null, icon, JLabel.CENTER);
			imagepanel.add(imageLabel);

			imagePresentFrame.pack();

		}
	}
}

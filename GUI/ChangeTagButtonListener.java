package GUI;

import Backend.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChangeTagButtonListener implements ActionListener {
	public JFrame changeTagFrame;
	public Image currentImage;
	public JButton changeTagButton;
	public JList<String> imagelistShow;
	public File directory;

	public ChangeTagButtonListener(Image image, JButton btnChangeTag, JList<String> imageList, File dir) {
		this.currentImage = image;
		this.changeTagFrame = new JFrame();
		this.changeTagButton = btnChangeTag;
		this.imagelistShow = imageList;
		this.directory = dir;

	}

	public void actionPerformed(ActionEvent e) {
		changeTagFrame.setTitle("PhotoRenamer");
		// changeTagFrame.setAlwaysOnTop(true);
		changeTagFrame.setVisible(true);
		changeTagFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		changeTagFrame.setBounds(100, 100, 676, 583);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		changeTagFrame.setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		// left
		JPanel TagCollectionP = new JPanel();
		contentPane.add(TagCollectionP);
		TagCollectionP.setLayout(new BoxLayout(TagCollectionP, BoxLayout.Y_AXIS));

		JLabel label = new JLabel("TAGS COLLECTION");
		label.setVerticalAlignment(SwingConstants.TOP);
		TagCollectionP.add(label);
		label.setHorizontalAlignment(SwingConstants.LEFT);

		JList<String> TagCollectionlist = new JList<String>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();

		for (Tag t : TagCollection.getCurrentlyExistingTags()) {
			listModel.addElement(t.getContent());
		}
		TagCollectionlist.setModel(listModel);
		Font font2 = new Font("Monospaced", Font.BOLD, 20);
		TagCollectionlist.setFont(font2);
		ListSelectionListener tagCollectionSeclet = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		};
		TagCollectionlist.addListSelectionListener(tagCollectionSeclet);
		JScrollPane TagCollectionPanel = new JScrollPane(TagCollectionlist);
		TagCollectionP.add(TagCollectionPanel);

		Component verticalGlue = Box.createVerticalGlue();
		TagCollectionP.add(verticalGlue);

		JPanel buttonpanel = new JPanel();
		TagCollectionP.add(buttonpanel);
		buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.X_AXIS));
		//
		JButton createTagButton = new JButton("Create Tag");
		createTagButton.setBackground(Color.PINK);
		createTagButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		ActionListener createTag = new CreateTagButtonListener(changeTagFrame, changeTagButton);
		createTagButton.addActionListener(createTag);
		buttonpanel.add(createTagButton);

		Component rigidArea = Box.createRigidArea(new Dimension(10, 20));
		buttonpanel.add(rigidArea);
		//
		JButton deleteTagButton = new JButton("Delete Tag");
		deleteTagButton.setBackground(Color.PINK);
		deleteTagButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		deleteTagButton.setHorizontalAlignment(SwingConstants.TRAILING);
		ActionListener deleteTag = new DeleteTagButtonListener(TagCollectionlist);
		deleteTagButton.addActionListener(deleteTag);
		buttonpanel.add(deleteTagButton);

		// middle
		JPanel addpanel = new JPanel();
		addpanel.setBackground(new Color(240, 240, 240));
		contentPane.add(addpanel);
		addpanel.setLayout(new BoxLayout(addpanel, BoxLayout.PAGE_AXIS));
		//
		JButton addButton = new JButton(">>> Choose a tag and click HERE >>>");
		addButton.setBackground(Color.PINK);
		addButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		addpanel.add(addButton);

		// right
		JPanel imageTag = new JPanel();
		contentPane.add(imageTag);
		imageTag.setLayout(new BoxLayout(imageTag, BoxLayout.PAGE_AXIS));

		JLabel lblImageTags = new JLabel("IMAGE TAGS");
		imageTag.add(lblImageTags);

		JList<String> imageTagList = new JList<String>();
		DefaultListModel<String> listModel1 = new DefaultListModel<String>();

		for (Tag t : currentImage.tags) {
			listModel1.addElement(t.getContent());
		}
		imageTagList.setModel(listModel1);
		imageTagList.setFont(font2);
		ListSelectionListener imageTagSeclet = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		};
		imageTagList.addListSelectionListener(imageTagSeclet);
		JScrollPane ImageTagPanel = new JScrollPane(imageTagList);
		imageTag.add(ImageTagPanel);
		Component verticalGlue_1 = Box.createVerticalGlue();
		imageTag.add(verticalGlue_1);

		JPanel button2panel = new JPanel();
		imageTag.add(button2panel);
		button2panel.setLayout(new BoxLayout(button2panel, BoxLayout.X_AXIS));
		// add action listener to add image tag
		ActionListener addTag = new addTagButtonListener(imageTagList, TagCollectionlist, currentImage, imagelistShow,
				directory);
		addButton.addActionListener(addTag);
		//
		JButton DeleteIamgeTagButton = new JButton("Delete Iamge Tag");
		DeleteIamgeTagButton.setBackground(Color.PINK);
		DeleteIamgeTagButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		ActionListener deleteImageTag = new DeleteImageTagButtonListener(imageTagList, currentImage, changeTagFrame,
				changeTagButton, imagelistShow, directory);
		DeleteIamgeTagButton.addActionListener(deleteImageTag);
		buttonpanel.add(DeleteIamgeTagButton);
		button2panel.add(DeleteIamgeTagButton);
		changeTagFrame.pack();
	}

}

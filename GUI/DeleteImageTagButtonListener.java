package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import Backend.*;

public class DeleteImageTagButtonListener implements ActionListener {

	public JList<String> imageTags;
	public Image currentImage;
	public JFrame currentFrame;
	public JButton buttonToReopenCurrentWindow;
	public JList<String> imagelistShow;
	public File directory;

	public DeleteImageTagButtonListener(JList<String> imageTagList, Image image, JFrame changeTagFrame,
			JButton changeTagButton, JList<String> imagelist, File dir) {
		this.directory = dir;
		this.imagelistShow = imagelist;
		this.imageTags = imageTagList;
		this.currentImage = image;
		this.currentFrame = changeTagFrame;
		this.buttonToReopenCurrentWindow = changeTagButton;
	}

	public void actionPerformed(ActionEvent e) {
		String text = imageTags.getSelectedValue();
		// create a instant parameter representing all tags
		@SuppressWarnings("unchecked")
		ArrayList<Tag> tagsTemporary = (ArrayList<Tag>) currentImage.tags.clone();
		for (Tag t : tagsTemporary) {
			if (t.getContent().equals(text)) {
				// we also want to change the list accordingly and refresh the
				// list
				// close the current window so that we can refresh it
				// use doClick to simulate a button click and create a new
				// window
				// with all the changes

				currentImage.deleteTags(t);
	
				ImageManager.add(currentImage);
				ImageFileExplorer ife = new ImageFileExplorer(directory);
				DefaultListModel<String> listModel = new DefaultListModel<>();
				for (File f : ife.listOfImages) {
					listModel.addElement(f.getAbsolutePath());
				}
				imagelistShow.setModel(listModel);

				// refresh the window
				currentFrame.dispose();
				buttonToReopenCurrentWindow.doClick();
				


			}
		}

	}

}

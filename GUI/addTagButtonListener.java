package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Backend.Image;
import Backend.ImageFileExplorer;
import Backend.ImageManager;
import Backend.Tag;
import Backend.TagCollection;

public class addTagButtonListener implements ActionListener {

	public JList<String> tagCollectionlist;
	public JList<String> imageTags;
	public  Image currentImage;
	public JList<String> imagelistShow;
	public File directory;
	public addTagButtonListener(JList<String> imageTagJlist, JList<String> tagCollectionlist, Image image, JList<String> imagelist, File dir) {
		this.imageTags = imageTagJlist;
		this.tagCollectionlist = tagCollectionlist;
		this.currentImage = image;		
		this.imagelistShow = imagelist;
		this.directory = dir;
	}

	public void actionPerformed(ActionEvent e) {
		String text = tagCollectionlist.getSelectedValue();
		for (Tag t:TagCollection.getCurrentlyExistingTags()){
			if (t.getContent().equals(text)){
				currentImage.selectTags(t);
			}
		}
		
		DefaultListModel<String> listModel3 = new DefaultListModel<String>(); 
		for (Tag t: currentImage.tags){
			listModel3.addElement(t.getContent());
		}
		imageTags.setModel(listModel3);	
		
		ImageFileExplorer ife = new ImageFileExplorer(directory);
		DefaultListModel<String> listModel = new DefaultListModel<>();
		for (File f : ife.listOfImages) {
			listModel.addElement(f.getAbsolutePath());
		}
		imagelistShow.setModel(listModel);
	}

}

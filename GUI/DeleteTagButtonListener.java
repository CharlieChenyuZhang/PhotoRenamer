package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Backend.Tag;
import Backend.TagCollection;

public class DeleteTagButtonListener implements ActionListener {
	
	public JList<String>  tagCollectionlist;
	public DeleteTagButtonListener(JList<String> tagCollectionList) {
		tagCollectionlist = tagCollectionList;
	}
	public void actionPerformed(ActionEvent e) {
		String deletetagcontent = tagCollectionlist.getSelectedValue();
		TagCollection.deleteTag(deletetagcontent);
		DefaultListModel<String> listModel2 = new DefaultListModel<String>(); 
		for (Tag t: TagCollection.getCurrentlyExistingTags()){
			listModel2.addElement(t.getContent());
		}
		tagCollectionlist.setModel(listModel2);
	}

}

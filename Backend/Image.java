package Backend;

import java.io.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * DESIGN PATTERN USED FOR THIS CLASS: Observer Pattern
 * whenever someone change the Image class, we update ImageManager class automaticall
 * CLASSES INVLOVED: Image (Observable) and Image Manager (Observer)
 * EXPLANATION: whenever when initialize a new Image class,
 * we call the ImageManager observer and store this new created Image class
 * to the ImageManager
 */

/**
 * a class representing an image file
 *
 */
public class Image extends Observable implements Serializable {
	
	/**
	 * Set the default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the Logger for all images
	 */
	private static final Logger logger = Logger.getLogger(Image.class.getName());

	/**
	 * Create a fileHandler for all images
	 */
	private static FileHandler fh = null;

	/**
	 * The image file we are working on.
	 */
	public File imageFile;

	/**
	 * The extension of the image file.
	 */
	private String extension;

	/**
	 * The tag of the image.
	 */
	public ArrayList<Tag> tags = new ArrayList<Tag>();

	/**
	 * All the names the image has had.
	 */
	public ArrayList<String> allNames = new ArrayList<String>();

	/**
	 * The constructor of Image Class, create a new Image
	 * 
	 * @param image
	 *            the file we want to work on
	 * @throws IOException
	 * @throws SecurityException
	 */
	public Image(File image) throws SecurityException, IOException {
		// contract the logger
		if (fh == null) {
			fh = new FileHandler("ImageNameLogs.txt", true);
			fh.setLevel(Level.ALL);
			fh.setFormatter(new SimpleFormatter());
			logger.setLevel(Level.ALL);
			logger.addHandler(fh);
		}
		// initialize the data
		this.imageFile = image;
		String name = image.getName();
		this.allNames.add(name);
		int dot = name.lastIndexOf(".");
		this.extension = name.substring(dot);
		// set the observer
		ImageManager o = new ImageManager();
		this.addObserver(o);
		// set the change
		setChanged();
		notifyObservers(this);
		
		
		
		System.out.println("A new Image file was created");

	}

	/**
	 * Add the tag the user chooses to tags, change the use time of that tag and
	 * also change the name of the image file; keep track of the name using
	 * allNames.
	 * 
	 * @param tag
	 *            the tag the user select for this image
	 */
	public void selectTags(Tag tag) {
		// check if the image already has the tag
		if (!this.tags.contains(tag)) {
			// remove the old copy in the Image Manager first
			ImageManager.delete(this);
			
			
			System.out.println("You deleted the old copy now the manager is -> ");
			System.out.println(ImageManager.getImages());
			
			
			this.tags.add(tag);
			String oldname = this.imageFile.getName();

			// change the file name
			String path = imageFile.getAbsolutePath();
			String newpath = path.substring(0, path.lastIndexOf(".")) + tag.getContent() + extension;
			File newfile = new File(newpath);

			// if successfully changed, log and add to the allNames
			Boolean success = this.imageFile.renameTo(newfile);
			if (success) {
				imageFile = newfile;
				allNames.add(this.imageFile.getName());
				Image.logger.info("old name:" + oldname + "  new name:" + this.imageFile.getName());
			}
			// set the observer
			ImageManager o = new ImageManager();
			// update the Image Manager
			//this.addObserver(o);
			// set the change
			setChanged();
			notifyObservers(this);
			
			
			System.out.println("after adding to manager current Manager");
			System.out.println(ImageManager.getImages());
		}
	}

	/**
	 * Delete the tag the user chooses from tags, change the use time of the tag
	 * and also change the name of the image file, keep track of the name using
	 * allNames.
	 * 
	 * @param tag
	 *            the tag the user want to delete from this image
	 */
	public void deleteTags(Tag tag) {
		// remove the old copy in the Image Manager first
		ImageManager.delete(this);
		
		
		this.tags.remove(tag);
		// change the name
		String oldname = imageFile.getName();

		String path = imageFile.getAbsolutePath();
		path = path.substring(0, path.indexOf("@"));
		for (Tag t : tags) {
			path = path + t.getContent();
		}
		path = path + extension;
		File newfile = new File(path);

		boolean success = this.imageFile.renameTo(newfile);
		if (success) {
			imageFile = newfile;
			allNames.add(this.imageFile.getName());

			// set the observer
			ImageManager o = new ImageManager();
			// update the Image Manager
			//this.addObserver(o);
			// set the change
			setChanged();
			notifyObservers(this);

			Image.logger.info("old name:" + oldname + "; new name: " + this.imageFile.getName());
		}

	}

	/**
	 * Change the current name to the old name the user chooses. First change
	 * file name back Second change all the tags off the image this method will
	 * also affect tagsForImage in TagCollection Class
	 * 
	 * @param oldname
	 *            the name the user want to change back
	 */

	public void goBackToName(String oldname) {
		// remove the old copy in the Image Manager first
		ImageManager.delete(this);
		
		
		// change file name to the old name user choose.
		String oldname1 = imageFile.getName();
		String path = imageFile.getAbsolutePath();
		path = path.substring(0, path.lastIndexOf("\\") + 1) + oldname;
		File newfile = new File(path);

		boolean success = this.imageFile.renameTo(newfile);
		if (success) {
			allNames.add(oldname);
			imageFile = newfile;
			Image.logger.info("old name:" + oldname1 + "  new name:" + oldname);
			// remove all the tags of the image
			tags.clear();
			// add new tags for the file
			if (oldname.contains("@")) {
				int dot = oldname.lastIndexOf(".");
				int a = oldname.indexOf("@");
				String name = oldname;
				if (dot > 0) {
					name = oldname.substring(a, dot);
				}
				String[] tagList = name.split("(?=@)");
				for (String i : tagList) {
					boolean find = false;
					// check whether we have this tag in the collection
					for (Tag each : TagCollection.getCurrentlyExistingTags()) {
						if (each.getContent() == i) {
							find = true;
							// add this tag to tags
							this.tags.add(each);
						}
					}
					if (!find) {
						// if we cannot find this tag in currently existing tag
						// we create a new tag
						Tag newtag = new Tag(i.substring(1));
						// change set of tags for this image file
						this.tags.add(newtag);
					}

					this.allNames.add(oldname);

				}

				// set the observer
				ImageManager o = new ImageManager();
				// update the Image Manager
				//this.addObserver(o);
				// set the change
				setChanged();
				notifyObservers(this);

			}
		}

	}

	public String getExtension() {
		return extension;
	}

}

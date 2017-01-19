package Backend;

import java.io.*;
import java.util.*;

/**
 * Singleton Design Pattern store all the tags which are currently being used
 */
public class TagCollection implements Serializable {

	/**
	 * Set the default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The tags which are currently being used by images
	 */
	private static ArrayList<Tag> currentlyExistingTags = new ArrayList<Tag>();

	/**
	 * @return a list of tags representing the tags which are currently being
	 *         used
	 */
	public static ArrayList<Tag> getCurrentlyExistingTags() {
		return currentlyExistingTags;
	}

	/**
	 * add a new tag to currently existing array list if this tag does not
	 * previously exist
	 * 
	 * @param newTag
	 *            represent the tag which the user type into the GUI
	 */
	public static void addTag(Tag newTag) {
		// check if already having the same tag
		boolean start = false;
		for (Tag tag : currentlyExistingTags) {
			if (tag.getContent().equals(newTag.getContent())) {
				start = true;
			}
		}
		// if not in collection, create new tag and add to tagsForUser
		if (!start) {
			currentlyExistingTags.add(newTag);
		}
	}

	/**
	 * delete the tag in the currently existing tags
	 * 
	 * @param tag
	 *            represent the tag which does not exist in the image file
	 */
	public static void deleteTag(String name) {
		Iterator<Tag> iter = currentlyExistingTags.iterator();
		while (iter.hasNext()) {
			Tag t = iter.next();
			if (t.getContent().equals(name)) {
				iter.remove();
			}
		}
	}

	/**
	 * Serialize the TagCollection class
	 * 
	 * @param filePath
	 *            is the Path of the file that we want to store the serialized
	 *            bytes info
	 * @param tagCollection
	 *            the TagCollection class you want to serialize ons
	 * @throws IOException
	 *             throw an exception if the filePath does not exist
	 */
	public static void serializeTagCollection() throws IOException {
		String filePath = "./serializeTagCollectioni.ser";
		// build up the file
		FileOutputStream fileOut = new FileOutputStream(filePath);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		// first create a mock instance, then serialize this instance
		// serialize the TagCollection class
		out.writeObject(currentlyExistingTags);
		// close the file
		out.close();
		fileOut.close();
	}

	/**
	 * deserialize the TagCollection class
	 * 
	 * @param filePath
	 *            is the Path of the file that we want to store the serialized
	 *            bytes info
	 * @throws IOException
	 *             throw an exception if the filePath does not exist
	 */
	@SuppressWarnings("unchecked")
	public static void deserializeTagCollection() throws IOException {
		try {
			String filePath = "./serializeTagCollectioni.ser";
			// deserialize the date from the file
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			// update TagCollection
			TagCollection.currentlyExistingTags = (ArrayList<Tag>) in.readObject();
			// close the file
			in.close();
			fileIn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException i) {
			i.printStackTrace();
		}

	}
}

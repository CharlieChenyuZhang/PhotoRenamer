package Backend;

import java.io.*;
import java.util.*;

/**
 * DESIGN PATTERN USED FOR THIS CLASS: Observer Pattern
 * CLASSES INVLOVED: Image (Observable) and Image Manager (Observer)
 * EXPLANATION: whenever when initialize a new Image class,
 * we call the ImageManager observer and store this new created Image class
 * to the ImageManager
 */

/**
 * We use this class to manage all Image classes ever created
 *
 */
public class ImageManager implements Observer {
	/**
	 * A mapping of the first name of the image to Images.
	 */
	private static ArrayList<Image> images = new ArrayList<Image>();

	/**
	 * @return the map containing all the images
	 */
	public static ArrayList<Image> getImages() {

		return ImageManager.images;
	}

	/**
	 * Adds record to this ImageManager.
	 * 
	 * @param record
	 *            a record to be added.
	 */
	public static void add(Image record) {

		try {

			ImageManager.images.add(record);

		} catch (NullPointerException e) {
		}
	}

	public static void delete(Image record) {
		// delete the record in the ImageManager
		ImageManager.images.remove(record);
	}

	/**
	 * Serialize the map
	 * 
	 * @param filePath
	 *            is the Path of the file that we want to store the serialized
	 *            bytes info
	 * @throws IOException
	 *             throw an exception if the filePath does not exist
	 */
	public static void serializeImage() throws IOException {
		// default the filePath to be the current directory
		String filePath = "./serializeImageManager.ser";
		// build up the file
		FileOutputStream fileOut = new FileOutputStream(filePath);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		// serialize the map
		out.writeObject(images);
		// close the file
		out.close();
		fileOut.close();
	}

	/**
	 * Deserialize the map
	 * 
	 * @param filePath
	 *            is the Path of the file that we want to store the serialized
	 *            bytes info
	 * @throws IOException
	 *             throw an exception if the filePath does not exist
	 */

	@SuppressWarnings("unchecked")
	public static void deserializeImage() throws IOException {
		try {
			// default location is the current folder
			String filePath = "./serializeImageManager.ser";
			// deserialize the date from the file
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			// update images map
			// create an instance variable to store the data from the file
			ArrayList<Image> instantImages = (ArrayList<Image>) in.readObject();

			// we only up-date the image if we get something
			if (instantImages != null) {
				images = (ArrayList<Image>) instantImages;
			}
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

	public static boolean checkExistance(File file) throws NullPointerException {
		boolean result = false;
		System.out.println(images);
		for (Image each : ImageManager.images) {
				if ((each.allNames.get(each.allNames.size() - 1).equals(file.getName()))) {
					
					result = true;
			}
		}
		return result;

	}

	@Override
	public void update(Observable obserableImage, Object arg) {
		if (arg instanceof Image) {
			// add the Image to the manager
			Image i = (Image) arg;
			// ImageManager.add(i);
			ImageManager.images.add(i);
			System.out.println("here is where you added one");
		}
	}

}

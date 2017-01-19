package Backend;

import java.io.*;
import java.util.*;
import javax.activation.MimetypesFileTypeMap;

/**
 * A class to explorer all the image file under the folder
 *
 */
public class ImageFileExplorer {
	/**
	 * a list of all images files which can be found under the directory
	 */
	public List<File> listOfImages = new ArrayList<File>();

	/**
	 * representing the File of the current directory
	 */
	public File directory;

	/**
	 * initialize the directory and also the listOfImages
	 * 
	 * @param dirName
	 *            representing the File of the current directory
	 */
	public ImageFileExplorer(File directory) {
		this.directory = directory;
		this.findImages(directory);
	}

	/**
	 * get the ArrayList of all images Files
	 * 
	 * @return listofImages
	 */
	public List<File> getListOfImages() {
		return listOfImages;
	}

	/**
	 * @return the File of the current directory
	 */
	public File getDirectory() {
		return directory;
	}

	/**
	 * this function check whether a file is an image file or not
	 * 
	 * @param file
	 *            a File representing the file that we are gonna check
	 * @return a boolean representing whether a file is an image file or not
	 */
	public boolean isImage(File file) {
		// define the file-type we want to look for
		MimetypesFileTypeMap mftp = new MimetypesFileTypeMap();
		mftp.addMimeTypes("image png jpg jpeg");
		// using mimetype to check whether a file is an image file or not
		String mimetype = mftp.getContentType(file);
		// get the info of the type
		String type = mimetype.split("/")[0];
		// return true if the type is image, otherwise return false
		if (type.equals("image"))
			return true;
		else
			return false;
	}

	/**
	 * find all the image files under the file directory and store those image
	 * files to the listOfImages variable
	 * 
	 * @param file
	 *            representing the directory that we selected
	 */
	public void findImages(File file) {
		// create an array of files
		File[] listFiles = file.listFiles();
		// loop through each files under this directory
		for (File f : listFiles) {
			// traverse each file, if it is a directory, recursively call
			// findImages
			if (f.isDirectory()) {
				findImages(f);
			} else if (isImage(f)) {
				// if it is an image file, add this file to the listOfImages
				if (! f.getName().contains("._")) {
					listOfImages.add(f);
				}
				
			}
		}

	}

}

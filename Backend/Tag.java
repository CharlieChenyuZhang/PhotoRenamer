package Backend;

import java.io.Serializable;

/**
 * This class represent a Tag
 *
 */
public class Tag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * content representing the name of the tag (with the symbol "@")
	 */
	private String content;
	/*
	 * useTimes representing how many time the user used this tag initialize the
	 * number to be one when you created the tag
	 */

	/**
	 * initialize the Tag class, call the observer and add the tag into the
	 * collection the first time creating a tag, we only create a new tag when
	 * users use this tag at the first time and use this tag to rename the image
	 * 
	 * @param content
	 *            representing the name of the tag, contain "@" symbol
	 */
	public Tag(String name) {
		this.content = "@" + name;
	}

	/**
	 * get and return the content of the tag
	 * 
	 * @return content
	 */
	public String getContent() {
		return content;
	}
}

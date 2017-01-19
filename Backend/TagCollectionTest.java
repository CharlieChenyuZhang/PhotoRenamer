package Backend;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * Java unit Test for Tag Collection class
 *
 */
public class TagCollectionTest extends TestCase {
	/**
	 * initialize a instance of tag collection
	 */
	TagCollection tagCollection = new TagCollection();

	/*
	 * set up the tag collection class
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	protected void setUp() throws Exception {
	}

	/*
	 * @throws java.lang.Exception
	 */
	@After
	protected void tearDown() throws Exception {
		// store a copy of the tag in currentlyExistingTags
		ArrayList<Tag> storage = new ArrayList<Tag>();
		for (Tag each : TagCollection.getCurrentlyExistingTags()) {
			storage.add(each);
		}
		// clean up the tag collection
		for (Tag each : storage) {
			TagCollection.deleteTag(each.getContent());
		}
	}

	/**
	 * Test method for
	 * {@link photo_renamer.TagCollectioin#addTag(photo_renamer.Tag)}
	 */
	@Test
	public void testAddAExistedTag() {
		// create a new tag, add that into tagCollection
		// try to add the same tag into tagCollection
		ArrayList<Tag> expected = new ArrayList<Tag>();
		Tag cat = new Tag("cat");
		TagCollection.addTag(cat);
		TagCollection.addTag(cat);
		expected.add(cat);
		Assert.assertEquals(expected, TagCollection.getCurrentlyExistingTags());
	}

	/**
	 * Test method for
	 * {@link photo_renamer.TagCollectioin#addTag(photo_renamer.Tag)}
	 */
	@Test
	public void testAddNewTag() {
		// create a new tag, add that into tagCollection
		// try to add another same tag into tagCollection
		ArrayList<Tag> expected = new ArrayList<Tag>();
		Tag cat = new Tag("cat");
		Tag mouse = new Tag("mouse");
		TagCollection.addTag(cat);
		TagCollection.addTag(mouse);
		expected.add(cat);
		expected.add(mouse);
		Assert.assertEquals(expected, TagCollection.getCurrentlyExistingTags());
	}

	/**
	 * Test method for
	 * {@link photo_renamer.TagCollectioin#deleteTag(photo_renamer.Tag)}
	 */
	@Test
	public void testDeleteTagFromEmpty() {
		// try to delete the tag from the empty array list
		ArrayList<Tag> expected = new ArrayList<Tag>();
		Tag cat = new Tag("cat");
		TagCollection.deleteTag(cat.getContent());
		Assert.assertEquals(expected, TagCollection.getCurrentlyExistingTags());
	}

	/**
	 * Test method for
	 * {@link photo_renamer.TagCollectioin#deleteTag(photo_renamer.Tag)}
	 */
	@Test
	public void testDeleteNewTag() {
		// add some tags to the array list
		ArrayList<Tag> expected = new ArrayList<Tag>();
		Tag cat = new Tag("cat");
		Tag mouse = new Tag("mouse");
		Tag charlie = new Tag("Charlie");
		TagCollection.addTag(cat);
		TagCollection.addTag(mouse);
		expected.add(cat);
		expected.add(mouse);
		// delete the tag
		TagCollection.deleteTag(charlie.getContent());
		Assert.assertEquals(expected, TagCollection.getCurrentlyExistingTags());
	}

	/**
	 * Test method for
	 * {@link photo_renamer.TagCollectioin#deleteTag(photo_renamer.Tag)}
	 */
	@Test
	public void testDeleteExistedTag() {
		// add some tags to the array list
		ArrayList<Tag> expected = new ArrayList<Tag>();
		Tag cat = new Tag("cat");
		Tag mouse = new Tag("mouse");
		TagCollection.addTag(cat);
		TagCollection.addTag(mouse);
		expected.add(mouse);
		// delete the tag
		TagCollection.deleteTag(cat.getContent());
		Assert.assertEquals(expected, TagCollection.getCurrentlyExistingTags());
	}

	/**
	 * Test method for
	 * {@link photo_renamer.TagCollectioin#getCurrentlyExistingTags()}}.
	 */
	@Test
	public void testEmptyGetCurrentlyExistingTags() {
		ArrayList<Tag> expected = new ArrayList<Tag>();
		ArrayList<Tag> actual = TagCollection.getCurrentlyExistingTags();
		assertEquals("Error getting empty currently existing tags", expected, actual);
	}

	/**
	 * Test the method getCurrentlyExistingTags when it is not empty
	 */
	@Test
	public void testNonEmptyGetCurrentlyExistingTags() {
		// add some tags to the array list
		ArrayList<Tag> expected = new ArrayList<Tag>();
		Tag cat = new Tag("cat");
		Tag mouse = new Tag("mouse");
		TagCollection.addTag(cat);
		TagCollection.addTag(mouse);
		expected.add(cat);
		expected.add(mouse);
		Assert.assertEquals(expected, TagCollection.getCurrentlyExistingTags());
	}

	/**
	 * Test method for
	 * {@link photo_renamer.TagCollectioin#serializeTagCollection(photo_renamer.TagCollection)}
	 * AND
	 * {@link photo_renamer.TagCollectioin#serializeTagCollection(photo_renamer.TagCollection)}
	 * @throws IOException 
	 */
	@Test
	public void testSerializationAndDeserialization() throws IOException {
		// add some tags to the array list
		ArrayList<Tag> expected = new ArrayList<Tag>();
		Tag mouse = new Tag("mouse");
		TagCollection.addTag(mouse);
		expected.add(mouse);
		// use the path group_0714/a3/src/photo_renamer
		TagCollection.serializeTagCollection();
		TagCollection.deserializeTagCollection();
		Assert.assertEquals(expected.get(0).getContent(), TagCollection.getCurrentlyExistingTags().get(0).getContent());
	}

}

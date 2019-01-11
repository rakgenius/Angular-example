package assignment;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import Model.Item;

public class MainActivityTest {
	@Test
	@SuppressWarnings("unchecked")
	public void testEmptyAlbum() {
		MainActivity main = new MainActivity();
		Response response = main.findAlbums("");
		List<Item> result = (List<Item>) response.getData();
		assertEquals("PASS", 0,result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEmptyBooks() {
		MainActivity main = new MainActivity();
		Response response = main.findBooks("");
		List<Item> result = (List<Item>) response.getData();
		assertEquals("PASS", 0, result.size());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testEmptyAlbumAndBooks() {
		MainActivity main = new MainActivity();
		Response response = main.findByName("");
		List<Item> result = (List<Item>) response.getData();
		assertEquals(0,result.size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testNullAlbum() {
		String search = null;
		MainActivity main = new MainActivity();
		Response response = main.findAlbums(search);
		List<Item> result = (List<Item>) response.getData();
		assertEquals("PASS", 0,result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testNullBooks() {
		String search = null;
		MainActivity main = new MainActivity();
		Response response = main.findBooks(search);
		List<Item> result = (List<Item>) response.getData();
		assertEquals("PASS", 0, result.size());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testNullAlbumAndBooks() {
		String search = null;
		MainActivity main = new MainActivity();
		Response response = main.findByName(search);
		List<Item> result = (List<Item>) response.getData();
		assertEquals(0,result.size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testMultipleSpaceAlbum() {
		String search = "micheal     jackson";
		MainActivity.limitQuery = 10;
		MainActivity main = new MainActivity();
		Response response = main.findAlbums(search);
		List<Item> result = (List<Item>) response.getData();
		assertEquals("PASS", 10,result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMultipleSpaceBooks() {
		String search = "micheal     jackson";
		MainActivity.limitQuery = 10;
		MainActivity main = new MainActivity();
		Response response = main.findBooks(search);
		List<Item> result = (List<Item>) response.getData();
		assertEquals("PASS", 10, result.size());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMultipleSpaceAlbumAndBooks() {
		String search = "micheal     jackson";
		MainActivity.limitQuery = 10;
		MainActivity main = new MainActivity();
		Response response = main.findByName(search);
		List<Item> result = (List<Item>) response.getData();
		assertEquals("PASS", 20,result.size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testBasicAlbum() {
		MainActivity main = new MainActivity();
		Response response = main.findAlbums("eminem");
		List<Item> result = (List<Item>) response.getData();
		assertEquals("PASS", 5,result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBasicBooks() {
		MainActivity main = new MainActivity();
		Response response = main.findBooks("eminem");
		List<Item> result = (List<Item>) response.getData();
		assertEquals("PASS", 5, result.size());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAlbumNotFound() {
		MainActivity main = new MainActivity();
		Response response = main.findAlbums("dsfsdfdsfdsf");
		List<Item> result = (List<Item>) response.getData();
		assertEquals(0,result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBooksNotFound() {
		MainActivity main = new MainActivity();
		Response response = main.findBooks("dsfsdfdsfdsf");
		List<Item> result = (List<Item>) response.getData();
		assertEquals(0, result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAlbumsAndBooksNotFound() {
		MainActivity main = new MainActivity();
		Response response = main.findByName("dsfsdfdsfdsf");
		List<Item> result = (List<Item>) response.getData();
		assertEquals(0, result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAlbumsLimit() {
		MainActivity main = new MainActivity();
		MainActivity.limitQuery = 10;
		Response response = main.findAlbums("eminem");
		List<Item> result = (List<Item>) response.getData();
		assertEquals(10, result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBooksLimit() {
		MainActivity main = new MainActivity();
		MainActivity.limitQuery = 10;
		Response response = main.findBooks("eminem");
		List<Item> result = (List<Item>) response.getData();
		assertEquals(10, result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAlbumsAndBooksLimit() {
		MainActivity main = new MainActivity();
		MainActivity.limitQuery = 10;
		Response response = main.findByName("eminem");
		List<Item> result = (List<Item>) response.getData();
		assertEquals(20, result.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAlbumsLimit100() {
		MainActivity main = new MainActivity();
		MainActivity.limitQuery = 100;
		Response response = main.findAlbums("eminem");
		List<Item> result = (List<Item>) response.getData();
		assertEquals(55, result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBooksLimit100() {
		MainActivity main = new MainActivity();
		MainActivity.limitQuery = 100;
		Response response = main.findBooks("eminem");
		List<Item> result = (List<Item>) response.getData();
		assertEquals(40, result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAlbumsAndBooksLimit100() {
		MainActivity main = new MainActivity();
		MainActivity.limitQuery = 100;
		Response response = main.findByName("eminem");
		List<Item> result = (List<Item>) response.getData();
		assertEquals(95, result.size());
	}
	
	@Test
	public void testInvalidLimitQuery() {
		MainActivity main = new MainActivity();
		Response response = main.setLimitQuery("abcdefg");
		assertEquals("PASS", "Fail", response.getStatus());
	}
	
	@Test
	public void testHugeLimitQuery() {
		MainActivity main = new MainActivity();
		Response response = main.setLimitQuery("99999999");
		assertEquals("PASS", "Fail", response.getStatus());
	}
}

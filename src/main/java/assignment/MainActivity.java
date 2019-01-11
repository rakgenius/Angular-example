package assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Model.Item;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootApplication
@RestController
public class MainActivity implements Runnable {
	// collections to store the final result
	List<Item> albums = null;
	List<Item> books = null;
	
	// making it static so that its visible to all threads
	static List<Item> response = new ArrayList<Item>();
	static int count = 0;
	String searchName = "";
	
	// Variable to set the limit of query result. By default its 5
	static int limitQuery = 5;
	
	// Item type we need to search
	public enum itemType {
		ALBUM,
		BOOK,
		ALL
	};

	// reference to thread
	Thread t;
	
	MainActivity() {
	}
	
	MainActivity(String name) {
		this.searchName = name;
	}
	
	// handler to store the limit query parameter
	@RequestMapping("/limitquery")
	public Response setLimitQuery(@RequestParam("limit") String limit) {
		String status = "Done";
		if (limit != null && !limit.equalsIgnoreCase("")) {
			if (limit.matches("^\\d{0,3}$")) {
				limitQuery = Integer.parseInt(limit);
			} else {
				status = "Fail";
			}
		} else {
			status = "Fail";
		}

		limitQuery = (limitQuery == 0) ? 5 : limitQuery;
		System.out.println("Limit set to " + limitQuery);
		return new Response(status, response);
	}

	// Handler to fetch the searched albums
	@RequestMapping("/findalbums")
	public Response findAlbums(@RequestParam("name") String name) {
		String result = "Done";
		response.clear();
		if (name == null || name.equalsIgnoreCase(""))
			return new Response("Fail", response);

		List<Item> response = getAll(name, itemType.ALBUM);

		if (response == null || response.size() == 0)
			result = "Fail";

		return new Response(result, response);
	}

	// Handler to fetch the searched books
	@RequestMapping("/findbooks")
	public Response findBooks(@RequestParam("name") String name) {
		String result = "Done";
		response.clear();
		if (name == null || name.equalsIgnoreCase(""))
			return new Response("Fail", response);

		List<Item> response = getAll(name, itemType.BOOK);

		if (response == null || response.size() == 0)
			result = "Fail";

		return new Response(result, response);
	}

	// Handler to search both albums and books
	@RequestMapping("/findall")
	public Response findByName(@RequestParam("name") String name) {
		String result = "Done";
		searchName = name;
		response.clear();

		if (name == null || name.equalsIgnoreCase(""))
			return new Response("Fail", response);

		MainActivity thread1 = new MainActivity(searchName);
		MainActivity thread2 = new MainActivity(searchName);

		// start two threads
		thread1.startThread();
		thread2.startThread();

		try {
			thread1.t.join();
			thread2.t.join();
		} catch (InterruptedException e) {
			System.out.println("why this exception");
		}

		if (response == null || response.size() == 0)
			result = "Fail";

		return new Response(result, response);
	}

	public void startThread() {
		t = new Thread(this);
		t.start();
	}
	
	public void run() {
		synchronized(this) {
			count++;
		}
		
		if (count % 2 == 0) {
			// get the albums
			AlbumController album = new AlbumController();
			albums = album.getAlbums(searchName, limitQuery);
			if (albums != null)
				response.addAll(albums);
		} else {
			// get the books
			BooksController book = new BooksController();
			books = book.getBooks(searchName, limitQuery);
			if (books != null)
				response.addAll(books);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(MainActivity.class, args);

	}

	public List<Item> getAll(String name, itemType type) {
		AlbumController album = new AlbumController();
		BooksController book = new BooksController();
		List<Item> result = new ArrayList<Item>();
		switch (type) {
			case ALBUM:
				albums = album.getAlbums(name, limitQuery);
				if (albums != null)
					result.addAll(albums);
				
				displayLog();
				break;
			
			case BOOK:
				books = book.getBooks(name, limitQuery);
				if (books != null)
					result.addAll(books);
				
				displayLog();
				break;
				
			case ALL:
				// This part wont be executed as its replaced by threads but still keeping it in case we dont want threads
				albums = album.getAlbums(name, limitQuery);
				books = book.getBooks(name, limitQuery);
				if (albums != null)
					result.addAll(albums);
				
				if (books != null)
					result.addAll(books);
				break;
		}

		
		return result;
	}
	
	public void displayLog() {
		if (albums != null) {
			for (Item item : albums) {
				System.out.println("Album Title is " + item.getTitle() + " Album Author is " + item.getName() +
						" Type is " + item.getType());
			}
		}
		
		if (books != null) {
			for (Item item : books) {
				System.out.println("Book Title is " + item.getTitle() + " Book Author is " + item.getName() +
						" Type is " + item.getType());
			}
		}
	}
	
}

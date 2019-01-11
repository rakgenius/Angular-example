package Model;

public class Item {
	private String title;
	private String name;
	private String type;
	public Item(String title, String name, String type) {
		super();
		this.title = title;
		this.name = name;
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}

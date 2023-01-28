/**
 * Represents an Item of generic type
 */
public abstract class Item {
	private String title;
	private double price;
	
	/**
	 * @param title Title of item
	 * @param price Price of item
	 */
	Item (String title, double price) {
		this.title = title;
		this.price = price;
	}
	
	public String getTitle () {
		return title;
	}
	
	public void setTitle (String title) {
		this.title = title;
	}
	
	public double getPrice () {
		return price;
	}
	
	public void setPrice (double price) {
		this.price = price;
	}
	
}

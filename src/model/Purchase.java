package model;

public class Purchase {
	
	String date;
	String totalPrice;
	int numberOfItems;
	String username;
	
	public Purchase(String date, String totalPrice, int numberOfItems, String username) {
		super();
		this.date = date;
		this.totalPrice = totalPrice;
		this.numberOfItems = numberOfItems;
		this.username = username;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getNumberOfItems() {
		return numberOfItems;
	}
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}

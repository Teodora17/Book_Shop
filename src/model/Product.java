package model;

public class Product {
	private int id;
	private String name;
	private String type;
	private String description;
	private double unitPrice;
	private int quantity;
	
	public Product(int id, String name, String type, String description, double unitPrice, int quantity) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}
	
	public Product(String name, String type, String description, double unitPrice, int quantity) {
		this(0, name, type, description, unitPrice,quantity);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", type=" + type + ", description=" + description
				+ ", unitPrice=" + unitPrice + "]";
	}
	
	
}
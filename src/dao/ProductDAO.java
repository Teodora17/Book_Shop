package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import model.*;
import utils.DBConnection;

public class ProductDAO {
	
	private static ProductDAO instance;
	
	public static ProductDAO getInstance() {
		
		if (instance == null) 
		{
			instance = new ProductDAO();
		}
		
		return instance;
		
	}
	
	public List<Product> retrieveProducts() throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		ArrayList<Product> products = new ArrayList<Product>();
		
		Statement instr = con.createStatement();
		String sql = "SELECT * FROM products";
		ResultSet rs = instr.executeQuery(sql);
		
		while(rs.next()) {
			Product p = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6));
			products.add(p);
		}
		
		return products;
	}

	public boolean addProduct(String name, String type, String description, double price, int quantity) throws SQLException, ClassNotFoundException {
		Connection con = DBConnection.getConnection();
		
		String sql="INSERT INTO products (name," + 
				"type," + 
				"description," +
				"price," +
				"quantity" +
				") values (?, ? , ?, ?, ?);";
		
		
		
		PreparedStatement statement = con.prepareStatement(sql);
		statement.setString(1, name);
		statement.setString(2, type);
		statement.setString(3, description);
		statement.setDouble(4, price);
		statement.setInt(5, quantity);
		statement.execute();		
		
		return true;
	}
	
	public boolean addPurchase(String date, String totalPrice, int numberOfItems, String username) throws SQLException, ClassNotFoundException {
		Connection con = DBConnection.getConnection();
		
		String sql2 = "SELECT id FROM infoperson WHERE username='" + username + "'";
		Statement statement2 = con.createStatement();
		ResultSet rs = statement2.executeQuery(sql2);
		String id="";
		while(rs.next()) {
			id = rs.getString(1);
		}
		
		
		String sql="INSERT INTO purchases (date," + 
				"totalPrice," + 
				"numberItems," + 
				"username" +
				") values (?, ? , ?, ?);";
		
		
		
		PreparedStatement statement = con.prepareStatement(sql);
		statement.setString(1, date);
		statement.setString(2, totalPrice);
		statement.setInt(3, numberOfItems);
		statement.setString(4, username);
		statement.execute();		
		
		return true;
	}
	
	public List<Purchase> retrievePurchases() throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		
		Statement instr = con.createStatement();
		String sql = "SELECT * FROM purchases";
		ResultSet rs = instr.executeQuery(sql);
		
		while(rs.next()) {
			Purchase p = new Purchase(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
			purchases.add(p);
		}
		
		ArrayList<Purchase> reversePurchases = new ArrayList<Purchase>();
		
		for (int i = purchases.size() - 1; i >= 0; i--) { 
			  
            // Append the elements in reverse order 
            reversePurchases.add(purchases.get(i)); 
        }
		
		return reversePurchases;
	}
	
	public boolean setQuantity(String name, int quantity) throws ClassNotFoundException, SQLException
	{
		Connection con = DBConnection.getConnection();
		
		String sql="UPDATE products SET quantity = '" + quantity + "' WHERE name='" + name + "';";

		PreparedStatement statement = con.prepareStatement(sql);
		statement.execute();
		
		return true;	
	}
	
}
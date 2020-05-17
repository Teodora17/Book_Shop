package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import model.*;


@WebListener("/ContextListener")
public class ContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		ProductDAO pp = new ProductDAO();
		List<Product> products = new ArrayList<Product>();
		List<Purchase> purchases = new ArrayList<Purchase>();
		
		try 
		{
			products = pp.retrieveProducts();
			purchases = pp.retrievePurchases();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sc.setAttribute("products", products);
		sc.setAttribute("purchases", purchases);
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) 
	{
		
	}
}
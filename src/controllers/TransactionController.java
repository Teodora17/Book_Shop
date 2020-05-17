package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import model.*;

/**
 * Servlet implementation class TransactionController
 */
@WebServlet("/TransactionController")
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
    
    	ProductDAO pd = new ProductDAO();
    	
    	String username = (String) request.getSession().getAttribute("User");
    	
    	List<Product> products = (List<Product>) getServletContext().getAttribute("products");
		List<Product> orderedProducts = new ArrayList<Product>();
		
		double total = 0;
		String priceRec="";
		int totalQ = 0;
		
		for(Product p : products) 
		{

				total = total + p.getUnitPrice() * p.getQuantity();
				totalQ += p.getQuantity();
				if(orderedProducts.isEmpty() && (p.getQuantity() != 0))
				{
					orderedProducts.add(p);
				}
				else if(!orderedProducts.contains(p) && (p.getQuantity() != 0))
				{
					orderedProducts.add(p);
				}
		}
    	
		String totalPrice = Double.toString(total);
		
    	try 
    	{
			pd.addPurchase(null, totalPrice, totalQ, username);
			List<Purchase> purchases = new ArrayList<Purchase>();
			purchases=pd.retrievePurchases();
			purchases.get(0).setDate(null);
			purchases.get(0).setTotalPrice(totalPrice);
			purchases.get(0).setNumberOfItems(totalQ);
			purchases.get(0).setUsername(username);
			request.getServletContext().setAttribute("purchases", purchases);
		} 
    	catch (ClassNotFoundException | SQLException e) 
    	{

			e.printStackTrace();
		}
    	
    	request.getSession().setAttribute("orderedProducts", orderedProducts);
    	request.getSession().setAttribute("totalPrice", totalPrice);
    	request.getSession().setAttribute("User", username);
    	
    	if(orderedProducts.isEmpty())
    	{
    		
    		request.getRequestDispatcher("LoginView.jsp").forward(request, response);
    	}
    	else
    	{    		
    		request.getRequestDispatcher("CheckoutView.jsp").forward(request, response);
    	}
    	
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import model.Product;

/**
 * Servlet implementation class RemoveController
 */
@WebServlet("/RemoveController")
public class RemoveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {

    			//When remove is clicked

    				ProductDAO pd = new ProductDAO();
    				
    				List<Product> products = (List<Product>) getServletContext().getAttribute("products");
    				List<Product> orderedProducts = new ArrayList<Product>();
    				
    				String totalPriceString = (String) request.getSession().getAttribute("totalPrice");
    				
    				double total = Double.valueOf(totalPriceString);
    				
    				for(Product p : products) {
    					String checkout = request.getParameter("checkout_"+String.valueOf(p.getId()));

    					if(checkout == "")
    					{
    						orderedProducts.add(p);
    					}
    					
    					else
    					{
    						total -= p.getUnitPrice() * p.getQuantity();
    						p.setQuantity(0);
    						ProductDAO.getInstance().setQuantity(p.getName(), 0);
    					}
    				}
    				
    				String totalPrice = String.valueOf(total);
    				
    				request.getSession().setAttribute("totalPrice", totalPrice);
    				request.getSession().setAttribute("orderedProducts", orderedProducts);
    				request.getRequestDispatcher("TransactionView.jsp").forward(request, response);
    	
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
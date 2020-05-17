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
 * Servlet implementation class BuyController
 */
@WebServlet("/BuyController")
public class BuyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
    	
    	if(request.getSession(false) != null && request.getSession().getAttribute("User")!=null)
		{
			
			List<Product> products = (List<Product>) getServletContext().getAttribute("products");
			List<Product> orderedProducts = new ArrayList<Product>();
			
			double total = 0;
			String priceRec="";
			
			ProductDAO pd = new ProductDAO();
			
			for(Product p : products) {
				int quantity = 0;
				priceRec = request.getParameter("product_"+String.valueOf(p.getId()));
				if(priceRec.isEmpty()) {quantity = 0;}
				else 
				{
					quantity = Integer.parseInt(priceRec);
				}
				if(quantity != 0) 
				{
					total = total + p.getUnitPrice() * quantity;
					p.setQuantity(quantity);	// setting the quantity received from the input box for the product
					
					ProductDAO.getInstance().setQuantity(p.getName(),quantity);
					
					if(orderedProducts.isEmpty())
					{
						orderedProducts.add(p);
					}
					else if(!orderedProducts.contains(p))
					{
						orderedProducts.add(p);
					}
				}
			}
			
			String totalPrice = Double.toString(total);
			
			String name = (String) request.getSession().getAttribute("User");
			request.getSession().setAttribute("User",name);
			
			request.getSession().setAttribute("totalPrice", totalPrice);
			request.getSession().setAttribute("orderedProducts", orderedProducts);
			request.getRequestDispatcher("TransactionView.jsp").forward(request, response);
			
		}
		
		else
		{
			response.sendRedirect("LoginView.jsp");
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
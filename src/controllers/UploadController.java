package controllers;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.*;
import model.*;

/**
 * Servlet implementation class UploadController
 */
@WebServlet("/UploadController")
@MultipartConfig
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		ProductDAO pd = new ProductDAO();
		
		UserDAO ud = new UserDAO();
		
		String username = (String) request.getSession().getAttribute("User");
		
		try {
			if(ud.isAdmin(username))
			{
			
			//CREATING PRODUCT
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			String description = request.getParameter("description");
			Double price = Double.parseDouble(request.getParameter("price"));
			
			//Registering product to DB
			pd.addProduct(name, type, description, price, 0);
			
			//We also need to update the product list within the servlet scope
			List<Product> products = new ArrayList<Product>();
			products = pd.retrieveProducts();
			products.get(products.size() - 1).setId(id);
			products.get(products.size() - 1).setName(name);
			products.get(products.size() - 1).setType(type);
			products.get(products.size() - 1).setDescription(description);
			products.get(products.size() - 1).setUnitPrice(price);
			products.get(products.size() - 1).setQuantity(0);
			
			request.getServletContext().setAttribute("products", products);
			
			request.getSession().setAttribute("User", username);
			request.getRequestDispatcher("AddProduct.jsp").forward(request, response);
			}
			else
			{
				request.getSession().setAttribute("User", username);
				request.getRequestDispatcher("ProductsView.jsp").forward(request, response);
			}
		} catch (NumberFormatException | ClassNotFoundException | NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}
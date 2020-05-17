package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import model.Product;
/**
 * Servlet implementation class ProductsView
 */
@WebServlet("/ProductsView")
public class ProductsView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductsView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException  {
		List<Product> products = (List<Product>) getServletContext().getAttribute("products");
		StringBuilder sb = new StringBuilder();
		String user = "";
		boolean loggedIn = false;
		
		if(request.getSession().getAttribute("User") != null) {
			user = (String) request.getSession().getAttribute("User");
			loggedIn = true;
		}
		
		for(Product p : products) {
			sb.append("<tr>");
			sb.append("<td>").append(p.getName()).append("</td>");
			sb.append("<td>").append(p.getType()).append("</td>");
			sb.append("<td>").append(p.getUnitPrice()).append("</td>");
			if(loggedIn) {
				sb.append("<td>").append("<input type=\"text\">").append("</td>");
			}
			sb.append("</tr>");
		}
		
		PrintWriter pw = response.getWriter();
		
		pw.append("<html>\r\n" + 
				"    <head>\r\n" + 
				"        <link rel=\"stylesheet\" href=\"css/main.css\" type=\"text/css\"/>\r\n" + 
				"        <title>Login</title>\r\n" + 
				"        <meta charset=\"UTF-8\">\r\n" + 
				"        <meta name=\"viewport\" content=\"width=device-width\">\r\n" + 
				"    </head>\r\n" + 
				"    <body>\r\n" + 
				" 		<h1>Products</h1>" +
				" <form method=\"post\">" +
				" <table>" +
				"<tr><td>Category</td><td>Name</td><td>Price</td>");
				if(loggedIn) {pw.append("<td>Quantity</td>");} 
				pw.append("</tr>" + 
				"" + sb.toString() + "\n" +
				"</table>");
				if(loggedIn) {pw.append("<input type=\"submit\" value=\"Buy\">\n");}
				pw.append("</form" +
				"</body" +
				"</html>"
				);
		
	}

}
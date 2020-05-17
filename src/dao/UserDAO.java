package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.naming.NamingException;

import utils.DBConnection;

public class UserDAO {
	
	private static UserDAO instance;
		
		public static UserDAO getInstance() {
			
			if (instance == null) 
			{
				instance = new UserDAO();
			}
			
			return instance;
			
		}
	
	public boolean userExists(String username) throws ClassNotFoundException, SQLException {
		boolean status = false;
		Connection con = DBConnection.getConnection();
		
		Statement instr = con.createStatement();
		String sql = "SELECT username FROM infoperson WHERE username='"+username+"'";
		ResultSet rs = instr.executeQuery(sql);
		if(rs.next())
			 status = true;
		 else status = false;

		instr.close();
		con.close();
		 
		return status;
	}
	
	public boolean addUser(String username, String password, String type) throws SQLException, ClassNotFoundException {
		Connection con = DBConnection.getConnection();
		
		String sql="INSERT INTO infoperson (username," + 
				"password," +
				"type) values (?, SHA(?) , ?);";
		
		PreparedStatement statement = con.prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, password);
		statement.setString(3, type);
		statement.execute();

		return true;
	}
	
	public boolean loginVerification(String username, String password) throws ClassNotFoundException, SQLException {
		boolean status1, status2 = false;
		Connection con = DBConnection.getConnection();
		
		Statement instr = con.createStatement();
		String sql = "SELECT username FROM infoperson WHERE username='"+username+"'";
		ResultSet rs = instr.executeQuery(sql);
		if(rs.next())
			 status1 = true;
		 else status1 = false;

		instr.close();
		
		Statement instr2 = con.createStatement();
		String sql2 = "SELECT password FROM infoperson WHERE username='"+username+"'";
		ResultSet rs2 = instr2.executeQuery(sql2);
		
		while(rs2.next()) {
			if(rs2.getString("password").equals(password)) {
				status2 = true;
			} else status2 = false;
		}
		
		instr2.close();
		con.close();
		 
		boolean finalStatus = status1 && status2;
		return finalStatus;
	}

	public boolean isAdmin(String username) throws NamingException, SQLException, ClassNotFoundException
	{
		
		boolean status = false;
		
		String sql = "SELECT type FROM infoperson WHERE username='"+username+"'";
		Connection dbconn = DBConnection.getConnection();
		PreparedStatement instr = dbconn.prepareStatement(sql);
		ResultSet rs = instr.executeQuery();
		
		while(rs.next()) {
			if(rs.getString("type").equals("admin")) {
				status = true;
			} else status = false;
		}
		
		return status;
	}
	
}
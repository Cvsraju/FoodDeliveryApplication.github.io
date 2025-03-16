package com.tap.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	 private static final String URL = "jdbc:mysql://localhost:3306/fooddelivery2";
	 private static final String USER = "root";
	 private static final String PASSWORD ="Harsha@825";                
	
	public static Connection getConnect() {
		
		Connection connection=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			connection=DriverManager.getConnection(URL,USER,PASSWORD);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
		
	}
	

}
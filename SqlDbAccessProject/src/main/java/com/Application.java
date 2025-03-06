package com;

import java.sql.*;
import org.mariadb.jdbc.*;

public class Application {
	
	private static java.sql.Connection connection;
	
	public static void main(String[] args) {
		openDatabaseConnection();
		closeDatabaseConnection();
	}
	
	private static void openDatabaseConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mariadb://localhost/kinodb", "KinoDbUser", "ILoveMovies123");
			System.out.println("Connection valid.");
		}
		catch(Exception e) {
			System.out.println("Error: Connection invalid.");
			e.printStackTrace();
		}
	}
	
	private static void closeDatabaseConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
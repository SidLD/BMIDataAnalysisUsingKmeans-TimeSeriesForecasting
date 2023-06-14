package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import Analysis.BaselineQuery;
import Analysis.Grade;
import Analysis.School;
import Analysis.Section;
import Analysis.Timeline;

public class SqlLite {
	private Connection conn;
	
	public Connection connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection test = DriverManager.getConnection("jdbc:sqlite:BMIDatabase.db");
			return test;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public boolean closeConnection(Connection con) {
		try {
			con.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}

package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Analysis.School;

public class SchoolSqlLite extends SqlLite{
	private Connection conn;
	
	public School getSchool(int id) {
		School sc = null;
		try {
			conn = connect();
			String query = "SELECT * from schools where id = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet res = ps.executeQuery();
			while(res.next()) {
				sc = new School(res.getInt("id"),res.getString("school"),res.getString("division"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection(conn);
		return sc;
	}
	public School getSchool(String school) {
		School sc = null;
		try {
			conn = connect();
			String query = "SELECT * from schools where school = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, school);
			ResultSet res = ps.executeQuery();
			while(res.next()) {
				sc = new School(res.getInt("id"),res.getString("school"),res.getString("division"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection(conn);
		return sc;
	}
	public ArrayList<School> getSchools(){
		ArrayList<School> data = new ArrayList<School>();
		try {
			conn = connect();
			Statement st = conn.createStatement();
			String query = "SELECT * from schools";
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				data.add(new School(res.getInt("id"),res.getString("school"),res.getString("division")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection(conn);
		
		return data;
		
	}
	public boolean addSchool(String school, String division) {
		
		int res = -1;
		try {
			conn = connect();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO schools(school, division) Values(?, ?)");
			stmt.setString(1, school);
			stmt.setString(2, division);
			res = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeConnection(conn);
		return res == -1 ? false: true;
	}
	
	public int getSchoolID(String schoolName) {
		int id = -1;
		try {
			conn = connect();
			String query = "select id from schools where school = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, schoolName);
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				id = res.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeConnection(conn);
		
		return id;
		
	}
	

}

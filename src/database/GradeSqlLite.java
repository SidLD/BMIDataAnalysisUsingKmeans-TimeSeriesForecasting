package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Analysis.Grade;

public class GradeSqlLite extends SqlLite{
	private Connection conn;
	public ArrayList<Grade> getGrades(int selectedSchoolId,int selectedSchoolYear) {
		ArrayList<Grade> data = new ArrayList<Grade>();
		try {
			conn = connect();
			String query = "SELECT * from grades where grades.school_id = ? and grades.school_year_id = ?";
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, selectedSchoolId);
			st.setInt(2, selectedSchoolYear);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				data.add(new Grade(res.getInt("id"), res.getInt("school_id"), res.getString("name"), res.getInt("school_year_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection(conn);		
		return data;
	}
	
	public int getGradeId(int selectedSchoolId, int selectedSchoolYear, String grade) {
		int id = -1;
		try {
			conn = connect();
			PreparedStatement stmt = conn.prepareStatement("select * from grades where grades.school_id = ? and grades.school_year_id = ? and name =?");
			stmt.setInt(1, selectedSchoolId);
			stmt.setInt(2, selectedSchoolYear);
			stmt.setString(3, grade);
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				id = res.getInt("id");
			}

			closeConnection(conn);
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return id;
		}
	}
	
	public int addGrade(int selectedSchoolId, int schoolYear, String grade) {
		try {
			conn = connect();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO grades(school_id, school_year_id, name) Values(?,?,?)");
			stmt.setInt(1, selectedSchoolId);
			stmt.setInt(2, schoolYear);
			stmt.setString(3, grade);
			stmt.executeUpdate();
			closeConnection(conn);
			return getGradeId(selectedSchoolId,schoolYear,grade );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public int getCountHFA(int id, String field) {
		int total = 0;
			try {
				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where students.hfa_status = ? and students.grade_id = ?");
				stmt.setString(1, field);
				stmt.setInt(2, id);
				
				ResultSet res = stmt.executeQuery();
				while(res.next()) {
					total = res.getInt("count(id)");
				}
		
				closeConnection(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return total;
	}

	public boolean renameGrade(int selectedGradeId, String res) {
		try {
			conn = connect();
			String query = "update grades set name = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, res);
			stmt.setInt(2, selectedGradeId);
			stmt.executeUpdate();
			closeConnection(conn);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}

	public boolean deleteGrade(int id) {
		try {	
			conn = connect();
			int ids[] = getSectionsId(id);
			closeConnection(conn);
			
			for (int i : ids) {
				new SectionSqlLite().deleteSection(i);
			}
			
			
			conn = connect();
			String query = "delete from grades where id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			closeConnection(conn);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	private int[] getSectionsId(int id) {
		ArrayList<Integer> data = new ArrayList<Integer>();
		try {
			conn = connect();
			String query = "select id from sections where grade_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				data.add(res.getInt("id"));
			}
			
			
			closeConnection(conn);
			
			
			int r[] = new int[data.size()];
			for (int i = 0; i < r.length; i++) {
				r[i] = data.get(i);
			}
			return r;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			int r[] = new int[0];
			return r;
		}
	}



}

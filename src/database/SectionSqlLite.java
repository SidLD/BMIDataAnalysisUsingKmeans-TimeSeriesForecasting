package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Analysis.Section;

public class SectionSqlLite extends SqlLite{
	private Connection conn;

	public int addSection(int selectedSchoolId, int gradeId, String section) {
		try {
			conn = connect();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO sections(school_id, grade_id, name) Values(?,?,?)");
			stmt.setInt(1, selectedSchoolId);
			stmt.setInt(2, gradeId);
			stmt.setString(3, section);
			stmt.executeUpdate();

			closeConnection(conn);
			return getSectionId(gradeId, selectedSchoolId, section);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	
	
	public ArrayList<Section> getSections(int selectedGradeId, int selectedSchool) {
		ArrayList<Section> data = new ArrayList<Section>();
		try {
			conn = connect();
			String query = "SELECT * from sections where sections.grade_id = ? and sections.school_id = ?";
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, selectedGradeId);
			st.setInt(2, selectedSchool);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				data.add(new Section(res.getInt("id"),
						res.getString("name"), 
						res.getInt("school_id"), 
						res.getInt("grade_id"))
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection(conn);
		return data;
	}

	public int getSectionId(int gradeId, int selectedSchoolId, String section) {
		int id = -1;
		try {
			conn = connect();
			PreparedStatement stmt = conn.prepareStatement("select * from sections where sections.school_id = ? and sections.grade_id = ? and name = ?");
			stmt.setInt(1, selectedSchoolId);
			stmt.setInt(2, gradeId);
			stmt.setString(3, section);
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



	public boolean setDate(int selectedSectionId, String date) {
		try {
			conn = connect();
			String query = "update sections set date_weighted = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, date);
			stmt.setInt(2, selectedSectionId);
			stmt.executeUpdate();
			closeConnection(conn);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}



	public boolean renameSection(int id, String res) {
		try {
			conn = connect();
			String query = "update sections set name = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, res);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			closeConnection(conn);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}



	public boolean deleteSection(int id) {
		try {
			new StudentSqlLite().deleteStudent(id);
			conn = connect();
			String query = "delete from sections where id = ?";
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

}

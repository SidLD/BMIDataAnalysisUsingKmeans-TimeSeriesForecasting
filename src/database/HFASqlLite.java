package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Analysis.Grade;

public class HFASqlLite extends SqlLite{
	private Connection conn;
	public int getTotalNormalHFA(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {
				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where hfa_range = ? and grade_id = ?");
				stmt.setString(1, "NORMAL");;
				stmt.setInt(2, grade.getId());
				ResultSet res = stmt.executeQuery();
				while(res.next()) {
					total += res.getInt("count(id)");
				}
				closeConnection(conn);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
	public int getTotalTallHFA(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {
				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where hfa_range = ? and grade_id = ?");
				stmt.setString(1, "TALL");;
				stmt.setInt(2, grade.getId());
				ResultSet res = stmt.executeQuery();
				while(res.next()) {
					total += res.getInt("count(id)");
				}
				closeConnection(conn);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
	public int getTotalStuntedHFA(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {
				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where hfa_range = ? and grade_id = ?");
				stmt.setString(1, "STUNTED");;
				stmt.setInt(2, grade.getId());
				ResultSet res = stmt.executeQuery();
				while(res.next()) {
					total += res.getInt("count(id)");
				}
				closeConnection(conn);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
	public int getTotalSeverelyStuntedHFA(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {
				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where hfa_range = ? and grade_id = ?");
				stmt.setString(1, "SEVERELY_STUNTED");;
				stmt.setInt(2, grade.getId());
				ResultSet res = stmt.executeQuery();
				while(res.next()) {
					total += res.getInt("count(id)");
				}
				closeConnection(conn);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
}

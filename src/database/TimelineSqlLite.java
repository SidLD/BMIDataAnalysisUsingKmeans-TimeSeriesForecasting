package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Analysis.Timeline;

public class TimelineSqlLite extends SqlLite{
	private Connection conn;
	public int getTimelineId(String sy, String type,int schoolId) {
		int id = -1;
		try {
			conn = connect();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM timelines where school_year = ? and type = ? and school_id = ?");
			stmt.setString(1, sy);
			stmt.setString(2, type);
			stmt.setInt(3, schoolId);
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
	
	public int addTimeline(String sy, String type , int schoolId) {
		try {
			conn = connect();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO timelines(school_year, type, school_id) Values(?,?, ?)");
			stmt.setString(1, sy);
			stmt.setString(2, type);
			stmt.setInt(3, schoolId);
			stmt.executeUpdate();

			closeConnection(conn);
			return getTimelineId(sy, type, schoolId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	public ArrayList<Timeline> getTimelines(int id) {
		ArrayList<Timeline> data = new ArrayList<Timeline>();
		try {
			conn = connect();
			String query = "SELECT * from timelines where school_id = ? order by school_year ";
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				data.add(new Timeline(res.getInt("id"),res.getString("school_year"), res.getString("type")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection(conn);
		return data;
	}

	public Timeline getTimelineData(int selectedSchoolYearId) {
		Timeline data = null;
		try {
			conn = connect();
			String query = "select * from timelines where id = ?";
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, selectedSchoolYearId);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				data = new Timeline(res.getInt("id"), res.getString("school_year"), res.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
}

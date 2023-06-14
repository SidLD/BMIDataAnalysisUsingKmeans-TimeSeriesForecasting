package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import Analysis.Grade;
import Analysis.KMeansData;
import Analysis.Section;
import Analysis.Student;
import Analysis.TimeData;
import Analysis.Timeline;

public class BMISqlLite extends SqlLite{
	private Connection conn;
	
	
	public int addStudent(int schoolId, int gradeId, int SectionId, Student student) {
		try {
			conn = connect();
			String query = "insert into students(school_id, grade_id, section_id, "
					+ "	name,  birthday, sex, ageYear, ageMonth, nutritional_status, hfa_status,"
					+ "	weight, height, is_weight_taken, is_height_taken, bmi, bmi_range, hfa_range) "
					+ "	VALUES (?, ?, ?, "
					+ "	?, ?, ?, ?, ?, ?, ?,"
					+ "	?, ?, ?, ?, ?, ?, ?"
					+ "	)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, schoolId);
			stmt.setInt(2, gradeId);
			stmt.setInt(3, SectionId);
			
			stmt.setString(4, student.getName());
			stmt.setString(5, student.getBirthday());
			stmt.setString(6, student.getSex());
			stmt.setInt(7, student.getAgeYear());
			stmt.setInt(8, student.getAgeMonth());
			stmt.setString(9, student.getNutritionalStatus());
			stmt.setString(10, student.getHfaStatus());
			
			stmt.setFloat(11, student.getWeight());
			stmt.setFloat(12, student.getHeight());
			stmt.setBoolean(13, student.isWeightTaken());
			stmt.setBoolean(14, student.isHieghtTaken());
			stmt.setFloat(15, student.calculateBMI());
			stmt.setString(16, student.getBmiStatus());
			stmt.setString(17, student.getHfaStatus());
			
			stmt.executeUpdate();
			closeConnection(conn);
			return getStudentId(schoolId, gradeId, SectionId, student);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	private int getStudentId(int schoolId, int gradeId, int sectionId, Student newStudent) {
		int id = -1;
		try {
			conn = connect();
			String query = "select * from students where students.school_id = ? and students.grade_id = ? and students.section_id = ? and students.name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, schoolId);
			stmt.setInt(2, gradeId);
			stmt.setInt(3, sectionId);
			stmt.setString(4, newStudent.getName());
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
	public int getTotalSeverelyWasted(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {

				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where bmi_range = ? and grade_id = ?");
				stmt.setString(1, "SEVERELY_WASTED");;
				stmt.setInt(2, grade.getId());
				ResultSet res = stmt.executeQuery();
				while(res.next()) {
					total = res.getInt("count(id)");
				}
				closeConnection(conn);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
	public int getTotalNormalBMI(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {
				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where bmi_range = ? and grade_id = ?");
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
	public int getTotalOverweightBMI(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {
				connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where bmi_range = ? and grade_id = ?");
				stmt.setString(1, "OVERWEIGHT");;
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
	public int getTotalWasted(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {

				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where bmi_range = ? and grade_id = ?");
				stmt.setString(1, "WASTED");;
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
	public int getTotalObesseBMI(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {

				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where bmi_range = ? and grade_id = ?");
				stmt.setString(1, "OBESE");;
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
	public ArrayList<KMeansData> getStudentsKmeansData(int selectedSchoolId, int selectedSchoolYearId) {
		ArrayList<KMeansData> total = new ArrayList<KMeansData>();
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {

				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select ROUND(bmi,2) AS bmi, ageYear, sex from students where grade_id = ?");
				stmt.setInt(1, grade.getId());
				ResultSet res = stmt.executeQuery();
				while(res.next()) {
					
					float bmi = res.getFloat("bmi");
					int ageYear = res.getInt("ageYear");
					String sex = res.getString("sex");
					
					if(bmi != 0 || ageYear != 0) {
						total.add(new KMeansData( bmi, ageYear, sex));
					}
				}	
				closeConnection(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DecimalFormat df =  new DecimalFormat();
		df.setMaximumFractionDigits(2);
		for (KMeansData kMeansData : total) {
			kMeansData.setOne(Double.parseDouble(df.format(kMeansData.getOne())));
		}
		
		return total;
	}
	public TimeData[] getTimeData(int selectedSchoolId, String min, String max) {
		ArrayList<TimeData> temp = new ArrayList<TimeData>();
		try {
			conn = connect();
			ArrayList<Timeline> schools = new TimelineSqlLite().getTimelines(selectedSchoolId);
			closeConnection(conn);
			
			
			for (Timeline timeline : schools) {
				conn = connect();
				ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, timeline.getId());
				closeConnection(conn);
				int total = 0;
				for (Grade grade : grades) {
					conn = connect();
					PreparedStatement stmt = conn.prepareStatement("select count(id) from students WHERE (grade_id = ? AND bmi_range = ?) or (grade_id = ? AND bmi_range = ?)");
					stmt.setInt(1, grade.getId());
					stmt.setString(2, min);
					stmt.setInt(3, grade.getId());
					stmt.setString(4, max);
					ResultSet res = stmt.executeQuery();
					while(res.next()) {
						total += res.getInt("count(id)");
					}
					closeConnection(conn);
				}
				TimeData newTimeData = new TimeData();
				newTimeData.setTime(timeline.getSchool_year());
				newTimeData.setData(total);
				temp.add(newTimeData);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimeData[] data = new TimeData[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			data[i] = temp.get(i);
		}
		return data;
	}
}

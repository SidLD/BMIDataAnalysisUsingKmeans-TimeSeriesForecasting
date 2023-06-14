package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Analysis.Grade;
import Analysis.Student;

public class StudentSqlLite extends SqlLite{
	private Connection conn;
	
	public ArrayList<Student> getStudents(int sectionId){
		ArrayList<Student> data = new ArrayList<Student>();
		try {
			conn = connect();
			String query = "select * from students where students.section_id = ?";
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, sectionId);
			ResultSet res = st.executeQuery();
			while(res.next()) {

				Student newStudent = new Student();
				newStudent.setName(res.getString("name"));
				newStudent.setId(res.getInt("id"));
				
					newStudent.setBirthday(res.getString("birthday"));
					newStudent.setSex(res.getString("sex"));
					newStudent.setWeight(res.getFloat("weight"));
					newStudent.setHeight(res.getFloat("height"));
					newStudent.setAgeYear(res.getInt("ageYear"));
					newStudent.setAgeMonth(res.getInt("ageMonth"));
					newStudent.setHfaStatus(res.getString("hfa_status"));
					newStudent.setNutritionalStatus(res.getString("nutritional_status"));
				data.add(newStudent);
			}
			
			
			closeConnection(conn);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return data;
	}

	public Student getStudent(int id) {
		try {
			conn = connect();
			String query = "select * from students where id = ?";
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			ResultSet res = st.executeQuery();
			Student newStudent = null;
			while(res.next()) {

				newStudent = new Student();
				newStudent.setName(res.getString("name"));
				newStudent.setId(res.getInt("id"));
				newStudent.setBirthday(res.getString("birthday"));
				newStudent.setSex(res.getString("sex"));
				newStudent.setWeight(res.getFloat("weight"));
				newStudent.setHeight(res.getFloat("height"));
				newStudent.setAgeYear(res.getInt("ageYear"));
				newStudent.setAgeMonth(res.getInt("ageMonth"));
				newStudent.setHfaStatus(res.getString("hfa_status"));
				newStudent.setNutritionalStatus(res.getString("nutritional_status"));

			}

			closeConnection(conn);
			return newStudent;
			
		} catch (Exception e) {
			return null;
		}
		
	}

	public int countStudent(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {
				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where grade_id = ?");
				stmt.setInt(1, grade.getId());
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

	public int countStudent(int selectedSchoolId, int selectedSchoolYearId, String string) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {
				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where grade_id = ? and sex = ?");
				stmt.setInt(1, grade.getId());
				stmt.setString(2, string);
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

	public int countStudentHeightTaken(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {
				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where grade_id = ? and is_weight_taken = ?");
				stmt.setInt(1, grade.getId());
				stmt.setBoolean(2, true);
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

	public int countStudentWeightTaken(int selectedSchoolId, int selectedSchoolYearId) {
		int total = 0;
		try {
			conn = connect();
			ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
			closeConnection(conn);
			for (Grade grade : grades) {
				conn = connect();
				PreparedStatement stmt = conn.prepareStatement("select count(id) from students where grade_id = ? and is_height_taken = ?");
				stmt.setInt(1, grade.getId());
				stmt.setBoolean(2, true);
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

	public int countStudentWeightTaken(int id, String gen) {
		int total = 0;
		try {
			conn = connect();
			String query = gen == "Total" ? "select count(id) from students where grade_id = ? and  is_weight_taken = ?"
					: "select count(id) from students where grade_id = ? and  is_weight_taken = ? and sex = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.setBoolean(2, true);
			if(gen != "Total") {
				stmt.setString(3, gen);
			}
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				total += res.getInt("count(id)");
			}
			closeConnection(conn);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}

	public int countStudentHeightTaken(int id, String gen) {
		int total = 0;
		try {
			conn = connect();
			String query = gen == "Total" ? "select count(id) from students where grade_id = ? and  is_height_taken = ?"
					: "select count(id) from students where grade_id = ? and  is_height_taken = ? and sex = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.setBoolean(2, true);
			if(gen != "Total") {
				stmt.setString(3, gen);
			}
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				total += res.getInt("count(id)");
			}
			closeConnection(conn);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}

	public Integer countStudent(int id, String gen) {
		int total = 0;
		try {
			conn = connect();
			String query = gen == "Total" ? "select count(id) from students where grade_id = ?"
					: "select count(id) from students where grade_id = ? and sex = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			if(gen != "Total") {
				stmt.setString(2, gen);
			}
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				total += res.getInt("count(id)");
			}
			closeConnection(conn);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}

	public int countGradeBMI(int id, String gen, String bmi_range) {
		int total = 0;
		try {
			conn = connect();
			String query = gen == "Total" ? "select count(id) from students where grade_id = ? and bmi_range = ?"
					: "select count(id) from students where grade_id = ? and bmi_range = ? and sex = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.setString(2, bmi_range);
			if(gen != "Total") {
				stmt.setString(3, gen);
			}
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				total += res.getInt("count(id)");
			}
			closeConnection(conn);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}

	public int countHFA(int id, String gen, String hfaRange) {
		int total = 0;
		try {
			conn = connect();
			String query = gen == "Total" ? "select count(id) from students where grade_id = ? and hfa_range = ?"
					: "select count(id) from students where grade_id = ? and hfa_range = ? and sex = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.setString(2, hfaRange);
			if(gen != "Total") {
				stmt.setString(3, gen);
			}
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				total += res.getInt("count(id)");
			}
			closeConnection(conn);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}

	public boolean updateStudent(Student student) {
		try {
			student.setBmiStatus(new BMIForAgeGirls().getBMIForGirlsStatus(student.getAgeYear(), student.getAgeMonth(), student.calculateBMI()));
			student.setHfaStatus(new HFAForAgeBoys().getHFAForBoysStatus(student.getAgeYear(), student.getAgeMonth(), student.calculateBMI()));
			
			conn = connect();
			String query = "UPDATE students SET "
					+ "	birthday = ?, "
					+ "	height = ?, weight = ?, "
					+ "	ageYear = ?, ageMonth = ?,"
					+ "	bmi_range = ?, bmi_range = ?,"
					+ "	bmi = ?, "
					+ "	is_height_taken = ?, is_weight_taken = ?,"
					+ " sex = ?, nutritional_status = ?"
					+ "	where id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, student.getBirthday());
			stmt.setFloat(2, student.getHeight());
			stmt.setFloat(3, student.getWeight());
			stmt.setInt(4, student.getAgeYear());
			stmt.setInt(5, student.getAgeMonth());
			stmt.setString(6, student.getBmiStatus());
			stmt.setString(7, student.getHfaStatus());
			stmt.setFloat(8, student.calculateBMI());
			stmt.setBoolean(9, student.isHieghtTaken());
			stmt.setBoolean(10, student.isWeightTaken());
			stmt.setString(11, student.getSex());
			stmt.setString(12, student.getNutritionalStatus());
			stmt.setInt(13, student.getId());
			
			int res = stmt.executeUpdate();
			closeConnection(conn);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void deleteStudent(int id) {
		try {	
			conn = connect();
			String query = "delete from students where section_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			closeConnection(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}


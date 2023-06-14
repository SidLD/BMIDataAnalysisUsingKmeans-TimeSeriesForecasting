package Analysis;

public class Section {
	int id;
	String name;
	int schoolId;
	int gradeId;
	

	public Section(int id, String name, int schoolId, int gradeId) {
		this.id = id;
		this.name = name;
		this.schoolId = schoolId;
		this.gradeId = gradeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	
	
}

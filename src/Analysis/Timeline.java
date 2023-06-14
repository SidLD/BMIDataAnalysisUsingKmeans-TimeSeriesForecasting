package Analysis;

public class Timeline {
	int id;
	String school_year;
	String type;
	public Timeline(int id, String school_year, String type) {
		this.id = id;
		this.school_year = school_year;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSchool_year() {
		return school_year;
	}
	public void setSchool_year(String school_year) {
		this.school_year = school_year;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}

/**
 * 
 */
package Analysis;

/**
 * @author Sid
 *
 */
public class Grade {
	int id;
	int schoolId;
	String name;
	int dateId;
	public Grade(int id, int schoolId, String name, int date) {
		this.id = id;
		this.schoolId = schoolId;
		this.name = name;
		this.dateId = date;
	}
	
	public int getDate() {
		return dateId;
	}

	public void setDate(int date) {
		this.dateId = date;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Grade [id=" + id + ", schoolId=" + schoolId + ", name=" + name + ", dateId=" + dateId + "]";
	}
	
}

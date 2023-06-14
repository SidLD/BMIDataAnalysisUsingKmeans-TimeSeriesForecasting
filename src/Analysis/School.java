package Analysis;

public class School {
	private int id;
	private String name;
	private String division;
	public School() {}
	
	public School(int id, String name, String division) {
		this.id = id;
		this.name = name;
		this.division = division;
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
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	@Override
	public String toString() {
		return "School [id=" + id + ", name=" + name + ", division=" + division + "]";
	}
}

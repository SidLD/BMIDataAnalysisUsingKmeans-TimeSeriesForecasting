package Analysis;

public class BaselineQuery {
	int id;
	String date;
	boolean isBaseline;
	@Override
	public String toString() {
		return "BaselineQuery [id=" + id + ", date=" + date + ", isBaseline=" + isBaseline + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean isBaseline() {
		return isBaseline;
	}
	public void setBaseline(boolean isBaseline) {
		this.isBaseline = isBaseline;
	}
	public BaselineQuery(int id, String date, boolean isBaseline) {
		this.id = id;
		this.date = date;
		this.isBaseline = isBaseline;
	}
	public BaselineQuery() {}
	
}

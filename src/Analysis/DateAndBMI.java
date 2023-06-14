package Analysis;

import java.util.ArrayList;


public class DateAndBMI {
	String date;
	ArrayList<SchoolAndPercentage> data ;
	public DateAndBMI() {
		data = new ArrayList<SchoolAndPercentage>();
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<SchoolAndPercentage> getData() {
		return data;
	}
	public void setData(ArrayList<SchoolAndPercentage> data) {
		this.data = data;
	}
	public void addData(SchoolAndPercentage temp) {
		this.data.add(temp);
	}
}

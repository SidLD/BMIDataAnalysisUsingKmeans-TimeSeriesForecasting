package Analysis;

public class SchoolAndPercentage{
	String school;
	double percentage;
	public SchoolAndPercentage(String school, double percentage) {
		this.school = school;
		this.percentage = percentage;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	@Override
	public String toString() {
		return "SchoolAndPercentage [school=" + school + ", percentage=" + percentage + "]";
	}
	
	
}
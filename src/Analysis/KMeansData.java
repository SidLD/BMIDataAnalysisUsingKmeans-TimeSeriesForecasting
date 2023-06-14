package Analysis;

public class KMeansData {
	double one;
	double two;
	double oneScale;
	double twoScale;
	String label;
	
	public double getOneScale() {
		return oneScale;
	}
	public void setOneScale(double oneScale) {
		this.oneScale = oneScale;
	}
	public double getTwoScale() {
		return twoScale;
	}
	public void setTwoScale(double twoScale) {
		this.twoScale = twoScale;
	}
	public KMeansData(double one, double two, String label) {
		this.one = one;
		this.two = two;
		this.label = label;
	}
	public KMeansData() {}
	public double getOne() {
		return one;
	}
	public void setOne(double one) {
		this.one = one;
	}
	public double getTwo() {
		return two;
	}
	public void setTwo(double two) {
		this.two = two;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return "KMeansData [one=" + one + ", two=" + two + ", oneScale=" + oneScale + ", twoScale=" + twoScale
				+ ", label=" + label + "]";
	}
	
	
	

}

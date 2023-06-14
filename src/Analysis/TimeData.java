package Analysis;

public class TimeData {
	String time;
	double data;
	public TimeData() {}
	public TimeData(String time, double data) {
		this.time = time;
		this.data = data;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public double getData() {
		return this.data;
	}
	public void setData(double data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "TimeData [time=" + time + ", data=" + data + "]";
	}
	

}

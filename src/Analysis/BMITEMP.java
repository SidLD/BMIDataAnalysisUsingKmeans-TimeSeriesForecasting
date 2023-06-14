package Analysis;

public class BMITEMP {
		String category;
		int year;
		int month;
		float min;
		float max;

		public BMITEMP(String gender, int year, int month, float min, float max) {
			this.category = gender;
			this.year = year;
			this.month = month;
			this.min = min;
			this.max = max;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String gender) {
			this.category = gender;
		}
		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}
		public int getMonth() {
			return month;
		}
		public void setMonth(int month) {
			this.month = month;
		}
		public float getMin() {
			return min;
		}
		public void setMin(float min) {
			this.min = min;
		}
		public float getMax() {
			return max;
		}
		public void setMax(float max) {
			this.max = max;
		}
}

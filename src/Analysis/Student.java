package Analysis;

import java.text.DecimalFormat;
import java.util.Random;

public class Student {
	int id;
	String name;
	String birthday = "";
	float weight = 0;
	float height = 0;
	String sex = "NaN";
	int ageYear = 0;
	int ageMonth = 0;
	float bmi = 0;
	String nutritionalStatus = "NaN";
	String hfaStatus = "NaN";
	String hfa = "NaN";
	int schoolId;
	int gradeId;
	int sectionId;
	String bmiStatus = "NaN";
	
	
	DecimalFormat df = new DecimalFormat();
	public Student() {
	}
	
	
	
	public String getBmiStatus() {
		return bmiStatus;
	}



	public void setBmiStatus(String bmiStatus) {
		this.bmiStatus = bmiStatus;
	}



	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public float getHeigh2() {
		float temp = this.height * this.height;
		df.setMaximumFractionDigits(2);
		temp = Float.parseFloat(df.format(temp));
		return temp;
	}
	
	public float calculateBMI() {
		if(isWeightTaken()) {
			float temp = this.weight/getHeigh2();
			df.setMaximumFractionDigits(2);
			temp = Float.parseFloat(df.format(temp));
			return temp;
		}else {
			return 0;
		}
	}
	
	public int getGradeId() {
		return gradeId;
	}



	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}



	public int getSectionId() {
		return sectionId;
	}



	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}



	public boolean isHieghtTaken() {
		if(this.height > 0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean isWeightTaken() {
		if(this.weight > 0) {
			return true;
		}else {
			return false;
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name.toUpperCase();
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public String getSex() {
		return sex.toUpperCase();
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAgeYear() {
		return ageYear;
	}
	public void setAgeYear(int ageYear) {
		this.ageYear = ageYear;
	}
	public int getAgeMonth() {
		return ageMonth;
	}
	public void setAgeMonth(int ageMonth) {
		this.ageMonth = ageMonth;
	}
	
	public void setBmi(float bmi) {
		this.bmi = bmi;
	}
	public String getNutritionalStatus() {
		return nutritionalStatus.toUpperCase();
	}
	public void setNutritionalStatus(String nutritionalStatus) {
		this.nutritionalStatus = nutritionalStatus;
	}
	public String getHfaStatus() {
		return hfaStatus.toUpperCase();
	}
	public void setHfaStatus(String hfaStatus) {
		this.hfaStatus = hfaStatus;
	}
	public String getHfa() {
		return hfa;
	}
	public void setHfa(String hfa) {
		this.hfa = hfa;
	}
	public float calculateHFA() {
		return (float) 0.0;
	}
	public String getBlurName() {
		int ran = new Random().nextInt(10 - 3 + 1) + 3;
		String res = "";
		for (int i = 0; i < this.name.length()-1; i++) {
			if(i%ran == 0) {
				res += this.name.substring(i, i+1);
			}else {
				res += "*";
			}
		}
		return res;
	}


	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", birthday=" + birthday + ", weight=" + weight + ", height="
				+ height + ", sex=" + sex + ", height2=" + ", ageYear=" + ageYear + ", ageMonth=" + ageMonth
				+ ", bmi=" + bmi + ", nutritionalStatus=" + nutritionalStatus + ", hfaStatus=" + hfaStatus + ", hfa="
				+ hfa + "]";
	}
	
}

package Analysis;

public class BaseLine {
	public String gradeLevel;
	public int enrollmentMale = 0;
	public int weightedMale;
	public int severelyWastedMale;
	public int wastedMale;
	public int normalBMIMale;
	public int overweightMale;
	public int obesseMale;
	public int severelyStuntedMale;
	public int stuntedMale;
	public int normalHFAMale;
	public int tallMale;
	public int pupilsTakenHeightMale;
	
	

	public int enrollmentFemale = 0;
	public int weightedFemale;
	public int severelyStuntedFemale;
	public int stuntedFemale;
	public int normalHFAFemale;
	public int tallFemale;
	
	
	public int pupilsTakenHeightFemale;
	public int severelyWastedFemale;
	public int wastedFemale;
	public int normalBMIFemale;
	public int overweightFemale;
	public int obesseFemale;
	public boolean isNull = false;
	
	public BaseLine() {}
	@Override
	public String toString() {
		return "BaseLine [gradeLevel=" + gradeLevel + ", enrollmentMale=" + enrollmentMale + ", enrollmentFemale="
				+ enrollmentFemale + ", weightedMale=" + weightedMale + ", weightedFemale=" + weightedFemale
				+ ", severelyWastedMale=" + severelyWastedMale + ", wastedMale=" + wastedMale + ", normalBMIMale="
				+ normalBMIMale + ", overweightMale=" + overweightMale + ", obesseMale=" + obesseMale
				+ ", severelyStuntedMale=" + severelyStuntedMale + ", stuntedMale=" + stuntedMale + ", normalHFAMale="
				+ normalHFAMale + ", tallMale=" + tallMale + ", pupilsTakenHeightMale=" + pupilsTakenHeightMale
				+ ", severelyWastedFemale=" + severelyWastedFemale + ", wastedFemale=" + wastedFemale
				+ ", normalBMIFemale=" + normalBMIFemale + ", overweightFemale=" + overweightFemale + ", obesseFemale="
				+ obesseFemale + ", severelyStuntedFemale=" + severelyStuntedFemale + ", stuntedFemale=" + stuntedFemale
				+ ", normalHFAFemale=" + normalHFAFemale + ", tallFemale=" + tallFemale + ", pupilsTakenHeightFemale="
				+ pupilsTakenHeightFemale + "]";
	}
	public double getBMIMalePercentage(int num) {
		return (num * 100.0f) / weightedMale;
	}
	public double getBMIFemalePercentage(int num) {
		return (num * 100.0f) / weightedFemale;
	}
	public double getHFAFemalePercentage(int num) {
		return (num * 100.0f) / pupilsTakenHeightFemale;
	}
	public double getHFAMalePercentage(int num) {
		return (num * 100.0f)/pupilsTakenHeightMale;
	}
	public double getTotalBMIPercentage(double num) {
		return ((num * 100.0f) /( weightedMale + weightedFemale));
	}
	public double getTotalHFAPercentage(double num) {
		return ((num * 100.0f) /(pupilsTakenHeightFemale + pupilsTakenHeightMale));
	}
	public double getWeightedPercentage() {
		return (((weightedMale + weightedFemale) * 100.0f) / (enrollmentMale + enrollmentFemale));
	}
	public double getHeightPercentage() {
		return (((pupilsTakenHeightFemale + pupilsTakenHeightMale) * 100.0f) / (enrollmentMale + enrollmentFemale));
	}

	
}

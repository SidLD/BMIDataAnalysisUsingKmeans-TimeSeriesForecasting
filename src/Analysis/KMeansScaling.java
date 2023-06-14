package Analysis;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class KMeansScaling {
	

	private DecimalFormat df;
	
	public ArrayList<KMeansData> scaleData(ArrayList<KMeansData> data, double maxRange, double minRange ){
		df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		ArrayList<KMeansData> scaledData = new ArrayList<KMeansData>();
	    double minValA = getMinA(data);
	    double maxValA = getMaxA(data);
	    
	    double minValB = getMinB(data);
	    double maxValB = getMaxB(data);
	    
//
//    	System.out.println(maxValA);
//    	System.out.println(minValA);
//    	System.out.println(maxValB);
//    	System.out.println(minValB);
	    
	    
	    // Scale the data to the desired range
	    for (int i = 0; i < data.size(); i++) {
	    	KMeansData temp = data.get(i);
	    	double tempA = ((temp.getOne() - minValA) / (maxValA - minValA) * (maxRange - minRange) + minRange);
	    	temp.setOneScale(Double.parseDouble(df.format(tempA)));
	    	double tempB =  ((temp.getTwo() - minValB) / (maxValB - minValB) * (maxRange - minRange) + minRange);
	    	temp.setTwoScale(Double.parseDouble(df.format(tempB)));
	    
	    	
	    	scaledData.add(temp);
	    }
	    return scaledData;
	}
	
	private double getMaxA(ArrayList<KMeansData> data) {
		double maxValB = Double.MIN_VALUE;
		for (KMeansData kMeansData : data) {
			double d =  kMeansData.getOne();

			if(maxValB < d) {
				if(Double.isInfinite(d)) {
					maxValB = 99;
				}
				
			}
		}
		return maxValB;
	}
	
	private double getMaxB(ArrayList<KMeansData> data) {
		double maxValB = Double.MIN_VALUE;
		for (KMeansData kMeansData : data) {
			double d =  kMeansData.getTwo();
			if(maxValB < d) {
				maxValB = Double.parseDouble(df.format(d));
			}
		}
		return maxValB;
	}
	
	
	
	
	private double getMinA(ArrayList<KMeansData> data) {
		double minValB = Double.MAX_VALUE;
		for (KMeansData kMeansData : data) {
			double d =  kMeansData.getOne();
			if(d < minValB) {
				minValB = Double.parseDouble(df.format(d));
				
			}
		}
		return minValB;
	}
	
	private double getMinB(ArrayList<KMeansData> data) {
	    double minValB = Double.MAX_VALUE;
		for (KMeansData kMeansData : data) {
			double d =  kMeansData.getTwo();
			if(d < minValB) {
				minValB = Double.parseDouble(df.format(d));
			}
		}
		return minValB;
	}

}

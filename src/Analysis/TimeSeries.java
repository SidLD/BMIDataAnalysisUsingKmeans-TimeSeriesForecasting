package Analysis;

import java.text.DecimalFormat;

public class TimeSeries {
	static DecimalFormat df = new DecimalFormat();
	public TimeData[] forecast(TimeData[] original, int windowSize) {
		double bestAlpha = 0;
	      double bestMAE = Double.MAX_VALUE;
	      for (double alpha = 0.1; alpha <= 0.9; alpha += 0.1) {
	          TimeData[] forecastValues = TimeSeries.forecast(original, alpha, windowSize);
	          double mae = TimeSeries.calculateMAE(original, forecastValues);
	          if (mae < bestMAE) {
	              bestMAE = mae;
	              bestAlpha = alpha;
	              System.out.println("Best ma"+mae);
	          }
	      }
	      TimeData[] testForcast = TimeSeries.forecast(original, bestAlpha, windowSize);
	      System.out.println("************ Best Mah "+bestAlpha);
//	      for (int i = 0; i < testForcast.length; i++) {
//	    	  if(i < testForcast.length-1) {
//
//	  			System.out.println("Original "+df.format(original[i].getData()));
//	    	  }
//			System.out.println("Forecast "+df.format(testForcast[i].getData()));
//	      }	
	      TimeData[] forecasts = TimeSeries.finalForecast(original, bestAlpha, windowSize);
	      forecasts[forecasts.length-1] = testForcast[testForcast.length-1];
	      return forecasts;
	}
	public static TimeData[] movingAverage(TimeData[] data, int windowSize) {
		TimeData[] result = new TimeData[data.length];
        for (int i = 0; i < data.length+windowSize; i++) {
        	result[i] = new TimeData();
        	result[i].setTime(data[i].getTime());
        	result[i].setData(0);
        	
            if (i > windowSize-1) {
            	 double sum = 0.0;
                 for (int j = i-windowSize+1; j <= i; j++) {
                     sum += data[j].getData();
                 }
                 result[i].setData(sum / windowSize);
            } 
        }
		return result;
	}
	public static TimeData[] calculateTrend(TimeData[] data) {

		df.setMaximumFractionDigits(2);
		TimeData[] result = new TimeData[data.length];
		if(data.length < 3) {
			return null;
		}
		int last = 0;
        for (int i = 0; i < data.length-1; i++) {
        	result[i] = new TimeData();
//        	result[i].setLabel(data[i].getLabel());
        	result[i].setTime(data[i].getTime());
        	
        	double sum = 0.0;
            if (i >= 1) {
            	sum += data[i-1].getData();
            	sum += data[i].getData();
            	sum += data[i+1].getData();
            	double temp = sum/3;
            	result[i].setData(Double.parseDouble(df.format(temp)));
            	
            }
            else {
            	result[i].setData(0);
            }
            last++;
        }

    	result[last] = new TimeData();
    	result[last].setTime(data[last].getTime());

    	result[last].setData(0);
        
		return result;
	}
	
	public static TimeData[] finalForecast(TimeData[] data, double alpha, int numForecasts) {
		int n = data.length;
	    TimeData[] forecasts = new TimeData[n + numForecasts];
	    forecasts[0] = data[0];
	    for (int i = 1; i < n; i++) {
	    	forecasts[i] = new TimeData();
	    	forecasts[i].setTime(data[i].getTime());
		    	forecasts[i].setData(data[i].getData());	
	    	
	    }
	    for (int i = n; i < n + numForecasts; i++) {
	    	forecasts[i] = new TimeData();
	    	forecasts[i].setData(Math.round(alpha * forecasts[i-1].getData() + (1 - alpha) * forecasts[i-n].getData()));
	    }
	    return forecasts;
	 }
	public static TimeData[] forecast(TimeData[] data, double alpha, int numForecasts) {
		int n = data.length;
	    TimeData[] forecasts = new TimeData[n + numForecasts];
	    forecasts[0] = data[0];
	    for (int i = 1; i < n; i++) {
	    	forecasts[i] = new TimeData();
//	    	forecasts[i].setLabel(data[i].getLabel());
	    	forecasts[i].setTime(data[i].getTime());
	    	forecasts[i].setData(alpha * data[i].getData() + (1 - alpha) * forecasts[i-1].getData());
	    }
	    for (int i = n; i < n + numForecasts; i++) {
	    	forecasts[i] = new TimeData();
//	    	forecasts[i].setLabel("Forecast");
	    	forecasts[i].setData(Math.round(alpha * forecasts[i-1].getData() + (1 - alpha) * forecasts[i-n].getData()));
	    }
	    return forecasts;
	 }
	public static double calculateMAE(TimeData[] actualValues, TimeData[] forecastValues) {
//	    if (actualValues.length != forecastValues.length) {
//	        throw new IllegalArgumentException("Arrays must have same length");
//	    }
	    double sum = 0;
	    for (int i = 0; i < actualValues.length; i++) {
	        sum += Math.abs(actualValues[i].getData() - forecastValues[i].getData());
	    }
	    return sum / actualValues.length;
	}
}

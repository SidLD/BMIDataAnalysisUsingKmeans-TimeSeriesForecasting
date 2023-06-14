package Analysis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import Swing.RoundPanel;

public class CustomScatterContainer extends RoundPanel {
    
    private String title;
    private String xLabel;
    private String yLabel;
    private ArrayList<KMeansClusterPoint> data;
	private DecimalFormat df;
    
    public CustomScatterContainer(String title, String xLabel, String yLabel, ArrayList<KMeansClusterPoint> data) {
        this.title = title;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.data = data;
        df = new DecimalFormat();
		df.setMaximumFractionDigits(0);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int chartWidth = width - 100;
        int chartHeight = height - 100;
        int pointCount = getDataLength();
        double xMin = getDataOneMinValue();
        double xMax = getDataOneMaxValue();
        double yMin = getDataTwoMinValue();
        double yMax = getDataTwoMaxValue();
        double xRange = xMax - xMin;
        double yRange = yMax - yMin;
        double xStep = chartWidth / xRange;
        double yStep = chartHeight / yRange;
        int xOffset = (int) (width * .12);
        int yOffset = (int) (height - (height * .12));
        int pointX, pointY;
        
        // Draw title
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(title, width/2 - 50, 30);
        
        // Draw Y axis label
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString(yLabel, 10, height/2);
        
        // Draw X axis label
        g.drawString(xLabel, width/2 - 20, height - 10);
        
        

        g.setFont(new Font("Arial", Font.PLAIN, 10));
        // Draw X axis ticks and labels
        g.setColor(Color.BLACK);
        for (double x = xMin; x <= xMax; x++) {
            int tickX = xOffset + (int) ((x - xMin) * xStep);
            g.drawLine(tickX, yOffset, tickX, yOffset + 5);
            g.drawString(df.format(x), tickX - 8, yOffset + 20);
        }
        
        // Draw Y axis ticks and labels
        for (double y = yMin; y <= yMax; y++) {
            int tickY = yOffset - (int) ((y - yMin) * yStep);
            g.drawLine(xOffset, tickY, xOffset - 5, tickY);
            g.drawString(df.format(y), xOffset - 30, tickY + 5);
        }
        
        // Draw data points
        int colorIndex = 0;
        for (KMeansClusterPoint kMeansClusterPoint : data) {
        	int clusterOne = 0;
        	int clusterTwo = 0;
//			for (KMeansData temp : kMeansClusterPoint.getData()) {
//				double valueTwo = temp.getTwo();
//				double valueOne = temp.getOne();
//				int pointXCluster = xOffset + (int) ((valueOne - xMin) * xStep);
//				int pointYCluster = yOffset - (int) ((valueTwo - yMin) * yStep);
//				clusterOne += pointXCluster;
//				clusterTwo += pointYCluster;
//			}
			clusterOne = (clusterOne/kMeansClusterPoint.getData().size());
			clusterTwo = (clusterTwo/kMeansClusterPoint.getData().size());
			for (KMeansData temp : kMeansClusterPoint.getData()) {
				 double valueTwo = temp.getTwo();
				 double valueOne = temp.getOne();
				 pointX = xOffset + (int) ((valueOne - xMin) * xStep);
		         pointY = yOffset - (int) ((valueTwo - yMin) * yStep);
		         //*******
//		         g.drawLine((int)clusterOne,(int)clusterTwo, pointX, pointY);
		         //*******
		         g.setColor(getColor(colorIndex));
		         g.fillOval(pointX - 3, pointY - 3, 10, 10);
		         g.setColor(Color.black);
		         g.setFont(new Font("Arial", Font.PLAIN, 10));
		         g.drawString(temp.getLabel(), pointX-1, pointY + 14);
		         
		        
			}
			colorIndex++;
		}

    }

    private double getDataOneMinValue() {
        double min = Double.POSITIVE_INFINITY;
        for (KMeansClusterPoint kMeansClusterPoint : data) {
			for (KMeansData temp : kMeansClusterPoint.getData()) {
				 double value = temp.getOne();
			     if (value < min) {
			    	 min = value;
			     }
			}
		}
 
        return min-1;
    }
    
    private double getDataOneMaxValue() {
        double max = Double.NEGATIVE_INFINITY;
        for (KMeansClusterPoint kMeansClusterPoint : data) {
			for (KMeansData temp : kMeansClusterPoint.getData()) {
				 double value = temp.getOne();
			     if (value > max) {
			    	 max = value;
			     }
			}
		}
 
        return max+1;
    }
    
    private double getDataTwoMinValue() {
        double min = Double.POSITIVE_INFINITY;
        for (KMeansClusterPoint kMeansClusterPoint : data) {
			for (KMeansData temp : kMeansClusterPoint.getData()) {
				 double value = temp.getTwo();
			     if (value < min) {
			    	 min = value;
			     }
			}
		}
 
        return min-1;
    }
    
    private double getDataTwoMaxValue() {
        double max = Double.NEGATIVE_INFINITY;
        for (KMeansClusterPoint kMeansClusterPoint : data) {
			for (KMeansData temp : kMeansClusterPoint.getData()) {
				 double value = temp.getTwo();
			     if (value > max) {
			    	 max = value;
			     }
			}
		}
 
        return max+1;
    }
   
    
    private int getDataLength() {
    	int length = 0;
    	for (KMeansClusterPoint kMeansClusterPoint : data) {
			length += kMeansClusterPoint.getData().size();
		}
    	
    	return length;
    }
    private Color getColor(int index) {
		Color color = null;
		switch (index) {
		case 0: {
			color = new Color(3, 232, 252);
			break;
		}
		case 1: {
			color = new Color(49, 252, 3);
			break;
		}
		case 2: {
			color = new Color(252, 252, 3);
			break;
		}
		case 3: {
			color = new Color(51, 204, 255);
			break;
		}
		case 4: {
			color = new Color(37, 150, 190);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + index);
		}
		return color;
	}

}
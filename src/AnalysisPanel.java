
//
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.data.xy.XYSeriesCollection;

import Analysis.BaselineQuery;
import Analysis.CustomScatterContainer;
import Analysis.KMeans;
import Analysis.KMeansClusterPoint;
import Analysis.KMeansData;
import Analysis.KMeansScaling;
import Analysis.School;
import Analysis.TimeData;
import Analysis.TimeSeries;
import Analysis.Timeline;
import Charts.ModelChart;
import Swing.RoundPanel;
import database.BMISqlLite;
import database.HFASqlLite;
import database.SqlLite;
import database.StudentSqlLite;
import database.TimelineSqlLite;
import javaswingdev.chart.ModelPieChart;
import javaswingdev.chart.PieChart;
import javaswingdev.chart.PieChart.PeiChartType;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
//
public class AnalysisPanel extends RoundPanel {
	private JScrollPane MainScrollPane;
    private Charts.LineChart aboveNormalChart;
    private Charts.LineChart normalChart;
    private Charts.LineChart belowNormalChart;
    private JPanel TimeSeriesPanel;
    private JPanel ClusterPanel;
    private int selectedSchoolId;
    private int selectedBaselineId;
	private RoundPanel BMIPiePanel;
	private RoundPanel HFAPiePanel;
	private int selectedSchoolYearId;
	private JLabel titleLabel;
	private JLabel totalStudent;
	private JLabel girlsCountLabel;
	private JLabel heightCountLabel;
	private JLabel weightCountLabel;
	private JLabel boysCountLabel;
	private JLabel BMILabel;
	private JLabel lblHfa;
	private JComboBox forecastYear;

//    //1190s752
	public AnalysisPanel(int width, int height) {
		setBackground(new Color(0, 0, 0));
		width = 1190;
		height = 752;
		setSize(width,height);
		setLayout(null);
		
		
		
	    aboveNormalChart = new Charts.LineChart(Color.gray, Color.BLACK, 10f);
	    normalChart = new Charts.LineChart(Color.gray, Color.BLACK, 10f);
	    belowNormalChart =new Charts.LineChart(Color.gray, Color.BLACK, 10f);
		
		/*********************************** TOP PANEL *********************************/
		
		JPanel TopPanel = new RoundPanel();
		TopPanel.setBounds(0, 0, width-10, width/2);
		TopPanel.setLayout(null);
		
		
		ClusterPanel = new RoundPanel();
		ClusterPanel.setBounds(0, 5, (width-10)/2, (width-10)/2);
		ClusterPanel.setLayout(new BorderLayout(0, 0));
		TopPanel.add(ClusterPanel);		
		
		
		
		//Pie Data
				JPanel PiePanel = new RoundPanel();
				PiePanel.setBackground(Color.WHITE);
				PiePanel.setBounds(ClusterPanel.getWidth(), ClusterPanel.getHeight()/2, ClusterPanel.getWidth(), TopPanel.getHeight()/2);
				PiePanel.setLayout(null);
				TopPanel.add(PiePanel);
				
				BMIPiePanel = new RoundPanel();
				BMIPiePanel.setBounds(5, 5, PiePanel.getWidth()/2 - 10, PiePanel.getHeight() - 50);
				
				
				
				PiePanel.add(BMIPiePanel);
				
				HFAPiePanel = new RoundPanel();
				HFAPiePanel.setBounds( (PiePanel.getWidth()/2 - 10), 5,  PiePanel.getWidth()/2 - 10,  PiePanel.getHeight() -50 );
				PiePanel.add(HFAPiePanel);
				
				BMILabel = new JLabel("BMI");
				BMILabel.setHorizontalAlignment(SwingConstants.CENTER);
				BMILabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				BMILabel.setBounds(56, 252, 184, 40);
				PiePanel.add(BMILabel);
				
				lblHfa = new JLabel("HFA");
				lblHfa.setHorizontalAlignment(SwingConstants.CENTER);
				lblHfa.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblHfa.setBounds(341, 252, 184, 40);
				PiePanel.add(lblHfa);
		
		
				JPanel DataPanel = new RoundPanel();
				DataPanel.setBounds(ClusterPanel.getWidth(), 0, ClusterPanel.getWidth(), TopPanel.getHeight()/2);
				DataPanel.setLayout(null);
				TopPanel.add(DataPanel);
				
				titleLabel = new JLabel("New label");
				titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				titleLabel.setBounds(224, 57, 241, 40);
				DataPanel.add(titleLabel);
				
				boysCountLabel = new JLabel("New label");
				boysCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				boysCountLabel.setBounds(97, 108, 184, 40);
				DataPanel.add(boysCountLabel);
				
				girlsCountLabel = new JLabel("New label");
				girlsCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				girlsCountLabel.setBounds(97, 183, 184, 40);
				DataPanel.add(girlsCountLabel);
				
				weightCountLabel = new JLabel("New label");
				weightCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				weightCountLabel.setBounds(305, 108, 184, 40);
				DataPanel.add(weightCountLabel);
				
				heightCountLabel = new JLabel("New label");
				heightCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				heightCountLabel.setBounds(305, 183, 184, 40);
				DataPanel.add(heightCountLabel);
				
				totalStudent = new JLabel("New label");
				totalStudent.setFont(new Font("Tahoma", Font.PLAIN, 15));
				totalStudent.setBounds(186, 234, 184, 40);
				DataPanel.add(totalStudent);
				
				JButton refreshBtn = new JButton("Refresh");
				refreshBtn.setBounds(10, 11, 89, 23);
				DataPanel.add(refreshBtn);
				
				forecastYear = new JComboBox(new String[] {"1","2"});
				forecastYear.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						try {
							int n = Integer.parseInt(e.getItem().toString());

							aboveNormalChartInit(n);
							normalChartInit(n);
							belowNormalChartInit(n);
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				});
				forecastYear.setBounds(197, 11, 66, 23);
				DataPanel.add(forecastYear);
				
				JLabel lblNewLabel = new JLabel("Forecast Year:");
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblNewLabel.setBounds(109, 15, 89, 14);
				DataPanel.add(lblNewLabel);
				refreshBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(selectedSchoolId > 0 && selectedSchoolYearId > 0) {
							try {
								initPieData();
								initClusterPlot();
								aboveNormalChartInit(1);
								normalChartInit(1);
								belowNormalChartInit(1);
								initData();
							} catch (Exception e2) {
								// TODO: handle exception
								e2.printStackTrace();
							}
						}
					}
				});
		
		
		
		
		
		
		
		
		/*********************************** BOTTOM PANEL *********************************/
		TimeSeriesPanel = new RoundPanel();
		TimeSeriesPanel.setBackground(Color.LIGHT_GRAY);
		TimeSeriesPanel.setLayout(null);
		TimeSeriesPanel.setBounds(0, TopPanel.getHeight()+5, width-10, 1200);

		int TimeSeriesHeight = (TimeSeriesPanel.getHeight()/3);
		
		JPanel AboveNormalPanel = new RoundPanel();
		AboveNormalPanel.setBounds(0, 5, TimeSeriesPanel.getWidth()-20, TimeSeriesHeight-10);
		AboveNormalPanel.setLayout(new BorderLayout(0, 0));
		AboveNormalPanel.add(aboveNormalChart);
		TimeSeriesPanel.add(AboveNormalPanel);
		
		JPanel NormalPanel = new RoundPanel();
		NormalPanel.setLayout(new BorderLayout(0, 0));
		NormalPanel.add(normalChart);
		NormalPanel.setBounds(0, (TimeSeriesHeight)+5, TimeSeriesPanel.getWidth()-20, TimeSeriesHeight-10);
		TimeSeriesPanel.add(NormalPanel);
		
		JPanel BelowNormalPanel = new RoundPanel();
		BelowNormalPanel.setLayout(new BorderLayout(0, 0));
		BelowNormalPanel.add(belowNormalChart);
		BelowNormalPanel.setBounds(0, (TimeSeriesHeight*2)+5, TimeSeriesPanel.getWidth()-20, TimeSeriesHeight-10);
		TimeSeriesPanel.add(BelowNormalPanel);
		
		
		
		final JPanel MainPanel = new RoundPanel();
		MainPanel.setLayout(null);
		MainPanel.setPreferredSize(new Dimension(width-10, TopPanel.getHeight() + TimeSeriesPanel.getHeight()));
		MainPanel.add(TopPanel);
		
		
		MainPanel.add(TimeSeriesPanel);
		
		
		MainScrollPane = new javax.swing.JScrollPane(MainPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel Container = new RoundPanel();
		Container.setBounds(5, 5, width-10, height-40);
		Container.setLayout(new BorderLayout(0, 0));
		Container.add(BorderLayout.CENTER, MainScrollPane);
		add(Container);
		
		
	}
	public void setName(String name) {
		this.selectedSchoolId = Integer.parseInt(name.split("-")[0]);
		this.selectedSchoolYearId = Integer.parseInt(name.split("-")[1]);

		try {
			initPieData();
			initClusterPlot();
			aboveNormalChartInit(1);
			normalChartInit(1);
			belowNormalChartInit(1);
			initData();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private Color getColor(int index) {
		Color color = null;
		switch (index) {
		case 0: {
			color = new Color(255, 0, 0);
			break;
		}
		case 1: {
			color = new Color(0, 255, 0);
			break;
		}
		case 2: {
			color = new Color(0, 0, 204);
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

	private boolean cluserIsEqual(ArrayList<KMeansData> clusteredData,
			ArrayList<KMeansData> newClusterPoints) throws Exception {
		boolean isMatch = true;
		if(clusteredData.size() != newClusterPoints.size()) {
			throw new Exception("Exception message");
		}else {
			for (int i = 0; i < clusteredData.size(); i++) {
				if((clusteredData.get(i).getOne() != newClusterPoints.get(i).getOne()) && (clusteredData.get(i).getTwo() != newClusterPoints.get(i).getTwo())) {
					return false;
				}
				
			}
		}
		return isMatch;
	}
	private void aboveNormalChartInit(int windowSize) {
		aboveNormalChart.clear();
		aboveNormalChart.setBounds(10, 11, 593, 392);
				
		aboveNormalChart.addLegend("Above Normal", getColor(2), getColor(0));
		
		
		try {
			TimeData original[] = new BMISqlLite().getTimeData(this.selectedSchoolId, "OVERWEIGHT", "OBESE");
			TimeData[] forecasts = new TimeSeries().forecast(original, windowSize);
		      for(int i = 0; i < forecasts.length;i++) {
		    	  if(i < original.length) {
		    		  aboveNormalChart.addData(new ModelChart(original[i].getTime(), new double[] {forecasts[i].getData()}));
		    	  }else {
		    		  aboveNormalChart.addData(new ModelChart("Forecasts", new double[] {forecasts[i].getData()}));
		    	  }
		      }
		      aboveNormalChart.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void normalChartInit(int windowSize) {
		normalChart.clear();
		normalChart.setBounds(10, 11, 593, 392);
				
		normalChart.addLegend("Normal", getColor(1), getColor(0));
		
		
		try {
			TimeData original[] = new BMISqlLite().getTimeData(this.selectedSchoolId, "NORMAL", "NORMAL");
			TimeData[] forecasts = new TimeSeries().forecast(original, windowSize);
		     
			for(int i = 0; i < forecasts.length;i++) {
		    	  if(i < original.length) {
		    		  normalChart.addData(new ModelChart(original[i].getTime(), new double[] {forecasts[i].getData()}));
		    	  }else {
		    		  normalChart.addData(new ModelChart("Forecasts", new double[] {forecasts[i].getData()}));
		    	  }
		      }
		      normalChart.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void belowNormalChartInit(int windowSize) {
		belowNormalChart.clear();
		belowNormalChart.setBounds(10, 11, 593, 392);
				
		belowNormalChart.addLegend("Below  Normal", getColor(3), getColor(0));
		
		
		try {
			TimeData original[] = new BMISqlLite().getTimeData(this.selectedSchoolId, "WASTED", "SEVERELY_WASTED");
			TimeData[] forecasts = new TimeSeries().forecast(original, windowSize);
		     
			for(int i = 0; i < forecasts.length;i++) {
		    	  if(i < original.length) {
		    		  belowNormalChart.addData(new ModelChart(original[i].getTime(), new double[] {forecasts[i].getData()}));
		    	  }else {
		    		  belowNormalChart.addData(new ModelChart("Forecasts", new double[] {forecasts[i].getData()}));
		    	  }
		      }
		      belowNormalChart.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void initPieData() {
		int totalSeverlyWasted = new BMISqlLite().getTotalSeverelyWasted(this.selectedSchoolId, this.selectedSchoolYearId);
		int totalWasted = new BMISqlLite().getTotalWasted(this.selectedSchoolId, this.selectedSchoolYearId);
		int totalNormalBMI = new BMISqlLite().getTotalNormalBMI(this.selectedSchoolId, this.selectedSchoolYearId);
		int totalOverweight = new BMISqlLite().getTotalOverweightBMI(this.selectedSchoolId, this.selectedSchoolYearId);
		int totalObese = new BMISqlLite().getTotalObesseBMI(this.selectedSchoolId, this.selectedSchoolYearId);;
		
		int totalSeverelyStunted = new HFASqlLite().getTotalSeverelyStuntedHFA(this.selectedSchoolId, this.selectedSchoolYearId);
		int totalStunted = new HFASqlLite().getTotalStuntedHFA(this.selectedSchoolId, this.selectedSchoolYearId);
		int totalNormalHFA = new HFASqlLite().getTotalNormalHFA(this.selectedSchoolId, this.selectedSchoolYearId);
		int totalTall = new HFASqlLite().getTotalTallHFA(this.selectedSchoolId, this.selectedSchoolYearId);
		
		
		PieChart bmiPieChart = new PieChart();
		bmiPieChart.setBounds(0, 0, 385, 356);
		bmiPieChart.setChartType(PeiChartType.DONUT_CHART);
		bmiPieChart.setBackground(Color.BLACK);

		bmiPieChart.addData(new ModelPieChart("Severely Wasted", totalSeverlyWasted, getColor(0)));
		bmiPieChart.addData(new ModelPieChart("Wasted", totalWasted, getColor(1)));
		bmiPieChart.addData(new ModelPieChart("Normal", totalNormalBMI, getColor(2)));
		bmiPieChart.addData(new ModelPieChart("Overweight", totalOverweight, getColor(3)));
		bmiPieChart.addData(new ModelPieChart("Obese", totalObese, getColor(4)));
		
		BMIPiePanel.setLayout(new BorderLayout(0, 0));
		HFAPiePanel.setLayout(new BorderLayout(0, 0));
		
		PieChart hfaPieChart = new PieChart();
		hfaPieChart.setBounds(0, 0, 385, 356);
		hfaPieChart.setChartType(PeiChartType.DONUT_CHART);
		hfaPieChart.setBackground(Color.BLACK);

		hfaPieChart.addData(new ModelPieChart("Severly Stunted", totalSeverelyStunted, getColor(0)));
		hfaPieChart.addData(new ModelPieChart("Stunted", totalStunted, getColor(1)));
		hfaPieChart.addData(new ModelPieChart("Normal", totalNormalHFA, getColor(2)));
		hfaPieChart.addData(new ModelPieChart("Tall", totalTall, getColor(3)));
		
		
		BMIPiePanel.removeAll();
		HFAPiePanel.removeAll();
		BMIPiePanel.add(bmiPieChart);
		HFAPiePanel.add(hfaPieChart);
		repaint();
	}
	private void initClusterPlot() {
		String title = "BMI And Age Clustering";

		KMeans kMean = new KMeans(3);	
		try {
			 ArrayList<KMeansData> data = new BMISqlLite().getStudentsKmeansData(this.selectedSchoolId, this.selectedSchoolYearId);

			 
			 data = new KMeansScaling().scaleData(data, 1, 0);
			 
			    ArrayList<KMeansData> prevClusterPoints = kMean.initClusterPoint(data);
			    ArrayList<KMeansClusterPoint> prevResult = kMean.initAssignDataToCluster(data, prevClusterPoints);
			    ArrayList<KMeansData> prevNewClusterPoints = kMean.getNewClusterPoint(prevResult);  

			      	try {
						while(!(cluserIsEqual(prevClusterPoints, prevNewClusterPoints))) {
							prevClusterPoints = prevNewClusterPoints;
							prevResult = kMean.initAssignDataToCluster(data, prevClusterPoints);
							prevNewClusterPoints = kMean.getNewClusterPoint(prevResult);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			      	try {
			      		CustomScatterContainer scatterPlot = new CustomScatterContainer(title, "BMI", "AGE", prevResult);
				      	scatterPlot.setBackground(Color.WHITE);
				      	ClusterPanel.removeAll();
				      	ClusterPanel.add(scatterPlot);
				      	ClusterPanel.repaint();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
			
		} catch (Exception e) {
			ClusterPanel.removeAll();
			ClusterPanel.repaint();
			e.printStackTrace();
		} 
	}
	private void initData() {
		Timeline timeline = new TimelineSqlLite().getTimelineData(this.selectedSchoolYearId);
		titleLabel.setText(timeline.getSchool_year() + " " +timeline.getType());
		
		int studentNumber = new StudentSqlLite().countStudent(this.selectedSchoolId, this.selectedSchoolYearId);
		totalStudent.setText("Total Student: "+studentNumber);
		
		int getFemaleCount = new StudentSqlLite().countStudent(this.selectedSchoolId, this.selectedSchoolYearId, "F");
		girlsCountLabel.setText("Total Female "+getFemaleCount);
		
		
		int heightTaken = new StudentSqlLite().countStudentHeightTaken(this.selectedSchoolId, this.selectedSchoolYearId);
		heightCountLabel.setText("Total height Taken "+heightTaken);
		
		int weightTaken = new StudentSqlLite().countStudentWeightTaken(this.selectedSchoolId, this.selectedSchoolYearId);
		weightCountLabel.setText("Total weight Taken "+weightTaken);
		
		int getMaleCount = new StudentSqlLite().countStudent(this.selectedSchoolId, this.selectedSchoolYearId, "M");
		boysCountLabel.setText("Total Male "+getMaleCount);
	}
}

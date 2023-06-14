
import java.awt.EventQueue;



import Swing.JnaFileChooser.Mode;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Analysis.Grade;
import Analysis.School;
import Analysis.Timeline;
import Swing.ButtonMenu;
import Swing.JnaFileChooser;
import Swing.RoundPanel;
import Swing.datechooser.DateChooser;
import database.BMIForAgeBoys;
import database.BMIForAgeGirls;
import database.GradeSqlLite;
import database.HFAForAgeBoys;
import database.HFAForAgeGirls;
import database.SchoolSqlLite;
import database.SectionSqlLite;
import database.SqlLite;
import database.StudentSqlLite;
import database.TimelineSqlLite;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.awt.event.ItemEvent;

public class MainWindow {

	private JFrame frame;
	private Color mainColor = new Color(0, 102, 255);
	private JPanel searchEditPanel;
	private JPanel importExcelPanel;
	private JPanel analysisPanel;
	private final String divisions[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
	private JComboBox schoolComboBox;

	private int selectedSchoolId;
	private int selectedSchoolYear;
	private JComboBox schoolYearComboBox;

	private DecimalFormat BMIFormat;
	private DecimalFormat heightFormat;
	
	
	private boolean isInit = true;
	private ButtonMenu generateReport;
	private ButtonMenu newSectionBtn;
	private ButtonMenu newGradeBtn;
	private ButtonMenu importExlBtn;
	private JButton searchEditBtn;
	private ButtonMenu analysisBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		frame = new JFrame();
		frame.setBackground(new Color(0, 0, 51));
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setUndecorated(true);
		frame.setSize(r.width, r.height);
		frame.setVisible(true);

//		new BMIForAgeGirls().init();
//		new HFAForAgeBoys().init();
//		new HFAForAgeGirls().init();
//		new BMIForAgeBoys().init();
		
		
		frame.getContentPane().setLayout(null);
		
		BMIFormat = new DecimalFormat();
		BMIFormat.setMaximumFractionDigits(2);
		

		heightFormat = new DecimalFormat();
		heightFormat.setMaximumFractionDigits(2);
		int SideBarWidth = frame.getWidth()/8;
		int SideBarHeight = frame.getHeight();
		int MainWidth = SideBarWidth * 7;
		int MainHeight = frame.getHeight();
		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		// ---- Main Panel Intro
	
		JTabbedPane MainPanel = new JTabbedPane(JTabbedPane.TOP);
		MainPanel.setBounds(SideBarWidth, -24, MainWidth, MainHeight+24);
		MainPanel.setBorder(null);
		MainPanel.setBackground(Color.BLACK);
		MainPanel.setOpaque(false);
		frame.getContentPane().add(MainPanel);
		
		
		analysisPanel = new AnalysisPanel(MainPanel.getWidth(), MainPanel.getHeight());
		MainPanel.addTab("Analysis", null, analysisPanel, null);

		
		searchEditPanel = new SearchEditPanel(MainPanel.getWidth(), MainPanel.getHeight());
		MainPanel.addTab("Search", null, searchEditPanel, null);

		JFrame thisFrame = this.frame;
		importExcelPanel = new ImportExcelPanel(MainPanel.getWidth(), MainPanel.getHeight(), thisFrame);
		MainPanel.addTab("Import", null, importExcelPanel, null);
		
//		overviewPanel = new OverviewPanel(MainPanel.getWidth(), MainPanel.getHeight());
//		MainPanel.addTab("Overview", null, overviewPanel, null);
		
		
		// ---- Side Bar Intro Base
		
		JPanel SidebarIntro = new RoundPanel();
		SidebarIntro.setBackground(mainColor);
		SidebarIntro.setBounds(5, 5, SideBarWidth - 10, (SideBarHeight / 3) -10);
		frame.getContentPane().add(SidebarIntro);
		SidebarIntro.setLayout(null);
		
		schoolComboBox = new JComboBox();
		schoolComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				String school = schoolComboBox.getSelectedItem().toString();
				String[] sc = school.split("-");
				selectedSchoolId = Integer.parseInt(sc[sc.length-1]);
				initSchoolYearComboBox(selectedSchoolId);
				initPanels();
			}
		});
		schoolComboBox.setBounds(10, 23, 140, 22);
		SidebarIntro.add(schoolComboBox);
		
		schoolYearComboBox = new JComboBox();
		schoolYearComboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				try {
					String school = schoolYearComboBox.getSelectedItem().toString();
					String[] sc = school.split("-");
					selectedSchoolYear = Integer.parseInt(sc[sc.length-1]);
					initPanels();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		schoolYearComboBox.setBounds(10, 56, 140, 22);
		SidebarIntro.add(schoolYearComboBox);
		
		
		ButtonMenu btnmnNewSchool = new ButtonMenu();
		btnmnNewSchool.setBounds(10, 89, 144, 60);
		SidebarIntro.add(btnmnNewSchool);
		btnmnNewSchool.setIcon(new ImageIcon(getClass().getResource("/img/student.png")));
		btnmnNewSchool.setText(" New School");
		btnmnNewSchool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel newEventPanel = new RoundPanel();
				newEventPanel.setLayout(null);
				newEventPanel.setSize(new Dimension(200, 200));
				
				JLabel lblFirstName = new JLabel("Division :");
				lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblFirstName.setBounds(20, 35, 145, 43);
				newEventPanel.add(lblFirstName);
				
				JLabel lblLastName = new JLabel("School:");
				lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblLastName.setBounds(20, 108, 145, 43);
				newEventPanel.add(lblLastName);

				JComboBox<Object> divisionComboBox = new JComboBox<Object>(divisions);
				divisionComboBox.setBounds(90, 35, 145, 43);
				newEventPanel.add(divisionComboBox);
				
				JTextField schoolField = new JTextField("");
				schoolField.setColumns(10);
				schoolField.setBounds(90, 114, 150, 30);
				newEventPanel.add(schoolField);
				UIManager.put("OptionPane.minimumSize",new Dimension(400, 250));      
				int result = JOptionPane.showConfirmDialog(null, newEventPanel, "Create",JOptionPane.OK_CANCEL_OPTION);
				UIManager.put("OptionPane.minimumSize",new Dimension(200, 100)); 
				if(JOptionPane.YES_OPTION == result) {
					String school = schoolField.getText();
					String division = divisionComboBox.getSelectedItem().toString();
					if(!(new SchoolSqlLite().getSchool(school) == null)) {
						JOptionPane.showMessageDialog(null, "Already Exist");
					}else {
						if(school.length() > 3 && division != null) {
							if(new SchoolSqlLite().addSchool(school, division)) {
								School newSchool = new SchoolSqlLite().getSchool(school);
								schoolComboBox.addItem(newSchool.getName()+"-"+newSchool.getId());
								JOptionPane.showMessageDialog(null, "Success");
							}else {
								JOptionPane.showMessageDialog(null, "Something Went Wrong");
							}
						}else {
							JOptionPane.showMessageDialog(null, "Invalid Data");
						}
					}
				}
			}
		});
		btnmnNewSchool.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		ButtonMenu newTimeLineBtn = new ButtonMenu();
		newTimeLineBtn.setBounds(10, 144, 144, 60);
		SidebarIntro.add(newTimeLineBtn);
		newTimeLineBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JPanel newEventPanel = new RoundPanel();
				newEventPanel.setSize(new Dimension(200, 200));
				newEventPanel.setLayout(null);

				JLabel lblFirstName = new JLabel("S.Y. :");
				lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblFirstName.setBounds(20, 35, 145, 43);
				newEventPanel.add(lblFirstName);
				
				JLabel lblLastName = new JLabel("Type :");
				lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblLastName.setBounds(20, 108, 145, 43);
				newEventPanel.add(lblLastName);

				JComboBox<Object> divisionComboBox = new JComboBox<Object>(new String[]{"Endline","Baseline"}) ;
				divisionComboBox.setBounds(90, 114, 145, 43);
				newEventPanel.add(divisionComboBox);
				
				JTextField yearOneField = new JTextField("");
				yearOneField.setFont(new Font("Tahoma", Font.PLAIN, 15));
				yearOneField.setColumns(5);
				yearOneField.setBounds(90, 35, 100, 43);
				newEventPanel.add(yearOneField);
				
				JLabel hypen = new JLabel("-");
				hypen.setFont(new Font("Tahoma", Font.PLAIN, 15));
				hypen.setBounds(195, 35, 100, 43);
				newEventPanel.add(hypen);
				
				
				JTextField yearTwoField = new JTextField("");
				yearTwoField.setColumns(5);
				yearTwoField.setFont(new Font("Tahoma", Font.PLAIN, 15));
				yearTwoField.setBounds(205, 35, 100, 43);
				newEventPanel.add(yearTwoField);
				
				
				UIManager.put("OptionPane.minimumSize",new Dimension(400, 250));  
				int result = JOptionPane.showConfirmDialog(null, newEventPanel, "New Timeline",JOptionPane.OK_CANCEL_OPTION);		
				UIManager.put("OptionPane.minimumSize",new Dimension(200, 100));
				if(result == JOptionPane.OK_OPTION) {
					try {
						int year1 = Integer.parseInt(yearOneField.getText());
						int year2 = Integer.parseInt(yearTwoField.getText());
						String type = divisionComboBox.getSelectedItem().toString();
						if(!(year1 > 2000 && (year1+1 == year2))) {
							JOptionPane.showMessageDialog(null, "Invalid School Year");
						}else {
							String sy = Integer.toString(year1)+"-"+Integer.toString(year2);
							int testId = new TimelineSqlLite().getTimelineId(sy, type, selectedSchoolId);
							if(testId == -1) {
								int syId = new TimelineSqlLite().addTimeline(sy, type, selectedSchoolId);
								if(syId != -1) {
									JOptionPane.showMessageDialog(null, "Success");
									initSchoolYearComboBox(selectedSchoolId);
								}else {
									JOptionPane.showMessageDialog(null, "Something Went Wrong");
								}
							}else {
								JOptionPane.showMessageDialog(null, sy+"-"+type+" already Exist");
							}
						}
						
					} catch (Exception e2) {
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null, "Invalid Data");
					}
				
				}
				
			}
		});
		newTimeLineBtn.setText("  School Year");
		newTimeLineBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		// ---- Side Bar Menu Base
		
		RoundPanel SideBarMenu = new RoundPanel();
		SideBarMenu.setBackground(mainColor);
		SideBarMenu.setBounds(5, SidebarIntro.getHeight()+10,  SideBarWidth - 10, ((SideBarHeight / 3)*2) - 10);
		frame.getContentPane().add(SideBarMenu);
		SideBarMenu.setLayout(null);
		
		
		// Analysis
		
		analysisBtn = new ButtonMenu();
		analysisBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPanel.setSelectedIndex(0);
			}
		});
		analysisBtn.setIcon(new ImageIcon(getClass().getResource("/img/backup.png")));
		analysisBtn.setText("  "+"Analysis");
		analysisBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		analysisBtn.setBounds(10, 11, 144, 60);
		SideBarMenu.add(analysisBtn);

		
		//SEARCH
		
		searchEditBtn = new ButtonMenu();
		searchEditBtn.setIcon(new ImageIcon(getClass().getResource("/img/search.png")));
		searchEditBtn.setText("Search/Edit");
		searchEditBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		searchEditBtn.setBounds(10, 78,144, 60);
		searchEditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPanel.setSelectedIndex(1);
			}
		});
		searchEditBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		SideBarMenu.add(searchEditBtn);
		
		importExlBtn = new ButtonMenu();
		importExlBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPanel.setSelectedIndex(2);
			}
		});
		importExlBtn.setIcon(new ImageIcon(getClass().getResource("/img/import.png")));
		importExlBtn.setText(" Import");
		importExlBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		importExlBtn.setBounds(10, 139, 144, 60);
		SideBarMenu.add(importExlBtn);
		
		ButtonMenu logOutBtn = new ButtonMenu();
		logOutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				frame.dispose();
			}
		});
		logOutBtn.setIcon(new ImageIcon(getClass().getResource("/img/logout.png")));
		logOutBtn.setText("Close");
		logOutBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		logOutBtn.setBounds(0, 413, SideBarWidth, 50);
		SideBarMenu.add(logOutBtn);
		
		newGradeBtn = new ButtonMenu();
		newGradeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel newEventPanel = new RoundPanel();
				newEventPanel.setLayout(null);
				newEventPanel.setSize(new Dimension(200, 200));
				
				
				String school = schoolComboBox.getSelectedItem().toString();
				String[] sc = school.split("-");
				
				String syString = schoolYearComboBox.getSelectedItem().toString();
				String[] sy = syString.split("-");
				
				
				JLabel lblFirstName = new JLabel("School: "+sc[0] +"      SY: "+sy[0]+"-"+sy[1]);
				lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblFirstName.setBounds(20, 35, 300, 43);
				newEventPanel.add(lblFirstName);
				
				JLabel lblLastName = new JLabel("Grade:");
				lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblLastName.setBounds(20, 108, 145, 43);
				newEventPanel.add(lblLastName);
				
				JTextField gradeField = new JTextField("");
				gradeField.setColumns(10);
				gradeField.setBounds(90, 114, 150, 30);
				newEventPanel.add(gradeField);
				
				
				UIManager.put("OptionPane.minimumSize",new Dimension(400, 250));      
				int result = JOptionPane.showConfirmDialog(null, newEventPanel, "Create",JOptionPane.OK_CANCEL_OPTION);
				UIManager.put("OptionPane.minimumSize",new Dimension(200, 100)); 
				if(JOptionPane.YES_OPTION == result) {
					String grade = gradeField.getText();
					try {
						if(grade.length() > 3) {
							int testId = new GradeSqlLite().getGradeId(selectedSchoolId, selectedSchoolYear, grade);
							if(testId == -1) {
								int newId = new GradeSqlLite().addGrade(selectedSchoolId, selectedSchoolYear, grade);
								if(newId > -1) {
									JOptionPane.showMessageDialog(null, "Success");
									initPanels();
								}else {
									JOptionPane.showMessageDialog(null, "Something Went Wrong");
								}
							}else {
								JOptionPane.showMessageDialog(null, "Grade Already Exist");
							}
							
						}else {
							JOptionPane.showMessageDialog(null, "Invalid Data");
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}	
				}
			}	
		});
		newGradeBtn.setIcon(new ImageIcon(getClass().getResource("/img/student.png")));
		newGradeBtn.setText("New Grade");
		newGradeBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		newGradeBtn.setBounds(10, 206, 144, 60);
		SideBarMenu.add(newGradeBtn);
		
		newSectionBtn = new ButtonMenu();
		newSectionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel newEventPanel = new RoundPanel();
				newEventPanel.setLayout(null);
				newEventPanel.setSize(new Dimension(200, 200));
				
				JLabel lblFirstName = new JLabel("Grade :");
				lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblFirstName.setBounds(20, 35, 145, 43);
				newEventPanel.add(lblFirstName);
				
				JLabel lblLastName = new JLabel("Section:");
				lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblLastName.setBounds(20, 108, 145, 43);
				newEventPanel.add(lblLastName);
				
				JTextField sectionField = new JTextField("");
				sectionField.setColumns(10);
				sectionField.setBounds(90, 114, 150, 30);
				newEventPanel.add(sectionField);

				ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYear);
				String gradeArray[] = new String[grades.size()];
				for (int i = 0; i < gradeArray.length; i++) {
					gradeArray[i] =  grades.get(i).getName()+"-"+grades.get(i).getId();
				}
				JComboBox<Object> gradeComboBox = new JComboBox<Object>(gradeArray);
				gradeComboBox.setBounds(90, 35, 145, 43);
				newEventPanel.add(gradeComboBox);
				
				
				UIManager.put("OptionPane.minimumSize",new Dimension(400, 250));      
				int result = JOptionPane.showConfirmDialog(null, newEventPanel, "Create",JOptionPane.OK_CANCEL_OPTION);
				UIManager.put("OptionPane.minimumSize",new Dimension(200, 100)); 
				if(JOptionPane.YES_OPTION == result) {
					String selectedGrade = gradeComboBox.getSelectedItem().toString();
					String temp[] = selectedGrade.split("-");
					int gradeId = Integer.parseInt(temp[temp.length-1]);
					String section = sectionField.getText();
					try {
						if(section.length() > 0) {
							int testId = new SectionSqlLite().getSectionId(gradeId, selectedSchoolId, section);
							if(testId == -1) {
								int id = new SectionSqlLite().addSection(selectedSchoolId, gradeId, section);
								if(id > 0) {
									JOptionPane.showMessageDialog(null, "Success");	
									initPanels();
								}else {
									JOptionPane.showMessageDialog(null, "Something Went Wrong");
								}
								
						
							}else {
								
							}
						}else {
							JOptionPane.showMessageDialog(null, "Invalid Data");
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}	
				}
			}
		});
		newSectionBtn.setIcon(new ImageIcon(getClass().getResource("/img/student.png")));
		newSectionBtn.setText(" New Section");
		newSectionBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		newSectionBtn.setBounds(10, 267, 144, 60);
		SideBarMenu.add(newSectionBtn);
		
		generateReport = new ButtonMenu();
		generateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					JPanel newEventPanel = new RoundPanel();
					newEventPanel.setSize(new Dimension(200, 100));
					
					JComboBox baselineComboBox = new JComboBox();
					ArrayList<Timeline> schools = new TimelineSqlLite().getTimelines(selectedSchoolId);
					for (Timeline t : schools) {
						baselineComboBox.addItem(t.getSchool_year() + "-"+t.getType()+"-"+t.getId());
					}
				
					newEventPanel.add(baselineComboBox);
					UIManager.put("OptionPane.minimumSize",new Dimension(100, 100));      
					int result = JOptionPane.showConfirmDialog(null, newEventPanel, "Generate Summary",JOptionPane.OK_CANCEL_OPTION);
					UIManager.put("OptionPane.minimumSize",new Dimension(200, 100)); 
					if(JOptionPane.YES_OPTION == result) {

						XSSFWorkbook workbook = new XSSFWorkbook();
					    XSSFSheet sheet = workbook.createSheet("Summary");
					    
					    CellStyle cellStyle1 = workbook.createCellStyle();
						cellStyle1.setWrapText(true);
						cellStyle1.setAlignment(HorizontalAlignment.JUSTIFY);
					    
					    int rowCountHeader = -1;
						int colCountHeader = -1;
						
						Row schoolHeader = sheet.createRow(++rowCountHeader);
						Cell school = schoolHeader.createCell(0);
						School s = new SchoolSqlLite().getSchool(selectedSchoolId);
						school.setCellValue((String) "School :"+ s.getName());
						
						Row divH = sheet.createRow(++rowCountHeader);
						Cell div = divH.createCell(0);
						div.setCellValue((String) " Division :"+s.getDivision());
						
						Row titleHeader = sheet.createRow(++rowCountHeader);
						Cell titleHeaderR = titleHeader.createCell(0);
						Timeline t = new TimelineSqlLite().getTimelineData(selectedSchoolYear);
						titleHeaderR.setCellValue((String) t.getSchool_year() + " " + t.getType());
						
						Row rowHeader = sheet.createRow(++rowCountHeader);
						String header[] = {"Grade Level", "Gender", "Enrollment", 
								"Pupils Weighed", "%", 
								"Severely Wasted", 	"%", "Wasted" , "%", "Normal", "%", "Overweight" , "%", "Obese" , "%",
								"Severely Stunted", "%", "Stunted", "%", "Normal", "%", "Tall", 	   "%", 
								"Pupils Taken Height", "%"
								};
						
						for (String string : header) {
							Cell cellHeader = rowHeader.createCell(++colCountHeader);
							cellHeader.setCellValue((String) string);
						}

						int rowCount = rowCountHeader;
						ArrayList<Grade> grades= new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYear);
						for (Grade g : grades) {
							String []gender = {"M", "F", "Total"};
							for (String gen : gender) {
								Row row = sheet.createRow(++rowCount);
								int columnCount = -1;
								try {
									int totalWeighted = new StudentSqlLite().countStudentWeightTaken(g.getId(), gen);
									int totalHeighTaken =  new StudentSqlLite().countStudentHeightTaken(g.getId(), gen);
									int totalStudentWithGender =  new StudentSqlLite().countStudent(g.getId(), gen);

										Cell cellGrade = row.createCell(++columnCount);
										if(gen == "F") {
											cellGrade.setCellValue((String) g.getName());
											cellGrade.setCellStyle(cellStyle1);
										}else {
											cellGrade.setCellValue((String) "");
										}
										
										Cell cellGender = row.createCell(++columnCount);
										cellGender.setCellValue((String) gen);
										cellGender.setCellStyle(cellStyle1);
										Cell enrollment = row.createCell(++columnCount);
										enrollment.setCellValue((Integer) totalStudentWithGender);
										enrollment.setCellStyle(cellStyle1);
										
										Cell pupilsWeighted = row.createCell(++columnCount);
										pupilsWeighted.setCellValue((Integer) totalWeighted);
										pupilsWeighted.setCellStyle(cellStyle1);
										Cell pupilsWeightedPercentage = row.createCell(++columnCount);
										try {
											pupilsWeightedPercentage.setCellValue((String) BMIFormat.format((totalWeighted * 100f / totalStudentWithGender))+ "%");
											pupilsWeightedPercentage.setCellStyle(cellStyle1);
										} catch (Exception e2) {
											pupilsWeightedPercentage.setCellValue((String) "0.0%");

										}
										
										
										int totalSeverelyWasted = new StudentSqlLite().countGradeBMI(g.getId(), gen, "SEVERELY_WASTED" );
										Cell severelyWasted = row.createCell(++columnCount);
										severelyWasted.setCellValue((Integer) totalSeverelyWasted);
										severelyWasted.setCellStyle(cellStyle1);
										Cell severelyWastedPercentage = row.createCell(++columnCount);
										try {
											severelyWastedPercentage.setCellValue((String) BMIFormat.format((totalSeverelyWasted * 100f / totalWeighted))+ "%");
											severelyWastedPercentage.setCellStyle(cellStyle1);
										} catch (Exception e2) {
											severelyWastedPercentage.setCellValue((String) "0.0%");
										}
										
										int totalWasted = new StudentSqlLite().countGradeBMI(g.getId(), gen, "WASTED" );
										Cell wasted = row.createCell(++columnCount);
										wasted.setCellValue((Integer) totalWasted);
										wasted.setCellStyle(cellStyle1);
										Cell wastedPercentage = row.createCell(++columnCount);
										try {
											wastedPercentage.setCellValue((String) BMIFormat.format((totalWasted * 100f / totalWeighted))+ "%");
											wastedPercentage.setCellStyle(cellStyle1);
										} catch (Exception e2) {
											wastedPercentage.setCellValue((String) "0.0%");

										}
										
										int normalBMI = new StudentSqlLite().countGradeBMI(g.getId(), gen, "NORMAL" );
										Cell normalBMICell = row.createCell(++columnCount);
										normalBMICell.setCellValue((Integer) normalBMI);
										normalBMICell.setCellStyle(cellStyle1);
										

										Cell normalBMICellPercentage = row.createCell(++columnCount);
										try {
											normalBMICellPercentage.setCellValue((String) BMIFormat.format((normalBMI * 100f / totalWeighted))+ "%");
											normalBMICellPercentage.setCellStyle(cellStyle1);
										} catch (Exception e2) {
											normalBMICellPercentage.setCellValue((String) "0.0%");

										}
										
										int overweight = new StudentSqlLite().countGradeBMI(g.getId(), gen, "OVERWEIGHT" );
										Cell overweightCell = row.createCell(++columnCount);
										overweightCell.setCellValue((Integer) overweight);
										overweightCell.setCellStyle(cellStyle1);
										Cell overweightCellPercentage = row.createCell(++columnCount);
										try {
											overweightCellPercentage.setCellValue((String) BMIFormat.format( (overweight * 100f / totalWeighted))+ "%");
											overweightCellPercentage.setCellStyle(cellStyle1);
										} catch (Exception e2) {
											overweightCellPercentage.setCellValue((String) "0.0%");

										}
										
										int obese = new StudentSqlLite().countGradeBMI(g.getId(), gen, "OBESE" );
										Cell obeseCell = row.createCell(++columnCount);
										obeseCell.setCellValue((Integer) obese);
										obeseCell.setCellStyle(cellStyle1);
										Cell obeseCellPercentage = row.createCell(++columnCount);
										try {
											obeseCellPercentage.setCellValue((String) BMIFormat.format( (obese * 100f / totalWeighted))+ "%");
											obeseCellPercentage.setCellStyle(cellStyle1);
										} catch (Exception e2) {
											obeseCellPercentage.setCellValue((String) "0.0%");

										}
										
										int severelyStunted = new StudentSqlLite().countHFA(g.getId(), gen, "SEVERELY_STUNTED" );
										Cell severelyStuntedCell = row.createCell(++columnCount);
										severelyStuntedCell.setCellValue((Integer) severelyStunted);
										severelyStuntedCell.setCellStyle(cellStyle1);
										Cell severelyStuntedCellPercentage = row.createCell(++columnCount);
										try {
											severelyStuntedCellPercentage.setCellValue((String) heightFormat.format(  (severelyStunted * 100f / totalHeighTaken))+ "%");
											severelyStuntedCellPercentage.setCellStyle(cellStyle1);
										} catch (Exception e2) {
											severelyStuntedCellPercentage.setCellValue((String) "0.0%");
										}
										
										int stunted = new StudentSqlLite().countHFA(g.getId(), gen, "STUNTED" );
										Cell stuntedCell = row.createCell(++columnCount);
										stuntedCell.setCellValue((Integer) stunted);
										stuntedCell.setCellStyle(cellStyle1);
										Cell stuntedCellPercentage = row.createCell(++columnCount);
										try {
											stuntedCellPercentage.setCellValue((String) heightFormat.format( (stunted * 100f / totalHeighTaken))+ "%");
											stuntedCellPercentage.setCellStyle(cellStyle1);
										} catch (Exception e2) {
											stuntedCellPercentage.setCellValue((String) "0.0%");
										}
										
										int normalHFA = new StudentSqlLite().countHFA(g.getId(), gen, "NORMAL" );
										Cell normalHFACell = row.createCell(++columnCount);
										normalHFACell.setCellValue((Integer) normalHFA);
										normalHFACell.setCellStyle(cellStyle1);
										Cell normalHFACellPercentage = row.createCell(++columnCount);
										try {
											normalHFACellPercentage.setCellValue((String) heightFormat.format( (normalHFA * 100f / totalHeighTaken))+ "%");
											normalHFACellPercentage.setCellStyle(cellStyle1);
										} catch (Exception e2) {
											normalHFACellPercentage.setCellValue((String) "0.0%");
										}
										
										int tall = new StudentSqlLite().countHFA(g.getId(), gen, "TALL" );
										Cell tallCell = row.createCell(++columnCount);
										tallCell.setCellValue((Integer) tall);
										tallCell.setCellStyle(cellStyle1);
										Cell tallPercentage = row.createCell(++columnCount);
										try {
											tallPercentage.setCellValue((String) heightFormat.format( (tall * 100f / totalHeighTaken))+ "%");
											tallPercentage.setCellStyle(cellStyle1);
										} catch (Exception e2) {
											tallPercentage.setCellValue((String) "0.0%");
										}
										
										
										Cell heightTakenCell = row.createCell(++columnCount);
										heightTakenCell.setCellValue((Integer) totalHeighTaken);
										heightTakenCell.setCellStyle(cellStyle1);
										Cell heightTakenCellPercentage = row.createCell(++columnCount);
										try {
											heightTakenCellPercentage.setCellValue((String) heightFormat.format((totalHeighTaken * 100f / totalStudentWithGender))+ "%");
											heightTakenCellPercentage.setCellStyle(cellStyle1);
										} catch (Exception e2) {
											heightTakenCellPercentage.setCellValue((String) "0.0%");
											e2.printStackTrace();
										}
								
								} catch (Exception e2) {
							//		e2.printStackTrace();
								}
							}
							
							//To be continued
						}
						
						JnaFileChooser jnaCh = new JnaFileChooser();
						jnaCh.addFilter("Folder", ".dir");
						jnaCh.setMode(Mode.Directories);
				        boolean save = jnaCh.showOpenDialog(frame);
				        if (save) {
				        	String fileName = jnaCh.getSelectedFile().getName();
				        	String filePath = jnaCh.getSelectedFile().getAbsolutePath();
				        	try (FileOutputStream outputStream = new FileOutputStream(filePath+"\\"+t.getSchool_year()+"-"+t.getType()+".xlsx")) {
					            workbook.write(outputStream);
					            JOptionPane.showMessageDialog(null, "Success "+t.getSchool_year()+"-"+t.getType()+".xlsx");
				        	} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
				        		e1.printStackTrace();
				        	} catch (IOException e1) {
				        	// TODO Auto-generated catch block
				        		e1.printStackTrace();
				        	}
				        }
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		});
		generateReport.setText("Generate Summary");
		generateReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		generateReport.setBounds(10, 338, 144, 60);
		SideBarMenu.add(generateReport);
		
		initSchoolComboBox();
		
		isInit = false;
		initPanels();
	}
	private void initSchoolComboBox() {
		schoolComboBox.removeAllItems();
		ArrayList<School> schools = new SchoolSqlLite().getSchools();
		for (School school : schools) {
			schoolComboBox.addItem(school.getName()+"-"+school.getId());
		}

		String school = schoolComboBox.getSelectedItem().toString();
		String[] sc = school.split("-");
		selectedSchoolId = Integer.parseInt(sc[sc.length-1]);
		initSchoolYearComboBox(selectedSchoolId);
	}
	private void initSchoolYearComboBox(int id) {
		schoolYearComboBox.removeAllItems();
		ArrayList<Timeline> schools = new TimelineSqlLite().getTimelines(id);
		for (Timeline t : schools) {
			schoolYearComboBox.addItem(t.getSchool_year() + "-"+t.getType()+"-"+t.getId());
		}
		
		
		if(!(schools.isEmpty() || schools == null)) {

			String school = schoolYearComboBox.getSelectedItem().toString();
			String[] sc = school.split("-");
			selectedSchoolYear = Integer.parseInt(sc[sc.length-1]);
		}
		
		if(schools.isEmpty()) {
			disableButtons();
		}
	}
	
	private void initPanels() {
		try {
			if(!isInit) {
				if(this.selectedSchoolId > 0 || this.selectedSchoolYear > 0) {
					analysisPanel.setName(selectedSchoolId+"-"+selectedSchoolYear);
					searchEditPanel.setName(selectedSchoolId+"-"+selectedSchoolYear);
					importExcelPanel.setName(selectedSchoolId+"-"+selectedSchoolYear);	
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void disableButtons() {
		this.analysisBtn.setEnabled(this.analysisBtn.isEnabled()? false : true);
		this.generateReport.setEnabled(this.generateReport.isEnabled()? false : true);
		this.importExcelPanel.setEnabled(this.importExcelPanel.isEnabled()? false : true);
		this.newGradeBtn.setEnabled(this.newGradeBtn.isEnabled()? false : true);
		this.newSectionBtn.setEnabled(this.newSectionBtn.isEnabled()? false : true);
		this.searchEditBtn.setEnabled(this.searchEditBtn.isEnabled()? false : true);
		
	}
}



import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import Analysis.Grade;
import Analysis.Section;
import Analysis.Student;
import Swing.JnaFileChooser;
import Swing.RoundPanel;
import Swing.TableDark;
import Swing.datechooser.DateChooser;
import database.BMIForAgeBoys;
import database.BMIForAgeGirls;
import database.BMISqlLite;
import database.GradeSqlLite;
import database.HFAForAgeBoys;
import database.HFAForAgeGirls;
import database.SectionSqlLite;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JOptionPane;

public class ImportExcelPanel extends JPanel {

	private JScrollPane ScrollPane;
	private JTable table;
	private DecimalFormat df;
	private DecimalFormat BMIFormat;
	private DecimalFormat heightFormat;
	ArrayList<Student> data;
	private String date;
	
	private int selectedSchoolId = -1;
	private int selectedSchoolYearId = -1;
	private int selectedGradeId = -1;
	private int selectedSectionId = -1;

	private boolean ifSchool = false;
	private boolean ifGrade = false;
	private boolean ifSection = false;
	private boolean ifDate = false;
	private boolean ifData = false;
	
	private JComboBox<Object> gradeComboBox;
	private JComboBox<Object> sectionComboBox;
	/**
	 * Create the panel.
	 */
	public ImportExcelPanel(int width, int height, JFrame thisFrame) {
		setBackground(Color.BLACK);
//		width = 1190;
//		height = 752;
		setSize(width, height);
		
		
		JButton openFileBtn = new JButton("Open File");
		openFileBtn.setBounds(7, 11, 77, 62);
		openFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JnaFileChooser jnaCh = new JnaFileChooser();
		        boolean save = jnaCh.showOpenDialog(thisFrame);
		        if (save) {
		        	String fileName = jnaCh.getSelectedFile().getName();
		        	String filePath = jnaCh.getSelectedFile().getAbsolutePath();
		        	String fileExt = fileName.substring(fileName.length()-4);
		        	if(fileExt.equals("xlsx")) {
		        		if(openFile(filePath)) {
		        			JOptionPane.showMessageDialog(null, "Success");
			        	}else {
			        		JOptionPane.showMessageDialog(null, "Invalid Excel Format");
			        	}
		        	}else {
		        		JOptionPane.showMessageDialog(null, "Please Input Excel File");
		        	}
		        }
			}
		});
		setLayout(null);

		df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		BMIFormat = new DecimalFormat();
		BMIFormat.setMaximumFractionDigits(2);
		

		heightFormat = new DecimalFormat();
		heightFormat.setMaximumFractionDigits(5);
		
		
		add(openFileBtn);
		
		JPanel tablePanel = new RoundPanel();
		tablePanel.setBounds(7, 80, 1176, 605);
		table = new TableDark();
		table.setEnabled(false);
		table.setCellSelectionEnabled(true);
		table.setBackground(Color.GRAY);
	
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"No", "Name", "Birthday (mm/dd/yyyy)", "Weight", "Height", "Sex", "Height2", "Age (y.m)", "BMI", "BMI STATUS", "Nutritional Status", "HFA", "HFA STATUS"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		table.getColumnModel().getColumn(9).setPreferredWidth(102);
		

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(String.class, centerRenderer);

		tablePanel.setLayout(new BorderLayout(0, 0));
        JTableHeader tableHeader = table.getTableHeader();
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 12));
		ScrollPane = new javax.swing.JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	
		
		
        tablePanel.add(tableHeader, BorderLayout.NORTH);
        tablePanel.add(ScrollPane,  BorderLayout.CENTER);
		add(tablePanel);
				
		JButton SaveBtn = new JButton("Save");
		SaveBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(!(ifGrade && ifDate)) {
					JOptionPane.showMessageDialog(null, "Requires Year and Data.");
				}else {
					try {
						String selectedGrade = gradeComboBox.getSelectedItem().toString();
						String g[] = selectedGrade.split("-");

						String selectedSection = sectionComboBox.getSelectedItem().toString();
						String s[] = selectedSection.split("-");
						int choice = JOptionPane.showConfirmDialog(null, "Importing in Grade "+  g[0]+ " and Section "+s[0]);
						if(choice == JOptionPane.YES_OPTION) {
							if(saveBMI()) {
								JOptionPane.showMessageDialog(null, "Success");
								data = new ArrayList<Student>();
								updateTable();
							}
							
							
						}
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
		});
		SaveBtn.setBounds(1094, 7, 57, 61);
		add(SaveBtn);
		
		JButton dateBtn = new JButton("Date");
		dateBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DateChooser datechooser = new DateChooser();
				
				JPanel newEventPanel = new RoundPanel();
				newEventPanel.setSize(new Dimension(200, 200));

				newEventPanel.add(datechooser);
				
				
				UIManager.put("OptionPane.minimumSize",new Dimension(400, 250));  
				int result = JOptionPane.showConfirmDialog(null, newEventPanel, "Date Weighted",JOptionPane.OK_CANCEL_OPTION);		
				UIManager.put("OptionPane.minimumSize",new Dimension(200, 100));
				if(result == JOptionPane.OK_OPTION) {
					int day = datechooser.getSelectedDate().getDay();
					int month = datechooser.getSelectedDate().getMonth();
					int year = datechooser.getSelectedDate().getYear();
					date = year+"/"+month+"/"+day;
					ifDate = true;
				}
			}
		});
		dateBtn.setBounds(633, 25, 157, 35);
		add(dateBtn);
		
		gradeComboBox = new JComboBox<Object>();
		gradeComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					String gradeString = gradeComboBox.getSelectedItem().toString();
					String temp[] = gradeString.split("-");
					selectedGradeId = Integer.parseInt(temp[temp.length-1]);
					initSectionField();
					ifGrade = true;
				} catch (Exception e2) {
					ifGrade = false;
				}
			}
		});
		gradeComboBox.setBounds(191, 25, 82, 35);
		add(gradeComboBox);
		
		JLabel lblGrade = new JLabel("Grade:");
		lblGrade.setForeground(Color.WHITE);
		lblGrade.setFont(new Font("Arial", Font.PLAIN, 15));
		lblGrade.setBounds(133, 22, 120, 39);
		add(lblGrade);
		
		JLabel lblSection = new JLabel("Section:");
		lblSection.setForeground(Color.WHITE);
		lblSection.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSection.setBounds(309, 22, 120, 39);
		add(lblSection);
		
		sectionComboBox = new JComboBox<Object>();
		sectionComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					String sectionString = sectionComboBox.getSelectedItem().toString();
					String temp[] = sectionString.split("-");
					selectedSectionId = Integer.parseInt(temp[temp.length-1]);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		sectionComboBox.setBounds(377, 25, 126, 35);
		add(sectionComboBox);
		
		JLabel lblDateWeighted = new JLabel("Date Weighted:");
		lblDateWeighted.setForeground(Color.WHITE);
		lblDateWeighted.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDateWeighted.setBounds(526, 22, 120, 39);
		add(lblDateWeighted);

		
	}
	
	//Modified method to set Id
	@Override
	public void setName(String name) {
		this.selectedSchoolId = Integer.parseInt(name.split("-")[0]);
		this.selectedSchoolYearId = Integer.parseInt(name.split("-")[1]);
		ifGrade = false;
		ifDate = false;
		initGradeField();
		initSectionField();
	}
	
	private void initGradeField() {
		gradeComboBox.removeAllItems();
		selectedGradeId = -1;
		ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
		for (Grade g : grades) {
			gradeComboBox.addItem( g.getName()+"-"+g.getId());
		}
	}
	private void initSectionField() {
		sectionComboBox.removeAllItems();
		ArrayList<Section> sections = new SectionSqlLite().getSections(selectedGradeId, selectedSchoolId);
		for (Section s : sections) {
			sectionComboBox.addItem( s.getName()+"-"+s.getId());
		}
	}
	
	private String getFileExtension(String fileName) {
	    int lastIndexOf = fileName.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return fileName.substring(lastIndexOf+1);
	}
	
	private boolean openFile(String path) {
		boolean isSuccess = true;
		try {
			data= new ArrayList<Student>();
			updateTable();
			
			FileInputStream inputStream = new FileInputStream(new File(path));
			Workbook workbook = WorkbookFactory.create(inputStream);
		
			Sheet sheet = workbook.getSheetAt(0);

			boolean isTitle = true;

			ArrayList<Student> students = new ArrayList<Student>();
			for(Row row : sheet) {
				int rowIndex = 0;
				Student student = new Student();
				int isString = 0;
				for(Cell cell : row) {
					isString = 0;
					String sValue = "";
					float fValue = 0;
					boolean isEmpty = true;
					
					try {
						sValue = cell.getStringCellValue();
						isString = 1;

						if(Integer.parseInt(sValue) == 1 && isTitle) {
							isTitle = false;
							rowIndex = 0;
						}
						
						if(sValue.isEmpty()) {
							isEmpty = true;
						}else {
							isEmpty = false;
						}
					}catch(Exception e2) {
						try {
							fValue = (float) cell.getNumericCellValue();
							isString = 2;

							if(fValue == 1.0 && isTitle) {
								isTitle = false;
								rowIndex = 0;
							}
						} catch (Exception e) {
							isEmpty = true;
						}
					}
						if(isString == 1) {							
							try {
								if(!isTitle && isEmpty) {
									switch (rowIndex) {
									case 0:
										student.setId(Integer.parseInt(sValue));
										break;
									case 1:
										break;
									case 2:
										student.setName(sValue);
										break;
									case 3:
										student.setBirthday(sValue);
										break;
									case 4:
										student.setWeight(Float.parseFloat(sValue));
										break;
									case 5:
										student.setHeight(Float.parseFloat(sValue));
										break;
									case 6:
										student.setSex(sValue);
										break;
									case 7:
//										student.setHeight2(Float.parseFloat(value));
										break;
									case 8:
										student.setAgeYear(Integer.parseInt(sValue));
										break;
									case 9:
										break;
									case 10:
										student.setAgeMonth(Integer.parseInt(sValue));
										break;
									case 11:
										student.setBmi(student.calculateBMI());
										break;
									case 12:
										student.setNutritionalStatus(sValue);
										break;
									case 13:
										student.setHfa(sValue);
										break;

									default:
										break;
									}
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						else if(isString == 2) {
							try {
								if(!isTitle){
									switch (rowIndex) {
									case 0:
										student.setId((int) fValue);
										break;
									case 1:
										
										break;
									case 2:
//										student.setName(value);
										break;
									case 3:
								        LocalDate date =cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
										student.setBirthday(date.getMonthValue() + "/" +date.getDayOfMonth()+ "/"+date.getYear());
										break;
									case 4:
										student.setWeight(fValue);
										break;
									case 5:
										student.setHeight(fValue);
										break;
									case 6:
//										student.setSex(value);
										break;
									case 7:
//										student.setHeight2(value);
										break;
									case 8:
										student.setAgeYear((int) fValue);
										break;
									case 9:
										break;
									case 10:
										student.setAgeMonth((int)fValue);
										break;
									case 11:
										student.setBmi(fValue);
										break;
									case 12:
//										student.setNutritionalStatus(value);
										break;
									case 13:
//										student.setHfaStatus(value);
										break;

									default:
										break;
									}
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					
					rowIndex++;
				}

				if(!isTitle) {
					try {
						if(student.getName().length() > 3 ) {
							if(!(student.getName().toLowerCase().contains("male") || student.getName().toLowerCase().contains("female"))) {
								students.add(student);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
					
			}		
		
		data = students;
		updateTable();	
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(data.size() > 0) {
			isSuccess = true;
		}else {
			isSuccess = false;
		}
		return isSuccess;
	}
	
	public void updateTable() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (Student student : data) {
			Object[] row = new Object[13];
			row[0] = student.getId();
			row[1] = student.getBlurName();
			row[2] = student.getBirthday();
			row[3] = student.getWeight();
			row[4] = student.getHeight();
			row[5] = student.getSex();
			row[6] = heightFormat.format(student.getHeigh2());
			row[7] = student.getAgeYear() + ","+student.getAgeMonth();
			row[8] = BMIFormat.format(student.calculateBMI());
			
			boolean isBoy = student.getSex() == "M" ? true: false;
			if(isBoy) {
				student.setHfaStatus(new HFAForAgeBoys().getHFAForBoysStatus(student.getAgeYear(), student.getAgeMonth(), student.getHeight() * 100));
				student.setBmiStatus(new BMIForAgeBoys().getBMIForBoysStatus(student.getAgeYear(), student.getAgeMonth(), student.calculateBMI()));

			}else {
				student.setBmiStatus(new BMIForAgeGirls().getBMIForGirlsStatus(student.getAgeYear(), student.getAgeMonth(), student.calculateBMI()));
				student.setHfaStatus(new HFAForAgeGirls().getHFAForGirlsStatus(student.getAgeYear(), student.getAgeMonth(), student.getHeight() * 100));
			}
			
			row[9] = student.getBmiStatus();
			row[10] = student.getNutritionalStatus();
			row[11] = student.getHfa();
			row[12] = student.getHfaStatus();
			model.addRow(row);
		}
	}
	private boolean saveBMI() {
		try {
			for (Student student : data) {
				new BMISqlLite().addStudent(selectedSchoolId, selectedGradeId, selectedSectionId, student);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}


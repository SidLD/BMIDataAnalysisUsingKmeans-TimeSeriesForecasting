
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Analysis.Grade;
import Analysis.Section;
import Analysis.Student;
import Swing.CustomeTextField;
import Swing.RoundPanel;
import Swing.TableDark;
import Swing.TextFieldAnimation;
import Swing.datechooser.DateChooser;
import database.BMISqlLite;
import database.GradeSqlLite;
import database.SectionSqlLite;
import database.StudentSqlLite;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextArea;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class SearchEditPanel extends RoundPanel {
	private JTable table;
	private int selectedGradeId = -1;
	private int selectedSchoolId;
	private int selectedSchoolYearId;
	private JComboBox<Object> gradeComboBox;
	private JComboBox<Object> sectionComboBox;


	private DecimalFormat BMIFormat;
	private DecimalFormat heightFormat;
	
	public SearchEditPanel(int width, int height) {
		width = 1190;
		height = 752;
		
//		width -= 10;
//		height -= 40;
		setBackground(new Color(0, 0, 0));
		setSize(width, height);
		setLayout(null);
		
		BMIFormat = new DecimalFormat();
		BMIFormat.setMaximumFractionDigits(2);
		

		heightFormat = new DecimalFormat();
		heightFormat.setMaximumFractionDigits(5);
		
		
		JPanel TopPanel = new RoundPanel();
		TopPanel.setBounds(5, 5, width, (int) (height * 0.20));
		TopPanel.setLayout(null);
		add(TopPanel);
		
		JPanel GradePanel = new JPanel();
		GradePanel.setBackground(Color.MAGENTA);
		GradePanel.setBounds(0, 0, TopPanel.getWidth()/2, TopPanel.getHeight());
		TopPanel.add(GradePanel);
		GradePanel.setLayout(null);
		
		gradeComboBox = new JComboBox<Object>();
		gradeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		gradeComboBox.setBounds(193, 41, 286, 19);
		gradeComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					String gradeString = e.getItem().toString();

					String temp[] = gradeString.split("-");
					selectedGradeId = Integer.parseInt(temp[temp.length-1]);

					initSection();
				} catch (Exception e2) {
					selectedGradeId = -1;
				}
			}

		});
		GradePanel.add(gradeComboBox);
		
		JLabel GradeLBL = new JLabel("Grade Level :");
		GradeLBL.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GradeLBL.setBounds(70, 42, 87, 17);
		GradePanel.add(GradeLBL);
		
		JButton editGradeLevelBtn = new JButton("Edit Grade Level");
		editGradeLevelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(selectedGradeId == -1) {
						JOptionPane.showMessageDialog(null, "Select Grade First");
					}else {
						String res = JOptionPane.showInputDialog("Rename Grade Level");
						if(!(res.isEmpty())) {
							if(new GradeSqlLite().renameGrade(selectedGradeId, res)) {
								JOptionPane.showMessageDialog(null, "Success");
								initGradeField();
								initSection();
							}else {
								JOptionPane.showMessageDialog(null, "Failed");
							}
						}
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		editGradeLevelBtn.setBounds(123, 84, 117, 23);
		GradePanel.add(editGradeLevelBtn);
		
		JButton btnDeleteGradeLevel = new JButton("DELETE Grade Level");
		btnDeleteGradeLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int choice = JOptionPane.showConfirmDialog(null, "This will also delete the students enrolled in this section");
					if(choice == JOptionPane.YES_OPTION) {
						String temp[] = gradeComboBox.getSelectedItem().toString().split("-");
						int id = Integer.parseInt(temp[temp.length-1]);
						if(new GradeSqlLite().deleteGrade(id)) {
							JOptionPane.showMessageDialog(null, "Successfully Deleted Grade "+temp[0]);
							updateTable(new ArrayList<Student>());
							initGradeField();
							initSection();
						}else {
							JOptionPane.showMessageDialog(null, "Something Went Wrong");
						}
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		btnDeleteGradeLevel.setBackground(Color.RED);
		btnDeleteGradeLevel.setBounds(327, 84, 152, 23);
		GradePanel.add(btnDeleteGradeLevel);
		
		JPanel SectionPanel = new JPanel();
		SectionPanel.setBounds(GradePanel.getWidth(), 0, TopPanel.getWidth()/2, TopPanel.getHeight());
		TopPanel.add(SectionPanel);
		SectionPanel.setLayout(null);
		
		JLabel SectionLbl = new JLabel("Section :");
		SectionLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		SectionLbl.setBounds(28, 38, 87, 17);
		SectionPanel.add(SectionLbl);
		
		JButton editSectionBtn = new JButton("Edit Section");
		editSectionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String selectedSection = sectionComboBox.getSelectedItem().toString();
					String temp[] = selectedSection.split("-");
					int id = Integer.parseInt(temp[temp.length-1]);
					String res = JOptionPane.showInputDialog("Rename Section");
					if(!( res.isEmpty())) {
						if(new SectionSqlLite().renameSection(id, res)) {
							JOptionPane.showMessageDialog(null, "Success");
							initSection();
						}else {
							JOptionPane.showMessageDialog(null, "Failed");
							
						}
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		editSectionBtn.setBounds(150, 82, 117, 23);
		SectionPanel.add(editSectionBtn);
		
		JButton deleteSectionBtn = new JButton("DELETE Section");
		deleteSectionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int choice = JOptionPane.showConfirmDialog(null, "This will also delete the students enrolled in this section");
					if(choice == JOptionPane.YES_OPTION) {
						String temp[] = sectionComboBox.getSelectedItem().toString().split("-");
						int id = Integer.parseInt(temp[temp.length-1]);
						if(new SectionSqlLite().deleteSection(id)) {
							JOptionPane.showMessageDialog(null, "Successfully Deleted Section "+temp[0]);
							updateTable(new ArrayList<Student>());
							initSection();
						}else {
							JOptionPane.showMessageDialog(null, "Something Went Wrong");
						}
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		deleteSectionBtn.setBackground(Color.RED);
		deleteSectionBtn.setBounds(298, 81, 152, 23);
		SectionPanel.add(deleteSectionBtn);
		
		sectionComboBox = new JComboBox<Object>();
		sectionComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					String temp[] = e.getItem().toString().split("-");
					int id = Integer.parseInt(temp[temp.length-1]);

					ArrayList<Student> students = new StudentSqlLite().getStudents(id);
					updateTable(students);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		sectionComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sectionComboBox.setBounds(138, 38, 286, 19);
		SectionPanel.add(sectionComboBox);
		
		JPanel BottomPanel = new RoundPanel();
		BottomPanel.setBounds(5, (int) (height * 0.20) + 5, width, (int) (height * 0.80));
		add(BottomPanel);
		BottomPanel.setLayout(null);
		
		JPanel TablePanel = new JPanel();
		TablePanel.setBounds(0, 0,BottomPanel.getWidth(), BottomPanel.getHeight());
		BottomPanel.add(TablePanel);
		TablePanel.setLayout(new BorderLayout(0, 0));
		
		
		
		table = new TableDark();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int selectedLink = table.getSelectedRow();

					int id = Integer.parseInt(table.getValueAt(selectedLink, 0).toString());
					
					Student student = new StudentSqlLite().getStudent(id);
					
					JPanel EditTablePanel = new JPanel();
					EditTablePanel.setBackground(Color.YELLOW);
					EditTablePanel.setBounds(0, 0, 300, 550);
					EditTablePanel.setLayout(null);
					
					JLabel idLabel = new JLabel("ID :"+id);
					idLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
					idLabel.setBounds(59, 20, 259, 25);
					EditTablePanel.add(idLabel);
					
					JLabel nameLabel = new JLabel("Name : "+student.getName());
					nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
					nameLabel.setBounds(59, 56, 259, 25);
					EditTablePanel.add(nameLabel);
					
					JLabel lblBirthday = new JLabel("Birthday :");
					lblBirthday.setFont(new Font("Tahoma", Font.PLAIN, 15));
					lblBirthday.setBounds(59, 92, 259, 25);
					EditTablePanel.add(lblBirthday);
					
					JLabel bdayLbl = new JLabel(student.getBirthday());
					bdayLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
					bdayLbl.setBounds(140, 92, 259, 25);
					EditTablePanel.add(bdayLbl);
					
					JButton dateBtn = new JButton("Date");
					dateBtn.setBounds(210, 92, 96, 25);
					EditTablePanel.add(dateBtn);
				
					dateBtn.addActionListener( new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							DateChooser datechooser = new DateChooser();
							JPanel newEventPanel = new RoundPanel();
							newEventPanel.setSize(new Dimension(200, 200));
							newEventPanel.add(datechooser);
							String newDate = "";
							UIManager.put("OptionPane.minimumSize",new Dimension(400, 250));  
							int dateResult = JOptionPane.showConfirmDialog(null, newEventPanel, "Update Status",JOptionPane.OK_CANCEL_OPTION);		
							UIManager.put("OptionPane.minimumSize",new Dimension(200, 100));
							if(dateResult == JOptionPane.OK_OPTION) {
								int day = datechooser.getSelectedDate().getDay();
								int month = datechooser.getSelectedDate().getMonth();
								int year = datechooser.getSelectedDate().getYear();
								newDate = month+"/"+day+"/"+year;
								bdayLbl.setText(newDate);
							}
						}
					});
					
					
					JLabel lblAgeInYear = new JLabel("Age in Year : ");
					lblAgeInYear.setFont(new Font("Tahoma", Font.PLAIN, 15));
					lblAgeInYear.setBounds(59, 130, 105, 25);
					EditTablePanel.add(lblAgeInYear);
					
					JLabel lblAgeInMonth = new JLabel("Age in Month : ");
					lblAgeInMonth.setFont(new Font("Tahoma", Font.PLAIN, 15));
					lblAgeInMonth.setBounds(59, 167, 113, 25);
					EditTablePanel.add(lblAgeInMonth);
					
					JTextField ageYearField = new JTextField(Integer.toString(student.getAgeYear()));
					ageYearField.setBounds(157, 128, 105, 27);
					EditTablePanel.add(ageYearField);
					ageYearField.setColumns(10);
					
					JTextField ageMonthField = new JTextField(Integer.toString(student.getAgeMonth()));
					ageMonthField.setColumns(10);
					ageMonthField.setBounds(157, 166, 105, 27);
					EditTablePanel.add(ageMonthField);
					
					JTextField heightField = new JTextField(Float.toString(student.getHeight()));
					heightField.setColumns(10);
					heightField.setBounds(157, 257, 105, 27);
					EditTablePanel.add(heightField);
					
					JLabel lblWeightkg = new JLabel("Weight (kg) :");
					lblWeightkg.setFont(new Font("Tahoma", Font.PLAIN, 15));
					lblWeightkg.setBounds(59, 221, 105, 25);
					EditTablePanel.add(lblWeightkg);
					
					JLabel lblHeight = new JLabel("Height (m)");
					lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 15));
					lblHeight.setBounds(59, 258, 113, 25);
					EditTablePanel.add(lblHeight);
					
					JLabel bmiLabel = new JLabel("BMI :"+student.calculateBMI());
					bmiLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
					bmiLabel.setBounds(100, 310, 143, 22);
					EditTablePanel.add(bmiLabel);
					
					JTextField weightField = new JTextField(Float.toString(student.getWeight()));
					weightField.setColumns(10);
					weightField.getDocument().addDocumentListener(new DocumentListener() {
						  public void changedUpdate(DocumentEvent e) {
						    warn();
						  }
						  public void removeUpdate(DocumentEvent e) {
						    warn();
						  }
						  public void insertUpdate(DocumentEvent e) {
						    warn();
						  }

						  public void warn() {
							  try {
								  float w = Float.parseFloat(weightField.getText().toString());
								  float h = Float.parseFloat(heightField.getText().toString());
								  bmiLabel.setText("BMI :"+w/(h*h));
							} catch (Exception e2) {
								// TODO: handle exception
							}
						  }
					});
					weightField.setBounds(157, 219, 105, 27);
					EditTablePanel.add(weightField);
					
					
					JLabel lblNutritionalStatus = new JLabel("Nutritional Status :");
					lblNutritionalStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
					lblNutritionalStatus.setBounds(59, 359, 146, 25);
					EditTablePanel.add(lblNutritionalStatus);
					
					JComboBox nutriComboBox = new JComboBox(new String[]{"WASTED", "NORMAL"});
					nutriComboBox.setBounds(185, 362, 143, 22);
					EditTablePanel.add(nutriComboBox);
					
					
					JLabel lblHfa = new JLabel("HFA : "+student.getHfaStatus());
					lblHfa.setFont(new Font("Tahoma", Font.PLAIN, 20));
					lblHfa.setBounds(100, 400, 259, 44);
					EditTablePanel.add(lblHfa);
					
					JLabel genderLabal = new JLabel("Gender :");
					genderLabal.setFont(new Font("Tahoma", Font.PLAIN, 15));
					genderLabal.setBounds(59, 470, 146, 25);
					EditTablePanel.add(genderLabal);
					
					JComboBox genderComboBox = new JComboBox(new String[]{"M", "F"});
					genderComboBox.setBounds(185, 470, 143, 22);
					EditTablePanel.add(genderComboBox);
					
					UIManager.put("OptionPane.minimumSize",new Dimension(400, 600));  
					int result = JOptionPane.showConfirmDialog(null, EditTablePanel, "Edit",JOptionPane.OK_CANCEL_OPTION);
					UIManager.put("OptionPane.minimumSize",new Dimension(200, 100)); 
					if(result == JOptionPane.YES_OPTION) {
						try {
							student.setBirthday(bdayLbl.getText());
							student.setAgeYear(Integer.parseInt(ageYearField.getText()));
							student.setAgeMonth(Integer.parseInt(ageMonthField.getText()));
							student.setSex(genderComboBox.getSelectedItem().toString());
							student.setNutritionalStatus(nutriComboBox.getSelectedItem().toString());
							student.setHeight(Float.parseFloat(heightField.getText()));
							student.setWeight(Float.parseFloat(weightField.getText()));
							if(new StudentSqlLite().updateStudent(student)) {
								JOptionPane.showMessageDialog(null, "Success");
								String temp[] = sectionComboBox.getSelectedItem().toString().split("-");
								int ids = Integer.parseInt(temp[temp.length-1]);

								ArrayList<Student> students = new StudentSqlLite().getStudents(ids);
								updateTable(students);
							}else {
								JOptionPane.showMessageDialog(null, "Fail");
							}
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "Invalid Input");
						}
					}
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		});
		table.setCellSelectionEnabled(true);
		table.setBackground(Color.GRAY);
	
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"No", "Name", "Birthday (mm/dd/yyyy)", "Weight", "Height", "Sex", "Height2", "Age (y.m)", "BMISTATUS", "Nutritional Status", "HFA"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		table.getColumnModel().getColumn(9).setPreferredWidth(102);
		

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(String.class, centerRenderer);

        JTableHeader tableHeader = table.getTableHeader();
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 12));
		JScrollPane ScrollPane = new javax.swing.JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	
			
		TablePanel.add(tableHeader, BorderLayout.NORTH);
		TablePanel.add(ScrollPane,  BorderLayout.CENTER);
	
		
	}
	@Override
	public void setName(String name) {
		this.selectedSchoolId = Integer.parseInt(name.split("-")[0]);
		this.selectedSchoolYearId = Integer.parseInt(name.split("-")[1]);
		selectedGradeId = -1;
		initGradeField();
		initSection();
	}
	private void initGradeField() {
		gradeComboBox.removeAllItems();
		ArrayList<Grade> grades = new GradeSqlLite().getGrades(selectedSchoolId, selectedSchoolYearId);
		for (Grade g : grades) {
			gradeComboBox.addItem( g.getName()+"-"+g.getId());
		}
	}
	private void initSection() {
		sectionComboBox.removeAllItems();
		ArrayList<Section> sections = new SectionSqlLite().getSections(selectedGradeId, selectedSchoolId);
		for (Section s : sections) {
			sectionComboBox.addItem( s.getName()+"-"+s.getId());
		}
	}
	public void updateTable(ArrayList<Student> students) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		int rowCount = 1;
		for (Student student : students) {
			Object[] row = new Object[11];
			row[0] = rowCount;
			row[1] = student.getBlurName();
			row[2] = student.getBirthday();
			row[3] = student.getWeight();
			row[4] = student.getHeight();
			row[5] = student.getSex();
			row[6] = heightFormat.format(student.getHeigh2());
			row[7] = student.getAgeYear() + ","+student.getAgeMonth();
			row[8] = BMIFormat.format(student.calculateBMI());
			row[9] = student.getNutritionalStatus();
			row[10] = student.getHfaStatus();
			model.addRow(row);
			rowCount++;
		}
	}
	
	private void setData(Student newStudent) {
		
	}
}


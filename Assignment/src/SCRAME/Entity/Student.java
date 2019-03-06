package SCRAME.Entity;

import java.io.Serializable;
import java.util.ArrayList;


// student entity class

public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String matricNumber;
	private String studName;
	private int year;
	private double GPA;
	private String facultyName;

	private ArrayList<Results> resultList;

	
	
	// constructor for student object - new? without GPA yet
	public Student(String studName, String matricNumber, String facultyName, int year) {
		this.studName = studName.substring(0, 1).toUpperCase() + studName.substring(1);
		this.facultyName = facultyName.toUpperCase();
		this.year = year; //set to -1? by default
		this.GPA = -1; //set to -1?
		this.matricNumber = matricNumber.toUpperCase();
		resultList = new ArrayList<Results>();
	}
	
	public Student(String studName, String matricNumber, String facultyName, int year, float GPA) {
		this.studName = studName.substring(0, 1).toUpperCase() + studName.substring(1);
		this.facultyName = facultyName.toUpperCase();
		this.year = year; //set to -1? by default
		this.GPA = GPA;
		this.matricNumber = matricNumber.toUpperCase();
		resultList = new ArrayList<Results>();
	}
	
	//getter and setter
	public String getMatricNumber() {
		return matricNumber;
	}

	public void setMatricNumber(String matricNumber) {
		this.matricNumber = matricNumber;
	}

	public String getStudName() {
		return studName;
	}

	public void setStudName(String studName) {
		this.studName = studName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getGPA() {
		if(resultList.isEmpty())
			return GPA;
		else
			return calGPA();
	}

	public void setGPA(double GPA) {
		this.GPA = GPA;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	
	public ArrayList<Results> getResultList(){
		return resultList;
	}

	
	// using course class - add into hash map?
	// how ?? 
	public void addCourse(Course theCourse) {
		resultList.add(new Results(this, theCourse));
		
	}

	public void addTotalScore(String courseCode, double examScore, double courseworkScore) {
		for(Results r: resultList) {
			if((r.getCourse().getCourseCode()).equals(courseCode)) {
				double totalScore = (courseworkScore * r.getCourse().getCourseWeightage())
						+ (examScore * (1-r.getCourse().getCourseWeightage()));
				
				r.setTotalScore(totalScore);
			}
		}
	}

	public void addExamScore(String courseCode, double examScore) {
		for(Results r: resultList) {
			if((r.getCourse().getCourseCode()).equals(courseCode)) {
				r.setExamScore(examScore);
			}
		}
	}

	public void addCourseworkScore(String courseCode, double courseworkScore) {
		for(Results r: resultList) {
			if((r.getCourse().getCourseCode()).equals(courseCode)) {
				r.setCourseworkScore(courseworkScore);
			}
		}
	}
	

	public void addSubComponentScore(String courseCode, double subComponentScore) {
		for(Results r: resultList) {
			if((r.getCourse().getCourseCode()).equals(courseCode)) {
				r.addSubComponentScore(subComponentScore);
			}
		}
		
	}
	public double calGPA() {
		double totalScore = 0;
		for(Results r: resultList) {
			totalScore = totalScore + r.getTotalScore();
		}
		return (totalScore/resultList.size());
	}


}

package SCRAME.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// results entity class

public class Results implements Serializable {

	private Student student;
	private Course course;
	private double examScore;
	private double courseworkScore;
	private double totalScore;
	private double GPA;
	private String grade;
	private String courseCode;
	private int examWeight;
	private int courseWorkWeight;
	private ArrayList<Double> subComponentScoreList = new ArrayList<Double>();

	public Results(Student stud, Course course){
		this.student = stud;
		this.course = course;
		this.examScore = -1;
		this.courseworkScore = -1;
		this.totalScore = -1;
		this.GPA = -1;
	}

	public String getCourseCode() {
		this.courseCode = course.getCourseCode();
		return courseCode;
	}
	public int getExamWeight() {
		this.examWeight =course.getExamWeightage();
		return examWeight;
	}
	public int courseWorkWeight() {
		this.courseWorkWeight = course.getCourseWeightage();
		return courseWorkWeight;
	}

	public ArrayList<SubComponent> getSubComponent() {
		return this.course.cw.getSubCompList();
	}

	public int getNumberOfSubComponent() {
		return this.course.cw.getNumberOfSubComponent();
	}

	public void addSubComponentScore(double subComponentScore) {
		subComponentScoreList.add(subComponentScore);
		this.courseworkScore = 0;
		
		ArrayList<Integer> tempSubComponentWeightage = course.getSubComponentWeight();
		
		for(int i=0; i<subComponentScoreList.size(); i++)
		{
			this.courseworkScore += ((this.subComponentScoreList.get(i)/100) * tempSubComponentWeightage.get(i));
			System.out.println("SubComponent");
			System.out.println(subComponentScoreList.get(i));
			System.out.println(tempSubComponentWeightage.get(i));
		}
		
		this.totalScore = ((this.courseworkScore/100) * this.courseWorkWeight) + ((this.examScore/100) * this.examWeight);
	}


	public Course getCourse() {
		return course;
	}

	public double getExamScore() {
		return examScore;
	}

	public void setExamScore(double examScore) {
		this.examScore = examScore;
	}

	public double getCourseworkScore() {
		return courseworkScore;
	}

	public void setCourseworkScore(double courseworkScore) {
		this.courseworkScore = courseworkScore;
	}

	//calculated in Student entity
	public double getTotalScore() {
		//return totalScore;
		return ((this.courseworkScore/100.0) * (double)this.courseWorkWeight) + ((this.examScore/100.0) * (double)this.examWeight);
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	public double getGPA() {
		return GPA;
	}

	public void calculateGrade(double marks) {
		if (marks > 90) {
			this.grade = "A+";
		}
		else if(marks <= 90 && marks > 80) {
			this.grade = "A";
		}
		else if(marks <= 80 && marks > 75) {
			this.grade = "A-";
		}
		else if(marks <= 75 && marks > 65) {
			this.grade = "B";
		}
		else if(marks <= 65 && marks > 55) {
			this.grade = "C";
		}
		else if(marks <= 55 && marks > 40) {
			this.grade = "D";
		}
		else if(marks == -1) {
			this.grade = "N.A";
		}
		else this.grade = "F";
	}

	public void calculateGPA(String grade) {
		if(grade == "A+" || grade == "A") {
			this.GPA = 5;
		}
		else if(grade == "A-") {
			this.GPA = 4.5;
		}
		else if(grade == "B") {
			this.GPA = 4;
		}
		else if(grade == "C") {
			this.GPA = 3.5;
		}
		else if(grade == "D") {
			this.GPA = 2.5;
		}
		else if(grade == "N.A") {
			this.GPA = -1;
		}
		else this.GPA = 1;
	}


/*
	public String getCourseCode() {
		return courseCode;
	}


	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}


	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	public double getMarks() {
		return marks;
	}


	public void setMarks(double marks) {
		this.marks = marks;
	}


	*/


}

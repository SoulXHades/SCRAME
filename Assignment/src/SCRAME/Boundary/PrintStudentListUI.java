package SCRAME.Boundary;

import SCRAME.Controller.*;
import SCRAME.Entity.Course;
import SCRAME.Entity.Lesson;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class PrintStudentListUI implements IPrintBoundary {
	static Scanner sc = new Scanner(System.in);
	private AcademicsController acadCtrl;
	
	private ArrayList<String> facultyNameList;
	private Map<String, ArrayList<Integer>> groupingNameList;
	private Map<String, ArrayList<String>> courseInfoList;
	private String facultyName;
	private String courseCode;
	private String courseName;
	private int groupNumber;
	private ArrayList<String> studentList;

	public PrintStudentListUI() {}
	
	public void setAcademicController(AcademicsController acadCtrl) {
		this.acadCtrl = acadCtrl;
	}
	
	public void studentListMenu() {
		//get list of faculty names
		this.facultyNameList = acadCtrl.getFacultyNameList();
		
		//get faculty of course student is registering for
		System.out.println("Input faculty of course");
		this.facultyName = displayFacultyList();
		
		//get a list of courses info
		//the keys are "courseCode", "courseName", "courseCoordinator"
		this.courseInfoList = acadCtrl.getCourseInfoList(this.facultyName);
		//check if courseInfoList content is empty means no courses are added in that faculty yet
		if(this.courseInfoList.get("courseCode").size() == 0)
		{
			System.out.println("No course is available!");
			System.out.println("Returning back to main menu.");
			return;
		}
		this.courseCode = displayCourseList();
		
		System.out.println("1. Print Student List by Lecture");
		System.out.println("2. Print Student List by Tutorial");
		System.out.println("3. Print Student List by Laboratory Session");
		int choice = sc.nextInt();
		
		if (choice == 1) {
			// get student list in the course
			this.studentList = acadCtrl.getStudentListCourse(this.facultyName, this.courseCode);
		}
		else if (choice == 2 || choice == 3) {
			//get a list of grouping(lab/tutorials) names
			this.groupingNameList = acadCtrl.getGroupingNameList(this.facultyName, this.courseCode);
			
			this.groupNumber = displayTutorialGroupingList();
			// get student list in tutorial
			this.studentList = acadCtrl.getStudentListLesson(this.facultyName, this.courseCode, this.groupNumber);
		}
		displayStudentList();
	}
	
	// print students in that course
	private void displayStudentList() {
		// to display index in student list
		int i = 1;
		//print all students in the course
		for (String s: studentList) {
			System.out.println(i+ ". " + s);
			++i;
		}
	}
	
	//get faculty of course student is registering for
	private String displayFacultyList(){
		//to display index in faculty list option
		int i = 1;
		int choice;
		//print all faculty names available
		for(String s : facultyNameList)
		{
			System.out.println(i + ". " + s);
			//increment index
			++i;
		}
		System.out.println("Choose a faculty:");
		choice = sc.nextInt();
		return this.facultyNameList.get(choice-1);
	}
	
	//get faculty of course student is registering for
	private String displayCourseList()
	{
		int choice;
		//print all faculty names available
		//FOR loop condition is the same as the size of ArrayList, not size of Map<>
		for(int j = 0; j < courseInfoList.get("courseCode").size(); j++)
		{
			//print out <index>. <course code> <course name>
			System.out.println(j+1 + ". " + this.courseInfoList.get("courseCode").get(j) + " " + this.courseInfoList.get("courseName").get(j) + " by " 
					+ this.courseInfoList.get("courseCoordinator").get(j));
		}
		
		System.out.println("Choose a course:");
		choice = sc.nextInt();
		
		return this.courseInfoList.get("courseCode").get(choice-1);
	}

	//get tutorial group of course student is registering for
	private int displayTutorialGroupingList(){
		//to display index in faculty list option
		int i = 1;
		int choice;
		//print all tutorial groups available
		for(int s : this.groupingNameList.get("tutorial")) {
			System.out.println(i + ". " + s);
			//increment index
			++i;
		}
		
		System.out.println("Choose a tutorial group:");
		choice = sc.nextInt();
		
		return this.groupingNameList.get("tutorial").get(choice-1);
	}
	
}

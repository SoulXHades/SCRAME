package SCRAME.Boundary;

import SCRAME.Controller.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class PrintStatsUI implements IPrintBoundary{
	static Scanner sc = new Scanner(System.in);
	private AcademicsController acadCtrl;
	
	private ArrayList<String> facultyNameList;
	private Map<String, ArrayList<String>> courseInfoList;
	private String facultyName;
	private ArrayList<String> courseInfo;
	private Map<String, Integer> courseStats;
	
	public void setAcadController(AcademicsController acadCtrl) {
		this.acadCtrl = acadCtrl;
	}
	
	public void displayMenu() {
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
		this.courseInfo = displayCourseList();
		
		String courseCode = this.courseInfo.get(0);
		String courseName = this.courseInfo.get(1);
		String courseCoordinator = this.courseInfo.get(2);
		
		// get more course info
		// the keys are "courseVacancy", "numEnrolled", "courseWeightage", "examWeightage"
		this.courseStats = acadCtrl.getCourseStats(this.facultyName, courseCode);
		
		System.out.println("Here are course statistics for: ");
		System.out.println(this.facultyName + " " + courseCode + " " + courseName);
		System.out.println("Course Coordinator: " + courseCoordinator);
		System.out.println("Number of Students Enrolled: " + courseStats.get("numEnrolled"));
		System.out.println("Current Number of Vacancy: " + courseStats.get("courseVacancy"));
		System.out.println("Exam Weightage: " + courseStats.get("examWeightage"));
		System.out.println("Coursework Weightage: " + courseStats.get("courseWeightage"));
		
		//get data of course grade percentage
		Map<String, Map<String, Double>> totalStats = acadCtrl.getCourseStats_gradePercentage(facultyName, courseCode);
		String[] gradeList = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "F"};

		System.out.println("\nOverall(%)");
		for(int j=0; j<9; j++)
		{
			System.out.println(gradeList[j] + " - " + totalStats.get("Overall").get(gradeList[j]));
		}
		
		System.out.println("\nExam(%)");
		for(int j=0; j<9; j++)
		{
			System.out.println(gradeList[j] + " - " + totalStats.get("Exam").get(gradeList[j]));
		}
		
		System.out.println("\nCoursework(%)");
		for(int j=0; j<9; j++)
		{
			System.out.println(gradeList[j] + " - " + totalStats.get("Coursework").get(gradeList[j]));
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
	private ArrayList<String> displayCourseList()
	{
		int choice;
		//print all faculty names available
		for(int j = 0; j < courseInfoList.get("courseCode").size(); j++)
		{
			//print out <index>. <course code> <course name>
			System.out.println(j+1 + ". " + this.courseInfoList.get("courseCode").get(j) + " " + this.courseInfoList.get("courseName").get(j) + " by " 
					+ this.courseInfoList.get("courseCoordinator").get(j));
		}
		
		System.out.println("Choose a course:");
		choice = sc.nextInt();
		
		ArrayList<String> courseInfo = new ArrayList<String>();
		courseInfo.add(this.courseInfoList.get("courseCode").get(choice-1));
		courseInfo.add(this.courseInfoList.get("courseName").get(choice-1));
		courseInfo.add(this.courseInfoList.get("courseCoordinator").get(choice-1));
		
		return courseInfo;
	}
		
}

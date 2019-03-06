package SCRAME.Boundary;

import SCRAME.Controller.*;
import java.util.*;

public class Check_RegisterUI {
	//declaration
	private AcademicsController acadCtrl;
	private String matricNumber;
	private ArrayList<String> facultyNameList;
	private Map<String, ArrayList<Integer>> groupingNameList;
	private Map<String, ArrayList<String>> courseInfoList;
	private String studentFacultyName;
	private String facultyName;
	private String courseCode;
	private String courseName;
	private int groupNumber;
	private boolean haveLab;
	private int tut_labChoice;
	
	Scanner sc;
	
	//constructor
	public Check_RegisterUI()
	{
		sc = new Scanner(System.in);
	}
	
	//get object of controller
	public void setAcadController(AcademicsController acadCtrl) 
	{
		this.acadCtrl = acadCtrl;
	}
	
	//UI to interact with user
	public void registrationDisplay()
	{
		this.groupNumber = -1;
		this.haveLab = false;
		
		while(true)
		{
			//get list of faculty names
			this.facultyNameList = acadCtrl.getFacultyNameList();
			
			//get student's details
			System.out.println("Input student's matriculation number:");
			this.matricNumber = sc.nextLine().toUpperCase();
			
			//check for whitespace
			if(this.matricNumber.trim().isEmpty())
				this.matricNumber = sc.nextLine().toUpperCase();
			
			//check if input student is authentic
			//faculty controller will throw exception if doesn't exist
			if(acadCtrl.isStudentInList(this.matricNumber))
				break;
			
			System.out.println("Student does not exist!");
			System.out.println("Please reenter again.\n");
		}
		
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
		
		//get course the student is registering for
		System.out.println("Input course to register for");
		this.courseCode = displayCourseList(false);
		
		//check if student already registered that course
		if(acadCtrl.isCourseRegistered(this.matricNumber, this.facultyName, this.courseCode))
		{
			System.out.println("Student already registered for the course.");
			System.out.println("Exiting registration.");
			
			return;
		}
		
		//get a list of grouping(lab/tutorials) names
		this.groupingNameList = acadCtrl.getGroupingNameList(this.facultyName, this.courseCode);
		
		//check if there is tutorial because there maybe courses with only lecture
		if(groupingNameList.get("tutorial").size() != 0)
		{
			//get grouping(tutorial/lab) the student is registering for
			System.out.println("Choose tutorial/lab group");
			if(this.groupingNameList.get("tutorial").size() != 0)
				this.groupNumber = displayTutorialGroupingList(true);
			if(this.groupingNameList.get("lab").size() != 0)
				this.haveLab = true;
		}
		
		//register student to course
		if(acadCtrl.addStudentToCourse(this.facultyName, this.courseCode, this.groupNumber, this.haveLab, this.matricNumber))
			System.out.println("Successfully registered student to course!");
		else
			System.out.println("Fail to registered student to course!");
	}
	
	public void displayVacancy()
	{
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
		
		//get course the student is registering for
		System.out.println("Input course to register for");
		this.courseCode = displayCourseList(false);
		
		//get a list of grouping(lab/tutorials) names
		this.groupingNameList = acadCtrl.getGroupingNameList(this.facultyName, this.courseCode);
		
		//check if there is tutorial because there maybe courses with only lecture
		if(groupingNameList.get("tutorial").size() == 0)
		{
			System.out.println("Tutorial and lab classes are not available in this course.");
			System.out.println("The vacancy of course is " + acadCtrl.getVacancy(this.facultyName, this.courseCode));
			
			return;
		}
		
		//get grouping(tutorial/lab) the student is registering for
		System.out.println("1. Tutorial class\n"
				+ "2. Lab class\n"
				+ "3. To Main Menu");
		System.out.println("Please choose tutorial or lab group");
		tut_labChoice = sc.nextInt();
		
		while(true)
		{
			if(tut_labChoice == 1)
			{
				this.groupNumber = displayTutorialGroupingList(false);
				System.out.println("The vacancy is " + acadCtrl.getVacancy(this.facultyName, this.courseCode, true, this.groupNumber));
				break;
			}
			else if(tut_labChoice == 2)
			{
				this.groupNumber = displayTutorialGroupingList(false);
				System.out.println("The vacancy is " + acadCtrl.getVacancy(this.facultyName, this.courseCode, false, this.groupNumber));
				break;
			}
			else if(tut_labChoice == 3)
			{
				return;
			}
			else
				System.out.println("Please input 1 or 2.");
		}
	}
	
	//get faculty of course student is registering for
	private String displayFacultyList()
	{
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
	private String displayCourseList(boolean returnAsCourseName)
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
		
		if(returnAsCourseName)
			return this.courseInfoList.get("courseName").get(choice-1);
		else
			return this.courseInfoList.get("courseCode").get(choice-1);
	}
	
	//get tutorial group of course student is registering for
	private int displayTutorialGroupingList(boolean withVacancy)
	{
		//to display index in faculty list option
		int i = 1;
		int choice;
		
		//print all faculty names available
		for(int s : this.groupingNameList.get("tutorial"))
		{
			if(withVacancy)
				System.out.println(i + ". " + s + " with vacancy of " + acadCtrl.getVacancy(this.facultyName, this.courseCode, true, s));
			else
				System.out.println(i + ". " + s);
			//increment index
			++i;
		}
		
		System.out.println("Choose a tutorial group:");
		choice = sc.nextInt();
		
		return this.groupingNameList.get("tutorial").get(choice-1);
	}
	
	private void ErrorMessage()
	{
		System.out.println("Student does not exist in the faculty.");
		System.out.println("Please input the correct data");
	}
	

}

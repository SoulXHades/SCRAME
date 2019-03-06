package SCRAME.Boundary;

import SCRAME.Controller.*;


import SCRAME.Entity.Professors;

import java.util.ArrayList;
import java.util.Scanner;
//function 2: Just add a course dont care about components
//check if it has any exisiting of such courses

public class AddCourseUI implements IAddBoundary {
	static Scanner sc = new Scanner(System.in);
	private AcademicsController acadCtrl;
	private ArrayList<String> facultyNameList;
	private ArrayList<String> courseCodeList;
	private ArrayList<String> courseNameList;
	private ArrayList<String> profNameList;
	private ArrayList<Integer> tutGroups;
	
	private ArrayList<String> courseCoordList = new ArrayList<String>(); // testing
	
	public AddCourseUI() {
	}

	// take in Faculty Controller object
	public void setAcadController(AcademicsController acadCtrl) {
		this.acadCtrl = acadCtrl;
	}
	
	// display and ask for Course Details
	public void askCourseDetails() {
		
		String courseCode, courseName, facultyName;
		int courseVacancy = 10;
	
		
		System.out.println("-------The current faculties are-------");
		facultyNameList = acadCtrl.getFacultyNameList();
		for(String fName : facultyNameList) {
			System.out.println(fName);
		}
		while(true)
		{
			System.out.println("Enter faculty name: ");
			facultyName = sc.nextLine().toUpperCase();
			
			//check if input data of faculty is correct
			if(checkFacName(facultyName)) {
				facultyNameList.add(facultyName);
				break;
			}
			else System.out.println("Wrong faculty Name");
		}
		
		
		courseCodeList = acadCtrl.getCourseCodeList(facultyName);
		System.out.println("The current course codes are ");
		for(String code : courseCodeList) {
			System.out.println(code);
		}
		System.out.println("Enter course code: ");
		courseCode = sc.nextLine().toUpperCase();
		
		
		if(checkCourseCode(courseCode)) {
			System.out.println("Course code already exists. \n Please try again");
			courseCode = sc.nextLine().toUpperCase();
			courseCodeList.add(courseCode);
		}else courseCodeList.add(courseCode);
		
		
		courseNameList = acadCtrl.getCourseNameList(facultyName);
		System.out.println("The current course names are ");
		for(String name : courseNameList) {
			System.out.println(name);
		}
		System.out.println("Enter course name: ");
		
		courseName = sc.nextLine().toUpperCase();
		
		if(checkCourseName(courseName)) {
			System.out.println("Course name already exists. \n Please try again");
		}else courseNameList.add(courseName);
		
		
		
		System.out.println("Choose your available professors");
		profNameList = acadCtrl.getProfessorNameList(facultyName);
		for(String p : profNameList) {
			System.out.println(p);
		}
		System.out.println("Enter the professor's staff name"); //input validation
		String ProfName = sc.nextLine();
		
		if(!CheckProfName(ProfName)) {
			System.out.println("Enter correct professor's name");
			ProfName = sc.nextLine();
		}
		
		// want to add if he's the coordinator?
		// input who is the coordinator
		System.out.println("Is he the course coordinator? Y for Yes / N for No");
		char yn = sc.nextLine().charAt(0);

		if(yn == 'Y' || yn == 'y') {
			// set ProfName to course coord
			acadCtrl.getProfessor(facultyName, ProfName).setCourseCoordinator(true);
			courseCoordList.add(ProfName);
		}
		else if (yn == 'N' || yn == 'n') {
			System.out.println("Who is the course coordinator for the course?");
			System.out.println();
			for(String p : profNameList) {
				System.out.println(p);
			}
			// for course coord
			System.out.println("Enter the professor's staff name"); //input validation
			String profNameCoord = sc.nextLine();
			
			if(!CheckProfName(profNameCoord)) {
				System.out.println("Enter correct professor's name");
				profNameCoord = sc.nextLine();
				acadCtrl.getProfessor(facultyName, profNameCoord).setCourseCoordinator(true);
				courseCoordList.add(profNameCoord);
			}
			acadCtrl.getProfessor(facultyName, profNameCoord).setCourseCoordinator(true);
			courseCoordList.add(profNameCoord);
			
		}
		
		
		System.out.println("Enter course Vacancy");
		if(sc.hasNextInt()) {
			courseVacancy = sc.nextInt();
		}else System.out.println("Enter only integers");
		
		String dummy = sc.nextLine(); // dummy scan changes from sc int to string
		
		System.out.println("Lectures only? Y for Yes / N for No");
		char input = sc.nextLine().charAt(0);

		if(input == 'Y' || input == 'y') {
			// creation of course object, by default only exam, only 1 lecture
			acadCtrl.passCourseDetails(courseCode, courseName, facultyName, courseVacancy, ProfName); 
		}
		else if (input == 'N' || input == 'n') {
			
			int tutVacancy = 0;
			
			System.out.println("Is there tutorial? Y for Yes / N for No");
			input = sc.nextLine().charAt(0);
			
			if (input == 'Y'|| input == 'y') {
				
				System.out.println("What is the class vacancy for each group?");	
				if(sc.hasNextInt()) {
					tutVacancy =  sc.nextInt(); // total tutorial vacancy > lecture size OK!
				}
				tutGroups = addTutorial(tutVacancy);
			
				dummy = sc.nextLine();
				
				System.out.println("Is there Lab? Y for Yes / N for No");
				input = sc.nextLine().charAt(0);
				
				if(input == 'Y'|| input == 'y') {
					System.out.println("Lab group is same as Lecture group");
					// creation of course object, with tutorials and lab group
					acadCtrl.passCourseDetailsWithTutorial(courseCode, courseName, facultyName, courseVacancy, ProfName, tutGroups, true, tutVacancy);
			
				}
				else if(input == 'N'|| input == 'n') {
					// creation of course object, with tutorials only
					acadCtrl.passCourseDetailsWithTutorial(courseCode, courseName, facultyName, courseVacancy, ProfName, tutGroups, false, tutVacancy);
				}
				else System.out.println("Please Enter Y or N only");
			}
		}else System.out.println("Please Enter Y or N only");
		
		//done and print
		
		System.out.println("Course added to System!");
		System.out.println("The current Lists of courses in "+ facultyName + " is: ");
		printCourses();
		System.out.println("To add sub components into Course Work use function 6 ");
		System.out.println();
	}
	
	
	
	private ArrayList<Integer> addTutorial(int vacancy) {
		int groupNumber = 0;
		ArrayList<Integer> tutGroups = new ArrayList<Integer>();
		do {
			System.out.println("Enter tutorial group number, Enter -1 to quit");
			if(sc.hasNextInt()) {
				groupNumber = sc.nextInt();
				tutGroups.add(groupNumber);
			}else System.out.println("Integers only");
		}while(groupNumber != -1);
		
		//remove the -1 that was added when exiting the DO-WHILE loop
		tutGroups.remove(tutGroups.lastIndexOf(-1));
		
		return tutGroups;
	}

	public void printCourses() {
		System.out.println("-----The current course names are-----");
		/*for(String name : courseNameList) {
			System.out.println(name);
		}*/
		for(int i = 0; i < courseNameList.size(); i++) {
			System.out.println(courseCodeList.get(i) + " " + courseNameList.get(i) + "," + " Course Coordinator: " + courseCoordList.get(i));
		}
	}
	
//	public void printCoursesAndCoord() {
//		System.out.println("-----The current course names are-----");
////		for(String name : courseNameList) {
////			System.out.println(name);
//		}
//	}
	

	//Check Methods
	
	public boolean CheckProfName(String profName) {
		for(String PName: profNameList) {
			if(PName.equals(profName)) {
				return true;
			}
		}
		return false;
	}
	
	//check if fac exists;
	public boolean checkFacName(String facultyName) {
		for(String facName: facultyNameList) {
			if (facultyName.equals(facName)) {
				return true;
			}
		}
		return false;
	}
	//check if course exist
	public boolean checkCourseCode(String courseCode) {
		for(String code: courseCodeList) {
			if(courseCode.equals(code))
				return true;
		}
		return false;
	}
	
	public boolean checkCourseName(String courseName) {
		for(String name: courseNameList) {
			if(courseName.equals(name)) {
				return true;
			}
		}
		return false;
	}
}

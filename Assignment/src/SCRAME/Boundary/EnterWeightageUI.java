package SCRAME.Boundary;

import SCRAME.Controller.*;


import java.util.ArrayList;
import java.util.Scanner;

public class EnterWeightageUI {
	Scanner sc = new Scanner(System.in);
	private ArrayList<String> courseCodeList;
	private ArrayList<String> facultyNameList;
	private AcademicsController acadCtrl;
	
	//function 6: 
	//ask user which faculty and course he wants to enter
	//Print out the course components: exams, coursework, and coursework's sub component
	//ask what course assesment to enter
	//2. exam + course work(no sub components)
	//3. exam + course work(2 sub components)
	//4. exam + course work + many sub components in which user will create own sub components;
	
	public void setAcadController(AcademicsController acadCtrl) {
		this.acadCtrl = acadCtrl;
	}
	
	public void askForWeightage() {

		String courseCode = null;
		
		System.out.println("---------Showing Faculty name---------");
		
		System.out.println("The current faculties are ");
		facultyNameList = acadCtrl.getFacultyNameList();
		for(String fName : facultyNameList) {
			System.out.println(fName);
		}
		
		String facultyName = null;
		
		while(true)
		{
			System.out.println("Enter course's faculty name");
			facultyName = sc.nextLine().toUpperCase();
			
			//check if input data of faculty is correct
			if(checkFacName(facultyName)) {
				facultyNameList.add(facultyName);
				break;
			}
			else System.out.println("Wrong faculty Name");
		}
		
		System.out.println("---------Showing all Course Code student registered for---------");
		courseCodeList = acadCtrl.getCourseCodeList(facultyName);
		int index = 1;
		for(String code : courseCodeList) {
			System.out.println(index + ". " + code);
			index++;
		}
		
		
		do {
			System.out.println("Choose course code to enter weightage for:");
			if(sc.hasNextInt()) {
				int choice = sc.nextInt();
				if (choice > index-1) {
					System.out.println("Wrong input");
					String dummy = sc.nextLine();
					continue;
				}
				courseCode = chooseCourseCode(choice);
			}else {
				String dummy = sc.nextLine();
				System.out.println("Try again, enter the choice number only");
			}
		}while(courseCode == null);
		
	
		if(checkCourseCode(courseCode)) { //if course code is correct
			int choice = choose();
			enterWeightage(facultyName, courseCode, choice);
		}else System.out.println("Course code does not exists. \n Please try again"); 
		
		System.out.println("Course weightage added to " + courseCode);
		
	}
	
	//check functions
	
	public String chooseCourseCode(int choice) {
		String courseCode = courseCodeList.get(choice-1);
		return courseCode;
	}
	
	public boolean checkCourseCode(String courseCode) {
		for(String code: courseCodeList) {
			if(code.equals(courseCode))
				return true;
		}
		return false;
	}
	
	public int choose() {
		int choice;
		System.out.println("Choose what to enter");
		System.out.println("1. exam + course work(no sub components)");
		System.out.println("2. exam + course work + many sub components");
		choice =  sc.nextInt();
		return choice;
	}
	
	public void enterWeightage(String facultyName, String courseCode, int choice) {
		int weightage = 0;
		boolean loop = true;
		while(loop){
			
			switch(choice) {
			case 1:
				//course  =  exam + course work(main only)
				System.out.println("Enter exam weightage"); 
				
				if(sc.hasNextInt()) {
					weightage = sc.nextInt();
					if(weightage > 100) {
						System.out.println("Enter an integer within 0-100");
					}
					acadCtrl.passWeightage(weightage, 100-weightage, facultyName, courseCode);
					System.out.println("course work weightage is " + (100-weightage));
					loop = false;
				}else System.out.println("Enter integers only");
				if(acadCtrl.getTotalWeightage(facultyName, courseCode) > 100 ) {
					System.out.println("Wrong weightage entered please try again");
					loop = true;
				}
				
				break;
				
			case 2: 
				//course = exam + multiple sub components for courseWork
				System.out.println("Enter exam weightage"); 
				
				if(sc.hasNextInt()) {
					weightage = sc.nextInt();
					if(weightage > 100) {
						System.out.println("Enter an integer within 0-100");
					}
					acadCtrl.passWeightage(weightage, 100-weightage, facultyName, courseCode);
					System.out.println("course work weightage is " + (100-weightage));
					loop = false;
				}else System.out.println("Enter integers only");
		
				
				System.out.println("How many sub components are there");
				if(sc.hasNextInt()) {
					int n = sc.nextInt();
					for(int i = 1; i <= n; i++) {
						System.out.println("Enter course work for sub component #" + i);
						passSubComponent(facultyName, courseCode);
					}
				}
				loop = false;
				break;
				
			default:
				System.out.println("Please enter 1 or 2");
				while(true) {
					if(sc.hasNextInt()) {
						choice = sc.nextInt();
						break;
					}
				}
				break;
			}
		}
		String dummy = sc.nextLine();
	}
		
	
	//add subcomponent
		public void passSubComponent(String facultyName, String courseCode) {
			System.out.println("Enter sub component Name");
			String subCompName = sc.nextLine().toUpperCase();
			
			String dummy = sc.nextLine();
			System.out.println("Enter subcomponent weightage, (the weightage pertaining to course work not overall!) ");
			
			int subCompWeight = sc.nextInt();
			acadCtrl.passSubComponent(subCompName,subCompWeight, facultyName, courseCode);
		}
	
		public boolean checkFacName(String facultyName) {
			for(String facName: facultyNameList) {
				if (facultyName.equals(facName)) {
					return true;
				}
			}
			return false;
		}
}

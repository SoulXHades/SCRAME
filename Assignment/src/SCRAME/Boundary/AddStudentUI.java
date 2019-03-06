package SCRAME.Boundary;

import SCRAME.Controller.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class AddStudentUI implements IAddBoundary {
	static Scanner sc = new Scanner(System.in);
	private HumanController humanCtrl;

	public AddStudentUI() {
	}
	
	// take in HumanController obj
	public void setHumanController(HumanController humanCtrl) {
		this.humanCtrl = humanCtrl;
	}
	
	// display and ask for user input
	public void askStudDetails() {
		String studName, matricNumber, facultyName;
		int year;
		
		System.out.println("Enter name for student: ");
		studName = sc.nextLine();
		System.out.println("Enter Matriculation Number: ");
		matricNumber = sc.next().toUpperCase();
		System.out.println("Enter Faculty of student: ");
		facultyName = sc.next().toUpperCase();
		System.out.println("Enter year student is in: ");
		year = sc.nextInt();
		
		//pass back to StudentController
		// Error message if student alr exist in DB
		if(humanCtrl.passStudDetails(studName, matricNumber, facultyName, year))
		{
			System.out.println("Student named " + studName + " added!");
			
			//get arraylist of student basic info
			ArrayList<Map<String, String>> studInfoList = humanCtrl.getStudInfoList();
			System.out.println("\nList of students in the database:");
			System.out.println("Name - Matriculation Number");
			
			for(Map<String, String> studInfo : studInfoList)
			{
				System.out.println(studInfo.get("studName") + " - " + studInfo.get("matricNumber"));
			}
			
			//create more new lines
			System.out.println("");
		}
		else
			System.out.println("Student with same matric number already exists!\n");
		
		String dummy = sc.nextLine();
	}
	
}

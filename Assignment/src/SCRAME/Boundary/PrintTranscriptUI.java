package SCRAME.Boundary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import SCRAME.Controller.*;
import SCRAME.Entity.*;

public class PrintTranscriptUI implements IPrintBoundary {
	private HumanController humanCtrl;
	public Scanner sc;
	
	//constructor
	public PrintTranscriptUI()
	{
		sc = new Scanner(System.in);
	}
	
	public void setHumanController(HumanController humanCtrl) {
		this.humanCtrl = humanCtrl;
	}
	
	public void displayTranscript() {
		String matricNumber;
		
		//prompt for student matric number before getting results
		while(true)
		{
			System.out.println("Enter Student Matric Number");
			matricNumber = sc.nextLine().toUpperCase();
			
			//check if student exist then break from infinite WHILE loop
			if(humanCtrl.isStudentInList(matricNumber))
				break;
			else
				System.out.println("\nStudent does not exist!\n");
		}
		
		//if the student exists, get all of student's results
		ArrayList<Map<String, String>> studResults = humanCtrl.showStudResults(matricNumber);
		
		//print error message if student have not registered for any course
		if(studResults.size() == 0)
		{
			System.out.println("\nStudent have not registered for any course!\n");
			return;
		}
		//print all of student's results
		for(Map<String, String> m : studResults){
			for(String eachResultType : m.keySet())
			{
				System.out.println(eachResultType + " - " + m.get(eachResultType));
			}
			
			//more new lines
			System.out.println("");
		}
		
		//print the corresponding weightage
		Map<String, ArrayList<String[]>> allWeight = humanCtrl.showSubWeightage(matricNumber);
		Map<String, Integer> examWeightageList = humanCtrl.getExamWeightage(matricNumber);
		
		//get all the courseCode which are the keys of the HashMap
		for(String courseCode : allWeight.keySet())
		{
			System.out.println(courseCode);
			//get exam weightage using same key as both are the same courseCode used as key
			System.out.println("Exam weightage - " + examWeightageList.get(courseCode));
			
			System.out.println("Subcomponent weightage");
			
			//get each sub component
			for(String[] sub : allWeight.get(courseCode))
			{
				//print <component name> - <weightage>
				System.out.println(sub[0] + " - " + sub[1]);
			}
			
			//more new lines
			System.out.println("");
		}
		
		//create one more new line for presentation
		System.out.println("");
	}
}

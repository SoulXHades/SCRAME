 //things to do in this class:
//people controller. should we have seperate prof and stud controller

package SCRAME.Controller;

import SCRAME.Boundary.*;
import SCRAME.Entity.*;
import java.io.*;
import java.util.*;

public class MainController {
	//declare sub boundaries and sub controllers
	private AddCourseUI addCourseUI;
	private AddStudentUI addStudUI;
	private Check_RegisterUI check_RegUI;
	private PrintStudentListUI printStudListUI;
	private EnterMarksUI enterMarksUI;
	private EnterWeightageUI enterWeightageUI;
	private PrintTranscriptUI printTransUI;
	private PrintStatsUI printStatsUI;
	private HumanController humanCtrl;
	private AcademicsController acadCtrl;
	
	private AppController appCtrl;
	
	private String facultyFileName = "FacultyData.dat";
	private String profFileName = "ProfessorsData.dat";
	private String studFileName = "StudentData.dat";
	
	private ArrayList<Faculty> facultyList;
	private ArrayList<Professors> profList;
	private ArrayList<Student> studentList;
	
	public MainController()
	{
		addCourseUI = new AddCourseUI();							//polymorphism to parent class AddBoundary
		addStudUI = new AddStudentUI();							//polymorphism to parent class AddBoundary
		check_RegUI = new Check_RegisterUI();
		printStudListUI = new PrintStudentListUI();
		enterMarksUI = new EnterMarksUI();
		enterWeightageUI = new EnterWeightageUI();
		acadCtrl = new AcademicsController();
		printTransUI = new PrintTranscriptUI();
		printStatsUI = new PrintStatsUI();
		humanCtrl = new HumanController();
		
		openFromDatabase();
		setAllContexts();
		setAllData();
	}
	
	//get data from database
	private void openFromDatabase()
	{
		//Deserialization 
        try { 
  
            //Reading the object from a file 
            FileInputStream facultyFile = new FileInputStream(facultyFileName);
            ObjectInputStream facultyIn = new ObjectInputStream(facultyFile);
            FileInputStream profFile = new FileInputStream(profFileName);
            ObjectInputStream profIn = new ObjectInputStream(profFile);
            FileInputStream studFile = new FileInputStream(studFileName);
            ObjectInputStream studIn = new ObjectInputStream(studFile);
  
            //Method for deserialization of object 
            this.facultyList = (ArrayList<Faculty>)facultyIn.readObject();
            this.profList = (ArrayList<Professors>)profIn.readObject();
            this.studentList = (ArrayList<Student>)studIn.readObject();
  
            facultyIn.close();
            profIn.close();
            studIn.close();
            facultyFile.close();
            profFile.close();
            studFile.close();
            
            if(studentList == null)
            	studentList = new ArrayList<Student>();
        } 
  
        catch (IOException ex) { 
            System.out.println("IOException is caught"); 
        } 
  
        catch (ClassNotFoundException ex) { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
	}
	
	//save data to database
	private void saveToDatabase()
	{
		// Serialization  
        try
        {    
            //Saving of object in a file 
        	FileOutputStream facultyFile = new FileOutputStream(facultyFileName);
            ObjectOutputStream facultyOut = new ObjectOutputStream(facultyFile);
            FileOutputStream profFile = new FileOutputStream(profFileName);
            ObjectOutputStream profOut = new ObjectOutputStream(profFile);
            FileOutputStream studFile = new FileOutputStream(studFileName);
            ObjectOutputStream studOut = new ObjectOutputStream(studFile);
              
            // Method for serialization of object 
            facultyOut.writeObject(facultyList);
            profOut.writeObject(profList);
            studOut.writeObject(studentList);
              
            facultyOut.close();
            profOut.close();
            studOut.close();
            facultyFile.close();
            profFile.close();
            studFile.close();
              
            System.out.println("Object has been serialized"); 
  
        } 
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
	}
	
	//send object of sub controllers to sub boundaries
	private void setAllContexts()
	{
		addStudUI.setHumanController(humanCtrl);
		addCourseUI.setAcadController(acadCtrl);
		check_RegUI.setAcadController(acadCtrl);
		printStudListUI.setAcademicController(acadCtrl);
		printStatsUI.setAcadController(acadCtrl);
		enterWeightageUI.setAcadController(acadCtrl);
		enterMarksUI.setHumanController(humanCtrl);
		printTransUI.setHumanController(humanCtrl);
		
		acadCtrl.setHumanController(humanCtrl);
	}
	
	private void setAllData()
	{
		acadCtrl.setFacultyList(facultyList);
		acadCtrl.setProfList(profList);
		humanCtrl.setStudFacList(studentList, facultyList);
	}
	
	//process user input choice
	public int chooseFunction(int choice)
	{
		switch(choice)
		{
			//for add student
			case 1:
				//to display Add Student Boundary 
				addStudUI.askStudDetails();
				return 1;
			
			//for add course
			case 2:
				//to display Add Student Boundary 
				addCourseUI.askCourseDetails();
				return 1;
			
			//Register student for a course (this include registering for Tutorial/Lab classes)
			case 3:
				check_RegUI.registrationDisplay();
				return 1;
			
			//Check available slot in a class (vacancy in a class)
			case 4:
				check_RegUI.displayVacancy();
				return 1;
			
			//Print student list by lecture, tutorial or laboratory session for a course.
			case 5:
				printStudListUI.studentListMenu();
				return 1;
			
			//Enter course assessment components weightage
			case 6: 
				enterWeightageUI.askForWeightage();
				return 1;
			
			//Enter exam marks and coursework mark inclusive of its components
			case 7:
				enterMarksUI.askForMarks();
				return 1;
			
			//Print course statistics
			case 8:
				printStatsUI.displayMenu();
				return 1;
			
			//Print student transcript
			case 9: 
				printTransUI.displayTranscript();
				return 1;
			
			//quit the app
			case 10:
				saveToDatabase();
				appCtrl.stop();
				return 1;
		}
		
		//return -1 to indicate that the user did not enter a correct input
		return -1;
	}
	
	//to allow MainController to stop the app when user is quiting the program
	protected void setContext(AppController appCtrl)
	{
		this.appCtrl = appCtrl;
	}

}

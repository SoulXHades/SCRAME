package SCRAME.Boundary;

import java.util.ArrayList;
import java.util.Scanner;

import SCRAME.Controller.*;

public class EnterMarksUI {
	static Scanner sc = new Scanner(System.in);
	private HumanController humanCtrl;
	private ArrayList<String> courseCodeList;
	int counter;
	
	public EnterMarksUI() {
	}
	
	public void setHumanController(HumanController humanCtrl) {
		this.humanCtrl = humanCtrl;
	}
	
	//function 7 & 8
	public void askForMarks() {
		String matricNumber = null;
		int choice = -1;
		double examScore = -1, courseworkScore = -1, subComponentScore = -1;
		ArrayList<Double> subComponentMarks = new ArrayList<Double>();
		
		//ask user for matric number and check if it is correct
		do {
			System.out.println("Enter student's matric number: ");
			matricNumber = sc.nextLine().toUpperCase();
			if(!humanCtrl.isStudentInList(matricNumber)) {
				System.out.println("Entered wrong matric number, please try again");
			}
		}while(!humanCtrl.isStudentInList(matricNumber));
		
		//ask user for course code to enter marks for
		choice = printRegisteredCourse(matricNumber);
		String courseCode = chooseCourseCode(choice);
		
		
		boolean loop = true;
		while(loop) {
			//ask user what to enter, exam ? course work? or sub?
			choice = chooseWhatToEnter();
			switch(choice) {
			case 1: //exam only
				
				//input validation
				if(humanCtrl.checkGotCourseWork(courseCode, matricNumber)) {
					System.out.println("Course have course work use function 2 or 3");
					break;
				}
				examScore = enterExamScore();
				//if courseWork = 0 means there is no coursework weightage is fully on exam
				humanCtrl.passMarksCourse(matricNumber, courseCode, examScore, 0);
				loop = false;
				break;
				
			case 2:	
				
				//input validation
				if(!humanCtrl.checkGotCourseWork(courseCode, matricNumber)) {
					System.out.println("Course have no course work");
					break;
				}
				
				counter = humanCtrl.checkNumberOfSubCompnent(courseCode, matricNumber);
				
				if(counter >= 1) {
					System.out.println("Course Work has more than 1 component use function 3");
					break;
				}
				
				examScore = enterExamScore();
				courseworkScore = enterCourseWorkScore();
				humanCtrl.passMarksCourse(matricNumber, courseCode, examScore, courseworkScore);
				
				loop = false;
				break;
				
			case 3:
				
				//input validation
				if(!humanCtrl.checkGotCourseWork(courseCode, matricNumber)) {
					System.out.println("Course have no course work");
					break;
				}
				if(!humanCtrl.checkGotCourseWork(courseCode, matricNumber)) {
					System.out.println("Course have no course work");
					break;
				}
				
				//get number of sub Components
				 counter = humanCtrl.checkNumberOfSubCompnent(courseCode, matricNumber);
				
				if(counter <= 1) {
					System.out.println("CourseWork only got main component no need to enter subcomponent");
					break;
				}
				
				examScore = enterExamScore();
				
				//get sub component score
				System.out.println("Enter individual sub Component Score");
				int index = 1;
				for(int i = 0; i < counter ; i++) {
//					subComponentScore = enterSubComponentScore();
					do {
						System.out.println("Sub Component # " + index + " raw score: ");
						index++;
						if(sc.hasNextDouble()) {
							subComponentScore = sc.nextDouble();
						}else System.out.println("Please enter numbers only and within 0 - 100");		
					}while(subComponentScore > 100 && subComponentScore < 0);
					
					subComponentMarks.add(subComponentScore);
				}
				
				System.out.println("Overall course work score will be automatically tabulated");
				//if courseWorkScore == -2 -> need to calculate score from weightage and sub Component marks
				humanCtrl.passMarksCourseWithSubComponent(matricNumber, courseCode, examScore, -2 , subComponentMarks);
				loop = false;
				break;
			
			default:
				System.out.println("Please enter 1 or 2 or 3");
				while(true) {
					if(sc.hasNextInt()) {
						choice = sc.nextInt();
						break;
					}
				}
				break;
			}	
		}
		
		
		
		System.out.println("Marks entered");
			String dummy = sc.nextLine();
	}

//	private double enterSubComponentScore() {
//		double subComponentScore = -1;
//		int index = 1;
//		do {
//			System.out.println("Sub Component # " + index + " raw score: ");
//			index++;
//			if(sc.hasNextDouble()) {
//				subComponentScore = sc.nextDouble();
//			}else System.out.println("Please enter numbers only and within 0 - 100");		
//		}while(subComponentScore > 100 && subComponentScore < 0);
//		
//		return subComponentScore;
//	}

	
	private double enterCourseWorkScore() {
		double courseworkScore = -1;
		do {
			System.out.println("Enter coursework score: ");
			if(sc.hasNextDouble()) {
				courseworkScore = sc.nextDouble();
			}else System.out.println("Please enter numbers only and within 0 - 100");		
		}while(courseworkScore > 100 && courseworkScore < 0);
		
		return courseworkScore;
	}

	private double enterExamScore() {
		double examScore = -1;
		do {
			System.out.println("Enter exam score: ");
			if(sc.hasNextDouble()) {		
				examScore = sc.nextDouble();
			}else System.out.println("Please enter numbers only and within 0 - 100");			
		}while(examScore > 100 && examScore < 0);
		return examScore;
	}
	
	//choose methods

	private int chooseWhatToEnter() {
		int choice = -1;
		String dummy = sc.nextLine();
		do {
			System.out.println("Choose what to enter");
			System.out.println("1. Exam Marks only");
			System.out.println("2. Exam + Course Work Marks");
			System.out.println("3. Exam + Course Work + Sub Component marks");
			
			if(sc.hasNextInt()) {
				choice = sc.nextInt();
			}else System.out.println("Enter 1 - 3 only");
		}while(choice < 0 && choice > 3);
		
		return choice;
	}
	
	
	public String chooseCourseCode(int choice) {
		return courseCodeList.get(choice - 1);
	}

	public int printRegisteredCourse(String matricNumber) {
		
		int choice = 0;
		System.out.println("-------Printing the course registered for student" + matricNumber + "-------");
		courseCodeList = humanCtrl.courseRegisteredForStudent(matricNumber);
		int index = 1;
		//prints course code
		for(String s: courseCodeList) {
			System.out.println(index + ". "+ s);
			index ++;
		}
		
		while(true){
			System.out.println("Choose the course to enter marks for");
			if(sc.hasNextInt()){
				choice = sc.nextInt();
				if (choice > index-1 || choice <= 0) {
					System.out.println("Wrong input");
					String dummy = sc.nextLine();
				}else break;
			}	
		}
		return choice;
	}
}

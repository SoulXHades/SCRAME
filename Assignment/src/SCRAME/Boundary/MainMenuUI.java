package SCRAME.Boundary;

import SCRAME.Controller.*;
import java.util.Scanner;

public class MainMenuUI {
	static Scanner sc = new Scanner(System.in);
	private MainController mainCtrl;
	
	public MainMenuUI() {
	}
	
	// take in MainController obj
	public void setMainController(MainController mainCtrl) {
		this.mainCtrl = mainCtrl;
	}
	
	// display menu
	public void displayMenu() {
		int choice;
		System.out.println("Welcome to SCRAME\n");
		do {
			//print menu options
			System.out.println("**************************************************************************************************");
			System.out.println("1. Add a student.\n"
					+ "2. Add a course.\n"
					+ "3. Register student for a course (this include registering for Tutorial/Lab classes).\n"
					+ "4. Check available slot in a class (vacancy in a class).\n"
					+ "5. Print student list by lecture, tutorial, or laboratory session for a course.\n"
					+ "6. Enter course assessment components weightage.\n"
					+ "7. Enter exam mark and coursework mark - inclusive of its components.\n"
					+ "8. Print course statistics.\n"
					+ "9. Print student transcript.\n"
					+ "10. Quit.");
			System.out.println("**************************************************************************************************");
			System.out.println("\nPlease choose an option from the menu:");
			choice = sc.nextInt();
		} while (mainCtrl.chooseFunction(choice) == -1); // pass choice to MainController
													 	// loop if invalid input return -1
	}
	
}

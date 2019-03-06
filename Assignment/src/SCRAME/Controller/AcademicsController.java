package SCRAME.Controller;

import SCRAME.Entity.*;
import java.util.*;

public class AcademicsController {
	private ArrayList<Faculty> facultyList;
	private ArrayList<Professors> profList;

	public Faculty tempFaculty;
	public HumanController humanCtrl;
	
	
	//empty constructor
	public AcademicsController() {}
	public void setHumanController(HumanController humanCtrl)
	{
		this.humanCtrl = humanCtrl;
	}
	//get faculty list from main controller
	public void setFacultyList(ArrayList<Faculty> facultyList)
	{
		this.facultyList = facultyList;
	}
	//get profList from main controller
	public void setProfList(ArrayList<Professors> profList) {
		this.profList = profList;
	}
	
	public void createFaculty(String facultyName) {
		facultyList.add(new Faculty(facultyName));
	}

	
	//get prof from prof Name and facultyName
	public Professors getProfessor(String facultyName,  String ProfName) {
		for(Faculty f: facultyList) {
			if((f.getFacultyName()).equals(facultyName)) { 
				for(Professors p: profList) {
					if((p.getStaffName()).equals(ProfName))
						return p;
				}
			}
		}
		return null;	
	}
	
	//for default create course no course course work no lab no tutorials
	public void passCourseDetails(String courseCode, String courseName, String facultyName, int courseVacancy, String coordinator) {
		
		
		for(Faculty f: facultyList) {
			if((f.getFacultyName()).equals(facultyName)) {
				f.createCourse(courseCode, courseName, facultyName, courseVacancy, getProfessor(facultyName, coordinator));
				f.getCourse(courseCode).setCoordinator(getProfessor(facultyName, coordinator));
			}
		}

		getProfessor(facultyName, coordinator).setCourseCoordinator(true);
		
	}
	//for creation of courses with lab or tutorials or tutorials only
	public void passCourseDetailsWithTutorial(String courseCode, String courseName, String facultyName,
			int courseVacancy, String coordinator, ArrayList<Integer> tutGroups, boolean lab, int tutVacancy) {
		for(Faculty f : facultyList) {
			if((f.getFacultyName()).equals(facultyName)) {
				f.createCourseWithClass(courseCode, courseName, facultyName, courseVacancy, getProfessor(facultyName, coordinator), tutGroups, lab, tutVacancy);
				f.getCourse(courseCode).setCoordinator(getProfessor(facultyName, coordinator));
			}
			getProfessor(facultyName, coordinator).setCourseCoordinator(true);
		}
		
	
	}


	public void passChoice(int nextInt) {
		// TODO Auto-generated method stub
		
	}
	
	//Get List methods
	
	public ArrayList<String> getCourseNameList(String facultyName) {
		ArrayList<String> courseNameList = new ArrayList<String>();
		for(Faculty f: facultyList) {
			if((f.getFacultyName()).equals(facultyName))
				courseNameList = f.getCourseNames();
		}
		return courseNameList;
	}
	
	public ArrayList<String> getCourseCodeList(String facultyName) {
		ArrayList<String> courseCodeList = new ArrayList<String>();
		for(Faculty f: facultyList) {
			if((f.getFacultyName()).equals(facultyName))
				courseCodeList = f.getCourseCodes();
		}
		return courseCodeList;
	}
	
	public ArrayList<String> getFacultyNameList() {
		ArrayList<String> facultyNameList = new ArrayList<String>();
		for(Faculty f: facultyList) {
			facultyNameList.add(f.getFacultyName());
		}
		return facultyNameList;
	}

	
	public ArrayList<String> getProfessorNameList(String facultyName) {
		ArrayList<String> profNameList = new ArrayList<String>();
		for(Professors p: profList) {
			if((p.getFaculty()).equals(facultyName) && (p.isCourseCoordinator() == false)) {
				profNameList.add(p.getStaffName());
			}
		}
		return profNameList;
	}
	
	//for enter weightage
	public void passWeightage(int examWeightage, int courseWeightage, String facultyName, String courseCode) {
		for(Faculty f: facultyList) {
			if((f.getFacultyName()).equals(facultyName)) {
				f.passCourseWork(examWeightage, courseWeightage, courseCode);
			}
		}
	}
	
	public void passSubComponent(String subCompName, int subCompWeight, String facultyName, String courseCode) {
		for(Faculty f: facultyList) {
			if((f.getFacultyName()).equals(facultyName)) {
				f.passSubComponent(subCompName, subCompWeight,courseCode);
			}
		}
		
	}
	
	//get an list of faculty names for Check_RegisterUI class
	public Map<String, ArrayList<String>> getCourseInfoList(String facultyName)
	{
		Map<String, ArrayList<String>> tempCourseInfoList = new HashMap<String, ArrayList<String>>();
		ArrayList<String> tempCourseCode = new ArrayList<String>();
		ArrayList<String> tempCourseName = new ArrayList<String>();
		ArrayList<String> tempCourseCoordinator = new ArrayList<String>();
		
		for(Course c : getFaculty(facultyName).getCourseList())
		{
			tempCourseCode.add(c.getCourseCode());
			tempCourseName.add(c.getCourseName());
			tempCourseCoordinator.add(c.getCoordinator().getStaffName());
		}
		
		tempCourseInfoList.put("courseCode", tempCourseCode);
		tempCourseInfoList.put("courseName", tempCourseName);
		tempCourseInfoList.put("courseCoordinator", tempCourseCoordinator);
		
		return tempCourseInfoList;
	}
	
	public Map<String, ArrayList<Integer>> getGroupingNameList(String facultyName, String courseCode)
	{
		Map<String, ArrayList<Integer>> tempLessonNameList = new HashMap<String, ArrayList<Integer>>();
		ArrayList<Integer> tempTutorialNameList = new ArrayList<Integer>();
		ArrayList<Integer> tempLabNameList = new ArrayList<Integer>();
		Course tempCourse = getFaculty(facultyName).getCourse(courseCode);
		
		for(Lesson l : tempCourse.getTutorialList())
		{
			tempTutorialNameList.add(l.getGroupNumber());
		}
		
		for(Lesson l : tempCourse.getLabList())
		{
			tempLabNameList.add(l.getGroupNumber());
		}
			
		tempLessonNameList.put("tutorial", tempTutorialNameList);
		tempLessonNameList.put("lab", tempLabNameList);
		
		return tempLessonNameList;
	}
	
	private Faculty getFaculty(String facultyName)
	{
		
		for(Faculty f : facultyList)
		{
			if((f.getFacultyName()).equals(facultyName))
			{
				return f;
			}
		}
		
		return null;
	}
	
	public boolean addStudentToCourse(String facultyName, String courseCode, int groupNumber, boolean haveLab, String matricNumber)
	{
		boolean successfullyAdded = false;
		Faculty tempFac = getFaculty(facultyName);
		Course tempCourse = tempFac.getCourse(courseCode);
		Student tempStud = this.humanCtrl.findStudent(matricNumber);
		
		//add student to course
		if(groupNumber != -1)
			successfullyAdded = tempCourse.addStudent(groupNumber, haveLab, tempStud);
		else
			successfullyAdded = tempCourse.addStudent(tempStud);	//only add to lecture
		
		//only add course to student if course registered student if there is space
		if(successfullyAdded)
			tempStud.addCourse(tempCourse);	//create new result for student for new course
		
		return successfullyAdded;
	}
	
	public String getVacancy(String facultyName, String courseCode, boolean isTutorial, int groupNumber)
	{
		Faculty tempFac = getFaculty(facultyName);
		Course tempCourse = tempFac.getCourse(courseCode);

		return tempCourse.getTutorial(groupNumber).getVacancy();
	}
	
	public String getVacancy(String facultyName, String courseCode)
	{
		Faculty tempFac = getFaculty(facultyName);
		Course tempCourse = tempFac.getCourse(courseCode);

		return tempCourse.getCourseVacancy() + "/" + tempCourse.getOriginalCourseVacancy() + " [vacancy/total size]";
	}
	
	
	//for PrintStudentListUI
	public ArrayList<String> getStudentListCourse(String facultyName, String courseCode)
	{
		ArrayList<String> tempStudList = new ArrayList<String>();
		
		//get all the students from that course of that faculty
		for(Student s : this.getFaculty(facultyName).getCourse(courseCode).getStudentList())
		{
			//get info of student and construct them for printing at boundary later
			tempStudList.add(s.getStudName() + " of year " + s.getYear() + ", " + s.getFacultyName());
		}
		
		return tempStudList;
	}

	//for PrintStudentListUI
	public ArrayList<String> getStudentListLesson(String facultyName, String courseCode, int groupNumber)
	{
		ArrayList<String> tempStudList = new ArrayList<String>();
		
		//get all the students from that tutorial/lab of that course of that faculty
		for(Student s : this.getFaculty(facultyName).getCourse(courseCode).getTutorial(groupNumber).getStudentList())
		{
			//get info of student and construct them for printing at boundary later
			tempStudList.add(s.getStudName() + " of year " + s.getYear() + ", " + s.getFacultyName());
		}
		
		return tempStudList;
	}
	
	// in AcademicsController
	// for printCourseStatsUI
	public Map<String, Integer> getCourseStats(String facultyName, String courseCode){
		Map<String, Integer> tempCourseStats = new HashMap<String, Integer>();
		int tempVacancy = 0;
		int tempNumEnrolled = 0;
		int tempCourseWeightage = 0;
		int tempExamWeightage = 0;
		
		for (Course c : getFaculty(facultyName).getCourseList()) {
			if ((c.getCourseCode()).equals(courseCode)) {
				tempVacancy = c.getCourseVacancy();
				tempNumEnrolled = c.getOriginalCourseVacancy() - c.getCourseVacancy(); // need to add getOriginalCourseVacancy()
																						// method in Course Entity class
				tempCourseWeightage = c.getCourseWeightage();
				tempExamWeightage = c.getExamWeightage();
				break;
			}
		}
		
		tempCourseStats.put("courseVacancy" , tempVacancy);
		tempCourseStats.put("numEnrolled", tempNumEnrolled);
		tempCourseStats.put("courseWeightage", tempCourseWeightage);
		tempCourseStats.put("examWeightage", tempExamWeightage);
		
		return tempCourseStats;
	}
	
	public Map<String, Map<String, Double>> getCourseStats_gradePercentage(String facultyName, String courseCode)
	{
		int i=0;
		double marks=0;
		final int sizeOfStatsTypeList = 3;
		String[] statsTypeList = {"Overall", "Exam", "Coursework"};
		String[] gradeList = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "F"};
		
		
		ArrayList<Student> studInCourse = this.getFaculty(facultyName).getCourse(courseCode).getStudentList();
		
		//initialize HashMap to return course stats without the need to create new class to return data
		Map<String, Double> overall_stats = new HashMap<String, Double>();
		Map<String, Double> exam_stats = new HashMap<String, Double>();
		Map<String, Double> coursework_stats = new HashMap<String, Double>();
		Map<String, Map<String, Double>> totalStats = new HashMap<String, Map<String, Double>>();
		
		totalStats.put(statsTypeList[0], overall_stats);
		totalStats.put(statsTypeList[1], exam_stats);
		totalStats.put(statsTypeList[2], coursework_stats);
		
		//initialize all grades to 0 number of students having them
		for(int j=0; j<9; j++)
		{
			overall_stats.put(gradeList[j], 0d);
			exam_stats.put(gradeList[j], 0d);
			coursework_stats.put(gradeList[j], 0d);
		}
		
		//get each student in that course
		for(Student s : studInCourse)
		{
			//store the number of students have that grade in a hash map
			for(Results result : s.getResultList())
			{
				//check if result of the student is the same as the course we are looking for
				if(!(result.getCourseCode()).equals(courseCode))
					continue;
				
				while(true)
				{
					if(i == 0)
						marks = ((result.getCourseworkScore()/100) * (100 - this.getFaculty(facultyName).getCourse(courseCode).getExamWeightage())) + ((result.getExamScore()/100) * this.getFaculty(facultyName).getCourse(courseCode).getExamWeightage());
					else if(i == 1)
						marks = result.getExamScore();
					else
						marks = result.getCourseworkScore();
					
					if(marks > 89)
					{
						if(i == 0)
							overall_stats.put("A+", 1 + overall_stats.get("A+"));
						else if(i == 1)
							exam_stats.put("A+", 1 + exam_stats.get("A+"));
						else
							coursework_stats.put("A+", 1 + coursework_stats.get("A+"));
					}
					else if(marks < 90 && marks > 79)
					{
						if(i == 0)
							overall_stats.put("A", 1 + overall_stats.get("A"));
						else if(i == 1)
							exam_stats.put("A", 1 + exam_stats.get("A"));
						else
							coursework_stats.put("A", 1 + coursework_stats.get("A"));
					}
					else if(marks < 80 && marks > 74)
					{
						if(i == 0)
							overall_stats.put("A-", 1 + overall_stats.get("A-"));
						else if(i == 1)
							exam_stats.put("A-", 1 + exam_stats.get("A-"));
						else
							coursework_stats.put("A-", 1 + coursework_stats.get("A-"));
					}
					else if(marks < 75 && marks > 69)
					{
						if(i == 0)
							overall_stats.put("B+", 1 + overall_stats.get("B+"));
						else if(i == 1)
							exam_stats.put("B+", 1 + exam_stats.get("B+"));
						else
							coursework_stats.put("B+", 1 + coursework_stats.get("B+"));
					}
					else if(marks < 70 && marks > 64)
					{
						if(i == 0)
							overall_stats.put("B", 1 + overall_stats.get("B"));
						else if(i == 1)
							exam_stats.put("B", 1 + exam_stats.get("B"));
						else
							coursework_stats.put("B", 1 + coursework_stats.get("B"));
					}
					else if(marks < 65 && marks > 59)
					{
						if(i == 0)
							overall_stats.put("B-", 1 + overall_stats.get("B-"));
						else if(i == 1)
							exam_stats.put("B-", 1 + exam_stats.get("B-"));
						else
							coursework_stats.put("B-", 1 + coursework_stats.get("B-"));
					}
					else if(marks < 60 && marks > 54)
					{
						if(i == 0)
							overall_stats.put("C+", 1 + overall_stats.get("C+"));
						else if(i == 1)
							exam_stats.put("C+", 1 + exam_stats.get("C+"));
						else
							coursework_stats.put("C+", 1 + coursework_stats.get("C+"));
					}
					else if(marks < 55 && marks > 49)
					{
						if(i == 0)
							overall_stats.put("C", 1 + overall_stats.get("C"));
						else if(i == 1)
							exam_stats.put("C", 1 + exam_stats.get("C"));
						else
							coursework_stats.put("C", 1 + coursework_stats.get("C"));
					}
					else if(marks != -1)
					{
						if(i == 0)
							overall_stats.put("F", 1 + overall_stats.get("F"));
						else if(i == 1)
							exam_stats.put("F", 1 + exam_stats.get("F"));
						else
							coursework_stats.put("F", 1 + coursework_stats.get("F"));
					}
					
					//increment i. Modulate to keep it in size of statsTypeList
					i = (i + 1) % sizeOfStatsTypeList;
					
					//all the scores in the result object has been obtained hence can break and get data of the next result object
					if(i == 0)
						break;
				}
			}
		}
		
		//convert data of each component's grades into percentage
		for(int j=0; j<9; j++)
		{
			//number of students in that grade divide by the number of students in that course
			overall_stats.put(gradeList[j], 100*overall_stats.get(gradeList[j])/this.getFaculty(facultyName).getCourseList().size());
			exam_stats.put(gradeList[j], 100*exam_stats.get(gradeList[j])/this.getFaculty(facultyName).getCourseList().size());
			coursework_stats.put(gradeList[j], 100*coursework_stats.get(gradeList[j])/this.getFaculty(facultyName).getCourseList().size());
		}
		
		return totalStats;
	}
	
	
	//All methods that uses HumanController
	public boolean isStudentInList(String matricNumber)
	{
		return this.humanCtrl.isStudentInList(matricNumber);
	}
	
	public boolean isCourseRegistered(String matricNumber, String courseFacultyName, String courseCode)
	{
		return this.humanCtrl.isCourseRegistered(matricNumber, courseFacultyName, courseCode);
	}
	public int getTotalWeightage(String facultyName, String courseCode) {
		int total = 0;
		
		for(Faculty f : facultyList) {
			if(f.getFacultyName().equals(facultyName)) {
				total = f.getCourse(courseCode).getTotalSubWeightage();				
			}
		}
		return total ;
	}
}

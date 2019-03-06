package SCRAME.Entity;

import java.io.Serializable;
import java.util.*; 


public class Course implements Serializable {
	protected String courseCode;
	protected String courseName;
	protected String facultyName;
	protected int courseVacancy;
	protected int originalCourseVacancy;
	protected int courseWeightage = 0;
	protected int examWeightage = 100; //by default exam only
	protected ArrayList<Lesson> tutorialList = new ArrayList<Lesson>();
	protected ArrayList<Lesson> labList = new ArrayList<Lesson>();
	protected ArrayList<Lectures> lectureList = new ArrayList<Lectures>();
	protected ArrayList<Student> studentList = new ArrayList<Student>();
	protected Professors coordinator;
	protected CourseWork cw;
	
	
	public Course(String facultyName) {
		this.facultyName  = facultyName;
		this.courseCode = "0";
		this.courseName = "default";
	}
	
	
	//course have no CourseWork, exam only, 1 lecture only
	public Course(String courseCode, String courseName, String facultyName, int courseVacancy, Professors coordinator) {
		Lectures lect = new Lectures(courseName, "Lecture 1"); //by default only 1 lecture
		addLecturetoList(lect);
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.facultyName = facultyName;
		this.courseVacancy = this.originalCourseVacancy = courseVacancy;
		this.coordinator = coordinator;
		this.courseWeightage = 0;
		this.examWeightage = 100;
	}
	
	
	public void createCourseWork(int courseWeightage) {
		cw = new CourseWork(courseWeightage);
		this.examWeightage = 100 - courseWeightage;
		this.courseWeightage = courseWeightage;
	}
	
	public void createTutorial(int groupNumber, int vacancy, boolean lab) {
		//create tutorial
		tutorialList.add(new Lesson(groupNumber, vacancy, false));
		
		//create lab is there is lab
		if(lab)
			labList.add(new Lesson(groupNumber, vacancy, true));
	}
	
	public ArrayList<Lesson> getTutorialList()
	{
		return tutorialList;
	}
	
	public ArrayList<Lesson> getLabList()
	{
		return labList;
	}
	
	public Lesson getTutorial(int groupNumber)
	{
		for(Lesson l : this.tutorialList)
		{
			if(l.getGroupNumber() == groupNumber)
			{
				return l;
			}
		}
		
		return null;
	}
	
	public Lesson getLab(int groupNumber)
	{
		for(Lesson l : this.labList)
		{
			if(l.getGroupNumber() == groupNumber)
			{
				return l;
			}
		}
		
		return null;
	}
	
	public void addLecturetoList(Lectures lec) {
		lectureList.add(lec);
	}

	public void addSubComponent(String componentName, int subComponentWeightage) {
		cw.createSubComponent(componentName, subComponentWeightage);
	}
	
	public ArrayList<Integer> getSubComponentWeight()
	{
		ArrayList<SubComponent> subComList = cw.getSubCompList();
		ArrayList<Integer> subComWeightage = new ArrayList<Integer>();
		
		//check incase there is no subComp
		if(subComList == null)
			return null;
		
		for(SubComponent subComInfo : subComList)
		{	
			subComWeightage.add(subComInfo.getWeightage());
			System.out.println(subComInfo.getWeightage()); //DEBUG
		}
		System.out.println(subComWeightage);
		
		return subComWeightage;
	}
	
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public int getCourseVacancy() {
		return courseVacancy;
	}
	
	public int getOriginalCourseVacancy()
	{
		return this.originalCourseVacancy;
	}

	public void setCourseVacancy(int courseVacancy) {
		this.courseVacancy = this.originalCourseVacancy = courseVacancy;
	}

	public int getCourseWeightage() {
		return courseWeightage;
	}

	public void setCourseWeightage(int courseWeightage) {
		this.courseWeightage = courseWeightage;
	}

	public int getExamWeightage() {
		return examWeightage;
	}

	public void setExamWeightage(int examWeightage) {
		this.examWeightage = examWeightage;
	}

	public ArrayList<Lectures> getLectureList() {
		return lectureList;
	}

	
	public ArrayList<Student> getStudentList() {
		return studentList;
	}
	
	public Student getStudent(String matricNumber)
	{
		for(Student s : this.studentList)
		{
			if((s.getMatricNumber()).equals(matricNumber))
				return s;
		}
		
		return null;
	}
	
	public boolean addStudent(Student stud)
	{
		//check if there is any vacancy before adding
		if(this.courseVacancy > 0)
		{
			this.studentList.add(stud);
			this.courseVacancy -= 1;
			
			return true;
		}
		
		return false;
	}
	
	public boolean addStudent(int groupNumber, boolean haveLab, Student stud)
	{
		//to return if there is space to add
		boolean successfullyAdded = false;
		
		//use own class method as that will help to check if there are vacancy in the course
		addStudent(stud);
		
		for(Lesson l : this.tutorialList)
		{
			if(l.getGroupNumber() == groupNumber)
			{
				successfullyAdded = l.addStudent(stud);
				break;
			}
		}
		
		if(haveLab)
		{
			for(Lesson l : this.labList)
			{
				if(l.getGroupNumber() == groupNumber)
				{
					successfullyAdded = l.addStudent(stud);
					break;
				}
			}
		}
		
		return successfullyAdded;
	}

	public Professors getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(Professors coordinator) {
		this.coordinator = coordinator;
	}


	public int getTotalSubWeightage() {
		return this.cw.getTotalSubWeightage();
	}

}

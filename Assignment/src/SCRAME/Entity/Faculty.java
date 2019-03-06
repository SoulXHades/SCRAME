package SCRAME.Entity;

import java.util.*;
import java.io.Serializable;

public class Faculty implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String facultyName;
	protected ArrayList<Course> courseList; 
	protected ArrayList<Student> studentList; 
	protected ArrayList<Professors> professorList; 
	
	
	public boolean equals(Object o) {
		if (o instanceof Faculty) {
			Faculty f = (Faculty)o;
			return (getFacultyName().equals(f.getFacultyName()));
		}
		return false;
	}
	
	//constructor
	public Faculty(String facultyName){
		courseList = new ArrayList<Course>();
		this.facultyName = facultyName;
		//get list of course code here, iterate through the arrayList, create the course objects and add to a
	}
	
	//get list and attribute methods
	public ArrayList<Student> getStudentList() {
		return studentList;
	}
	public ArrayList<Professors> getProfessorList() {
		return professorList;
	}
	public ArrayList<Course> getCourseList() {
		return courseList;
	}
	public String getFacultyName() {
		return facultyName;
	}
	//set list and attribute methods
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}

	public void setProfessorList(ArrayList<Professors> professorList) {
		this.professorList = professorList;
	}

	//get course using courseCode
	public Course getCourse(String courseCode) {
		for(Course c : courseList) {
			if((c.getCourseCode()).equals(courseCode)) {
				return c;
			}
		}
		return null; //throw exception
	}
	

	
	//create new course with only 1 lecture no coursework
	//added to course list once course is created
	public void createCourse(String courseCode, String courseName, String facultyName, int courseVacancy, Professors coordinator) {
		courseList.add(new Course(courseCode,courseName,facultyName,courseVacancy, coordinator));
	}
	//create new course with only 1 lecture with tutorials only,  no course work
	public void createCourseWithClass(String courseCode, String courseName, String facultyName,
			int courseVacancy, Professors coordinator, ArrayList<Integer> tutGroups, boolean lab, int tutVacancy) {
		
		if(lab == false) {
			//create course with tutorials only
			courseList.add(new Course(courseCode,courseName,facultyName,courseVacancy, coordinator));
			for(Course C : courseList) {
				if ((C.getCourseCode()).equals(courseCode)) {
					for(Integer t: tutGroups) {
						C.createTutorial(t, tutVacancy, false);
					}
				}
			}
		}
		else {
			courseList.add(new Course(courseCode,courseName,facultyName,courseVacancy, coordinator));
			for(Course C : courseList) {
				if ((C.getCourseCode()).equals(courseCode)) {
					for(Integer t: tutGroups) {
						C.createTutorial(t, tutVacancy, true);
					}
				}
			}
		}
	}
	
	
	
	public void addStudentList(ArrayList<Student> studentList, Student s) {
		studentList.add(s);
	}
	public void addProfessorList(ArrayList<Professors> professorList, Professors p) {
		professorList.add(p);
	}
	
	public Professors getCoordinator(ArrayList<Professors> pList, Course c) {
		for(Professors p: pList) {
			if((p.getCoordinatingCourse()).equals( c.getCourseName())) {
				return p;
			}
		}
		return null;
	}
	
	//get course name and course codes 

	public ArrayList<String> getCourseNames() {
		ArrayList<String> courseNameList = new ArrayList<String>();
		for(Course c: courseList) {
			courseNameList.add(c.getCourseName());
		}
		return courseNameList;
	}

	public ArrayList<String> getCourseCodes() {
		ArrayList<String> courseCodeList = new ArrayList<String>();
		for(Course c: courseList) {
			courseCodeList.add(c.getCourseCode());
		}
		return courseCodeList;
	}
	
	
	//passing of weightages
		
	public void passCourseWork(int examWeightage, int courseWeightage, String courseCode) {
		for(Course c: courseList) {
			c.createCourseWork(courseWeightage);
		}
	}
	
	public void passSubComponent( String subCompName, int subCompWeight, String courseCode) {
		for(Course c: courseList) {
			c.addSubComponent(subCompName, subCompWeight);
		}
	}


}

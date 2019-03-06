package SCRAME.Entity;

import java.io.Serializable;

//professor entity class

public class Professors implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String staffName;
	private String staffNumber;
	private String faculty;
	private String courseCode;
	private String courseName;
	private boolean isCourseCoordinator;

	
	//constructor for course coordinator
	public Professors(String staffName, String staffNumber, String faculty, boolean isCourseCoordinator, String courseName, String courseCode) {
		this.staffName = staffName;
		this.staffNumber = staffNumber;
		this.faculty = faculty;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.isCourseCoordinator = isCourseCoordinator;
		
	}
	

	//constructor for NOT! course coordinator
	public Professors(String staffName, String staffNumber, String faculty) {
		this.staffName = staffName;
		this.staffNumber = staffNumber;
		this.faculty = faculty;
		this.isCourseCoordinator = false;
		
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


	//getter and setter
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public boolean isCourseCoordinator() {
		return isCourseCoordinator;
	}

	public void setCourseCoordinator(boolean isCourseCoordinator) {
		this.isCourseCoordinator = isCourseCoordinator;
	}

	public String getCoordinatingCourse() {
		if(isCourseCoordinator()) {
			return (getCourseCode());
		}
		return null;
	}

/*	is this needed here? or get it from Faculty and Course class respectively?
 * 
 * public ArrayList<Faculty> getFacultyList() {
		return facultyList;
	}

	public void setFacultyList(ArrayList<Faculty> facultyList) {
		this.facultyList = facultyList;
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}*/
	
	
	
	
	

}

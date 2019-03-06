package SCRAME.Controller;

import SCRAME.Entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



// student logic class

public class HumanController {
	
	private ArrayList<Student> studList;
	private ArrayList<Faculty> facList;
	//private ArrayList<Results> resultList;
	Student tempStud = null;

	public boolean passStudDetails(String studName, String matricNumber, String facultyName, int year) {
		return this.addStudent(studName, matricNumber, facultyName, year);
	}
	public boolean addStudent(String studName, String matricNumber, String facultyName, int year) {
		//return false as fail to add student cause student exist in DB already
		if(isStudentInList(matricNumber)) {
			return false;
		}
		
		studList.add(new Student(studName, matricNumber, facultyName, year));
		
		return true;
	}
	
	public void setStudFacList(ArrayList<Student> studList, ArrayList<Faculty> facList) {
		this.facList = facList;
		this.studList = studList;
	}
	
	/*public void setResultList(ArrayList<Results> resultList) {
		this.resultList = resultList;
	}*/

	//check if student is registered
	public boolean isStudentInList(String matric) {
		for (Student s : studList) {
			if (s.getMatricNumber().equals(matric)) {
				return true;
			}
		}
		return false;
	}

	//return student with matric number
	public Student findStudent(String matric) {
		Student studentFound = null;
		for (Student s : studList) {
			if (s.getMatricNumber().equals(matric)) {
				studentFound = s;
			}
		}
		return studentFound;
	}
	
	//get student basic information such as name and matriculation number
	public ArrayList<Map<String, String>> getStudInfoList()
	{
		ArrayList<Map<String, String>> tempStudInfoList = new ArrayList<Map<String, String>>();
		Map<String, String> studInfo;
		
		//get student info from arraylist of students
		for(Student s : this.studList)
		{
			//instantiate every FOR loop iteration to create new HashMap to store new content
			studInfo = new HashMap<String, String>();
			
			studInfo.put("studName", s.getStudName());
			studInfo.put("matricNumber", s.getMatricNumber());
			tempStudInfoList.add(studInfo);
		}
		
		return tempStudInfoList;
	}

	/*@Override
	public void addToFaculty(String facName) {
		// TODO Auto-generated method stub
		for (Faculty f : facList) {
			if(f.equals(facName)) {
				f.setFacultyName(facName);
				break; 
			}
			
		}
		System.out.println("The faculty does not exist!");
	}*/
	
	public boolean addCourse(String matricNumber, String courseFacultyName, String courseCode) {
		Course newCourse = null;
		
		//check if course is already registered by student
		//tempStud is global variable (local in this class) which will be assigned in isCourseRegistered()
		if(isCourseRegistered(matricNumber, courseFacultyName, courseCode))
			return false;
		
		//search for the particular course in the faculty based on faculty name and coursecode given by the boundary
		for(Faculty f : this.facList) {
			//check if student have already registered for the course
			if((f.getFacultyName()).equals(courseFacultyName)) {
				for(Course c : f.getCourseList())
				{
					if((c.getCourseCode()).equals(courseCode))
						newCourse = c;
				}
			}
		}
		
		tempStud.addCourse(newCourse);
		
		return true;
	}
	
	public ArrayList<String> courseRegisteredForStudent(String matricNumber) {
			ArrayList<Results> courseRegistered ;
			ArrayList<String> courseCodeList = new ArrayList<String>();
			//find student
			//get student object based on student matricNumber
			for(Student s : studList)
			{
				if(s.getMatricNumber().equals(matricNumber))
				{
					//assign tempStud for addCourse() to use
					tempStud = s;
					break;
				}
			}
					
			//get all course registered by the student
			courseRegistered = tempStud.getResultList();
			
			//it means student have not registered for any courses hence result list is null
			//and hence this course the student is applying for have not been registered
			if(courseRegistered == null)
				return null;
					
			//check if student have already registered for the course
			for(Results r : courseRegistered) {
				courseCodeList.add(r.getCourse().getCourseCode());
			}
			
			return courseCodeList;
	}
	
	public boolean isCourseRegistered(String matricNumber, String courseFacultyName, String courseCode)
	{
		ArrayList<Results> courseRegistered;
		
		//get student object based on student matricNumber
		for(Student s : studList)
		{
			if(s.getMatricNumber().equals(matricNumber))
			{
				//assign tempStud for addCourse() to use
				tempStud = s;
				break;
			}
		}
				
		//get all course registered by the student
		courseRegistered = tempStud.getResultList();
		
		//it means student have not registered for any courses hence result list is null
		//and hence this course the student is applying for have not been registered
		if(courseRegistered == null)
			return false;
				
		//check if student have already registered for the course
		for(Results r : courseRegistered) {
			//return function if student already registered for the course
			if(r.getCourse().getCourseCode().equals(courseCode)) {
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<Map<String, String>> showStudResults(String matricNumber) {
		//get student object using matricNumber
		Student stud = findStudent(matricNumber);
		
		//resultList is all the results the student have
		ArrayList<Results> resultList = stud.getResultList();
		ArrayList<Map<String, String>> overallResults = new ArrayList<Map<String, String>>();
		Map<String, String> components = new HashMap<String, String>();
		
		String[] TypeList = {"Course Code", "Overall", "Exam", "Coursework"};
		
		for(Results r: resultList) {
			components.put(TypeList[0], r.getCourse().getCourseCode());
			components.put(TypeList[1], Double.toString(((r.getCourseworkScore()/100) * (100 - r.getCourse().getExamWeightage())) + ((r.getExamScore()/100) * r.getCourse().getExamWeightage())));
			components.put(TypeList[2], Double.toString(r.getExamScore()));
			components.put(TypeList[3], Double.toString(r.getCourseworkScore()));
			
			overallResults.add(components);
		}
		return overallResults;
		
		
		/* exception
			if (c.getCourseName().equals(theCourse)){
				if(c.getCourseVacancy() == 0) {
					System.out.println("Sorry, course is already full!");
				}
				else {
					//add course
					CoursesTaken.put(theCourse, -1); // initialize results as -1? or else idk how to do this :/
				}
			}
			else {
				System.out.println("Course code does not exist!");
			}
			*/
	}
	///combine
	public Map<String, Integer> getExamWeightage(String matricNumber) {
		//get student object based on the matricNumber
		Student s = findStudent(matricNumber);
		
		ArrayList<Results> resultList = s.getResultList();
		Map<String, Integer> examWeightageList = new HashMap<String, Integer>();
		for(Results r: resultList) {
			//key is courseCode, value is exam weightage
			examWeightageList.put(r.getCourse().getCourseCode(), r.getExamWeight());
		}
		
		return examWeightageList;
	}
	
	public Map<String, ArrayList<String[]>> showSubWeightage(String matricNumber) {
		//get student object based on the matricNumber
		Student s = findStudent(matricNumber);
		
		ArrayList<Results> resultList = s.getResultList();
		String[] sub;
		ArrayList<String[]> subList = new ArrayList<String[]>();
		Map<String, ArrayList<String[]>> allWeight = new HashMap<String, ArrayList<String[]>>();
		
		//for every result for each course there is 1 arrayList of sub Component. 
		for(Results r: resultList) {
			String courseCode = r.getCourseCode();
			
			//get each component info
			for(SubComponent sc : r.getSubComponent())
			{
				//create new String[] before writing to it
				sub = new String[2];
				sub[0] = sc.getCompName();
				sub[1] = Integer.toString(sc.getWeightage());
				
				subList.add(sub);
			}
			allWeight.put(courseCode, subList);
		}
		
		return allWeight;
	}

	public void passMarksCourse(String matricNumber, String courseCode, double examScore, double courseworkScore) {
		 /*Iterator<Student> student= studList.iterator(); 
	        while (student.hasNext()) 
	            if(((Student) student).getMatricNumber() == martricNumber) {
	            	((Student) student).addMarks(marks, courseCode);
	            }*/
		for (Student s: studList) {
			if((s.getMatricNumber()).equals(matricNumber)) {
				for (Results r: s.getResultList()) {
					if((r.getCourse().getCourseCode()).equals(courseCode)) {
						s.addExamScore(courseCode, examScore);
						s.addCourseworkScore(courseCode, courseworkScore);
					}
				}
			}
		}
	}
	public void passMarksCourseWithSubComponent(String matricNumber, String courseCode, double examScore,
			double courseworkScore, ArrayList<Double> subComponentMarks) {
		for (Student s: studList) {
			if((s.getMatricNumber()).equals(matricNumber)) {
				for (Results r: s.getResultList()) {
					if((r.getCourse().getCourseCode()).equals(courseCode)) {
						s.addExamScore(courseCode, examScore);
						s.addCourseworkScore(courseCode, courseworkScore);
						for(Double score: subComponentMarks) {
							s.addSubComponentScore(courseCode, score);
						}
					}
				}
			}
		}
	}
	
	//if course weightage == 0, means no course work
	//returns true if course weightages has been set
	public boolean checkGotCourseWork(String courseCode, String matric) {
		for(Student s: studList) {
			if((s.getMatricNumber()).equals(matric)) {
				for (Results r: s.getResultList()) {
					if((r.getCourse().getCourseCode()).equals(courseCode)) {
						if(r.getCourse().getCourseWeightage() > 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public int checkNumberOfSubCompnent(String courseCode, String matric) {
		int number = 0;
		for(Student s: studList) {
			if((s.getMatricNumber()).equals(matric)) {
				for (Results r: s.getResultList()) {
					if((r.getCourse().getCourseCode()).equals(courseCode)) {
						number = r.getNumberOfSubComponent();
					}
				}
			}
		}
		
		return number;
	}
	
	
	


}

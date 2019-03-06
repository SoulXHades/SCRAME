package SCRAME.Entity;

import java.io.Serializable;
import java.util.*;

public class Lesson {
	private int groupNumber;
	private int vacancy;
	private int originalVacancy;
	private ArrayList<Student> studList;
	private boolean isLab;
	
	public Lesson(int groupNumber, int vacancy, boolean isLab)
	{
		this.groupNumber = groupNumber;
		this.vacancy = vacancy;
		this.originalVacancy = vacancy;
		this.isLab = isLab;
		
		studList = new ArrayList<Student>();
	}
	
	public int getGroupNumber()
	{
		return this.groupNumber;
	}
	
	public void setGroupNumber(int groupNumber)
	{
		this.groupNumber = groupNumber;
	}
	
	public String getVacancy()
	{
		return this.vacancy + "/" + this.originalVacancy + " [vacancy/total size]";
	}

	public boolean haveVacancy()
	{
		if((this.originalVacancy - this.vacancy) > 0)
			return true;
		else
			return false;
	}
	
	public boolean addStudent(Student stud)
	{
		//check if there is any vacancy before adding
		if(this.vacancy > 0)
		{
			this.studList.add(stud);
			//reduce vacancy when more students join the group
			this.vacancy -= 1;
			return true;
		}
		else
			return false;
	}
	
	public void removeStudent(Student stud)
	{
		this.studList.remove(stud);
		//increase vacancy
		this.vacancy += 1;
	}
	
	public ArrayList<Student> getStudentList()
	{
		return this.studList;
	}
	
	public Student getStudent(String matricNumber)
	{
		for(Student s : this.studList)
		{
			if(s.getMatricNumber() == matricNumber)
				return s;
		}
		
		return null;
	}

}

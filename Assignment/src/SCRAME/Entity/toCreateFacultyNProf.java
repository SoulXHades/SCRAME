package SCRAME.Entity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class toCreateFacultyNProf {

	public static void main(String[] args) throws IOException {
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
		facultyList.add(new Faculty("EEE"));
		facultyList.add(new Faculty("HSS"));
		facultyList.add(new Faculty("MAE"));
		facultyList.add(new Faculty("NBS"));
		facultyList.add(new Faculty("SCSE"));
		
		ArrayList<Professors> profList = new ArrayList<Professors>();
		profList.add(new Professors("James","S101", "SCSE", false, null, null));
		profList.add(new Professors("Lily", "S102", "EEE", false, null, null));
		profList.add(new Professors("Potter", "S103", "SCSE", false, null, null));
		profList.add(new Professors("Harry", "S104", "MAE", false, null, null));
		profList.add(new Professors("Snape", "S105", "SCSE", false, null, null));
		profList.add(new Professors("Ron", "S106", "SCSE", false, null, null));
		profList.add(new Professors("Ray", "S107", "SCSE", false, null, null));
		profList.add(new Professors("May", "S108", "HSS", false, null, null));

		
		ArrayList<Student> studentList = new ArrayList<Student>();
		
		
		
		// Serialization  
        //try
        //{    
            //Saving of object in a file 
        	FileOutputStream facultyFile = new FileOutputStream("FacultyData.dat");
            ObjectOutputStream facultyOut = new ObjectOutputStream(facultyFile);
            FileOutputStream profFile = new FileOutputStream("ProfessorsData.dat");
            ObjectOutputStream profOut = new ObjectOutputStream(profFile);
            FileOutputStream studFile = new FileOutputStream("StudentData.dat");
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
  
        /*}
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } */

	}

}

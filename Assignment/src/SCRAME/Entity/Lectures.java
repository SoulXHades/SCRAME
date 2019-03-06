package SCRAME.Entity;

import java.io.Serializable;
import java.util.*;

public class Lectures implements Serializable {
	private String courseName;
	private String groupName;
	
	public Lectures(String courseName, String groupName){
		this.courseName = courseName;
		this.groupName = groupName;
	}
}

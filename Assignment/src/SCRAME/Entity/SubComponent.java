package SCRAME.Entity;

import java.io.Serializable;

public class SubComponent implements Serializable {
	private String compName;
	private int weightage;
	
	public SubComponent(String compName, int weightage) {
		this.compName = compName;
		this.weightage = weightage;
	}
	
	public String getCompName() {
		return this.compName;
	}
	
	public int getWeightage() {
		return this.weightage;
	}
	
	
	public void setCompName(String compName) {
		this.compName = compName;
	}
	

	public void setWeightage(int weightage) {
		this.weightage = weightage;
	}



	
}

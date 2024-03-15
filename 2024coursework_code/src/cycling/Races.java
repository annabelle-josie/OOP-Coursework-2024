package cycling;

import java.util.ArrayList;
import java.util.Arrays;

public class Races {
	// 4 attributes
	private int raceID ;
	private static int nextID;
	private String name;
	private String description;
	//private stages[] stages;
	int[] stages ;
	private Double length;
	private static ArrayList<Integer> currentRaces = new ArrayList<Integer>();


	/**
	 * Creates new race with unique ID
	 * @param name
	 * @param description
	 */
	public Races(String name, String description) {
		// throw new throws IllegalNameException, InvalidNameException
		this.raceID = nextID;
        nextID++;
        currentRaces.add(raceID);
        this.name = name;
		this.description = description;
		this.stages = null ;
		this.length = 0.0d;
	}


// add stages
// using race ID 
    public void setStages(int stageId) {
		if (stages == null){
			System.out.println("hello there sir");
			this.stages = new int[]{stageId};
		} else {
			System.out.println("hello there");
			int length = stages.length;
			int[] newArray = new int[length + 1];
			int i;
			for (i = 0; i < length; i++)
				newArray[i] = stages[i];
			newArray[length] = stageId;
			this.stages = newArray;
		}
    }
	public void replaceStages(int[] stagelist){
		this.stages = stagelist;
	}
	public void setLength(double length){
			this.length += length;
	}

    
// public getters and setters for private instance
	public int getRaceID() {
		return raceID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() { return description; }

	public int[] getStages() {
        	return stages;
    	}

	public Double getLength() {return length;}
	public String raceasstring(){
		String s;
		if(stages == null){
			s = ("races of ID " + raceID + " " + " "+ name + " " + description + " " +length);
		} else {
			s = ("races of ID " + raceID + " " + " "+ name + " " + description +" "+ Arrays.toString(stages) + " " +length);
		}
        return s;
	}
}

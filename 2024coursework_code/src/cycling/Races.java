package cycling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Races {
	// 4 attributes
	private int raceID ;
	private static int nextID;
	private String name;
	private String description;
	//private stages[] stages;
	private ArrayList<Integer> stages = new ArrayList<Integer>();
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
		this.length = 0.0d;
	}


// add stages
// using race ID 
    public void setStages(int stageId) {
			this.stages.add(stageId);
    }

	public void removeStage(int stageId) throws IDNotRecognisedException {
		Boolean worked = stages.remove((Integer)stageId);
		if (!worked){
			throw new IDNotRecognisedException();
		}
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
		int[] stagelist = new int[]{};
		if (stages != null){
			//int[] stagelist = stages.toArray(new int[stages.size()]);
			stagelist = new int[stages.size()];
			int count=0;
        	for (int stage : stages) {
            	stagelist[count] = stage;
            	count++;
        	}
		}
		return stagelist;
	} 
		

	public Double getLength() {return length;}
	public String raceasstring(){
		String s;
		if(stages == null){
			s = ("races of ID " + raceID + " " + " "+ name + " " + description + " " +length);
		} else {
			s = ("races of ID " + raceID + " " + " "+ name + " " + description +" "+ stages.toString() + " " +length);
		}
        return s;
	}
}

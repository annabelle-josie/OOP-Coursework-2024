package cycling;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.Serializable;
import java.time.LocalTime;

public class Races implements Serializable {
	// 4 attributes
	private int raceID ;
	private String name;
	private String description;
	private ArrayList<Integer> stages = new ArrayList<Integer>();
	private Double length;
	


	/**
	 * Creates new race
	 * @param ID			ID of the race
	 * @param name			Name of the race
	 * @param description	Description of the race
	 */
	public Races(int ID, String name, String description) {
		// throw new throws IllegalNameException, InvalidNameException
		this.raceID = ID;
        this.name = name;
		this.description = description;
		this.length = 0.0d;
	}


	/**
	 * Adds a stage to the race
	 * @param stageId	ID of the stage to be added
	 */
    public void setStages(int stageId) {
			this.stages.add(stageId);
    }

	/**
	 * Removes a stage from the race
	 * @param stageId	ID of the stage to be removed
	 * @throws IDNotRecognisedException	If there is no stage matching that ID in the race
	 */
	public void removeStage(int stageId) throws IDNotRecognisedException {
		Boolean worked = stages.remove((Integer)stageId);
		if (!worked){
			throw new IDNotRecognisedException();
		}
	}

	/**
	 * Updates total length of the race, used when stages are added
	 * TODO: Add a way of reducing length
	 * @param length	Length to be added to the total length
	 */
	public void setLength(double length){
			this.length += length;
	}

    
	/**
	 * @return ID of the current race
	 */
	public int getRaceID() {
		return raceID;
	}
	
	/**
	 * @return The name of the race
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The description of the race
	 */
	public String getDescription() { return description; }

	/**
	 * Gets an array of the stages in the race
	 * @return Array of stage IDs of the stages in the race
	 */
	public int[] getStages() {
		int[] stagelist = new int[]{};
		if (stages != null){
			stagelist = new int[stages.size()];
			int count=0;
        	for (int stage : stages) {
            	stagelist[count] = stage;
            	count++;
        	}
		}
		return stagelist;
	}

	/**
	 * @return The number of stages in the race
	 */
	public int getNumberOfStages(){
		return stages.size();
	}
	

	/**
	 * @return the length of the race
	 */
	public Double getLength() {
		return length;
	}

	/**
	 * @return A string of the important details of the race for use in debugging
	 */
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

	


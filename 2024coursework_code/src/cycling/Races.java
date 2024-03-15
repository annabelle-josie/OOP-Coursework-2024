package cycling;
import java.util.ArrayList;
// need to add total length
public class Races {
	// 4 attributes
	private int raceID ;
	private String name;
	private String description;
	private Double length; // have to get it from the stage and add them all up
	//private stages[] stages ; add add stages from stage class ??
	ArrayList<Integer> raceIDList = new ArrayList<Integer>();
	// constructor
	public Races(String name, String description) {
		this.raceID = createRaceID();
        this.name = name;
        this.description = description;
		this.stages = null ;
		this.length = 0.0d;
	}
	
		
	public int createRaceID() {
		// if race list is empty race ID = 0001
		if (raceIDList.isEmpty()) {
			raceID = 1;
		} else {
			raceID = (raceIDList.get(raceIDList.size()-1) +1); // gets the last used arraya nd adds 1 to it
		}
		// add an array to list
		raceIDList.add(raceID);
		return raceID;
	}
	

	public ArrayList getRaceIDs() {
        	return raceIDList;
	}	


	public String viewRaceDetails(int raceID) {
		// get race details from using race ID and finding it
		// return 
		//string race;
		//return string race = 
		//return String("race ID = " + raceID + ", name =" + race.getName() + ", description =" + race.getDescription() + ", stages={" + race.getStages() + "}" ) ;
		return "hello";

	}

	public int removeRaceByID(int raceID) {
		//remove race by id from a list
		return raceID;
	}

// using get race IDs list
// cycle list check if it exists 

    
// public getters and setters for private instance
	public int getRaceID() {
		return raceID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
    	}

    	public stages[] getStages() {
        	return stages;
    	}
	public Double getlength() {
        	return length;
    	}


	//public void setlength() { // will get the length from each stage and add them up hopefully
	//	Double length = 0.0d;
	//	for(stages stageid : stages);
	//		length += stages.getlength(); // how to get the length from the stages 
	//	this.length = length;
	//}

	public void addStages(stages stages[]) {
		//stages.add(stages); 
		// what format do we need to as them in 
		// want to be able to do as a list and single add trough stage ID??
        	this.stages = stages;
    	}

}



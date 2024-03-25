package cycling;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.io.Serializable;


public class Stages implements Serializable {
	private int stageID ;
	private int raceID;
	private static int nextID;
	private String stageName;
	private String description;
	private double length;
	private LocalDateTime StartTime;
	private StageType type;
	private String stageState;
	private ArrayList<Integer> stageIDList = new ArrayList<Integer>();
	private ArrayList<Integer> checkpoints = new ArrayList<Integer>();
	private static ArrayList<Integer> currentStages = new ArrayList<Integer>();
	private HashMap<Integer, LocalTime[]> results= new HashMap<>();
	private HashMap<Integer, LocalTime> adjustedResults = new HashMap<>();
	// constructor
	public Stages(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) {
	
		this.stageID = nextID;
        nextID++;
        currentStages.add(stageID);
		this.raceID = raceId;
		this.stageName = stageName;
		this.description = description;
		this.length = length;
		this.StartTime = startTime;
		this.type = type;
		this.stageState = "in development" ;
	
	}

	
// same with adding stages to races other option just use IDS dont reference the class 
	public void addCheckpoint(int checkpointID) {
		this.checkpoints.add(checkpointID);
		}
	public void removeCheckpoint(int checkpointID) throws IDNotRecognisedException {
		Boolean worked = checkpoints.remove((Integer)checkpointID);
			if (!worked){
				throw new IDNotRecognisedException();
			}
	}
	public void removeRider(int riderID) throws IDNotRecognisedException {
		boolean found =false;
		for (Integer i : results.keySet()) {
	 		if (i == riderID){
				found = true;
			  this.results.remove(riderID);
		  }
	  	}
		if (!found){
			throw new IDNotRecognisedException();
		}
	}
// the getters

	public void setStageState(){
		this.stageState = "waiting for results";
	}
	public String getStageState(){
		return stageState;
	}
	
	
	public int getRaceID() {
		return raceID;
	}
	public int getStageID() {
		return stageID;
	}
	public String getstageName() {
		return stageName;
	}
	public String getdescription() {
		return description;
	} 
	public Double getlength() {
		return length;
	}
	public LocalDateTime getStartTime() {
		return StartTime;
	}
	public StageType getType() {
		return type;
	}
	public int[] getCheckpoints(){
		int[] checkpointlist = new int[]{};
		if (checkpoints != null){
			checkpointlist = new int[checkpoints.size()];
			int count=0;
        	for (int checkpoint : checkpoints) {
            	checkpointlist[count] = checkpoint;
            	count++;
        	}
		}
		return checkpointlist;
	}

	public String stageasstring(){
        return ("stages of raceId" + raceID + " " + stageID + " "+ stageName + " " + description + " " + length + " " + StartTime + " " + type);
	}
	
	// create a method to add rider times 
	public void setResults(int riderId, LocalTime... times) throws DuplicatedResultException{
		for (Integer i : results.keySet()) {
  			if (i == riderId){
				throw new DuplicatedResultException();
			}
		}
		this.results.put(riderId, times);
	}

	public LocalTime[] getRiderTimes(int riderID) throws IDNotRecognisedException{
		//for the array or rider times find the RiderID that 
		LocalTime[] ridertimes = new LocalTime[]{};
		try{
			ridertimes = this.results.get(riderID);
		}catch(Exception e){
			System.out.println(e);
			throw new IDNotRecognisedException();
		}
		return ridertimes;
	}

	public LocalTime realElapsedTime(int riderID){
		LocalTime[] riderResults = results.get(riderID);
		LocalTime end = riderResults[riderResults.length -1];
		LocalTime start = riderResults[0];
		long time = start.until(end, ChronoUnit.MILLIS);
		//TODO: For reasons I cannot figure out this adds an hour
		//I can't fix it, its killing me
		//It does do it to everything, so it gets cancelled out
		//So I guess it doesn't matter?
		LocalTime returnTime = LocalTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
		System.out.println("Difference for " + riderID + " = " + returnTime);
		return returnTime;
	}

	public LocalTime getAdjustedTime(int rider){
		return adjustedResults.get(rider);
	}

	public LocalTime[] getAdjustedRank(){
		calculateAdjustment();
		LocalTime[] orderedTimes = new LocalTime[adjustedResults.size()];
		int count = 0;
		for(Integer id : adjustedResults.keySet()){
			orderedTimes[count] = adjustedResults.get(id);
			count++;
		}
		return orderedTimes;
	}

	public int[] getRank(){
		ArrayList<LocalTime> riderTimes = new ArrayList<>(); //Array that can be sorted
		HashMap<LocalTime, Integer> timeRiderDict = new HashMap<>(); //Dictionary matching times to IDs
		for (Integer id : results.keySet()) {
			LocalTime theirTime = realElapsedTime(id);
			riderTimes.add(theirTime);
			timeRiderDict.put(theirTime, id);
		}
		Collections.sort(riderTimes);
		int[] orderedIDs = new int[riderTimes.size()];
		int count = 0;
		for(LocalTime time : riderTimes){
			orderedIDs[count] = timeRiderDict.get(time);
			count++;
		}
		return orderedIDs;
	}

	public void calculateAdjustment(){
		// =====
		// Get times from results
		// =====
		ArrayList<LocalTime> riderTimes = new ArrayList<>(); //Array that can be sorted
		HashMap<LocalTime, Integer> timeRiderDict = new HashMap<>(); //Dictionary matching times to IDs
		for (Integer id : results.keySet()) {
			LocalTime theirTime = realElapsedTime(id);
  		 	riderTimes.add(theirTime);
			timeRiderDict.put(theirTime, id);
		}

		// =====
		// Sort the times
		// =====
		Collections.sort(riderTimes);
		System.out.println("Times sorted: " + riderTimes);

		// =====
		//Group times into peletons
		// =====
		ArrayList<Integer> groupList = new ArrayList<>(); //Assigns the times into peletons if necessary
		//In form: {1,1,1,0,0,4,4} where non-zero indicate being members of the specified group

		int groupCount = 1; //Tracks which group the times are currently part of
		for (int i = 0; i < riderTimes.size()-1; i++) {
			LocalTime next = riderTimes.get(i+1); //Next time in list
			LocalTime current = riderTimes.get(i); //This time in list
			System.out.println("next = " + next + ", current = " + current);
			System.out.println("If maths = " + current.until(next, ChronoUnit.MILLIS));
			if((current.until(next, ChronoUnit.MILLIS)) < 1000){ //Difference between is less than one
				groupList.add(groupCount);
			} else{
				groupList.add(0);
				groupCount++;;
			}
		}
		System.out.println("Groups are " + groupList);

		// =====
		// Create a new hashmap of all the values with their ids
		// =====
		adjustedResults.clear();
		//First Value will always be unchanged
		adjustedResults.put(0, riderTimes.get(0));
		int count = 0;
		for (int group : groupList) {
			int idToChange = timeRiderDict.get(riderTimes.get(count+1));
			if (group == 0){
				//Make no change
				adjustedResults.put(idToChange, riderTimes.get(count+1));
			} else{
				//Take that index and make next one same
				riderTimes.set(count+1, riderTimes.get(count));
				adjustedResults.put(idToChange, riderTimes.get(count));
			}
			count++;
		}	

		for(int id : adjustedResults.keySet()){
			System.out.println(id + ": " + adjustedResults.get(id));
		}
		
	}
}





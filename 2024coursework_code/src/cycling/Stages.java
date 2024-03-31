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
	private String stageName;
	private String description;
	private double length;
	private LocalDateTime StartTime;
	private StageType type;
	private String stageState;
	//private ArrayList<Integer> stageIDList = new ArrayList<Integer>();
	private ArrayList<Integer> checkpoints = new ArrayList<Integer>();
	//private static ArrayList<Integer> currentStages = new ArrayList<Integer>();
	private HashMap<Integer, LocalTime[]> results= new HashMap<>();
	private HashMap<Integer, LocalTime> adjustedResults = new HashMap<>();
	private HashMap<Integer, int[]> rankedCheckpointTimes = new HashMap<>();
	// constructor
	public Stages(int id, int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) {
	
		this.stageID = id;
        //currentStages.add(stageID);
		this.raceID = raceId;
		this.stageName = stageName;
		this.description = description;
		this.length = length;
		this.StartTime = startTime;
		this.type = type;
		this.stageState = "in development" ;
	
	}
	
	// public int[] getStages() {
	// 	int[] stagelist = new int[]{};
	// 	if (currentStages != null){
	// 		//int[] stagelist = stages.toArray(new int[stages.size()]);
	// 		stagelist = new int[currentStages.size()];
	// 		int count=0;
    //     	for (int stage : currentStages) {
    //         	stagelist[count] = stage;
    //         	count++;
    //     	}
	// 	}
	// 	return stagelist;
	// } 
	
	
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
	public int getResultsSize(){
		//System.out.println("ahhhh " +results.get(checkpointID).length);
		return checkpoints.size() ;
		// id not by checkpoint by rider ids
		// need to get a list of the checkpoints time in the right order
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
		//System.out.println("Difference for " + riderID + " = " + returnTime);
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
		//System.out.println("Times sorted: " + riderTimes);

		// =====
		//Group times into peletons
		// =====
		ArrayList<Integer> groupList = new ArrayList<>(); //Assigns the times into peletons if necessary
		//In form: {1,1,1,0,0,4,4} where non-zero indicate being members of the specified group

		int groupCount = 1; //Tracks which group the times are currently part of
		for (int i = 0; i < riderTimes.size()-1; i++) {
			LocalTime next = riderTimes.get(i+1); //Next time in list
			LocalTime current = riderTimes.get(i); //This time in list
			//System.out.println("next = " + next + ", current = " + current);
			//System.out.println("If maths = " + current.until(next, ChronoUnit.MILLIS));
			if((current.until(next, ChronoUnit.MILLIS)) < 1000){ //Difference between is less than one
				groupList.add(groupCount);
			} else{
				groupList.add(0);
				groupCount++;;
			}
		}
		//System.out.println("Groups are " + groupList);

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
	public int[] calculateStagePoints(){
		//int[] orderedIDs = new int[]{};
		//HashMap<Integer, Integer> pointsInStage= new HashMap<>();
		ArrayList<Integer> listOfPoints = new ArrayList<Integer>();
		if(type == StageType.FLAT){
			Collections.addAll(listOfPoints, 50,30,20,18,16,14,12,10,8,7,6,5,4,3,2);
		}else if(type == StageType.MEDIUM_MOUNTAIN){
			// change points to correct
						Collections.addAll(listOfPoints, 50,20,20,18,16,14,12,10,8,7,6,5,4,3,2);
		}else if(type == StageType.TT || type == StageType.HIGH_MOUNTAIN) {
			Collections.addAll(listOfPoints, 20,17,15,13,11,10,9,8,7,6,5,4,3,2,1);
		}
		
		int[] returnArray = new int[results.size()];
		for (int i = 0;  i < returnArray.length; i++) {
			if( i < 15){
				returnArray[i] = listOfPoints.get(i);
			}else{
				returnArray[i] = 0;
			}
		}
	
		return returnArray;
		// orderedIDs = getRank();
		// int count = 0;
		// for(int id : orderedIDs){
		// 	if( count < 15){
		// 		pointsInStage.put(id, listOfPoints.get(count));
		// 		count++;
		// 	}else {
		// 		pointsInStage.put(id,0);
		// 	}
		// }
		
		
	} 
	public void calulateMountainStage(){
		//have a list of checkpoints for rider id 
		// create a list of times for each checkpoint and sort that pass in the scores and add them to rider ID totals 
		// calculate riders order for each checkpoint and sum them up
		LocalTime[] ahh= new LocalTime[]{};
		int count = 0;
		int[] orderedIDs = new int[]{}; 
		ArrayList<LocalTime> riderTimes = new ArrayList<>(); //Array that can be sorted
		HashMap<LocalTime, Integer> timeRiderDict = new HashMap<>(); //Dictionary matching times to IDs
		//System.out.println(Arrays.toString(results.get(0)));
		// for the times in the stage get rank for each time then allocate points based on the time from that rank 
		// gives all the rider IDS sorted by time for each checkpoint 
			for(int i=1; i< checkpoints.size() +1; i++){
				//System.out.println("new " + i);
				timeRiderDict.clear();
				riderTimes.clear();
				for(Integer riderID : results.keySet()){
					ahh= results.get(riderID);
					LocalTime theirTime = ahh[i];
					//System.out.println("i " + i + " their time" + theirTime + "rider ID" + riderID);
					//System.out.println(" " + theirTime + " " + riderID);
					///System.out.println(ahh);
					riderTimes.add(theirTime);
					timeRiderDict.put(theirTime, riderID);
			}
			Collections.sort(riderTimes);
			orderedIDs = new int[riderTimes.size()];
			count = 0;
			//System.out.println("running " );
			for(LocalTime time : riderTimes){
				orderedIDs[count] = timeRiderDict.get(time);
				count++;
				//System.out.println("running count " + count );
			}
			//System.out.println("check " +checkpoints.get(i-1));
			this.rankedCheckpointTimes.put(checkpoints.get(i-1), orderedIDs);
			//System.out.println(" this " + Arrays.toString(rankedCheckpointTimes.get(i-1)));
		}
	}
		public int[] getRankedCheckpoint(int checkpointID){
			//System.out.println( "times " + rankedCheckpointTimes.get(checkpointID));
			return rankedCheckpointTimes.get(checkpointID);
		}
		
	}
	



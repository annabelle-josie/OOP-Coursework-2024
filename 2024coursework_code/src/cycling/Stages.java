package cycling;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.io.Serializable;


public class Stages implements Serializable {
	private int stageID ;
	private int raceID;
	private String stageName;
	private String description;
	private double length;
	private LocalDateTime startTime;
	private StageType type;
	private String stageState;
	private ArrayList<Integer> checkpoints = new ArrayList<>();
	private HashMap<Integer, LocalTime[]> results= new HashMap<>();
	private HashMap<Integer, LocalTime> adjustedResults = new HashMap<>();
	private HashMap<Integer, int[]> rankedCheckpointTimes = new HashMap<>();

	/**
	 * Creates a new stage
	 * @param id			The ID of the stage
	 * @param raceId		The RaceID of the race the stage belongs to
	 * @param stageName		The name of the stage
	 * @param description	A description of the stage
	 * @param length		The length of the stage
	 * @param startTime		The startTime of the stage
	 * @param type			The type of stage
	 */
	public Stages(int id, int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) {
		this.stageID = id;
		this.raceID = raceId;
		this.stageName = stageName;
		this.description = description;
		this.length = length;
		this.startTime = startTime;
		this.type = type;
		this.stageState = "in development" ;
	
	}

	/**
	 * Adds a checkpoint to the stage
	 * @param checkpointID	The ID of the checkpoint to be added
	 */
	public void addCheckpoint(int checkpointID) {
		this.checkpoints.add(checkpointID);
		}

	/**
	 * Removes a checkpoint from the stage
	 * @param checkpointID					ID of the checkpoint to be removed
	 * @throws IDNotRecognisedException		If the checkpoint is not found listed in the stage (so cannot be removed)
	 */
	public void removeCheckpoint(int checkpointID) throws IDNotRecognisedException {
		boolean worked = checkpoints.remove((Integer)checkpointID);
			if (!worked){
				throw new IDNotRecognisedException();
			}
	}
	/**
	 * Removes rider from the stage along with all their results
	 * @param riderID			ID of the rider to be removed
	 * @return 					True if there was data to remove, false if not
	 */
	public boolean removeRider(int riderID){
		boolean found = false;
		for (Integer i : results.keySet()) {
	 		if (i == riderID){
				found = true;
			  this.results.remove(riderID);
		  }
	  	}
		return found;
	}

	/**
	 * Changes the stage state to "waiting for results"
	 */
	public void setStageState(){
		this.stageState = "waiting for results";
	}
	/**
	 * Returns the stage state, this is either: "in development", "waiting for results"
	 * @return Stage State
	 */
	public String getStageState(){
		return stageState;
	}

	/**
	 * @return Stage ID of the stage
	 */
	public int getStageID() {
		return stageID;
	}

	/**
	 * @return Name of the stage
	 */
	public String getStageName() {
		return stageName;
	}

	/**
	 * @return Description of the stage
	 */
	public String getDescription() {
		return description;
	} 

	/**
	 * @return Length of the stage
	 */
	public Double getLength() {
		return length;
	}

	/**
	 * @return Start-time of the race
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @return Type of stage
	 */
	public StageType getType() {
		return type;
	}

	/**
	 * @return Number results in stage
	 */
	public int getResultsSize(){
		return results.size();
		// id not by checkpoint by rider ids
		// need to get a list of the checkpoints time in the right order
	}

	/**
	 * Gets a list of all the checkpoints in a stage
	 * @return Array of checkpoint IDs
	 */
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

	/**
	 * @return A string containing the basic information contained within a stage
	 */
	public String stageAsString(){
        return ("stages of raceId" + raceID + " " + stageID + " "+ stageName + " " + description + " " + length + " " + startTime + " " + type);
	}
	
	/**
	 * Adds the results from a specific rider to the stage
	 * @param riderId	ID of the rider who's results are being added
	 * @param times		LocalTime times for the rider's results, these should be in order of the checkpoints
	 * @throws DuplicatedResultException	If the given rider already has results for that stage
	 */
	public void setResults(int riderId, LocalTime... times) throws DuplicatedResultException{
		for (Integer i : results.keySet()) {
  			if (i == riderId){
				throw new DuplicatedResultException();
			}
		}
		this.results.put(riderId, times);
	}

	/**
	 * Gets the times of each checkpoint for a given rider in the stage
	 * @param riderID	The ID of the rider whose results are being returned
	 * @return			LocalTime array of times in order of checkpoints
	 * @throws IDNotRecognisedException		If the Rider has no registered results in the stage
	 */
	public LocalTime[] getRiderTimes(int riderID) throws IDNotRecognisedException{
		//for the array or rider times find the RiderID that 
		LocalTime[] ridertimes = new LocalTime[]{};
		try{
			ridertimes = this.results.get(riderID);
		}catch(Exception e){
			throw new IDNotRecognisedException();
		}
		return ridertimes;
	}

	/**
	 * Returns the unadjusted elapsed time from the start to the end of the stage for a given rider
	 * @param riderID	ID of the rider whose total time is checked
	 * @return			The LocalTime elapsed between the start and end of the stage
	 */
	public LocalTime realElapsedTime(int riderID){
		LocalTime[] riderResults = results.get(riderID);
		LocalTime end = riderResults[riderResults.length -1];
		LocalTime start = riderResults[0];
		return end.minusHours(start.getHour()).minusMinutes(start.getMinute()).minusSeconds(start.getSecond()).minusNanos(start.getNano());
	}

	/**
	 * Returns the adjusted total time for a given rider to complete the stage
	 * @param riderId		Rider whose adjusted time is being returned
	 * @return			The adjusted LocalTime of the stage's completion by a given rider
	 */
	public LocalTime getAdjustedTime(int riderId){
		return adjustedResults.get(riderId);
	}

	/**
	 * Ranks the results in order of adjusted elapsed time to complete the stage
	 * <p> 
	 * Caculates adjustements and returns them in order of speed. This corresponds to the order of Riders given by
	 * {@link #getRank}.
	 * @return	The adjusted times in order of fastest to slowest. Or an empty array if no results are registered
	 */
	public LocalTime[] getAdjustedRank(){
		LocalTime[] orderedTimes = new LocalTime[results.size()];
		if(results.size() != 0){
			calculateAdjustment();
			int count = 0;
			for(Integer id : adjustedResults.keySet()){
				orderedTimes[count] = adjustedResults.get(id);
				count++;
			}
		}
		return orderedTimes;
	}

	/**
	 * Returns a list of the Rider IDs in the order they finished according to their adjusted times
	 * @return Rider IDs in order of completion. This will return an empty array if no results are registered.
	 */
	public int[] getRank(){
		int[] orderedIDs = new int[results.size()];
		if(results.size()!=0){
			ArrayList<LocalTime> riderTimes = new ArrayList<>(); //Array that can be sorted
			HashMap<LocalTime, Integer> timeRiderDict = new HashMap<>(); //Dictionary matching times to IDs
			for (Integer id : results.keySet()) {
				LocalTime theirTime = realElapsedTime(id);
				riderTimes.add(theirTime);
				timeRiderDict.put(theirTime, id);
			}
			Collections.sort(riderTimes);
			int count = 0;
			for(LocalTime time : riderTimes){
				orderedIDs[count] = timeRiderDict.get(time);
				count++;
			}
		}
		return orderedIDs;
	}

	/**
	 * Calculates the adjusted times for riders in the stage
	 * <p> This updates the attribute adjustedTimes for use in other methods. Adjustments are made if
	 * a rider has a finishing time less than one second slower than the person ahead.
	 * <p> This should not be called if no results are registered or on time trials
	 */
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
		if(type != StageType.TT){
			// =====
			//Group times into peletons
			// =====
			ArrayList<Integer> groupList = new ArrayList<>(); //Assigns the times into peletons if necessary
			//In form: {1,1,1,0,0,4,4} where non-zero indicate being members of the specified group

			int groupCount = 1; //Tracks which group the times are currently part of
			for (int i = 0; i < riderTimes.size()-1; i++) {
				LocalTime next = riderTimes.get(i+1); //Next time in list
				LocalTime current = riderTimes.get(i); //This time in list
				if((current.until(next, ChronoUnit.MILLIS)) < 1000){ //Difference between is less than one
					groupList.add(groupCount);
				} else{
					groupList.add(0);
					groupCount++;
				}
			}

			// =====
			// Create a new hashmap of all the values with their ids
			// =====
			adjustedResults.clear();
			//First Value will always be unchanged
			adjustedResults.put(timeRiderDict.get(riderTimes.get(0)), riderTimes.get(0));
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
		
		}else{
			for(LocalTime time : riderTimes){
				adjustedResults.put(timeRiderDict.get(time), time);
			}
	
		}
		}
	

	/**
	 * Calculates the number of points a person will earn in a given stage
	 * <p> These will correspond to the points assigned for different stage types. 
	 * @return An array of points assigned to riders in the order they place in the stage
	 */
	public int[] calculateStagePoints(){
	ArrayList<Integer> listOfPoints = new ArrayList<>();
		if(type == StageType.FLAT){
			Collections.addAll(listOfPoints, 50,30,20,18,16,14,12,10,8,7,6,5,4,3,2);
		}else if(type == StageType.MEDIUM_MOUNTAIN){
			Collections.addAll(listOfPoints, 30,25,22,19,17,15,13,11,9,7,6,5,4,3,2);
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
	} 

	/**
	 * Calculates the checkpoint ranks for a stage using the checkpoint times in results and adds them to a 
	 * hash map that you can access. 
	 */
	public void calulateRankedCheckpoint(){
		//have a list of checkpoints for rider id 
		// create a list of times for each checkpoint and sort that pass in the scores and add them to rider ID totals 
		// calculate riders order for each checkpoint and sum them up
		int count = 0;
		LocalTime[] times;
		int[] orderedIDs;
		ArrayList<LocalTime> riderTimes = new ArrayList<>(); //Array that can be sorted
		HashMap<LocalTime, Integer> timeRiderDict = new HashMap<>(); //Dictionary matching times to IDs
		// for the times in the stage get rank for each time then allocate points based on the time from that rank 
		// gives all the rider IDS sorted by time for each checkpoint 
			for(int i=1; i< checkpoints.size() +1; i++){
				timeRiderDict.clear();
				riderTimes.clear();
				for(Integer riderID : results.keySet()){
					times = results.get(riderID);
					LocalTime theirTime = times[i];
					riderTimes.add(theirTime);
					timeRiderDict.put(theirTime, riderID);
			}
			Collections.sort(riderTimes);
			orderedIDs = new int[riderTimes.size()];
			count = 0;
			for(LocalTime time : riderTimes){
				orderedIDs[count] = timeRiderDict.get(time);
				count++;
			}
			this.rankedCheckpointTimes.put(checkpoints.get(i-1), orderedIDs);
		}
	}
	
	/**
	 * gets the checkpoint rank from the hashmap based on its checkpoint ID
	 * @param checkpointID	Checkpoint ID whose time is being retrived
	 * @return	the ranks for a given checkpoint		
	 */
		public int[] getRankedCheckpoint(int checkpointID){
			return rankedCheckpointTimes.get(checkpointID);
		}

		
	}
	



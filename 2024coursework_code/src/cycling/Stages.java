package cycling;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;


public class Stages {
	// 4 attributes
	private int stageID ;
	private int raceID;
	private static int nextID;
	private String stageName;
	private String description;
	private double length;
	LocalDateTime StartTime;
	private StageType type;
	private String stageState;
	ArrayList<Integer> stageIDList = new ArrayList<Integer>();
	private ArrayList<Integer> checkpoints = new ArrayList<Integer>();
	private static ArrayList<Integer> currentStages = new ArrayList<Integer>();
	HashMap<Integer, LocalTime[]> results= new HashMap<>();

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

	public long realElapsedTime(int riderID){
		LocalTime[] riderResults = results.get(riderID);
		LocalTime end = riderResults[riderResults.length -1];
		LocalTime start = riderResults[0];
		long time = start.until(end, ChronoUnit.MILLIS);
		return time;
	}

	public static void caculateAdjustment(){ 
		//TODO: Once this all works, change to the following to read in instead
		// // for (Integer id : results.keySet()) {a
  		// // 	listOfTimes.add(realElapsedTime(id) + " " + id);
		// // }
		/* 
		
		// =====
		// Get times from results
		// =====
		ArrayList<Double> riderTimes = new ArrayList<>(); //Array that can be sorted
		HashMap<Double, Double> timeRiderDict = new HashMap(); //Dictionary matching times to IDs
		for (Integer id : results.keySet()) {
  		 	riderTimes.add(realElapsedTime(id)); //TODO: Might cause an error as realElapsedTime() returns long?
			timeRiderDict.put(realElapsedTime(id), id);
		}

		// =====
		// Sort and rejoin the IDs to the times
		// =====
		Collections.sort(riderTimes);

		ArrayList<Double[]> listOfTimes = new ArrayList<>(); //Finished array containing a 2D array
		//In form [ [time, id], [time, id]]
		for (int i = 0; i < 7; i++) {
			Double[] temp = new Double[]{riderTimes.get(i), timeRiderDict.get(riderTimes.get(i))};
			listOfTimes.add(temp);
		}

		// =====
		//Group times into peletons
		// =====
		ArrayList<Integer> groupList = new ArrayList<>(); //Assigns the times into peletons if necessary
		//In form: {1,1,1,0,0,4,4} where non-zero indicate being members of the specified group

		int groupCount = 1; //Tracks which group the times are currently part of
		for (int i = 0; i < listOfTimes.size()-1; i++) {
			Double time1 = listOfTimes.get(i+1)[0]; //Next time in list
			Double time2 = listOfTimes.get(i)[0]; //This time in list
			if((time1 - time2) < 1){ //Difference between is less than one
				groupList.add(groupCount);
			} else{
				groupList.add(0);
				groupCount++;;
			}
		}

		// =====
		// Reassign all times of memebers of a peleton to the leader's time
		// =====
		int currentGroupLead = 0;
		Double groupTime = 0.0d;
		int count = 0;
		for (int group : groupList) {
			if (group == 0){
				//Do Nothing
				//currentGroupLead = 0;
			} else{
				//count = current index
				listOfTimes.get(count+1)[0] = listOfTimes.get(count)[0];
				// take that index and make next one same
			}
			count++;
		}

		return listOfTimes;
		//Or maybe store as an attribute? Depends how we want the others to work
		*/
		

		ArrayList<Double> riderTimes = new ArrayList<>();
		riderTimes.add(8.1);
		riderTimes.add(17.1);
		riderTimes.add(9.6);
		riderTimes.add(17.2);
		riderTimes.add(17.3);
		riderTimes.add(9.5);
		riderTimes.add(27.2);

		HashMap<Double, Double> timeRiderDict = new HashMap<>();
		for (int i = 0; i < 7; i++) {
			timeRiderDict.put(riderTimes.get(i), Double.valueOf(i));
		}
		
		Collections.sort(riderTimes);

		System.out.println(riderTimes);

		ArrayList<Double[]> listOfTimes = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			Double[] temp = new Double[]{riderTimes.get(i), timeRiderDict.get(riderTimes.get(i))};
			listOfTimes.add(temp);
		}
		
		for (Double[] set : listOfTimes) {
			System.out.print(Arrays.toString(set));
		}

		ArrayList<Integer> groupList = new ArrayList<>();
		int groupCount = 1;
		for (int i = 0; i < listOfTimes.size()-1; i++) {
			//If (the first half of the string (the time) as an int of i minus the next one is smaller than one)
			//Aka if the time distance bewteen two consecutive riders in a sorted list is smaller than 1 second
			Double time1 = listOfTimes.get(i+1)[0];
			Double time2 = listOfTimes.get(i)[0];
			if((time1 - time2) < 1){
				groupList.add(groupCount);
			} else{
				groupList.add(0);
				groupCount++;;
			}
		}

		System.out.println("\n" + groupList);

// //GroupList contains something like {0,0,1,1,1,1,0,0,3,3,3,0,0,5,5,5}
// //When 0 do nothing
// //When number, change all values with that number to the value of the first instance of that number
// get the first number because its the first not 0 then if they equal that number all equal the first number ??

		int count = 0;
		for (int group : groupList) {
			if (group == 0){
				//Do Nothing
				//currentGroupLead = 0;
			} else{
				//count = current index
				listOfTimes.get(count+1)[0] = listOfTimes.get(count)[0];
				// take that index and make next one same
			}
			count++;
		}

		for (Double[] set : listOfTimes) {
			System.out.print(Arrays.toString(set));
		}
	}
}





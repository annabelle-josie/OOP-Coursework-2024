package cycling;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.Serializable;
import java.time.LocalTime;
public class Checkpoints implements Serializable {
	// 4 attributes
	private int checkpointID;
	private static int nextID;
	private int stageID ;
	private Double location;
	private CheckpointType type;
	private Double averageGradient;
	private Double length;
	ArrayList<Integer> currentCheckpoints = new ArrayList<Integer>();
	// constructor
	public Checkpoints(int id, int stageId, Double location, CheckpointType type, Double averageGradient,Double length) {
		this.checkpointID = id;
        currentCheckpoints.add(checkpointID);
		this.stageID = stageId;
        this.location = location;
        this.type = type;
		this.averageGradient = averageGradient;
		this.length = 0.0d;
	}
	public Checkpoints(int stageId, Double location) {
		this.checkpointID = nextID;
        nextID++;
        currentCheckpoints.add(checkpointID);
		this.stageID = stageId;
        this.location = location;
		this.length = 0.0d;
	}
	public int getCheckpointID() {
		return checkpointID;
	} 
	public int getStageID() {
		return stageID;
	}
	public CheckpointType getType() {
		return type;
	}
	public Double getAverageGradient() {
		return averageGradient ;
	}
	public Double getLength() {
		return length;
	}
	// public int[] getCheckpointRank(){
	// 	// get each time for that stage for the stage results 
	// 	ArrayList<LocalTime> riderTimes = new ArrayList<>(); //Array that can be sorted
	// 	HashMap<LocalTime, Integer> timeRiderDict = new HashMap<>(); //Dictionary matching times to IDs
		
	// 	for (Integer id : results.keySet()) {
	// 		// to change 
	// 		LocalTime theirTime = realElapsedTime(id);
	// 		riderTimes.add(theirTime);
	// 		timeRiderDict.put(theirTime, id);
	// 	}
	// 	Collections.sort(riderTimes);
	// 	int[] orderedIDs = new int[riderTimes.size()];
	// 	int count = 0;
	// 	for(LocalTime time : riderTimes){
	// 		orderedIDs[count] = timeRiderDict.get(time);
	// 		count++;
	// 	}
	// 	return orderedIDs;
	// }

	// public int[] getMountainPoints(int results){
	// 	ArrayList<Integer> listOfPoints = new ArrayList<Integer>();
	// 	// use rank in stage to get the order of rider in stage
	// 	if(type == CheckpointType.C1){
	// 		Collections.addAll(listOfPoints, 1);
	// 	}else if(type == CheckpointType.C2){
	// 		Collections.addAll(listOfPoints, 2,1);
	// 	}else if(type == CheckpointType.C3) {
	// 		Collections.addAll(listOfPoints, 5,3,2,1);
	// 	}else if(type == CheckpointType.C4) {
	// 		Collections.addAll(listOfPoints, 10,8,6,4,2,1);
	// 	}else if(type == CheckpointType.HC) {
	// 		Collections.addAll(listOfPoints, 20,15,12,10,8,6,4,2);
	// 	}
	// 	int[] returnArray = new int[results];
	// 	for (int i = 0;  i < returnArray.length; i++) {
	// 		if( i < listOfPoints.size() ){
	// 			returnArray[i] = listOfPoints.get(i);
	// 		}else{
	// 			returnArray[i] = 0;
	// 		}
	// 	}
	// 	return returnArray;
	// }
}

package cycling;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.io.Serializable;
import java.time.LocalTime;

public class Checkpoints implements Serializable {
	private int checkpointID;
	private int stageID ;
	private Double location;
	private CheckpointType type;
	private Double averageGradient;
	private Double length;
	ArrayList<Integer> currentCheckpoints = new ArrayList<Integer>();

	/**
	 * Creates a new checkpoint
	 * TODO: is this specific to mountain ones? I feel like it is...
	 * @param id				ID of the checkpoint to be added
	 * @param stageId			ID of the stage the checkpoint belongs to
	 * @param location			The location of the checkpoint
	 * @param type				The type of checkpoint
	 * @param averageGradient	The average gradient of the checkpoint
	 * @param length			The length of the checkpoint
	 */
	public Checkpoints(int id, int stageId, Double location, CheckpointType type, Double averageGradient,Double length) {
		this.checkpointID = id;
		this.stageID = stageId;
        this.location = location;
        this.type = type;
		this.averageGradient = averageGradient;
		this.length = 0.0d;
	}

	/**
	 * Alternate constructor for more limited information
	 * @param id			ID of the checkpoint
	 * @param stageId		ID of the stage the checkpoint belongs to
	 * @param location		The location of the checkpoint
	 */
	public Checkpoints(int id, int stageId, Double location) {
		this.checkpointID = id;
		this.stageID = stageId;
        this.location = location;
		this.length = 0.0d;
	}

	/**
	 * @return ID of the checkpoint
	 */
	public int getCheckpointID() {
		return checkpointID;
	} 

	/**
	 * @return  The ID of the stage the checkpoint belongs to
	 */
	public int getStageID() {
		return stageID;
	}

	/**
	 * @return The type of checkpoint
	 */
	public CheckpointType getType() {
		return type;
	}

	/**
	 * @return The average gradient of the checkpoint
	 */
	public Double getAverageGradient() {
		return averageGradient ;
	}

	/**
	 * @return The length of the checkpoint
	 */
	public Double getLength() {
		return length;
	}

	/**
	 * The location of the checkpoint
	 * @return
	 */
	public Double getLocation() {
		return location;
	}
	// public int[] getCheckpointRank(HashMap<Integer, LocalTime[]> results){
	// 	// get each time for that stage for the stage results 
	// 	ArrayList<LocalTime> riderTimes = new ArrayList<>(); //Array that can be sorted
	// 	HashMap<LocalTime, Integer> timeRiderDict = new HashMap<>(); //Dictionary matching times to IDs
		
	// 	for (Integer id : results.keySet()) {
	// 		// to change 
	// 		//LocalTime theirTime = (id);
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

	/**
	 * TODO: Amy help please write this doc! I don't know what it does
	 * @param size
	 * @return
	 */
	public int[] getMountainPoints(int size){
		// this works if they are in the right order to give riders the right order 
		// attribute these to riders and sum them 
		//LocalTime[] checkpointTimes){
		ArrayList<Integer> listOfPoints = new ArrayList<Integer>();
		// use rank in stage to get the order of rider in stage
		if(type == CheckpointType.C1){
			Collections.addAll(listOfPoints, 1);
		}else if(type == CheckpointType.C2){
			Collections.addAll(listOfPoints, 2,1);
		}else if(type == CheckpointType.C3) {
			Collections.addAll(listOfPoints, 5,3,2,1);
		}else if(type == CheckpointType.C4) {
			Collections.addAll(listOfPoints, 10,8,6,4,2,1);
		}else if(type == CheckpointType.HC) {
			Collections.addAll(listOfPoints, 20,15,12,10,8,6,4,2);
		} 
		// if not a moutain pass by 
		int[] returnArray = new int[size];
		for (int i = 0;  i < size; i++) {
			if(i <listOfPoints.size()){
				//System.out.println("yoo");
				//System.out.println(listOfPoints);
				//System.out.println("length " + returnArray.length);
				//System.out.println(Arrays.toString(returnArray));
				returnArray[i] = listOfPoints.get(i);
			}else{
				//System.out.println("no");
				returnArray[i] = 0;
			}
		}
		return returnArray;
	}
}
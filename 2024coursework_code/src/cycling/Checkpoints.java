package cycling;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class Checkpoints implements Serializable {
	private int checkpointID;
	private int stageID;
	private Double location;
	private CheckpointType type;
	private Double averageGradient;
	private Double length;

	/**
	 * Creates a new checkpoint for Mountain Checkpoints, adding mountain specific attributes
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
	 * Checkpoint constructor for sprints or 
	 * @param id			ID of the checkpoint
	 * @param stageId		ID of the stage the checkpoint belongs to
	 * @param location		The location of the checkpoint
	 * @param type			The type of checkpoint
	 */
	public Checkpoints(int id, int stageId, Double location, CheckpointType type) {
		this.checkpointID = id;
		this.stageID = stageId;
        this.location = location;
		this.length = 0.0d;
		this.type = type;
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

	/**
	 * calculates the list of mountain points using the amount of riders that passed it
	 * @param size
	 * @return list of the mountain points achieved for that checkpoint
	 */
	public int[] getMountainPoints(int size){
		// this works if they are in the right order to give riders the right order 
		// attribute these to riders and sum them 
		ArrayList<Integer> listOfPoints = new ArrayList<>();
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
				returnArray[i] = listOfPoints.get(i);
			}else{
				returnArray[i] = 0;
			}
		}
		return returnArray;
	}

/**
	 * calculates the list of intermidiate sprint points using the amount of riders that passed it
	 * @param size
	 * @return list of the intermidiate sprint points achieved for that checkpoint
	 */
	public int[] addIntermidiatePoints(int size ){
		int[] returnArray = new int[size];
			ArrayList<Integer> listOfPoints = new ArrayList<>();
			Collections.addAll(listOfPoints, 20,17,15,13,11,10,9,8,7,6,5,4,3,2,1);
			for (int i = 0;  i < size; i++) {
			if(i <listOfPoints.size()){
				returnArray[i] = listOfPoints.get(i);
			}else{
				returnArray[i] = 0;
			}
		}
		return returnArray;
	}
}
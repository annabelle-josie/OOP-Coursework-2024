package cycling;
import java.util.ArrayList;
public class Checkpoints {
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
	public Checkpoints(int stageId, Double location, CheckpointType type, Double averageGradient,Double length) {
		this.checkpointID = nextID;
        	nextID++;
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


}

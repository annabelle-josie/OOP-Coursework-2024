import java.util.ArrayList;
public class checkpoints {
	// 4 attributes
	private int checkpointID;
	private int stageID ;
	private Double location;
	private CheckpointType type;
	private Double averageGradient;
	private Double length;
	ArrayList<Integer> checkpointIDList = new ArrayList<Integer>();
	// constructor
	public checkpoints(int stageId, Double location, CheckpointType type, Double averageGradient,Double length) {
		this.stageID = stageId;
        	this.location = location;
        	this.type = type;
		this.averageGradient = averageGradient;
		this.length = 0.0d;
	}

	
	public int createcheckpointID() {
		// if checkpoint list is empty checkpoint ID = 0001
		if (checkpointIDList.isEmpty()) {
			checkpointID = 1;
		} else {
			checkpointID = (checkpointIDList.get(checkpointIDList.size()-1) +1);
		}
		// add an array to list
		checkpointIDList.add(checkpointID);
		return checkpointID;
	}


}
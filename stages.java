package cycling
import java.util.ArrayList;
import java.time.LocalDateTime;
public class stages {
	// 4 attributes
	private int stageID ;
	private int raceID;
	private String stageName;
	private String description;
	private double length;
	LocalDateTime StartTime;
	private StageType type;
	ArrayList<Integer> checkpointIDs = new ArrayList<Integer>();
	ArrayList<Integer> stageIDList = new ArrayList<Integer>();

	// constructor
	public stages(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) {
	
		this.stageID = createStageID();
		this.raceID = raceId;
        	this.stageName = stageName;
        	this.description = description;
		this.length = length;
		this.StartTime = startTime;
		this.type = type;
	}

	
	public int createStageID() {
		// if stage list is empty stage ID = 0001
		if (stageIDList.isEmpty()) {
			stageID = 1;
		} else {
			stageID = (stageIDList.get(stageIDList.size()-1) +1);
		}
		// add an array to list
		stageIDList.add(stageID);
		return stageID;
	}
	
// same with adding stages to races other option just use IDS dont reference the class 
	public void addCheckpoint(int checkpointID) {
		checkpointIDs.add(checkpointID);
	
// the getters

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



}





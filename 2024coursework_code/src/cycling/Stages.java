package cycling;
import java.util.ArrayList;
import java.time.LocalDateTime;

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
	int[] checkpoints;
	ArrayList<Integer> stageIDList = new ArrayList<Integer>();
	private static ArrayList<Integer> currentStages = new ArrayList<Integer>();

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
		this.stageState = null;
		this.checkpoints = null;
	}

	
// same with adding stages to races other option just use IDS dont reference the class 
	public void addCheckpoint(int checkpointID) {
		if (checkpoints== null){
			this.checkpoints = new int[]{checkpointID};
		} else {
			int length = checkpoints.length;
			int[] newArray = new int[length + 1];
			int i;
			for (i = 0; i < length; i++)
				newArray[i] = checkpoints[i];
			newArray[length] = checkpointID;
			this.checkpoints = newArray;
		}
// the getters

	}
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
		return checkpoints;
	}

	public String stageasstring(){
        return ("stages of raceId" + raceID + " " + stageID + " "+ stageName + " " + description + " " + length + " " + StartTime + " " + type);
	}



}





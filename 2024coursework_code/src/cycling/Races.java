package cycling;

public class Races {
	// 4 attributes
	private int raceID ;
	private String name;
	private String description;
	//private stages[] stages;
	int[] stages ;
	private Double length;


	// constructor

	public Races(int raceId, String name, String description) {
		this.raceID = raceId;
        this.name = name;
		this.description = description;
		this.stages = null ;
		this.length = 0.0d;
	}


// add stages
// using race ID 
    public void setStages(int stageId) {
		if (stages == null){
			System.out.println("hello there sir");
			this.stages = new int[]{stageId};
		} else {
			System.out.println("hello there");
			int length = stages.length;
			int[] newArray = new int[length + 1];
			int i;
			for (i = 0; i < length; i++)
				newArray[i] = stages[i];
			newArray[length] = stageId;
			this.stages = newArray;
		}
    }
	public void replaceStages(int[] stagelist){
		this.stages = stagelist;
	}
	public void setLength(double length){
			this.length += length;
	}

    
// public getters and setters for private instance
	public int getRaceID() {
		return raceID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() { return description; }

	public int[] getStages() {
        	return stages;
    	}

	public Double getLength() {return length;}
}

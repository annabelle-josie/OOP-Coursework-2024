package cycling;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Team Class
 */

public class Teams implements Serializable{
    private int teamID;
    private ArrayList<Integer> riders = new ArrayList<>();
    private String name;
    private String description;

    /**
     * Creates a new team
     * @param id            The ID of the team
     * @param name          The name of the team
     * @param description   A description of the team
     */
    public Teams(int id, String name, String description){
        this.teamID = id;
        this.name = name;
        this.description = description;
    }


    /**
     * @return ID of the team
     */
    public int getTeamID(){
        return teamID;
    }

    /**
     * @return Name of the team
     */
    public String getName(){
        return name;
    } 

    /**
     * @return Description of the team
     */
    public String getDescription(){
        return description;
    } 

    /**
     * Adds a rider to the team
     * @param riderID   ID of the rider to be added
     */
    public void addRider(int riderID){
        riders.add(riderID);
    }
    
    
    /**
     * Removes a rider of the team
     * @param riderID       ID of the rider to be removed
     * @throws IDNotRecognisedException     If the ID given does not correspond to a rider in the team
     */
    public void removeRider(int riderID ) throws IDNotRecognisedException {
		boolean worked = riders.remove((Integer)riderID);
		if (!worked){
			throw new IDNotRecognisedException();
		}
    }

    /**
     * @return The IDs of the riders in a team
     */
    public int[] getRiders(){
        int[] riderIDArray = new int[riders.size()];
        int count=0;
        for (int rider : riders) {
            riderIDArray[count] = rider;
            count++;
        }
        return riderIDArray;
    }
}
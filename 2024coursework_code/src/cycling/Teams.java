package cycling;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Team Class
 * <p>
 * Allows Teams to be created with unique IDs, names and descriptions
 * <p>
 * Also an ArrayList containing the riders who are part of the team
 */

public class Teams implements Serializable{
    private int teamID;
    private static int nextID;
    private ArrayList<Integer> riders = new ArrayList<>();
    private String name, description;

    /**
     * Constructor class for rider
     * <p>
     * Creates new Team with unique ID
     */
    public Teams(String name, String description){
        this.teamID = nextID;
        nextID++; 
        this.name = name;
        this.description = description;
    }


    /**
     * @return Unique ID of the team
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
     * <p>
     * Takes RiderID, checks if that rider exists throws error if not.
     * Adds RiderID to the ArrayList of Riders in that team
     * @param riderID                       Integer ID of the rider to be added
     * @throws IDNotRecognisedException     If a rider with that ID does not exist
     */
    public void addRider(int riderID){
        riders.add(riderID);
    }
    
    /**
     * Returns the RiderIDs of Riders in the given team
     * @return ArrayList of Integers of RiderIDs
     */
    public void removeRider(int riderID ) throws IDNotRecognisedException {
		Boolean worked = riders.remove((Integer)riderID);
		if (!worked){
			throw new IDNotRecognisedException();
		}
    }

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
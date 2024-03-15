package cycling;
import java.util.ArrayList;

/**
 * Team Class
 * <p>
 * Allows Teams to be created with unique IDs, names and descriptions
 * <p>
 * Also an ArrayList containing the riders who are part of the team
 */

public class Team{
    private int teamID;
    private static int nextID;
    private ArrayList<Rider> riders = new ArrayList<Rider>();
    private String name, description;

    /**
     * Constructor class for rider
     * <p>
     * Creates new Team with unique ID
     */
    public Team(){
        this.teamID = nextID;
        nextID++; 
    }

    /**
     * Allows team details to be added to the team
     * @param name          Name of the Team
     * @param description   Description of the Team
     */
    public void fillDetails(String name, String description){
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
     * There is a very real chance this doesn't work
     * TODO: Test and debate changing to arraylist of integers (RiderIDs)
     * <p>
     * Currently Rider is inputted, this should be changed to ID when brain works
     * <p>
     * TODO: Change to accept these two:
     * @param riderID                       The ID of the rider to be added
     * @throws IDNotRecognisedException     If a rider with that ID does not exist
     */
    public void addRider(Rider newRider){
        //riderID
        riders.add(newRider);
    }
    
    /**
     * Returns a list the Riders IDs
     * <p>
     * This will simplify if addRider() is changed
     * TODO: Test and debate changing to arraylist of integers (RiderIDs) 
     * @return Integer array of RiderIDs
     */
    public int[] getRiderIDList(){
        int[] riderIDs = new int[riders.size()];
        int count = 0;
        for (Rider rider : riders) {
            riderIDs[count] = rider.getRiderID();
            count++;
        }
        return riderIDs;
    }
}
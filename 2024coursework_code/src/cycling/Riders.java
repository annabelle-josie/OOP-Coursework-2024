package cycling;
import java.util.ArrayList;
import java.io.Serializable;


/**
 * Rider Class
 * <p>
 * Allows riders to be created, currently accepts name, yearOfBirth and sets the ID
 * <p>
 * Just getters and setters (basically)
 */

public class Riders implements Serializable{
    //TODO: Is it best programming practice to declare as a group or individually?
    private int riderID, yearOfBirth;
    private static int nextID; //Static as independent of instances
    private String name;
    private static ArrayList<Integer> currentRiders = new ArrayList<Integer>();
    //TODO: Some way of counting their point totals

    /**
     * Constructor class for rider
     * Creates new Rider with unique ID
     */
    public Riders(int id, String name, int yearOfBirth){
        this.riderID = id;
        currentRiders.add(riderID);
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * @return The unique ID of the rider
     */
    public int getRiderID(){
        return riderID;
    }

    // public static void isRider(int riderID) throws IDNotRecognisedException{
    //     if (!currentRiders.contains(riderID)){
    //         throw new IDNotRecognisedException();
    //     }
    // }

    /**
     * @return The rider's name
     */
    public String getName(){
        return name;
    }

    /**
     * @return The birth year of the rider
     */
    public int getYearOfBirth(){
        return yearOfBirth;
    }

    /**
     * Made to quickly return rider details. Used in debugging
     * @return A String containing all the details of that rider
     */
    @Override
    public String toString(){
        String out = "";
        out = String.valueOf(this.getRiderID()) + ": ";
        out = out + this.getName() + ", ";
        out = out + this.getYearOfBirth() + " ";
        return out;
    }

    //TODO:Add delete rider to remove from list
}
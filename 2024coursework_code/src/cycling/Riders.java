package cycling;
import java.io.Serializable;


/**
 * Riders stores the information relevent to a given rider
 * @author Amy Lewis and Annabelle Ronald
 * @version 1.0
 */
public class Riders implements Serializable{
    private int riderID;
    private int yearOfBirth;
    private String name;

    /**
     * Creates a new rider
     * @param id            ID of the rider
     * @param name          Name of the rider
     * @param yearOfBirth   Year of Birth of the Rider (In the form YYYY)
     */
    public Riders(int id, String name, int yearOfBirth){
        this.riderID = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * @return The ID of the rider
     */
    public int getRiderID(){
        return riderID;
    }

    /**
     * @return The rider's name
     */
    public String getName(){
        return name;
    }

    /**
     * @return The birth year of the rider (In the form YYYY)
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
}
package cycling;

/**
 * Rider Class
 * <p>
 * Allows riders to be created, currently accepts name, yearOfBirth and sets the ID
 * <p>
 * Just getters and setters (basically)
 */

public class Rider{
    //TODO: Is it best programming practice to declare as a group or individually?
    private int riderID, yearOfBirth;
    private static int nextID; //Static as independent of instances
    private String name;
    //TODO: Some way of counting their point totals

    /**
     * Constructor class for rider
     * Creates new Rider with unique ID
     */
    public Rider(){
        //ID will incriment each time. If a rider is deleted then there will just be a gap in the numbers
        this.riderID = nextID;
        nextID++;
    }

    /**
     * Allows Riders's details to be added to the Rider
     * @param name          The name of the rider
     * @param yearOfBirth   The birth year of the rider
     */
    public void fillDetails(String name, int yearOfBirth){
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * @return The unique ID of the rider
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
}
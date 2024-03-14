import cycling.BadMiniCyclingPortalImpl;
import cycling.IllegalNameException;
import cycling.InvalidNameException;
import cycling.MiniCyclingPortal;
import cycling.Rider;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortal interface -- note you
 * will want to increase these checks, and run it on your CyclingPortalImpl class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 2.0
 */
public class CyclingPortalTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");
		/* Annabelle's Testing of Rider.java
		 * Working:
		 * All getters and setters
		 * fillDetails
		 * The ID setting done by the constructor. IDs cannot be reused, that was intentional, but is that what we want?
		 
		Rider firstRider = new Rider();
		System.out.println(firstRider.getRiderID());
		Rider secondRider = new Rider();
		System.out.println(secondRider.getRiderID());
		firstRider.fillDetails("Alice", 2005);
		secondRider.fillDetails("Bob", 1990);
		System.out.println(firstRider);
		System.out.println(secondRider);

		Rider riderList[] = new Rider[50];
		for (int i = 0; i < 50; i++) {
			riderList[i] = new Rider();
			System.out.println(riderList[i].getRiderID());
		}
		riderList[6] = new Rider();
		riderList[37] = new Rider();
		for (int i = 0; i < 50; i++) {
			System.out.println(riderList[i].getRiderID());
		}
		*/
		/*
		 * TODO:Annabelle's testing of Team.java
		 * 
		 */


		// TODO replace BadMiniCyclingPortalImpl by CyclingPortalImpl
		MiniCyclingPortal portal1 = new BadMiniCyclingPortalImpl();
		MiniCyclingPortal portal2 = new BadMiniCyclingPortalImpl();

		assert (portal1.getRaceIds().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";
		assert (portal1.getTeams().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";

		try {
			portal1.createTeam("TeamOne", "My favorite");
			portal2.createTeam("TeamOne", "My favorite");
		} catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}

		assert (portal1.getTeams().length == 1)
				: "Portal1 should have one team.";

		assert (portal2.getTeams().length == 1)
				: "Portal2 should have one team.";

	}

}

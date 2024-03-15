import cycling.BadMiniCyclingPortalImpl;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidNameException;
import cycling.MiniCyclingPortal;
import cycling.Rider;
import cycling.Team;

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
		 */

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
		
		/*
		 * Annabelle's testing of Team.java */
		Team teamA = new Team();
		Team teamB = new Team();
		Team teamC = new Team();
		teamA.fillDetails("First Team", "The first team");
		System.out.println(teamA.getName());
		try {
			teamB.addRider(riderList[10].getRiderID());
			teamB.addRider(riderList[1].getRiderID());
			teamB.addRider(riderList[15].getRiderID());
		} catch (IDNotRecognisedException e) {
			System.out.println("Error thrown");
		}
		try {
			teamB.addRider(67);
		} catch (IDNotRecognisedException e) {
			System.out.println("Error thrown");
		}
		System.out.println(teamB.getRiderIDList());
		//Ok, so this works... but I think you need to be able to add from IDs
		//I'm going to branch and try and fix it.
		

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

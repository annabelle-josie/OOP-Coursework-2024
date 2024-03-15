
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidNameException;
import java.time.LocalDateTime;
import cycling.CyclingPortal;
import cycling.CyclingPortalImpl;
import cycling.StageType;
import cycling.CheckpointType;
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
	 * @throws InvalidNameException 
	 * @throws IllegalNameException 
	 */
	public static void main(String[] args) throws IllegalNameException, InvalidNameException {
		System.out.println("The system compiled and started the execution...");
		/* Annabelle's Testing of Rider.java
		 * Working:
		 * All getters and setters
		 * fillDetails
		 * The ID setting done by the constructor. IDs cannot be reused, that was intentional, but is that what we want?
		 */

		// Rider riderList[] = new Rider[50];
		// for (int i = 0; i < 50; i++) {
		// 	riderList[i] = new Rider();
		// 	System.out.println(riderList[i].getRiderID());
		// }
		// riderList[6] = new Rider();
		// riderList[37] = new Rider();
		// for (int i = 0; i < 50; i++) {
		// 	System.out.println(riderList[i].getRiderID());
		// }
		
		// /*
		//  * Annabelle's testing of Team.java */
		// Team teamA = new Team();
		// Team teamB = new Team();
		// Team teamC = new Team();
		// teamA.fillDetails("First Team", "The first team");
		// System.out.println(teamA.getName());
		// try {
		// 	teamB.addRider(riderList[10].getRiderID());
		// 	teamB.addRider(riderList[1].getRiderID());
		// 	teamB.addRider(riderList[15].getRiderID());
		// } catch (IDNotRecognisedException e) {
		// 	System.out.println("Error thrown");
		// }
		// try {
		// 	teamB.addRider(67);
		// } catch (IDNotRecognisedException e) {
		// 	System.out.println("Error thrown");
		// }
		// System.out.println(teamB.getRiderIDList());
		//Ok, so this works... but I think you need to be able to add from IDs
		//I'm going to branch and try and fix it.
		

		// TODO replace BadMiniCyclingPortalImpl by CyclingPortalImpl
		CyclingPortal portal1 = new CyclingPortalImpl();
		try {
			portal1.createRace("name", "description");
			portal1.createRace("second", "description");
		} catch (Exception e) {
			System.out.println("sorry");		
		}

		//portal1.getRaceIds();
		try {
			System.out.println(portal1.viewRaceDetails(1));
		} catch (IDNotRecognisedException e) {
			System.out.println("this oneeeee");
		}
		try{
			portal1.removeRaceById(1);
		}catch (IDNotRecognisedException e){
			System.out.println("ahhhhh");
		}
		try {
			System.out.println(portal1.viewRaceDetails(1));
		} catch (IDNotRecognisedException e) {
			System.out.println("this oneeeee");
		}

		
		try {
			System.out.println(portal1.addStageToRace(0,"stage1", "hills",13.1d, LocalDateTime.now() ,StageType.FLAT ));
		} catch (Exception e) {
			System.out.println("error here");
		}
		try {
			System.out.println(portal1.viewRaceDetails(0));
		} catch (IDNotRecognisedException e) {
			System.out.println("this oneeeee");
		}
		try{

			int[] stagesInRace = portal1.getRaceStages(0);
			for (int stage : stagesInRace) {
				System.out.println(stage);
			}
		}catch(IDNotRecognisedException e){
			System.out.println("current");
		}
		try{
			System.out.println(portal1.getNumberOfStages(0));
		}catch(IDNotRecognisedException e){
			System.out.println("current 1");
		}
		try{
			System.out.println(portal1.getStageLength(0));
		}catch(IDNotRecognisedException e){
			System.out.println("current 2");
		}

		assert (portal1.getRaceIds().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";
		assert (portal1.getTeams().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";

		try {
			portal1.createTeam("TeamOne", "My favorite");
			//portal2.createTeam("TeamOne", "My favorite");
		} catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}

		assert (portal1.getTeams().length == 1)
				: "Portal1 should have one team.";

		// assert (portal2.getTeams().length == 1)
		// 		: "Portal2 should have one team.";

	}

}

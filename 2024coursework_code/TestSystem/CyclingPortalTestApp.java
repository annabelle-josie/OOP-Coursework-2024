
// import cycling.IDNotRecognisedException;
// import cycling.IllegalNameException;
// import cycling.InvalidLengthException;
// import cycling.InvalidNameException;
// import cycling.InvalidLengthException;
// import cycling.CyclingPortal;
// import cycling.CyclingPortalImpl;
// import cycling.StageType;
// import cycling.CheckpointType;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import cycling.*;

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
		CyclingPortal portal1 = new CyclingPortalImpl();
		try {
			portal1.createRace("name", "description");
			portal1.createRace("second", "description");
		} catch (Exception e) {
			System.out.println("sorry");		
		}

		portal1.getRaceIds();
// 		try {
// 			System.out.println(portal1.viewRaceDetails(1));
// 		} catch (IDNotRecognisedException e) {
// 			System.out.println("this oneeeee");
// 		}
// 		try{
// 			portal1.removeRaceById(1);
// 			System.out.println("Removeed success");
// 		}catch (IDNotRecognisedException e){
// 			System.out.println("ahhhhh");
// 		}
// 		try {
// 			System.out.println(portal1.viewRaceDetails(1));
// 		} catch (IDNotRecognisedException e) {
// 			System.out.println("this oneeeee");
// 		}

		
		try {
			System.out.println("NEW STAGE IS " + portal1.addStageToRace(0,"stage1", "hills",13.1d, LocalDateTime.now() ,StageType.FLAT));
		} catch (IDNotRecognisedException e) {
			System.out.println("error here");
		} catch(IllegalNameException e){
System.out.println("name broke");
		} catch(InvalidNameException e){
System.out.println("name broke 2");
		} catch(InvalidLengthException e){
System.out.println("length broke");
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

// 		try{
// 			portal1.removeStageById(0);
// 			System.out.println("IT WORKED I PROMISE");
// 		} catch(IDNotRecognisedException e){
// 			System.out.println("ID not working :()");
// 		}
// 		try {
// 			System.out.println(portal1.viewRaceDetails(0));
// 		} catch (IDNotRecognisedException e) {
// 			System.out.println("this oneeeee");
// 		}

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


		try {
			//int stageId, Double location, CheckpointType type, 
			//Double averageGradient, Double length) throws IDNotRecognisedException, 
			//InvalidLocationException, InvalidStageStateException, InvalidStageTypeException
			portal1.addCategorizedClimbToStage(0, 11.d, CheckpointType.C1, 1.2d, 1.5d);
		} catch (IDNotRecognisedException e) {
			System.out.println("ID isssues");
		} catch (InvalidLocationException e){
			System.out.println("location issues");
		} catch (InvalidStageStateException e){
			System.out.println("stage state issues");
		} catch (InvalidStageTypeException e){
			System.out.println("stage type issues");
		}
		
		try {
			//int stageId, Double location, CheckpointType type, 
			//Double averageGradient, Double length) throws IDNotRecognisedException, 
			//InvalidLocationException, InvalidStageStateException, InvalidStageTypeException
			portal1.addCategorizedClimbToStage(0, 11.d, CheckpointType.C1, 1.2d, 1.5d);
		} catch (IDNotRecognisedException e) {
			System.out.println("ID isssues");
		} catch (InvalidLocationException e){
			System.out.println("location issues");
		} catch (InvalidStageStateException e){
			System.out.println("stage state issues");
		} catch (InvalidStageTypeException e){
			System.out.println("stage type issues");
		}
		try{
		portal1.addIntermediateSprintToStage(0, 11.d);
	} catch (IDNotRecognisedException e) {
		System.out.println("ID isssues");
	} catch (InvalidLocationException e){
		System.out.println("location issues");
	} catch (InvalidStageStateException e){
		System.out.println("stage state issues");
	} catch (InvalidStageTypeException e){
		System.out.println("stage type issues");
	}

	try{
		System.out.println(Arrays.toString(portal1.getStageCheckpoints(0)));
	} catch(IDNotRecognisedException e){
		System.out.println("Nope, im broken");
	}
	try{
		portal1.removeCheckpoint(0);
	} catch(IDNotRecognisedException e){
		System.out.println("Nope, im broken");
	}catch(InvalidStageStateException e){
		System.out.println("Nope, im broken");
	}

	try{
		System.out.println(Arrays.toString(portal1.getStageCheckpoints(0)));
	} catch(IDNotRecognisedException e){
		System.out.println("Nope, im broken");
	}

	try {
		portal1.concludeStagePreparation(0);
	} catch (IDNotRecognisedException e) {
		System.out.println("hello");
	} catch(InvalidStageStateException e){
		System.out.println("this");
	}
	// try{
	// 	int[] stagesInRace = portal1.getStageCheckpoints(0);
	// 	for (int stage : stagesInRace) {
	// 		System.out.println(stage);
	// 	}
	// }catch(IDNotRecognisedException e){
	// 	System.out.println("current");
	// }

	

	

	try {
			portal1.createTeam("name", "description");
			portal1.createTeam("second", "description");
			portal1.createTeam("thrid", "description");
			portal1.createTeam("sfasdfacond", "description");
		} catch (IllegalNameException e) {
			System.out.println("sorry teams");		
		} catch (InvalidNameException e) {
			System.out.println("sorry teams 2");		
		}

		try{
			portal1.removeTeam(3);
		}catch (IDNotRecognisedException e){
			System.out.println("team");
		}
		
		// int[] teamList = portal1.getTeams();
		// //System.out.println("HERES");
		// for (int team : teamList) {
		// 	System.out.println(team);
		// }

		try {
			portal1.createRider(2, "Jeff", 2000);
		} catch (IDNotRecognisedException e) {
			System.out.println("ID wrong");
		} catch (IllegalArgumentException e){
			System.out.println("Name or year wrong");
		}
		try {
			portal1.createRider(2, "Jim", 2000);
		} catch (IDNotRecognisedException e) {
			System.out.println("ID wrong");
		} catch (IllegalArgumentException e){
			System.out.println("Name or year wrong");
		}
		try {
			portal1.createRider(2, "Dave", 2004);
		} catch (IDNotRecognisedException e) {
			System.out.println("ID wrong");
		} catch (IllegalArgumentException e){
			System.out.println("Name or year wrong");
		}

// 		try {
// System.out.println(Arrays.toString(portal1.getTeamRiders(2)));
// 		} catch (IDNotRecognisedException e) {
// 			System.out.println("id team veiw broken");		
// 		}

// 		try {
// 			portal1.removeRider(2);
// 			System.out.println("Success");
// 		} catch (IDNotRecognisedException e) {
// 			System.out.println("ID wrong");
// 		}

// 		try {
// 			System.out.println(Arrays.toString(portal1.getTeamRiders(2)));
// 					} catch (IDNotRecognisedException e) {
// 						System.out.println("id team veiw broken");		
// 					}

		

		// assert (portal1.getRaceIds().length == 0)
		// 		: "Innitial Portal not empty as required or not returning an empty array.";
		// assert (portal1.getTeams().length == 0)
		// 		: "Innitial Portal not empty as required or not returning an empty array.";

		// try {
		// 	portal1.createTeam("TeamOne", "My favorite");
		// 	//portal2.createTeam("TeamOne", "My favorite");
		// } catch (IllegalNameException e) {
		// 	e.printStackTrace();
		// } catch (InvalidNameException e) {
		// 	e.printStackTrace();
		// }

		

		
		try{
			System.out.println("STages: "+ Arrays.toString(portal1.getRaceStages(0)));
		}catch(IDNotRecognisedException e){
			System.out.println("current");
		}

		try{
		LocalTime[] times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(7), LocalTime.now().plusMinutes(10), LocalTime.now().plusMinutes(12) };
		portal1.registerRiderResultsInStage(0,1,times);
	}catch(IDNotRecognisedException e){
		System.out.println(e);
	}
	catch(DuplicatedResultException e){
		System.out.println("error 2");
	}
	catch(InvalidStageStateException e){
		System.out.println("error 3");
	}
	catch(InvalidCheckpointTimesException e){ 
		System.out.println("error 4");
	}
	
	try{
		LocalTime[] stagesInRace = portal1.getRiderResultsInStage(0,1);
		for (LocalTime stage : stagesInRace) {
			System.out.println(stage);
		}
	}catch(IDNotRecognisedException e){
		System.out.println("current");
	}
	try{
		portal1.deleteRiderResultsInStage(0, 1);
	}catch(IDNotRecognisedException e){
		System.out.println("current");
	}

	Races.dothis();
	Stages.caculateAdjustment();
	
	
		assert (portal1.getTeams().length == 1)
				: "Portal1 should have one team.";

		// assert (portal2.getTeams().length == 1)
		// 		: "Portal2 should have one team.";

	}

}

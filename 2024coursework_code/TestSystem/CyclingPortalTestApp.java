
// import cycling.IDNotRecognisedException;
// import cycling.IllegalNameException;
// import cycling.InvalidLengthException;
// import cycling.InvalidNameException;
// import cycling.InvalidLengthException;
// import cycling.CyclingPortal;
// import cycling.CyclingPortalImpl;
// import cycling.StageType;
// import cycling.CheckpointType;
import java.io.IOException;
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

		try {
			portal1.addStageToRace(0,"stage1", "hills",13.0d, LocalDateTime.now() ,StageType.FLAT);
		} catch (IDNotRecognisedException e) {
			System.out.println("error here");
		} catch(IllegalNameException e){
System.out.println("name broke");
		} catch(InvalidNameException e){
System.out.println("name broke 2");
		} catch(InvalidLengthException e){
System.out.println("length broke");
		}
		// try{
		// 	portal1.removeRaceById(0);
		// 	System.out.println("Removeed success");
		// }catch (IDNotRecognisedException e){
		// 	System.out.println("ahhhhh");
		// }
		// try {
		// 	System.out.println(portal1.viewRaceDetails(0));
		// } catch (IDNotRecognisedException e) {
		// 	System.out.println("this oneeeee");
		// }
		
		// try{
		// 	int[] stagesInRace = portal1.getRaceStages(0);
		// 	for (int stage : stagesInRace) {
		// 		System.out.println(stage);
		// 	}
		// }catch(IDNotRecognisedException e){
		// 	System.out.println("current");
		// }
		try {
			//int stageId, Double location, CheckpointType type, 
			//Double averageGradient, Double length) throws IDNotRecognisedException, 
			//InvalidLocationException, InvalidStageStateException, InvalidStageTypeException
			portal1.addCategorizedClimbToStage(0, 11.d, CheckpointType.C1, 3.2d, 1.5d);
		} catch (IDNotRecognisedException e) {
			System.out.println("ID isssues");
		} catch (InvalidLocationException e){
			System.out.println("location issues");
		} catch (InvalidStageStateException e){
			System.out.println("stage state issues");
		} catch (InvalidStageTypeException e){
			System.out.println("stage type issues");
		}

		// try {
		// 	System.out.println(portal1.viewRaceDetails(0));
		// } catch (IDNotRecognisedException e) {
		// 	System.out.println("this oneeeee");
		// }

		// try{
		// 	System.out.println(portal1.getNumberOfStages(0));
		// }catch(IDNotRecognisedException e){
		// 	System.out.println("current 1");
		// }
		// try{
		// 	System.out.println(portal1.getStageLength(0));
		// }catch(IDNotRecognisedException e){
		// 	System.out.println("current 2");
		// }


		
	
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
		
	// try{
	// 	portal1.removeStageById(0);
	// 	System.out.println("IT WORKED I PROMISE");
	// } catch(IDNotRecognisedException e){
	// 	System.out.println("ID not working :()");
	// }

	// try{
	// 	System.out.println(Arrays.toString(portal1.getStageCheckpoints(0)));
	// } catch(IDNotRecognisedException e){
	// 	System.out.println("Nope, im broken");
	// }
	// try{
	// 	portal1.removeCheckpoint(0);
	// } catch(IDNotRecognisedException e){
	// 	System.out.println("invalid ID");
	// }catch(InvalidStageStateException e){
	// 	System.out.println(" Ibalid stage state");
	// }

	// try{
	// 	System.out.println(Arrays.toString(portal1.getStageCheckpoints(0)));
	// } catch(IDNotRecognisedException e){
	// 	System.out.println("Nope, im broken");
	// }

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

		// try{
		// 	portal1.removeTeam(3);
		// }catch (IDNotRecognisedException e){
		// 	System.out.println("team");
		// }
		
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
    	portal1.saveCyclingPortal("file1.ser");
		} catch (IOException e) {
			System.out.println("oh no");;
			}
	portal1.eraseCyclingPortal();
	try{
		portal1.loadCyclingPortal("file1.ser");
		} catch (Exception e) {
			System.out.println("bad luck");;
			}
			
	portal1.getRaceIds();
		try {
			System.out.println(portal1.viewRaceDetails(0));
		} catch (IDNotRecognisedException e) {
			System.out.println("whyyy ");
		}
		try {
			portal1.addStageToRace(0,"stage1", "hills",13.0d, LocalDateTime.now() ,StageType.FLAT);
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
			System.out.println("whyyy ");
		}
	// try{
	// 	LocalTime[] stagesInRace = portal1.getRiderResultsInStage(0,1);
	// 	for (LocalTime stage : stagesInRace) {
	// 		System.out.println(stage);
	// 	}
	// }catch(IDNotRecognisedException e){
	// 	System.out.println("current");
	// }

	// try{
	// 	portal1.deleteRiderResultsInStage(0, 1);
	// }catch(IDNotRecognisedException e){
	// 	System.out.println("current");
	// }
	try {
		System.out.println("Checkpoints: " + Arrays.toString(portal1.getStageCheckpoints(0)));
	} catch (IDNotRecognisedException e) {
		System.out.println("Another error, I'm dying");
	}

	try{
		LocalTime[] times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(10), LocalTime.now().plusMinutes(12), LocalTime.now().plusHours(2)  };
		portal1.registerRiderResultsInStage(0,1,times);
		System.out.println("Registered Results for stage 0, rider 1");
	}catch(IDNotRecognisedException e){
		System.out.println("ID broke in register");
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
		LocalTime[] times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusSeconds(5), LocalTime.now().plusMinutes(12), LocalTime.now().plusHours(2).plusMinutes(7)  };
		portal1.registerRiderResultsInStage(0,0,times);
		System.out.println("Registered Results for stage 0, rider 0");
	}catch(IDNotRecognisedException e){
		System.out.println("ID broke in register");
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
		LocalTime[] times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusSeconds(12), LocalTime.now().plusMinutes(34), LocalTime.now().plusHours(2).plusMinutes(5).plusSeconds(1)  };
		portal1.registerRiderResultsInStage(0,1,times);
		System.out.println("Registered Results for stage 0, rider 1");
	}catch(IDNotRecognisedException e){
		System.out.println("ID broke in register");
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
		LocalTime[] times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusSeconds(7), LocalTime.now().plusMinutes(12), LocalTime.now().plusHours(2).plusMinutes(5).plusNanos(100000000)  };
		portal1.registerRiderResultsInStage(0,2,times);
		System.out.println("Registered Results for stage 0, rider 2");
	}catch(IDNotRecognisedException e){
		System.out.println("ID broke in register");
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


	try {
		System.out.println(Arrays.toString(portal1.getRiderResultsInStage(0,0)));
		System.out.println(Arrays.toString(portal1.getRiderResultsInStage(0,1)));
		System.out.println(Arrays.toString(portal1.getRiderResultsInStage(0,2)));
	} catch (IDNotRecognisedException e) {
		System.out.println("ID broke in get results");
	}

	System.out.println("========");

	try {
		System.out.println(Arrays.toString(portal1.getRidersRankInStage(0)));
	} catch (IDNotRecognisedException e) {
		System.out.println("ID wrong, sucker");
	}

	try {
		System.out.println(Arrays.toString(portal1.getRankedAdjustedElapsedTimesInStage(0)));
	} catch (IDNotRecognisedException e) {
		System.out.println("ID wrong, sucker");
	}
	System.out.println("========");

	try {
		System.out.println(portal1.getRiderAdjustedElapsedTimeInStage(0, 0));

	} catch (IDNotRecognisedException e) {
		System.out.println("ID wrong in adjusted");	
	}
	try {
		portal1.removeRaceByName("name");
		System.out.println("testing");
	} catch(NameNotRecognisedException e){
		System.out.println("name doesnt work");
	}
	// does this work 
	try {
		System.out.println("Checkpoints: (should be empty) " + Arrays.toString(portal1.getStageCheckpoints(0)));
	} catch (IDNotRecognisedException e) {
		System.out.println("NO");
	}
	// 	assert (portal1.getTeams().length == 1)
	// 			: "Portal1 should have one team.";

		// assert (portal2.getTeams().length == 1)
		// 		: "Portal2 should have one team.";

	perfectPortal();
}

/**
 * Perfect Portal creates a base portal that contains:
 *races:0
 *stages:0, 1, 2, 3(TT)
checkpoints
0: 0,1,2
1:0,1,2
2:0,1
3: None (TT)

people:
6 people in team 0
2 people in team 1
2 people in team 2
 */
	public static void perfectPortal(){		
		System.out.println("PERFECT PORTAL RUNNING \n");
		
		CyclingPortal portal = new CyclingPortalImpl();
		try {
			portal.createRace("a", "full race");
			portal.createRace("b", "empty race");
		} catch (Exception e) {
			System.out.println("race not created");	
		}
		//portal.getRaceIds();
		try{
			portal.addStageToRace(0,"Stage_1", "Flat Stage", 13.0d, LocalDateTime.now(), StageType.FLAT );
			portal.addStageToRace(0,"Stage_2", "Medium Mountain Stage", 13.0d, LocalDateTime.now(), StageType.MEDIUM_MOUNTAIN);
			portal.addStageToRace(0,"Stage_3", "High Mountain Stage", 13.0d, LocalDateTime.now(), StageType.HIGH_MOUNTAIN);
			portal.addStageToRace(0,"Stage_4", "Time Trial Stage", 13.0d, LocalDateTime.now(), StageType.TT);
			// ^ 3 cannot contain any checkpoints as TT 
		} catch(IDNotRecognisedException e){
			System.out.println("Error adding stage to race addding stages");
		}catch(IllegalNameException f){
	
			System.out.println("Error adding stage to race 2");
		} catch(InvalidNameException f){
			System.out.println("Error adding stage to race 3");
		}catch(  InvalidLengthException g) {
			System.out.println("Error adding stage to race 4");
		}

		try {
			//Add 3 categorised climbs to 0
			portal.addCategorizedClimbToStage(0, 1.d, CheckpointType.C1, 3.2d, 1.5d);
			portal.addCategorizedClimbToStage(0, 11.d, CheckpointType.C2, 3.2d, 1.5d);
			portal.addCategorizedClimbToStage(0, 5.d, CheckpointType.C3, 3.2d, 1.5d);
			portal.addCategorizedClimbToStage(1, 5.d, CheckpointType.C2, 3.2d, 1.5d);
			//Add 1 catergoerised climb to 2
			portal.addCategorizedClimbToStage(1, 1.d, CheckpointType.C1, 3.2d, 1.5d);

		} catch (IDNotRecognisedException e) {
			System.out.println("ID isssues add checkpoints ");
		} catch (InvalidLocationException e){
			System.out.println("location issues");
		} catch (InvalidStageStateException e){
			System.out.println("stage state issues");
		} catch (InvalidStageTypeException e){
			System.out.println("stage type issues 1");
		}
		

		try{
			//Add 3 intermediate sprints to 1
			portal.addIntermediateSprintToStage(1, 1.d);
			portal.addIntermediateSprintToStage(2, 5.d);
			portal.addIntermediateSprintToStage(2, 11.d);
			portal.addIntermediateSprintToStage(2, 1.d);
			//Add 1 to 2
			
		} catch (IDNotRecognisedException e) {
			System.out.println("ID isssues add checkpoints 2");
		} catch (InvalidLocationException e){
			System.out.println("location issues");
		} catch (InvalidStageStateException e){
			System.out.println("stage state issues");
		} catch (InvalidStageTypeException e){
			System.out.println("stage type issues 1");
		}
		try{
		System.out.println(Arrays.toString(portal.getRaceStages(0)));
		} catch (IDNotRecognisedException e){
			System.out.println("oops");
		}

		try {
			portal.concludeStagePreparation(0);
			portal.concludeStagePreparation(1);
			portal.concludeStagePreparation(2);
			portal.concludeStagePreparation(3);
		} catch (IDNotRecognisedException e) {
			System.out.println("conclude stage 1");
		} catch(InvalidStageStateException e){
			System.out.println("conclude stage 2");
		}

		//People stuff
		try {
			portal.createTeam("first", "description");
			portal.createTeam("second", "description");
			portal.createTeam("third", "description");
			System.out.println("created teams");
		} catch (IllegalNameException e) {
			System.out.println("issue with teams 1");		
		} catch (InvalidNameException e) {
			System.out.println("issue with teams 2");		
		}

		//System.out.println(portal.getTeams());
		try {
			portal.createRider(0, "a", 2004);
			portal.createRider(0, "b", 2004);
			portal.createRider(0, "c", 2004);
			portal.createRider(0, "d", 2004);
			portal.createRider(0, "e", 2004);
			portal.createRider(0, "f", 2004);
			portal.createRider(1, "Dave", 2004);
			portal.createRider(1, "Jim", 2004);
			portal.createRider(2, "Dave", 2004);
			portal.createRider(2, "Jim", 2004);
		} catch (IDNotRecognisedException e) {
			System.out.println("ID wrong here");
		} catch (IllegalArgumentException e){
			System.out.println("Name or year wrong");
		}
		 
		try{
			LocalTime[] times;
			//stage 0
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusSeconds(10),LocalTime.now().plusSeconds(20), LocalTime.now().plusSeconds(40), LocalTime.now().plusSeconds(50)};
			portal.registerRiderResultsInStage(0,0,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusSeconds(5),LocalTime.now().plusSeconds(12), LocalTime.now().plusSeconds(14), LocalTime.now().plusSeconds(15)};
			portal.registerRiderResultsInStage(0,1,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(8).plusSeconds(5),LocalTime.now().plusMinutes(9).plusSeconds(45),LocalTime.now().plusMinutes(10).plusSeconds(55), LocalTime.now().plusMinutes(20)};
			portal.registerRiderResultsInStage(0,2,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(4).plusSeconds(5), LocalTime.now().plusMinutes(6).plusSeconds(5), LocalTime.now().plusMinutes(12).plusSeconds(5), LocalTime.now().plusMinutes(14).plusSeconds(1)};
			portal.registerRiderResultsInStage(0,3,times);
			times = new LocalTime[]{LocalTime.now(),  LocalTime.now().plusMinutes(1).plusSeconds(5), LocalTime.now().plusMinutes(3).plusSeconds(5), LocalTime.now().plusMinutes(13).plusSeconds(5),LocalTime.now().plusMinutes(15).plusSeconds(1).plusNanos(100000)};
			portal.registerRiderResultsInStage(0,4,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(3).plusSeconds(5), LocalTime.now().plusMinutes(4).plusSeconds(50), LocalTime.now().plusMinutes(14).plusSeconds(5), LocalTime.now().plusMinutes(18)};
			portal.registerRiderResultsInStage(0,5,times);
			times = new LocalTime[]{LocalTime.now(),  LocalTime.now().plusMinutes(2).plusSeconds(6), LocalTime.now().plusMinutes(5).plusSeconds(20), LocalTime.now().plusMinutes(8).plusSeconds(14),LocalTime.now().plusMinutes(16)};
			portal.registerRiderResultsInStage(0,6,times);
			
			// stage 1 
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusSeconds(10),LocalTime.now().plusSeconds(20), LocalTime.now().plusSeconds(40), LocalTime.now().plusSeconds(50)};
			portal.registerRiderResultsInStage(1,0,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusSeconds(5),LocalTime.now().plusSeconds(12), LocalTime.now().plusSeconds(14), LocalTime.now().plusSeconds(15)};
			portal.registerRiderResultsInStage(1,1,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(8).plusSeconds(5),LocalTime.now().plusMinutes(9).plusSeconds(45),LocalTime.now().plusMinutes(10).plusSeconds(55), LocalTime.now().plusMinutes(20)};
			portal.registerRiderResultsInStage(1,2,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(4).plusSeconds(5), LocalTime.now().plusMinutes(6).plusSeconds(5), LocalTime.now().plusMinutes(12).plusSeconds(5), LocalTime.now().plusMinutes(14).plusSeconds(1)};
			portal.registerRiderResultsInStage(1,3,times);
			times = new LocalTime[]{LocalTime.now(),  LocalTime.now().plusMinutes(1).plusSeconds(5), LocalTime.now().plusMinutes(3).plusSeconds(5), LocalTime.now().plusMinutes(13).plusSeconds(5),LocalTime.now().plusMinutes(15).plusSeconds(1).plusNanos(100000)};
			portal.registerRiderResultsInStage(1,4,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(3).plusSeconds(5), LocalTime.now().plusMinutes(4).plusSeconds(50), LocalTime.now().plusMinutes(14).plusSeconds(5), LocalTime.now().plusMinutes(18)};
			portal.registerRiderResultsInStage(1,5,times);
			times = new LocalTime[]{LocalTime.now(),  LocalTime.now().plusMinutes(2).plusSeconds(6), LocalTime.now().plusMinutes(5).plusSeconds(20), LocalTime.now().plusMinutes(8).plusSeconds(14),LocalTime.now().plusMinutes(16)};
			portal.registerRiderResultsInStage(1,6,times);

			// stage 2 
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusSeconds(10),LocalTime.now().plusSeconds(20), LocalTime.now().plusSeconds(40), LocalTime.now().plusSeconds(50)};
			portal.registerRiderResultsInStage(2,0,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusSeconds(5),LocalTime.now().plusSeconds(12), LocalTime.now().plusSeconds(14), LocalTime.now().plusSeconds(15)};
			portal.registerRiderResultsInStage(2,1,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(8).plusSeconds(5),LocalTime.now().plusMinutes(9).plusSeconds(45),LocalTime.now().plusMinutes(10).plusSeconds(55), LocalTime.now().plusMinutes(20)};
			portal.registerRiderResultsInStage(2,2,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(4).plusSeconds(5), LocalTime.now().plusMinutes(6).plusSeconds(5), LocalTime.now().plusMinutes(12).plusSeconds(5), LocalTime.now().plusMinutes(14).plusSeconds(1)};
			portal.registerRiderResultsInStage(2,3,times);
			times = new LocalTime[]{LocalTime.now(),  LocalTime.now().plusMinutes(1).plusSeconds(5), LocalTime.now().plusMinutes(3).plusSeconds(5), LocalTime.now().plusMinutes(13).plusSeconds(5),LocalTime.now().plusMinutes(15).plusSeconds(1).plusNanos(100000)};
			portal.registerRiderResultsInStage(2,4,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(3).plusSeconds(5), LocalTime.now().plusMinutes(4).plusSeconds(50), LocalTime.now().plusMinutes(14).plusSeconds(5), LocalTime.now().plusMinutes(18)};
			portal.registerRiderResultsInStage(2,5,times);
			times = new LocalTime[]{LocalTime.now(),  LocalTime.now().plusMinutes(2).plusSeconds(6), LocalTime.now().plusMinutes(5).plusSeconds(20), LocalTime.now().plusMinutes(8).plusSeconds(14),LocalTime.now().plusMinutes(16)};
			portal.registerRiderResultsInStage(2,6,times);

			//stage 3
			times = new LocalTime[]{LocalTime.now(),LocalTime.now().plusSeconds(50)};
			portal.registerRiderResultsInStage(3,0,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusSeconds(5)};
			portal.registerRiderResultsInStage(3,1,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(9)};
			portal.registerRiderResultsInStage(3,2,times);
			times = new LocalTime[]{LocalTime.now(),LocalTime.now().plusMinutes(19)};
			portal.registerRiderResultsInStage(3,3,times);
			times = new LocalTime[]{LocalTime.now(),LocalTime.now().plusMinutes(15).plusSeconds(1)};
			portal.registerRiderResultsInStage(3,4,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(18)};
			portal.registerRiderResultsInStage(3,5,times);
			times = new LocalTime[]{LocalTime.now(), LocalTime.now().plusMinutes(16)};
			portal.registerRiderResultsInStage(3,6,times);

		}catch(IDNotRecognisedException e){
			System.out.println("register results");
		}catch(DuplicatedResultException e){
			System.out.println("register results");
		}catch(InvalidStageStateException e){
			System.out.println("register results");
		}catch(InvalidCheckpointTimesException e){ 
			System.out.println("register results");
		}
		
		try{
				System.out.println("1" +Arrays.toString(portal.getStageCheckpoints(0)));
				System.out.println("2" + Arrays.toString(portal.getStageCheckpoints(1)));
				System.out.println("3" +Arrays.toString(portal.getStageCheckpoints(2)));
			} catch(IDNotRecognisedException e){
				System.out.println("Nope, I'm broken");
			}
		try {
			System.out.println("mountain points for stage 0 are (should be empty array) " + Arrays.toString(portal.getRidersMountainPointsInStage(0)));
			System.out.println("mountain points for stage 1 are  " + Arrays.toString(portal.getRidersMountainPointsInStage(1)));
			System.out.println("mountain points for stage 2 are " + Arrays.toString(portal.getRidersMountainPointsInStage(2)));
			//System.out.println("mountain points for stage 3 are " + Arrays.toString(portal.getRidersMountainPointsInStage(3)));
		} catch(IDNotRecognisedException e){
			System.out.println("ahh");
		}
		// gives empty array ! but also for stage 1 and 2 which is bad
		
		try {
			System.out.println("races stages are :  " + portal.getNumberOfStages(0));
		} catch(IDNotRecognisedException e){
			System.out.println("ahh");
		}
		try {
			System.out.println("races " + Arrays.toString(portal.getRidersMountainPointsInRace(0)));
		} catch(IDNotRecognisedException e){
			System.out.println("ahh");
		}
		// gives empty array !
		try {
			System.out.println("rider points " + Arrays.toString(portal.getRidersPointsInRace(0)));
			} catch(IDNotRecognisedException e){
				System.out.println("ahh");
		}
		// gives empty array !

		try {
			for (int i = 0; i < 6; i++){
				System.out.println(i + ": " + portal.getRiderAdjustedElapsedTimeInStage(0, i));
			}
		} catch (Exception e) {
			System.out.println("Broken");
		}

		System.out.println("\n");
		try{
			LocalTime[] orderedTimes = portal.getGeneralClassificationTimesInRace(0);
			int[] orderedId = portal.getRidersGeneralClassificationRank(0);
			for(int i = 0; i < orderedId.length; i++){
				System.out.println(orderedId[i] + ": " + orderedTimes[i]);
			}
		} catch(IDNotRecognisedException e){
			System.out.println("Rank broke 1");
		}
		try{
			System.out.println(" points  in race" +Arrays.toString(portal.getRidersPointsInRace(0)));
		} catch(IDNotRecognisedException e){
			System.out.println("Rank broke 2");
		}
		// gives empty array !
		try{
			System.out.println("mountain points in race" +Arrays.toString(portal.getRidersMountainPointsInRace(0)));
		} catch(IDNotRecognisedException e){
			System.out.println("Rank broke 3");
		}
		// gives empty array !
		try{
			System.out.println("ranked mountain points " +Arrays.toString(portal.getRidersMountainPointClassificationRank(0)));
		} catch(IDNotRecognisedException e){
			System.out.println("Rank broke 2");
		}
		// gives empty array !
		try{
			System.out.println("ranked points " +Arrays.toString(portal.getRidersPointClassificationRank(0)));
		} catch(IDNotRecognisedException e){
			System.out.println("Rank broke 3");
		}
		// gives empty array !
		try{
			System.out.println(portal.getNumberOfStages(0));
		} catch(IDNotRecognisedException e){
			System.out.println("stages wrong");
		}
		try{
			System.out.println("ranked points " +Arrays.toString(portal.getRidersRankInStage(0)));
		} catch(IDNotRecognisedException e){
			System.out.println("Rank broke 3");
		}
		try{
			System.out.println("ranked points " +Arrays.toString(portal.getRankedAdjustedElapsedTimesInStage(0)));
		} catch(IDNotRecognisedException e){
			System.out.println("Rank broke 3");
		}
		try{
			System.out.println("final" + portal.getRiderAdjustedElapsedTimeInStage(0,1));
		} catch(IDNotRecognisedException e){
			System.out.println("Rank broke 3");
		}
		try{
			System.out.println("final" + Arrays.toString(portal.getRiderResultsInStage(0,1)));
		} catch(IDNotRecognisedException e){
			System.out.println("Rank broke 3");
		}
		try{
			System.out.println("final" + Arrays.toString(portal.getRaceStages(1)));
		} catch(IDNotRecognisedException e){
			System.out.println("Rank broke 3");
		}


		System.out.println("\n PERFECT PORTAL COMPLETE");
	}
}
	
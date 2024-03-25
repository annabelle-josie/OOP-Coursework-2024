package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
	
/**
 * CyclingPortalImpl is the implimentation of the CyclingPortal interface.
 * @author Amy Lewis and Annabelle Ronald
 * @version 3.0
 *
 */
public class CyclingPortalImpl implements CyclingPortal {
	ArrayList<Races> listOfRaces = new ArrayList<Races>();
	ArrayList<Stages> listOfStages = new ArrayList<Stages>();
	ArrayList<Teams> listOfTeams = new ArrayList<Teams>();
	ArrayList<Riders> listOfRiders = new ArrayList<Riders>();
	ArrayList<Checkpoints> listOfCheckpoints = new ArrayList<Checkpoints>();

	@Override
	public int[] getRaceIds() {
		int[] raceIdlist = new int[listOfRaces.size()];
		int count = 0;
		for (Races race : listOfRaces) {
			raceIdlist[count] = race.getRaceID();
			count++;
		}

		for (int id : raceIdlist) {
			System.out.println(id);
		}

		return raceIdlist;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		if(name == "" || name == null || name.contains(" ")|| name.length() > 30){
			throw new InvalidNameException();
		}
		for (Races races : listOfRaces) {
			if (name == races.getName())
				throw new IllegalNameException();
			}	

		listOfRaces.add(new Races(name, description));
		int raceID = listOfRaces.get(listOfRaces.size() -1).getRaceID();

		return raceID;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		String s = "hi";
		for (Races races : listOfRaces) {
			System.out.println(races.getRaceID());
			if (raceId == races.getRaceID()){
				s = races.raceasstring();
				return s;
			}
		}		
		throw new IDNotRecognisedException();
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		Boolean found = false;
		int count = 0;
		int[] stages = new int[]{};
		//TODO
		while (!found && count < listOfRaces.size()){
			if (raceId == listOfRaces.get(count).getRaceID()){
				stages = listOfRaces.get(count).getStages();
				listOfRaces.remove(listOfRaces.get(count));
				found = true;
			}
			count++;	
		}
			
		for(int stage : stages){
				removeStageById(stage);	
			}
			
		
		if(!found){
			throw new IDNotRecognisedException();
		}
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		int count = 0;
		boolean found = false;
		for (Stages stage : listOfStages) {
			if (raceId == stage.getRaceID())
				count += 1;
				found =true;
		}
		if (!found) {
			throw new IDNotRecognisedException();
		}
		return count;
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
				Boolean found = false;
				for (Races race : listOfRaces) {
					if(race.getRaceID() == raceId){
						found = true;
					}
				}
				if(found == false){
					throw new IDNotRecognisedException();
				}

				if(stageName == "" || stageName == null || stageName.contains(" ") || stageName.length() > 30){
					throw new InvalidNameException();
				}
				for (Stages stage : listOfStages) {
					if (stageName == stage.getstageName())
						throw new IllegalNameException();
					}	
				if(length < 5 | length == 0.0d){
					throw new InvalidLengthException();
				}
				listOfStages.add(new Stages(raceId,stageName, description,length, startTime,type));
				int stageID = listOfStages.get(listOfStages.size() -1).getStageID();
				for (Races race : listOfRaces) {
					if (raceId == race.getRaceID())
						race.setStages(stageID);
						race.setLength(length);
				}		
		return stageID;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		for (Races races : listOfRaces) {
			if (raceId == races.getRaceID()){
				return races.getStages();
			}
		}	
		throw new IDNotRecognisedException();
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		double length = 0.0d;
		for (Stages stage : listOfStages) {
			if (stageId == stage.getStageID()){
				length = stage.getlength();
				return length;
			}
		}
		throw new IDNotRecognisedException();
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		Boolean found = false;
		Boolean foundCheckpoint = false;
		int count = 0;
		int[] checkpoints = new int[]{};

		for (Races races : listOfRaces) {
			for(int stages : races.getStages()){
				if (stages == stageId){
					races.removeStage(stageId);
					
				}
			}
		}
		while (!found && count < listOfStages.size()){
			if (stageId == listOfStages.get(count).getStageID()){
				checkpoints = listOfStages.get(count).getCheckpoints();
				listOfStages.remove(listOfStages.get(count));
				System.out.println(Arrays.toString(checkpoints));
				found = true;
				}
			count++;
			}	

		for(int checkpoint : checkpoints){
			int countCheckpoint = 0;
			while (!foundCheckpoint && countCheckpoint < listOfCheckpoints.size()){
				if (checkpoint == listOfCheckpoints.get(countCheckpoint).getCheckpointID()){
					System.out.println(listOfCheckpoints.get(countCheckpoint).getCheckpointID());
					listOfCheckpoints.remove(listOfCheckpoints.get(countCheckpoint));
					foundCheckpoint = true;
				}
				countCheckpoint++;
			}
			
			}
		
		if(!found){
			throw new IDNotRecognisedException();
		}
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
				Boolean found = false;
				double maxlength = 0.0d;
				StageType stagetype = null;
				String stageState = null;
				for (Stages stage: listOfStages) {
					if(stage.getStageID() == stageId){
						found = true;
						maxlength = stage.getlength();
						stageState = stage.getStageState();
						stagetype = stage.getType();

					}
				}
				if(found == false){
					throw new IDNotRecognisedException();
				}
				if (stageState.equals("waiting for results")){
					throw new InvalidStageStateException();
				}	

				if(location > maxlength){
					throw new InvalidLocationException();
				}
				if (stagetype == StageType.TT){
					throw new InvalidStageTypeException();
				}
				listOfCheckpoints.add(new Checkpoints(stageId, location, type,averageGradient,length));
				int checkpointID = listOfCheckpoints.get(listOfCheckpoints.size() -1).getCheckpointID();
				for (Stages stage : listOfStages) {
					if (stageId == stage.getStageID())
						stage.addCheckpoint(checkpointID);
				}		
		return checkpointID;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
				Boolean found = false;
				double maxlength = 0.0d;
				StageType stagetype = null;
				String stageState = null;
				for (Stages stage: listOfStages) {
					if(stage.getStageID() == stageId){
						found = true;
						maxlength = stage.getlength();
						stageState = stage.getStageState();
						stagetype = stage.getType();

					}
				}
				if(found == false){
					throw new IDNotRecognisedException();
				}
				if (stageState.equals("waiting for results")){
					throw new InvalidStageStateException();
				}	

				if(location > maxlength){
					throw new InvalidLocationException();
				}
				if (stagetype == StageType.TT){
					throw new InvalidStageTypeException();
				}
				listOfCheckpoints.add(new Checkpoints(stageId, location));
				int checkpointID = listOfCheckpoints.get(listOfCheckpoints.size() -1).getCheckpointID();
				for (Stages stage : listOfStages) {
					if (stageId == stage.getStageID())
						stage.addCheckpoint(checkpointID);
				}		
		return checkpointID;
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		Boolean found = false;
		int count = 0;
		for (Stages stage : listOfStages) {
			for(int checkpoint : stage.getCheckpoints()){
				if (checkpoint == checkpointId){
					stage.removeCheckpoint(checkpointId);
				}
			}
		}
		while (!found && count < listOfCheckpoints.size()){
			if (checkpointId == listOfCheckpoints.get(count).getCheckpointID()){
				System.out.println(listOfCheckpoints.get(count).getCheckpointID());
				listOfCheckpoints.remove(listOfCheckpoints.get(count));
				found = true;
			}
			count++;	
		}
		if(!found){
			throw new IDNotRecognisedException();
		}

	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		Boolean found = false;
		for (Stages stages : listOfStages) {
			if (stageId == stages.getStageID()){
				found = true;
				if (stages.getStageState().equals("waiting for results")){
					throw new InvalidStageStateException();
				} else{
					stages.setStageState();
				}
			}
		}
		if (!found){
			throw new IDNotRecognisedException();
		}
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		// TODO checkpoints ordered by location in stage
		for (Stages stages : listOfStages) {
			if (stageId == stages.getStageID()){
				return stages.getCheckpoints();
			}
		}	
		throw new IDNotRecognisedException();
		}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		if(name == "" || name == null || name.contains(" ") || name.length() > 30){
			throw new InvalidNameException();
		}
		for (Teams teams : listOfTeams) {
			if (name == teams.getName())
				throw new IllegalNameException();
		}	
		
		listOfTeams.add(new Teams(name, description));
		
		return listOfTeams.get(listOfTeams.size()-1).getTeamID();
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		Boolean found = false;
		int count = 0;
		while (!found && count < listOfTeams.size()){
			if (teamId == listOfTeams.get(count).getTeamID()){
				listOfTeams.remove(listOfTeams.get(count));
				found = true;
			}
			count++;	
		}
		if(!found){
			throw new IDNotRecognisedException();
		}
				
	}

	@Override
	public int[] getTeams() {
		int[] teamIdlist = new int[listOfTeams.size()];
		int count = 0;
		for (Teams teams : listOfTeams) {
			teamIdlist[count] = teams.getTeamID();
			count++;
		}

		for (int id : teamIdlist) {
			System.out.println(id);
		}

		return teamIdlist;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		for (Teams teams : listOfTeams) {
			if (teamId == teams.getTeamID()){
				return teams.getRiders();
			}
		}	
		throw new IDNotRecognisedException();
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
				if(name == "" || name == null || yearOfBirth < 1900){
					throw new IllegalArgumentException();
				}
				
				listOfRiders.add(new Riders(name, yearOfBirth));
				int riderID = listOfRiders.get(listOfRiders.size()-1).getRiderID();
				
				Boolean found = false;
				for (Teams team : listOfTeams) {
					if(teamID == team.getTeamID()){
						team.addRider(riderID);
						found = true;
					}
				}
				if (found == false){
					throw new IDNotRecognisedException();
				}
				return riderID;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		//Remove from team and from list
		
		//Remove from team
		for (Teams teams : listOfTeams) {
			for(int riders : teams.getRiders()){
				if (riders == riderId){
					teams.removeRider(riderId);
				}
			}
		}
		//Remove from list of Riders
		Boolean found = false;
		int count = 0;
		while (!found && count < listOfRiders.size()){
			if (riderId == listOfRiders.get(count).getRiderID()){
				
				listOfRiders.remove(listOfRiders.get(count));
				found = true;
			}
			count++;
		}
		if(!found){
			throw new IDNotRecognisedException();
		}
	}

	@Override
	//Record the times of a rider in a stage.
	/** @param checkpointTimes An array of times at which the rider reached each of the
	 *                    checkpoints of the stage, including the start time and the
	 *                    finish line.
	 * @throws DuplicatedResultException   Thrown if the rider has already a result
	 *                                     for the stage. Each rider can have only
	 *                                     one result per stage.
	 */
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException{
		boolean riderFound = false;
		boolean stageFound = false;
		int numberOfCheckpoints = 0;
		for (Riders rider: listOfRiders){
			if (riderId == rider.getRiderID())
				riderFound = true;
		}	
		for (Stages stage : listOfStages){
			if (stageId == stage.getStageID() && riderFound){
				stageFound = true;
				numberOfCheckpoints = (stage.getCheckpoints()).length;
				if (!(stage.getStageState() == "waiting for results")){
					throw new InvalidStageStateException();
				} else {
		// if rider times is not empty execption else add the times 
					stage.setResults(riderId, checkpoints);
				}
			}
		}

		if (!riderFound ){
			System.out.println("Rider not found");
			throw new IDNotRecognisedException();
		}
		else if (!stageFound){
			System.out.println("Stage not found");
			throw new IDNotRecognisedException();
		}
		if((numberOfCheckpoints+2) != checkpoints.length){
			throw new InvalidCheckpointTimesException();
		}

		
	}
			

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		for (Stages stage : listOfStages){
			if (stageId == stage.getStageID()){
				return(stage.getRiderTimes(riderId));
			}
		}
		throw new IDNotRecognisedException();
		
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		//Go through to get full time by last-first for all riders
		//add to list
		//order list
		//find the adjustemnt (ew)
		
		for(Stages stage : listOfStages){
			if (stageId == stage.getStageID()){
				stage.realElapsedTime(riderId);
			}
		}

		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// create as a method in stage and remove rider results for that rider in the rider resutls variable
		for (Stages stage : listOfStages) {
			if (stageId == stage.getStageID() ){
					stage.removeRider(riderId);
				}
			}
		}
		
	/**
	 * Get the riders finished position in a a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return A list of riders ID sorted by their elapsed time. An empty list if
	 *         there is no result for the stage.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */	
	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		//TODO ; Auto-generated method stub
		return null;
	}
	/**
	 * Get the adjusted elapsed times of riders in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any exceptions are
	 * thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of adjusted elapsed times sorted by their finish
	 *         time. An empty list if there is no result for the stage. These times
	 *         should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}. Assume the total elapsed time of
	 *         in a stage never exceeds 24h and, therefore, can be represented by a
	 *         LocalTime variable. There is no need to check for this condition or
	 *         raise any exception.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Get the number of points obtained by each rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of points each riders received in the stage, sorted
	 *         by their elapsed time. An empty list if there is no result for the
	 *         stage. These points should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

		/**
	 * Get the number of mountain points obtained by each rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of mountain points each riders received in the stage,
	 *         sorted by their finish time. An empty list if there is no result for
	 *         the stage. These points should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// creating a stream and write all the information to a file 
		// TODO Auto-generated method stub

	}
/**
	 * Method saves this MiniCyclingPortal contents into a serialised file,
	 * with the filename given in the argument.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 *
	 * @param filename Location of the file to be saved.
	 * @throws IOException If there is a problem experienced when trying to save the
	 *                     store contents to the file.
	 */
	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}
/**
	 * Method should load and replace this MiniCyclingPortal contents with the
	 * serialised contents stored in the file given in the argument.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 *
	 * @param filename Location of the file to be loaded.
	 * @throws IOException            If there is a problem experienced when trying
	 *                                to load the store contents from the file.
	 * @throws ClassNotFoundException If required class files cannot be found when
	 *                                loading.
	 */
	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		Boolean found = false;
		Boolean yes = false;
		int count = 0;
		int[] stages = new int[]{};
		while (!found && count < listOfRaces.size()){
			if (name == listOfRaces.get(count).getName()){
				stages = listOfRaces.get(count).getStages();
				listOfRaces.remove(listOfRaces.get(count));
				found = true;
			}
			count++;	
		}
		for(int stage : stages){
			removeStageById(stage);	
		
		}
		
		if(!found){
			throw new NameNotRecognisedException();
		}

	}
	/**
	 * Get the general classification times of riders in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any exceptions are
	 * thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A list of riders' times sorted by the sum of their adjusted elapsed
	 *         times in all stages of the race. An empty list if there is no result
	 *         for any stage in the race. These times should match the riders
	 *         returned by {@link #getRidersGeneralClassificationRank(int)}. Assume
	 *         the total elapsed time of a race (the sum of all of its stages) never
	 *         exceeds 24h and, therefore, can be represented by a LocalTime
	 *         variable. There is no need to check for this condition or raise any
	 *         exception.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}
/**
	 * Get the overall points of riders in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return An array of riders' points (i.e., the sum of their points in all stages
	 *         of the race), sorted by the total adjusted elapsed time. An empty array if
	 *         there is no result for any stage in the race. These points should
	 *         match the riders returned by {@link #getRidersGeneralClassificationRank(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}
/**
	 * Get the overall mountain points of riders in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return An array of riders' mountain points (i.e., the sum of their mountain
	 *         points in all stages of the race), sorted by the total adjusted elapsed time.
	 *         An empty array if there is no result for any stage in the race. These
	 *         points should match the riders returned by
	 *         {@link #getRidersGeneralClassificationRank(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Get the general classification rank of riders in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders' IDs sorted ascending by the sum of their
	 *         adjusted elapsed times in all stages of the race. That is, the first
	 *         in this list is the winner (least time). An empty list if there is no
	 *         result for any stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	*/

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}
/**
	 * Get the ranked list of riders based on the points classification in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders' IDs sorted descending by the sum of their
	 *         points in all stages of the race. That is, the first in this list is
	 *         the winner (more points). An empty list if there is no result for any
	 *         stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}
/**
	 * Get the ranked list of riders based on the mountain classification in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders' IDs sorted descending by the sum of their
	 *         mountain points in all stages of the race. That is, the first in this
	 *         list is the winner (more points). An empty list if there is no result
	 *         for any stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

}

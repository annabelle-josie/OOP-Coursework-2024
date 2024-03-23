package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
	
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
		//TODO: add character limit check
		if(name == "" || name == null || name.contains(" ")){
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
		while (!found && count < listOfRaces.size()){
			if (raceId == listOfRaces.get(count).getRaceID()){
				listOfRaces.remove(listOfRaces.get(count));
				found = true;
			}
			count++;	
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

				if(stageName == "" || stageName == null || stageName.contains(" ")){
					throw new InvalidNameException();
				}
				for (Stages stage : listOfStages) {
					if (stageName == stage.getstageName())
						throw new IllegalNameException();
					}	
				//TODO: Add a check to see if length is null (can a double be null?)
				if(length < 5 ){
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
		int count = 0;

		for (Races races : listOfRaces) {
			for(int stages : races.getStages()){
				if (stages == stageId){
					races.removeStage(stageId);
				}
			}
		}
		while (!found && count < listOfStages.size()){
			if (stageId == listOfStages.get(count).getStageID()){
				
				listOfStages.remove(listOfStages.get(count));
				found = true;
			}
			count++;
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
		for (Stages stages : listOfStages) {
			if (stageId == stages.getStageID()){
				return stages.getCheckpoints();
			}
		}	
		throw new IDNotRecognisedException();
		}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		if(name == "" || name == null || name.contains(" ")){
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

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

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		Boolean found = false;
		int count = 0;
		while (!found && count < listOfRaces.size()){
			if (name == listOfRaces.get(count).getName()){
				listOfRaces.remove(listOfRaces.get(count));
				found = true;
			}
			count++;	
		}
		if(!found){
			throw new NameNotRecognisedException();
		}

	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

}

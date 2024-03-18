package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
	
/**
 * BadCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the CyclingPortal interface.
 * @author Diogo Pacheco
 * @version 2.0
 *
 */
public class CyclingPortalImpl implements CyclingPortal {
	ArrayList<Races> listOfRaces = new ArrayList<Races>();
	ArrayList<Stages> listOfStages = new ArrayList<Stages>();
	ArrayList<Teams> listOfTeams = new ArrayList<Teams>();
	ArrayList<Riders> listOfRiders = new ArrayList<Riders>();

	/**
	 * Returns arrray of RaceIDs that are currently in use
	 * @return Integer Array of the RaceIDs
	 */
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

	/**
	 * Creates a new race with given paramenters
	 * @param name						The name of the Race
	 * @param description				The description of the Race
	 * @return raceID of the new Race
	 * @throws IllegalNameException 	If the name is already in use in the system
	 * @throws InvalidNameException		If the name is in an invalid format: either null, empty, too large or containing whitespace
	 */
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

	/**
	 * Returns a String of all race details
	 * @return String of race details in formatt: raceID, name, description, length
	 */
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

	/**
	 * Removes race from active race list
	 * @param raceID 						The integer ID of the race to be removed
	 * @throws IDNotRecognisedException		If the ID entered is not in the list of current races
	 */
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


	/**
	 * Returns the number of stages in a given race
	 * @param raceId	the ID of the race whose stages are being checked
	 * @return Number of stages in race
	 */
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

	/**
	 * Create a new stage and add it to the race
	 * @param raceId						The integer ID of the race to be added to
	 * @param stageName						The name of the stage
	 * @param description					The stage description
	 * @param length						The length of the stage
	 * @param startTime						The start time of the stage
	 * @return StageID of the new stage created
	 * @throws IDNotRecognisedException		If the raceId provided does not match an active race
	 * @throws IllegalNameException			If the name provided already exists as a stage
	 * @throws InvalidNameException			If the name is in an invalid format: either null, empty, too large or containing whitespace
	 * @throws InvalidLengthException		If the race length is null or less than 5
	 */
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

	/**
	 * Returns an array of the IDs of the race stages
	 * @param raceId	The ID of the race whose stages are being selected
	 * @return Integer Array of race stage IDs
	 */
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
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
			System.out.println("count = " + count);
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
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

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

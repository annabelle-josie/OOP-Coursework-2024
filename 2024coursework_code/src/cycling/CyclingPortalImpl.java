package cycling;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Collections;

/**
 * CyclingPortalImpl is the implimentation of the CyclingPortal interface.
 * @author Amy Lewis and Annabelle Ronald
 * @version 3.0
 *
 */
public class CyclingPortalImpl implements CyclingPortal {
	ArrayList<Races> listOfRaces = new ArrayList<>();
	ArrayList<Stages> listOfStages = new ArrayList<>();
	ArrayList<Teams> listOfTeams = new ArrayList<>();
	ArrayList<Riders> listOfRiders = new ArrayList<>();
	ArrayList<Checkpoints> listOfCheckpoints = new ArrayList<>();
	HashMap<Integer, LocalTime> totalRiderTimes = new HashMap<>();

	@Override
	public int[] getRaceIds() {
		int[] raceIdlist = new int[listOfRaces.size()];
		int count = 0;
		for (Races race : listOfRaces) {
			raceIdlist[count] = race.getRaceID();
			count++;
		}

		return raceIdlist;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		if(name == null || name.equals("") || name.contains(" ")|| name.length() > 30){
			assert (name == null) : "Name being null has been caught";
			assert (name.equals("")) : "Name being empty has been caught";
			assert (name.contains(" ")) : "Name containing whitespace has been caught";
			assert (name.length() > 30) : "Name being too long has been caught";
			throw new InvalidNameException();
		}
		for (Races races : listOfRaces) {
			if (name.equals(races.getName())){
				throw new IllegalNameException();
			}
		}

		int nextID;	
		int[] ids = getRaceIds();
		if(ids.length == 0){
			nextID = 0;
		} else{
			nextID = ids[ids.length-1]+1;
		}
		
		listOfRaces.add(new Races(nextID, name, description));
		assert(listOfRaces.contains(listOfRaces.get(nextID))) : "New race cannot be found in listOfRaces";
		return nextID;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		for (Races races : listOfRaces) {
			if (raceId == races.getRaceID()){
				return races.raceAsString();
			}
		}		
		throw new IDNotRecognisedException();
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		boolean found = false;
		int count = 0;
		int[] stages = new int[]{};
		while (!found && count < listOfRaces.size()){
			if (raceId == listOfRaces.get(count).getRaceID()){
				stages = listOfRaces.get(count).getStages();
				listOfRaces.remove(listOfRaces.get(count));
				found = true;
				assert(!listOfRaces.contains(listOfRaces.get(count))) : "Race still found in listOfRaces";
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
		for (Races race : listOfRaces) {
			if (raceId == race.getRaceID())
				return race.getNumberOfStages();
		}
			throw new IDNotRecognisedException();
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		boolean found = false;
		for (Races race : listOfRaces) {
			if(race.getRaceID() == raceId){
				found = true;
			}
		}
		if(!found){
			throw new IDNotRecognisedException();
		}

		if(stageName == null || stageName.equals("") || stageName.contains(" ") || stageName.length() > 30){
			assert (stageName == null) : "Name being null has been caught";
			assert (stageName.equals("")) : "Name being null has been caught";
			assert (stageName.contains(" ")) : "Name containing whitespace has been caught";
			assert (stageName.length() > 30) : "Name being too long has been caught";
			
			throw new InvalidNameException();
		}
		for (Stages stage : listOfStages) {
			if (stageName.equals(stage.getStageName()))
				throw new IllegalNameException();
			}	
		if(length < 5){
			assert (length < 5) : "length being too long has been caught";
			throw new InvalidLengthException();
		}

		int nextID;	
		int[] stageIdlist = new int[listOfStages.size()];
		int count = 0;
		for (Stages stage : listOfStages) {
			stageIdlist[count] = stage.getStageID();
			count++;
		}
		if(stageIdlist.length == 0){
			nextID = 0;
		} else{
			nextID = stageIdlist[stageIdlist.length-1]+1;
		}
		
		listOfStages.add(new Stages(nextID, raceId ,stageName, description,length, startTime,type));
		for (Races race : listOfRaces) {
			if (raceId == race.getRaceID()){
				race.setStages(nextID);
				race.setLength(length);
			}
		}		
		return nextID;
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
		for (Stages stage : listOfStages) {
			if (stageId == stage.getStageID()){
				return stage.getLength();
			}
		}
		throw new IDNotRecognisedException();
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		boolean found = false;
		boolean foundCheckpoint = false;
		int count = 0;
		int[] checkpoints = new int[]{};

		Double lengthToRemove = 0d;
		while (!found && count < listOfStages.size()){
			if (stageId == listOfStages.get(count).getStageID()){
				lengthToRemove = listOfStages.get(count).getLength();
				checkpoints = listOfStages.get(count).getCheckpoints();
				listOfStages.remove(listOfStages.get(count));
				found = true;
			}
			count++;
		}	

		for (Races races : listOfRaces) {
			for(int stages : races.getStages()){
				if (stages == stageId){
					races.removeStage(stageId);
					races.setLength(-(lengthToRemove));
				}
			}
		}

		for(int checkpoint : checkpoints){
			int countCheckpoint = 0;
			while (!foundCheckpoint && countCheckpoint < listOfCheckpoints.size()){
				if (checkpoint == listOfCheckpoints.get(countCheckpoint).getCheckpointID()){
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
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,Double length) 
	throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,InvalidStageTypeException {
		boolean found = false;
		for (Stages stage: listOfStages) {
			if(stage.getStageID() == stageId){
				found = true;
				if (stage.getStageState().equals("waiting for results")){
					throw new InvalidStageStateException();
				}	
				if(location > stage.getLength()){
					throw new InvalidLocationException();
				}
				if (stage.getType() == StageType.TT){
					throw new InvalidStageTypeException();
				}
			}
		}
		if(!found){
			throw new IDNotRecognisedException();
		}
	
		int nextID;	
		int[] checkpointIDList = new int[listOfCheckpoints.size()];
		int count = 0;
		for (Checkpoints checkpoint : listOfCheckpoints) {
			checkpointIDList[count] = checkpoint.getCheckpointID();
			count++;
		}
		if(checkpointIDList.length == 0){
			nextID = 0;
		} else{
			nextID = checkpointIDList[checkpointIDList.length-1]+1;
		}

		listOfCheckpoints.add(new Checkpoints(nextID, stageId, location, type,averageGradient,length));
		for (Stages stage : listOfStages) {
			if (stageId == stage.getStageID())
				stage.addCheckpoint(nextID);
		}		
		return nextID;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, 
	InvalidStageStateException, InvalidStageTypeException {
		boolean found = false;
		for (Stages stage: listOfStages) {
			if(stage.getStageID() == stageId){
				found = true;
				if (stage.getStageState().equals("waiting for results")){
					throw new InvalidStageStateException();
				}	
				if(location > stage.getLength()){
					throw new InvalidLocationException();
				}
				if (stage.getType() == StageType.TT){
					throw new InvalidStageTypeException();
				}
			}
		}
		if(!found){
			throw new IDNotRecognisedException();
		}
				
		int nextID;	
		int[] checkpointIDList = new int[listOfCheckpoints.size()];
		int count = 0;
		for (Checkpoints checkpoint : listOfCheckpoints) {
			checkpointIDList[count] = checkpoint.getCheckpointID();
			count++;
		}
		if(checkpointIDList.length == 0){
			nextID = 0;
		} else{
			nextID = checkpointIDList[checkpointIDList.length-1]+1;
		}
		listOfCheckpoints.add(new Checkpoints(nextID, stageId, location, CheckpointType.SPRINT));
		for (Stages stage : listOfStages) {
			if (stageId == stage.getStageID())
				stage.addCheckpoint(nextID);
		}		
		return nextID;
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		boolean found = false;
		int count = 0;
		for (Stages stage : listOfStages) {
			for(int checkpoint : stage.getCheckpoints()){
				if (checkpoint == checkpointId){
					stage.removeCheckpoint(checkpointId);
					if (stage.getStageState().equals("waiting for results")){
						assert (stage.getStageState().equals("waiting for results")) : "stage state being waiting for results  has been caught";
						throw new InvalidStageStateException();
					}
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
		boolean found = false;
		for (Stages stages : listOfStages) {
			if (stageId == stages.getStageID()){
				found = true;
				if (stages.getStageState().equals("waiting for results")){
					assert (stages.getStageState().equals("waiting for results")) : "stage state being waiting for results  has been caught";
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
				int[] checkpointArray =  stages.getCheckpoints();
				ArrayList<Double> locations = new ArrayList<>();
				HashMap<Double, Integer> locationMap = new HashMap<>();

				for(int id : checkpointArray){
					for (Checkpoints checkpoint : listOfCheckpoints) {
						if (id == checkpoint.getCheckpointID()){
							locationMap.put(checkpoint.getLocation(), checkpoint.getCheckpointID());
							locations.add(checkpoint.getLocation());
						}
					}
				}

				Collections.sort(locations);

				int[] returnCheckpointArray = new int[checkpointArray.length];
				int count = 0;	
				for (Double location: locations){
					returnCheckpointArray[count] = locationMap.get(location);
					count++;
				}

				return returnCheckpointArray;
			}
		}
		throw new IDNotRecognisedException();
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		if(name == null || name.equals("") ||  name.contains(" ") || name.length() > 30){
			throw new InvalidNameException();
		}
		for (Teams teams : listOfTeams) {
			if (name.equals(teams.getName())){
				throw new IllegalNameException();
			}
		}	

		int nextID;	
		int[] teamIDList = new int[listOfTeams.size()];
		int count = 0;
		for (Teams team : listOfTeams) {
			teamIDList[count] = team.getTeamID();
			count++;
		}
		if(teamIDList.length == 0){
			nextID = 0;
		} else{
			nextID = teamIDList[teamIDList.length-1]+1;
		}

		listOfTeams.add(new Teams(nextID, name, description));
		
		return nextID;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		boolean found = false;
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
		if(name == null || name.equals("") ||  yearOfBirth < 1900){
			throw new IllegalArgumentException();
		}

		int nextID;	
		int[] riderIDList = new int[listOfRiders.size()];
		int count = 0;
		for (Riders rider : listOfRiders) {
			riderIDList[count] = rider.getRiderID();
			count++;
		}
		if(riderIDList.length == 0){
			nextID = 0;
		} else{
			nextID = riderIDList[riderIDList.length-1]+1;
		}
				
		listOfRiders.add(new Riders(nextID, name, yearOfBirth));				
		boolean found = false;
		for (Teams team : listOfTeams) {
			if(teamID == team.getTeamID()){
				team.addRider(nextID);
				found = true;
			}
		}
		if (!found){
			throw new IDNotRecognisedException();
		}
		return nextID;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {	
		for (Stages stage : listOfStages) {
			stage.removeRider(riderId);
		}

		//Remove from team
		for (Teams teams : listOfTeams) {
			for(int riders : teams.getRiders()){
				if (riders == riderId){
					teams.removeRider(riderId);
				}
			}
		}
		//Remove from list of Riders
		boolean found = false;
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
				if (!(stage.getStageState().equals("waiting for results"))){
					throw new InvalidStageStateException();
				} else {
		// if rider times is not empty execption else add the times 
					stage.setResults(riderId, checkpoints);
				}
			}
		}

		if (!riderFound || !stageFound ){
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
		for(Stages stage : listOfStages){
			if (stageId == stage.getStageID()){
				stage.calculateAdjustment();
				for(Riders rider : listOfRiders){
					if (riderId == rider.getRiderID()){
						return stage.getAdjustedTime(rider.getRiderID());
					}
				}
			}
		}
		throw new IDNotRecognisedException();
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		for (Stages stage : listOfStages){
			if (stageId == stage.getStageID()){
				boolean riderFound = stage.removeRider(riderId);
				if(!riderFound){
					throw new IDNotRecognisedException();
				}
				break;
			}
		}
		throw new IDNotRecognisedException();
	}
		
	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		for (Stages stage : listOfStages){
			if (stageId == stage.getStageID()){
				return(stage.getRank());
			}
		}
		throw new IDNotRecognisedException();
	}
	
	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		for (Stages stage : listOfStages) {
			if (stageId == stage.getStageID()){
				return(stage.getAdjustedRank());
			}
		}
		throw new IDNotRecognisedException();
	}
	
	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		HashMap<Integer, Integer> pointsInStage = new HashMap<>();
		int[] finished;
		int current = 0;
		for (Stages stage : listOfStages){
			if (stageId == stage.getStageID()){
				stage.calulateRankedCheckpoint();
				int[] checkpoints = stage.getCheckpoints();
				for(int i=0; i < checkpoints.length; i++){
					for (Checkpoints checkpoint :listOfCheckpoints) {
						if (checkpoint.getCheckpointID() == checkpoints[i] && checkpoint.getType() == CheckpointType.SPRINT){
							int[] returnArray = stage.getRankedCheckpoint(checkpoint.getCheckpointID());
							if(returnArray != null){
								int[] pointArray = checkpoint.addIntermidiatePoints(returnArray.length);
								for(int j = 0; j< pointArray.length; j++){
									if(pointsInStage.get(returnArray[j])== null){
										current = 0;
									} else {
										current = pointsInStage.get(returnArray[j]);
										pointsInStage.put(returnArray[j], current + pointArray[j]);
									}
								}	
							}
						}
					}
				}
				int[] rank = stage.getRank();
				finished = new int[rank.length];
				int count = 0;
				int[] stagePoints = stage.calculateStagePoints();
				for (int id : rank) {
					if(pointsInStage.containsKey(rank[id])){
						finished[count] = pointsInStage.get(id);
					}
					count ++;
				}
				int[] finalArray = new int[rank.length];
				for(int i = 0; i<rank.length; i++){
					finalArray[i] = finished[i] + stagePoints[i];
				}
				return finalArray;
			}
		}
		throw new IDNotRecognisedException();
	}


	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		int current = 0;
		HashMap<Integer, Integer> pointsInStage = new HashMap<>();
		int[] finished;
		for (Stages stage : listOfStages){
			if (stageId == stage.getStageID()){
				stage.calulateRankedCheckpoint();
				int[] rank = stage.getRank();
				int[] stageCheckpoints = stage.getCheckpoints();
				for(int i=0; i < stageCheckpoints.length; i++){
					for (Checkpoints checkpoint :listOfCheckpoints) {
						if (checkpoint.getCheckpointID() == stageCheckpoints[i]){
							int[] returnArray = stage.getRankedCheckpoint(checkpoint.getCheckpointID());
							if(returnArray != null){
								int[] pointArray = checkpoint.getMountainPoints(returnArray.length);
								for(int j = 0; j< pointArray.length; j++){
									if(pointsInStage.get(returnArray[j])== null){
										current = 0;
									}else{
										current = pointsInStage.get(returnArray[j]);
									}	
									pointsInStage.put(returnArray[j],current + pointArray[j]);
									
								
							}	
						}	
					}
					}		
							// match the points to the iDS each time and record the id and
						// add points each time for that iD 
				}
				finished = new int[rank.length];
				int count = 0;
				for (int id : rank) {
					if(pointsInStage.containsKey(rank[id])){
						finished[count] = pointsInStage.get(id);
					}
					count ++;
				}
				return finished;
			} 
		}
		throw new IDNotRecognisedException();
	}
		
	

	@Override
	public void eraseCyclingPortal() {
		listOfRaces.clear();
		listOfRiders.clear();
		listOfStages.clear();
		listOfTeams.clear();
		listOfCheckpoints.clear();
		// creating a stream and write all the information to a file 

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		try(FileOutputStream writeData = new FileOutputStream(filename);
    	ObjectOutputStream writeStream = new ObjectOutputStream(writeData);){
    		writeStream.writeObject(listOfRaces);
			writeStream.writeObject(listOfStages);
			writeStream.writeObject(listOfTeams);
			writeStream.writeObject(listOfRiders);
			writeStream.writeObject(listOfCheckpoints);
		}
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		try(	FileInputStream readData = new FileInputStream(filename);
				ObjectInputStream readStream  = new ObjectInputStream(readData); ){
			listOfRaces = (ArrayList<Races>) readStream.readObject();
			listOfStages = (ArrayList<Stages>) readStream.readObject();
			listOfTeams = (ArrayList<Teams>) readStream.readObject();
			listOfRiders = (ArrayList<Riders>) readStream.readObject();
			listOfCheckpoints = (ArrayList<Checkpoints>) readStream.readObject();
		}
	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		boolean foundRace = false;
		int count = 0;
		int[] stages = new int[]{};
		int[] checkpoints = new int[]{};
		while (!foundRace && count < listOfRaces.size()){
			if (name.equals(listOfRaces.get(count).getName())){
				stages = listOfRaces.get(count).getStages();
				listOfRaces.remove(listOfRaces.get(count));
				foundRace = true;
			}
			count++;	
		}
		for(int stage : stages){
			boolean foundStage = false;
			int countStages =0;
			while (!foundStage && count < listOfStages.size()){
				if (stage == listOfStages.get(countStages).getStageID()){
					checkpoints = listOfStages.get(countStages).getCheckpoints();
					listOfStages.remove(listOfStages.get(countStages));
					foundStage = true;
				}
				countStages++;
			}	

			for(int checkpoint : checkpoints){
				boolean foundCheckpoint = false;
				int countCheckpoint = 0;
				while (!foundCheckpoint && countCheckpoint < listOfCheckpoints.size()){
					if (checkpoint == listOfCheckpoints.get(countCheckpoint).getCheckpointID()){
						listOfCheckpoints.remove(listOfCheckpoints.get(countCheckpoint));
						foundCheckpoint = true;
					}
					countCheckpoint++;
				}
			}
		}
		
		
		if(!foundRace){
			throw new NameNotRecognisedException();
		}
	}
	/**
	 * Get the general classification times of riders in a race.
	 * @return A list of riders' times sorted by the sum of their adjusted elapsed
	 *         times in all stages of the race. An empty list if there is no result
	 *         for any stage in the race. These times should match the riders
	 *         returned by {@link #getRidersGeneralClassificationRank(int)}. Assume
	 *         the total elapsed time of a race (the sum of all of its stages) never
	 *         exceeds 24h and, therefore, can be represented by a LocalTime
	 *         variable. There is no need to check for this condition or raise any
	 *         exception.
	 */
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		int[] riderIDs = getRidersGeneralClassificationRank(raceId); //Ordered IDs
		LocalTime[] returnTimes = new LocalTime[riderIDs.length];
		for (Races race : listOfRaces) {
			if (raceId == race.getRaceID()){
				int count = 0;
				for(int id: riderIDs){
					returnTimes[count] = totalRiderTimes.get(id);
					count++;
				}
				return returnTimes;
			}
		}
		throw new IDNotRecognisedException();
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// gives empty array !
		int current = 0;
		HashMap<Integer, Integer> pointsInStage = new HashMap<>();
		int[] finished;
		for (Races race : listOfRaces){
			if (raceId== race.getRaceID()){
				int[] rank = getRidersGeneralClassificationRank(raceId);
				int[] stages = race.getStages();
				for(int i=0; i < stages.length; i++){
					for (Stages stage :listOfStages){
						if (stage.getStageID() == stages[i]){
							int[] pointArray = getRidersPointsInStage(stage.getStageID());
							int[] returnArray = stage.getRank();
							if (stage.getResultsSize() == 0){
								finished = new int[]{};
								return finished;	
							}else{
								for(int j = 0; j< pointArray.length; j++){
									if(pointsInStage.get(returnArray[j]) == null){
										current = 0;
									}else{
										current = pointsInStage.get(returnArray[j]);
									}
									pointsInStage.put(returnArray[j],current + pointArray[j]);
								}
								
							}
						}
					}		
				}
				
				finished = new int[rank.length];
				int count = 0;
				for (int id : rank) {
					if(pointsInStage.containsKey(rank[id])){
						finished[count] = pointsInStage.get(id);
					}
					count ++;
				}
				return finished;	
			}	
		}
		throw new IDNotRecognisedException();
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// gives empty array !
		int current = 0;
		HashMap<Integer, Integer> pointsInStage = new HashMap<>();
		int[] finished;
		for (Races race : listOfRaces) {
			if (raceId== race.getRaceID() ){
				int[] rank = getRidersGeneralClassificationRank(raceId);
				int[] stages = race.getStages();
				for(int i=0; i < stages.length; i++){
					for (Stages stage :listOfStages) {
						if (stage.getStageID() == stages[i]){
							int [] pointArray = getRidersMountainPointsInStage(stage.getStageID());
							if (stage.getResultsSize() == 0){
								finished = new int[]{};
								return finished;
							}
							int[] returnArray = stage.getRank();
							if(returnArray != null){
								for(int j = 0; j< pointArray.length; j++){
									if(pointsInStage.get(returnArray[j]) == null){
										current = 0;
									}else{
										current = pointsInStage.get(returnArray[j]);
									}
									pointsInStage.put(returnArray[j],current + pointArray[j]);
									}
								}
							}
						}	
					}		
					finished = new int[rank.length];
					int count = 0;
					for (int id : rank) {
						if(pointsInStage.containsKey(rank[id])){
							finished[count] = pointsInStage.get(id);
						}
						count ++;
					}
					return finished;	
			}
		}
		throw new IDNotRecognisedException();
	}
			

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		//For all in race
		//can get each stage from getAdjustedRank (to give times)
		// and getRank to get IDs
		//Add all together here
		totalRiderTimes.clear();
		for (Races race : listOfRaces) {
			if (raceId == race.getRaceID() ){
				//Loads all the stages and adds their total times together
				int[] stagesInRace = race.getStages(); //Get the IDs of all stages
				boolean ridersAdded = false;
				for(Stages stage : listOfStages){
					boolean contained = false;
					for(int stageInRace: stagesInRace){
						if (stageInRace == stage.getStageID()){
							contained = true;
						}
					}
					
					if (contained){
						LocalTime[] times = stage.getAdjustedRank();
							
						int[] ids = stage.getRank();
						if (times.length != 0){
							if (!ridersAdded){
								for(int id : ids){
									totalRiderTimes.put(id, times[id]);
								}
								ridersAdded = true;
							} else{
								for(int id : ids){
									LocalTime time = totalRiderTimes.get(id);
									totalRiderTimes.put(id, times[id].plusHours(time.getHour()).plusMinutes(time.getMinute()).plusSeconds(time.getSecond()).plusNanos(time.getNano()));
								}
							}
					}
				}
				}
				//Sorts the hashmap values and returns ids in order
				ArrayList<LocalTime> riderTimes = new ArrayList<>(); //Array that can be sorted
				HashMap<LocalTime, Integer> timeRiderDict = new HashMap<>(); //Dictionary matching times to IDs
				for (Integer id : totalRiderTimes.keySet()) {
					LocalTime theirTime = totalRiderTimes.get(id);
					riderTimes.add(theirTime);
					timeRiderDict.put(theirTime, id);
				}
				Collections.sort(riderTimes);
				int[] orderedIDs = new int[riderTimes.size()];
				int count = 0;
				for(LocalTime time : riderTimes){
					orderedIDs[count] = timeRiderDict.get(time);
					count++;
				}
				return orderedIDs;
			}
		}
		throw new IDNotRecognisedException();
}

	
	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// gives empty array 
		for(Races race : listOfRaces){
			if(race.getRaceID() == raceId){
				int[] points;
				try{
					points = getRidersPointsInRace(raceId);
				} catch(Exception e){
					throw new IDNotRecognisedException();
				}
				int[] ranks = getRidersGeneralClassificationRank(raceId);
				
				ArrayList<Integer> pointsinList = new ArrayList<>(); 
				HashMap<Integer, Integer> pointsandranks = new HashMap<>(); 
				ArrayList<Integer> ranksUsed = new ArrayList<>();
				for(int i=0; i<points.length; i++){
					pointsandranks.put(ranks[i], points[i]);
					pointsinList.add(points[i]);
				}
		
				Collections.sort(pointsinList);
				Collections.reverse(pointsinList);
	
				int[] orderedIDs = new int[pointsinList.size()];
				int count = 0;
				for(Integer point : pointsinList){
					for( int j =0; j<pointsinList.size(); j++){
						Integer current = orderedIDs[count];
						if(point == points[j] && current.equals(0) && !ranksUsed.contains(ranks[j])){
							orderedIDs[count] = ranks[j];
							ranksUsed.add(ranks[j]);
						}
					}
					count++;
				}
				return orderedIDs;
			}
		}
		throw new IDNotRecognisedException();
	}

	
	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// gives empty array for riders point classification
		for(Races race : listOfRaces){
			if(race.getRaceID() == raceId){
				int[] points = getRidersMountainPointsInRace(raceId);
				int[] ranks = getRidersGeneralClassificationRank(raceId);
		
				ArrayList<Integer> pointsinList = new ArrayList<>(); 
				HashMap<Integer, Integer> pointsandranks = new HashMap<>(); 
				ArrayList<Integer> ranksUsed = new ArrayList<>();
				for(int i=0; i<points.length; i++){
					pointsandranks.put(ranks[i], points[i]);
					pointsinList.add(points[i]);
				}
		
				Collections.sort(pointsinList);
				Collections.reverse(pointsinList);
	
				int[] orderedIDs = new int[pointsinList.size()];
				int count = 0;
				for(Integer point : pointsinList){
					for( int j =0; j<pointsinList.size(); j++){
						Integer current = orderedIDs[count];
						if(point == points[j] && current.equals(0) && !ranksUsed.contains(ranks[j])){
							orderedIDs[count] = ranks[j];
							ranksUsed.add(ranks[j]);
						}
					}
					count++;
				}
				return orderedIDs;
			}
		}
		throw new IDNotRecognisedException();
	}
}
		



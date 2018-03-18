package cz.muni.fi.missions;

import java.util.List;

/**
 * @author Samuel Gorta
 */
public interface MissionManager {

    /**
     * Function adds mission into database of missions.
     * @param mission, which will be add to database
     * @return added mission
     */
    Mission createMission(Mission mission);

    /**
     * Function updates mission by replacing the old one with new (by Id).
     * @param mission updated mission
     * @return updated mission
     */
    Mission updateMission(Mission mission);

    /**
     * Function delete mission from database.
     * @param missionId to delete
     * @return true if deletion was succes, false otherwise
     */
    boolean deleteMission(long missionId);

    /**
     * Function finds mission in database by id.
     * @param missionId to find
     * @return found mission
     */
    Mission findMissionByid(long missionId);

    /**
     * Function finds all missions in database.
     * @return all missions in database
     */
    List<Mission> findAllMissions();
}

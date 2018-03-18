package cz.muni.fi.missions;

import java.util.List;

public interface MissionManager {

    public Mission createMission(Mission mission);

    public Mission updateMission(Mission mission);

    public boolean deleteMission(long MissionId);

    public Mission findMissionByid(long missionId);

    List<Mission> findAllMissions();
}

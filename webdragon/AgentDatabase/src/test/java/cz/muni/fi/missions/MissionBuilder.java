package cz.muni.fi.missions;

import cz.muni.fi.agents.Equipment;

import java.time.LocalDate;
import java.time.Month;

/**
 * @author Samuel Gorta
 */
public class MissionBuilder {
    private long id;
    private String target;
    private Equipment necesarryEquipment;
    private LocalDate deadline;

    public Mission build(){
        Mission mission = new Mission();
        mission.setId(id);
        mission.setTarget(target);
        mission.setNecessaryEquipment(necesarryEquipment);
        mission.setDeadline(deadline);
        return mission;
    }

    public MissionBuilder id(long id){
        this.id = id;
        return this;
    }

    public MissionBuilder target(String target){
        this.target = target;
        return this;
    }

    public MissionBuilder necesaryEquipmnt(Equipment necesarryEquipment){
        this.necesarryEquipment = necesarryEquipment;
        return this;
    }

    public MissionBuilder deadline(LocalDate deadline){
        this.deadline = deadline;
        return this;
    }

    public MissionBuilder deadline(int year, Month month, int day) {
        this.deadline = LocalDate.of(year, month, day);
        return this;
    }
}

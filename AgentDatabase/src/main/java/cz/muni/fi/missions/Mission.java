package cz.muni.fi.missions;

import cz.muni.fi.agents.Equipment;

import java.time.LocalDate;

public class Mission {

    private long id;
    private String target;
    private Equipment necesarryEquipment;
    private LocalDate deadline;

    public Mission() {
    }

    public Mission(long id, String target, Equipment necesarryEquipment, LocalDate deadline) {
        this.id = id;
        this.target = target;
        this.necesarryEquipment = necesarryEquipment;
        this.deadline = deadline;
    }

    public Equipment getNecesarryEquipment() {
        return necesarryEquipment;
    }

    public long getId() {
        return id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setNecesarryEquipment(Equipment necesarryEquipment) {
        this.necesarryEquipment = necesarryEquipment;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mission)) return false;

        Mission mission = (Mission) o;

        return id == mission.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", target='" + target + '\'' +
                ", necesarryEquipment=" + necesarryEquipment +
                ", deadline=" + deadline +
                '}';
    }
}

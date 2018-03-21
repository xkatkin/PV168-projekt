package cz.muni.fi.missions;

import cz.muni.fi.agents.Equipment;

import java.time.LocalDate;

/**
 * @author Samuel Gorta
 */
public class Mission {
    private long id;
    private String target;
    private Equipment necessaryEquipment;
    private LocalDate deadline;

    Mission() {
    }

    Mission(long id, String target, Equipment necesarryEquipment, LocalDate deadline) {
        this.id = id;
        this.target = target;
        this.necessaryEquipment = necesarryEquipment;
        this.deadline = deadline;
    }

    Equipment getNecesarryEquipment() {
        return necessaryEquipment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    String getTarget() {
        return target;
    }

    void setTarget(String target) {
        this.target = target;
    }

    void setNecesarryEquipment(Equipment necesarryEquipment) {
        this.necessaryEquipment = necesarryEquipment;
    }

    LocalDate getDeadline() {
        return deadline;
    }

    void setDeadline(LocalDate deadline) {
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
                ", necesarryEquipment=" + necessaryEquipment +
                ", deadline=" + deadline +
                '}';
    }
}

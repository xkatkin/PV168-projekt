package cz.muni.fi.missions;

import cz.muni.fi.agents.Equipment;

import java.time.LocalDate;

/**
 * @author Samuel Gorta
 */
public class Mission {
    private Long id;
    private String target;
    private Equipment necessaryEquipment;
    private LocalDate deadline;

    public Mission() {
    }

    public Mission(Long id, String target, Equipment necesarryEquipment, LocalDate deadline) {
        this.id = id;
        this.target = target;
        this.necessaryEquipment = necesarryEquipment;
        this.deadline = deadline;
    }

    public Equipment getNecesarryEquipment() {
        return necessaryEquipment;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setNecesarryEquipment(Equipment necesarryEquipment) {
        this.necessaryEquipment = necesarryEquipment;
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
                ", necesarryEquipment=" + necessaryEquipment +
                ", deadline=" + deadline +
                '}';
    }
}

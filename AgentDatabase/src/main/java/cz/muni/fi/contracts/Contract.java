package cz.muni.fi.contracts;

import cz.muni.fi.agents.Agent;
import cz.muni.fi.missions.Mission;

import java.time.LocalDate;

/**
 * @author Slavomir Katkin
 */
public class Contract {
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Agent agent;
    private Mission mission;

    public Contract(long id, LocalDate startDate, LocalDate endDate, Agent agent, Mission mission) {
        this.id = id;
        startDate = startDate;
        endDate = endDate;
        this.agent = agent;
        this.mission = mission;
    }

    public Contract() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        endDate = endDate;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        return id == contract.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}

package cz.muni.fi.contracts;

import cz.muni.fi.agents.Agent;
import cz.muni.fi.missions.Mission;

import java.time.LocalDate;

/**
 * @author Slavomir Katkin
 */
public class Contract {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Agent agent;
    private Mission mission;

    public Contract(Long id, LocalDate startDate, LocalDate endDate, Agent agent, Mission mission) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.agent = agent;
        this.mission = mission;
    }

    public Contract() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contract{");
        sb.append("id=").append(id);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", agent=").append(agent);
        sb.append(", mission=").append(mission);
        sb.append('}');
        return sb.toString();
    }
}

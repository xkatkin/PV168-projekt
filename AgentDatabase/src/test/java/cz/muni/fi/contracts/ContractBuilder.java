package cz.muni.fi.contracts;

import cz.muni.fi.agents.Agent;

import java.time.LocalDate;
import java.time.Month;

/**
 * @author Slavomir Katkin
 */
public class ContractBuilder {
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Agent agent;
    private Mission mission;

    public Contract build() {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setStartDate(startDate);
        contract.setEndDate(endDate);
        contract.setAgent(agent);
        contract.setMission(mission);
        return contract;
    }

    public ContractBuilder id(long id) {
        this.id = id;
        return this;
    }

    public ContractBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public ContractBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public ContractBuilder startDate(int year, Month month, int day) {
        this.startDate = LocalDate.of(year, month, day);
        return this;
    }

    public ContractBuilder endDate(int year, Month month, int day) {
        this.endDate = LocalDate.of(year, month, day);
        return this;
    }

    public ContractBuilder agent(Agent agent) {
        this.agent = agent;
        return this;
    }

    public ContractBuilder mission(Mission mission) {
        this.mission = mission;
        return this;
    }

}

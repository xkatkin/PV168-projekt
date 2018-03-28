package cz.muni.fi.contracts;


import cz.muni.fi.agents.Agent;
import cz.muni.fi.agents.AgentBuilder;
import cz.muni.fi.agents.AgentManagerImpl;
import cz.muni.fi.agents.Equipment;
import cz.muni.fi.missions.Mission;
import cz.muni.fi.missions.MissionBuilder;
import cz.muni.fi.missions.MissionManagerImpl;
import cz.muni.fi.mySpringTestConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;



import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Slavomir Katkin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {mySpringTestConfig.class})
@Transactional
public class ContractManagerImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private ContractManagerImpl contractManager;
    @Autowired
    private AgentManagerImpl agentManager;
    @Autowired
    private MissionManagerImpl missionManager;

    private MissionBuilder testMission1Builder(){
        return new MissionBuilder()
                .target("Buckingham Palace")
                .necesaryEquipmnt(Equipment.BADASSCAR)
                .deadline(LocalDate.of(2021, Month.JUNE, 14));
    }

    private MissionBuilder testMission2Builder(){
        return new MissionBuilder()
                .target("Bratislava's UFO")
                .necesaryEquipmnt(Equipment.MOJITO)
                .deadline(LocalDate.of(2027, Month.APRIL, 1));
    }

    private MissionBuilder testMission3Builder(){
        return new MissionBuilder()
                .target("FI at MUNI")
                .necesaryEquipmnt(Equipment.CHARMINGCOMPANION)
                .deadline(LocalDate.of(2030, Month.MARCH, 24));
    }

    private AgentBuilder testAgent1Builder() {
        return new AgentBuilder()
                .fullName("James Bond")
                .secretName("007")
                .equipment(Equipment.CHARMINGCOMPANION);
    }

    private AgentBuilder testAgent2Builder() {
        return new AgentBuilder()
                .fullName("John Smith")
                .secretName("008")
                .equipment(Equipment.BADASSCAR);
    }

    private ContractBuilder testContract1(){
        return new ContractBuilder()
                .agent(testAgent1Builder().build())
                .mission(testMission1Builder().build())
                .endDate(LocalDate.of(2019, Month.MARCH, 23))
                .startDate(LocalDate.of(2019, Month.MARCH, 10));
    }

    private ContractBuilder testContract2(){
        return new ContractBuilder()
                .agent(testAgent2Builder().build())
                .mission(testMission2Builder().build())
                .endDate(LocalDate.of(2020, Month.MARCH, 19))
                .startDate(LocalDate.of(2019, Month.SEPTEMBER, 2));
    }

    private ContractBuilder testContract3(){
        return new ContractBuilder()
                .agent(testAgent1Builder().build())
                .mission(testMission3Builder().build())
                .endDate(LocalDate.of(2022, Month.DECEMBER, 23))
                .startDate(LocalDate.of(2021, Month.JANUARY, 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithoutAgent() throws Exception {
        Contract contract = new ContractBuilder()
                .mission(testMission1Builder().build())
                .startDate(LocalDate.of(2018, Month.MARCH, 19))
                .endDate(LocalDate.of(2018, Month.SEPTEMBER, 2))
                .build();
        contractManager.createContract(contract);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithoutMission() throws Exception {
        Contract contract = new ContractBuilder()
                .agent(testAgent1Builder().build())
                .startDate(LocalDate.of(2018, Month.MARCH, 19))
                .endDate(LocalDate.of(2018, Month.SEPTEMBER, 2))
                .build();
        contractManager.createContract(contract);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithBusyAgent() throws Exception {
        Contract contract1 = testContract1().build();
        contractManager.createContract(contract1);

        Contract contract2 = testContract1()
                .startDate(LocalDate.of(2018, Month.MARCH, 15))
                .endDate(LocalDate.of(2018, Month.MARCH, 24))
                .build();
        contractManager.createContract(contract2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithExpiredMission() throws Exception {
        Contract contract = new ContractBuilder()
                .agent(testAgent2Builder().build())
                .mission(testMission2Builder().build())
                .startDate(LocalDate.of(1990, Month.JANUARY, 19))
                .endDate(LocalDate.of(1990, Month.FEBRUARY, 2))
                .build();
        contractManager.createContract(contract);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithoutDeadline() throws Exception {
        Contract contract = new ContractBuilder()
                .agent(testAgent1Builder().build())
                .mission(testMission1Builder().build())
                .startDate(LocalDate.of(2018, Month.MARCH, 19))
                .build();
        contractManager.createContract(contract);

    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithInvalidDeadline() throws Exception {
        Contract contract = testContract1()
                .startDate(LocalDate.of(2018, Month.MARCH, 19))
                .endDate(LocalDate.of(2018, Month.MARCH, 18))
                .build();
        contractManager.createContract(contract);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithContractedMission() throws Exception {
        Contract contract1 = testContract1().build();
        Contract contract2 = testContract1().
                agent(testAgent2Builder().build())
                .build();
        contractManager.createContract(contract1);
        contractManager.createContract(contract2);
    }

    @Test
    public void simpleCreateContract() throws Exception {
        Agent agent = testAgent1Builder().build();
        Mission mission = testMission1Builder().build();
        agentManager.createAgent(agent);
        missionManager.createMission(mission);
        Contract contract1 = testContract1()
                .agent(agent)
                .mission(mission)
                .build();
        contractManager.createContract(contract1);
        assertFalse(contractManager.findAllContracts().isEmpty());
        assertTrue(contractManager.findAllContracts().contains(contract1));
    }

    @Test
    public void complexCreateContract() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        Agent agent2 = testAgent2Builder().build();
        agentManager.createAgent(agent1);
        agentManager.createAgent(agent2);

        Mission mission1 = testMission1Builder().build();
        Mission mission2 = testMission2Builder().build();
        missionManager.createMission(mission1);
        missionManager.createMission(mission2);

        Contract contract1 = testContract1()
                .agent(agent1)
                .mission(mission1)
                .build();
        Contract contract2 = testContract2()
                .agent(agent2)
                .mission(mission2)
                .build();

        contractManager.createContract(contract1);
        contractManager.createContract(contract2);

        assertTrue(contractManager.findAllContracts().size() == 2);
        assertTrue(contractManager.findAllContracts().contains(contract1));
        assertTrue(contractManager.findAllContracts().contains(contract2));
    }

    @Test
    public void simpleUpdateContract() throws Exception {
        Agent agent = testAgent1Builder().build();
        Mission mission = testMission1Builder().build();
        agentManager.createAgent(agent);
        missionManager.createMission(mission);
        Contract contract = testContract1()
                .agent(agent)
                .mission(mission)
                .build();
        contractManager.createContract(contract);

        Agent agent2 = testAgent2Builder().build();
        agentManager.createAgent(agent2);
        assertTrue(agent2.getId() != 0);

        contract.setAgent(agent2);
        System.out.println(agent2);
        System.out.println(contract.toString());
        contractManager.updateContract(contract);

        assertTrue(contractManager.findAllContracts().contains(contract));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateInvalidContract() throws Exception {
        Contract contract = testContract1().build();
        contractManager.createContract(contract);
        contract.setAgent(null);
        contractManager.updateContract(contract);
    }

    @Test
    public void updateNonExistingContract() throws Exception {
        Contract contract = testContract3().build();
        contractManager.updateContract(contract);
        assertTrue(contractManager.findAllContracts().size() == 0);
    }

    @Test
    public void simplyDeleteContract() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        agentManager.createAgent(agent1);

        Mission mission1 = testMission1Builder().build();
        missionManager.createMission(mission1);
        Contract contract = testContract2()
                .agent(agent1)
                .mission(mission1)
                .build();
        contractManager.createContract(contract);
        contractManager.deleteContract(contract.getId());
        assertTrue(contractManager.findAllContracts().size() == 0);
    }

    @Test
    public void complexDeleteContract() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        Agent agent2 = testAgent2Builder().build();
        agentManager.createAgent(agent1);
        agentManager.createAgent(agent2);

        Mission mission1 = testMission1Builder().build();
        Mission mission2 = testMission2Builder().build();
        missionManager.createMission(mission1);
        missionManager.createMission(mission2);

        Contract contract1 = testContract1()
                .agent(agent1)
                .mission(mission1)
                .build();
        Contract contract2 = testContract2()
                .agent(agent2)
                .mission(mission2)
                .build();

        contractManager.createContract(contract1);
        contractManager.createContract(contract2);
        assertTrue(contractManager.findAllContracts().size() == 2);
        contractManager.deleteContract(contract1.getId());
        assertFalse(contractManager.findAllContracts().contains(contract1));

        contractManager.deleteContract(contract2.getId());
        assertTrue(contractManager.findAllContracts().isEmpty());

    }

    @Test
    public void deleteNonExistingContract() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        agentManager.createAgent(agent1);

        Mission mission1 = testMission1Builder().build();
        missionManager.createMission(mission1);
        Contract contract1 = testContract2()
                .agent(agent1)
                .mission(mission1)
                .build();
        contractManager.createContract(contract1);
        contractManager.deleteContract(testContract2().build().getId());
        assertTrue(contractManager.findAllContracts().size() == 1);
    }

    @Test
    public void findAllContracts() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        Agent agent2 = testAgent2Builder().build();
        agentManager.createAgent(agent1);
        agentManager.createAgent(agent2);

        Mission mission1 = testMission1Builder().build();
        Mission mission2 = testMission2Builder().build();
        missionManager.createMission(mission1);
        missionManager.createMission(mission2);

        Contract contract1 = testContract1()
                .agent(agent1)
                .mission(mission1)
                .build();
        Contract contract2 = testContract2()
                .agent(agent2)
                .mission(mission2)
                .build();

        contractManager.createContract(contract1);
        contractManager.createContract(contract2);

        List<Contract> contractList = contractManager.findAllContracts();
        assertTrue(contractList.size() == 2);
        assertTrue(contractList.contains(contract1));
        assertTrue(contractList.contains(contract2));

    }

    @Test
    public void findAllContractsInEmptyDB() throws Exception {
        assertTrue(contractManager.findAllContracts().size() == 0);
    }
}
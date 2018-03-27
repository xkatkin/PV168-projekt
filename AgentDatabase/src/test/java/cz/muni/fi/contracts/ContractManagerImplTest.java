package cz.muni.fi.contracts;


import cz.muni.fi.agents.AgentBuilder;
import cz.muni.fi.agents.Equipment;
import cz.muni.fi.missions.MissionBuilder;
import cz.muni.fi.mySpringTestConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
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

    private MissionBuilder testMission1Builder(){
        return new MissionBuilder().target("Buckingham Palace")
                .necesaryEquipmnt(Equipment.BADASSCAR)
                .deadline(LocalDate.of(2018, Month.JUNE, 14));
    }

    private MissionBuilder testMission2Builder(){
        return new MissionBuilder().target("Bratislava's UFO")
                .necesaryEquipmnt(Equipment.MOJITO)
                .deadline(LocalDate.of(2027, Month.APRIL, 1));
    }

    private MissionBuilder testMission3Builder(){
        return new MissionBuilder().target("FI at MUNI")
                .necesaryEquipmnt(Equipment.CHARMINGCOMPANION)
                .deadline(LocalDate.of(2019, Month.MARCH, 24));
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
                .endDate(LocalDate.of(2019, Month.MARCH, 19))
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
        Contract contract1 = testContract1().build();
        contractManager.createContract(contract1);
        assertFalse(contractManager.findAllContracts().isEmpty());
        assertTrue(contractManager.findAllContracts().contains(contract1));
    }

    @Test
    public void complexCreateContract() throws Exception {
        Contract contract1 = testContract1().build();
        Contract contract2 = testContract2().build();
        Contract contract3 = testContract3().build();
        contractManager.createContract(contract1);
        contractManager.createContract(contract2);
        contractManager.createContract(contract3);

        assertTrue(contractManager.findAllContracts().size() == 3);
        assertTrue(contractManager.findAllContracts().contains(contract1));
        assertTrue(contractManager.findAllContracts().contains(contract2));
        assertTrue(contractManager.findAllContracts().contains(contract3));
    }

    @Test
    public void simpleUpdateContract() throws Exception {
        Contract contract = testContract1().build();
        contractManager.createContract(contract);
        contract.setAgent(testAgent2Builder().build());
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

    @Test(expected = IllegalArgumentException.class)
    public void updateNonExistingContract() throws Exception {
        Contract contract = testContract3().build();
        contractManager.updateContract(contract);
    }

    @Test
    public void simplyDeleteContract() throws Exception {
        Contract contract = testContract3().build();
        contractManager.createContract(contract);
        contractManager.deleteContract(contract.getId());
        assertTrue(contractManager.findAllContracts().size() == 0);
    }

    @Test
    public void complexDeleteContract() throws Exception {
        Contract contract1 = testContract1().build();
        Contract contract2 = testContract2().build();
        Contract contract3 = testContract3().build();
        contractManager.createContract(contract1);
        contractManager.createContract(contract2);
        contractManager.createContract(contract3);

        contractManager.createContract(contract1);
        assertTrue(contractManager.findAllContracts().size() == 2);
        assertFalse(contractManager.findAllContracts().contains(contract1));

        contractManager.deleteContract(contract2.getId());
        contractManager.deleteContract(contract3.getId());
        assertTrue(contractManager.findAllContracts().isEmpty());

    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNonExistingContract() throws Exception {
        Contract contract = testContract3().build();
        contractManager.deleteContract(contract.getId());
    }

    @Test
    public void findAllContracts() throws Exception {
        Contract contract1 = testContract1().build();
        Contract contract2 = testContract2().build();
        Contract contract3 = testContract3().build();
        contractManager.createContract(contract1);
        contractManager.createContract(contract2);
        contractManager.createContract(contract3);

        List<Contract> contractList = contractManager.findAllContracts();
        assertTrue(contractList.size() == 3);
        assertTrue(contractList.contains(contract1));
        assertTrue(contractList.contains(contract2));
        assertTrue(contractList.contains(contract3));

    }

    @Test
    public void findAllContractsInEmptyDB() throws Exception {
        assertTrue(contractManager.findAllContracts().size() == 0);
    }
}
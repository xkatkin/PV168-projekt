package cz.muni.fi.contracts;


import cz.muni.fi.agents.AgentBuilder;
import cz.muni.fi.agents.Equipment;
import cz.muni.fi.missions.MissionBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.sql.DataSource;

import java.time.*;

import static org.junit.Assert.*;

/**
 * @author Slavomir Katkin
 */
public class ContractManagerImplTest {
    private ContractManager contractManager;
    private DataSource ds;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        contractManager = new ContractManagerImpl();
    }

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
                .deadline(LocalDate.of(2018, Month.MARCH, 24));
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
                .endDate(LocalDate.of(2018, Month.MARCH, 23))
                .startDate(LocalDate.of(2018, Month.MARCH, 10));
    }

    private ContractBuilder testContract2(){
        return new ContractBuilder()
                .agent(testAgent2Builder().build())
                .mission(testMission2Builder().build())
                .endDate(LocalDate.of(2018, Month.MARCH, 19))
                .startDate(LocalDate.of(2018, Month.SEPTEMBER, 2));
    }

    private ContractBuilder testContract3(){
        return new ContractBuilder()
                .agent(testAgent1Builder().build())
                .mission(testMission3Builder().build())
                .endDate(LocalDate.of(2021, Month.DECEMBER, 23))
                .startDate(LocalDate.of(2022, Month.JANUARY, 10));
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
    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithExpiredMission() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithoutDeadline() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithInvalidDeadline() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithCompletedMission() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void createContractWithContractedMission() throws Exception {
    }

    @Test
    public void simpleCreateContract() throws Exception {
    }

    @Test
    public void complexCreateContract() throws Exception {
    }

    @Test
    public void simpleUpdateContract() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateInvalidContract() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNonExistingContract() throws Exception {
    }

    @Test
    public void simplyDeleteContract() throws Exception {
    }

    @Test
    public void complexDeleteContract() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNonExistingContract() throws Exception {
    }

    @Test
    public void findAllContracts() throws Exception {
    }

    @Test
    public void findAllContractsInEmptyDB() throws Exception {
    }
}
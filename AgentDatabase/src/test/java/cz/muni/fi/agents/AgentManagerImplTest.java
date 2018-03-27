package cz.muni.fi.agents;

import cz.muni.fi.mySpringTestConfig;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.*;

/**
 * @author Slavomir Katkin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {mySpringTestConfig.class})
@Transactional
public class AgentManagerImplTest {
    @Autowired
    private AgentManagerImpl manager;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


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


    @Test(expected = IllegalArgumentException.class)
    public void createAgentDuplicateId () throws Exception {
        Agent agent1 = testAgent1Builder().build();
        manager.createAgent(agent1);
        Agent agent2 = testAgent2Builder().id(agent1.getId()).build();
        manager.createAgent(agent2);
    }

    @Test
    public void createAgent () throws Exception {
        Agent agent1 = testAgent1Builder().build();
        manager.createAgent(agent1);
        assertTrue(manager.findAllAgents().contains(agent1));
        assertTrue(agent1.getId() != 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullAgent () throws Exception {
        manager.createAgent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAgentWithoutName () throws Exception {
        Agent agent = new AgentBuilder()
                .fullName(null)
                .secretName("001")
                .equipment(Equipment.GUN)
                .build();
        manager.createAgent(agent);

    }

    @Test(expected = IllegalArgumentException.class)
    public void createAgentWithoutSecretName () throws Exception {
        Agent agent = new AgentBuilder()
                .fullName("Mr. Secret")
                .secretName(null)
                .equipment(Equipment.GUN)
                .build();
        manager.createAgent(agent);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAgentWithoutEquipment () throws Exception {
        Agent agent = new AgentBuilder()
                .fullName("Mr. Secret")
                .secretName("001")
                .equipment(null)
                .build();
        manager.createAgent(agent);
    }

    @Test
    public void updateAgent() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        Agent agent2 = testAgent2Builder().build();
        manager.createAgent(agent1);
        manager.createAgent(agent2);
        agent1.setFullName("John Snow");
        manager.updateAgent(agent1);

        assertTrue(manager.findAllAgents().size() == 2);
        Agent agent = manager.findAgentById(agent1.getId());
        assertTrue(agent.getFullName().equals("John Snow"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNullAgent() throws Exception {
        manager.updateAgent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateAgentWithNullParameter() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        manager.createAgent(agent1);
        agent1.setEquipment(null);
        manager.updateAgent(agent1);
    }

    @Test
    public void updateNonexistentAgent() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        Agent agent2 = testAgent2Builder().build();
        manager.createAgent(agent1);
        agent1.setFullName("John Snow");
        manager.updateAgent(agent2);

        assertTrue(manager.findAllAgents().size() == 1);
        assertTrue(manager.findAgentById(agent1.getId()).getFullName().equals("James Bond"));
    }


    @Test
    public void deleteAgent() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        Agent agent2 = testAgent2Builder().build();
        manager.createAgent(agent1);
        manager.createAgent(agent2);
        manager.deleteAgent(agent1.getId());

        assertFalse(manager.findAllAgents().contains(agent1));
        assertTrue(manager.findAllAgents().contains(agent2));
        assertTrue(manager.findAllAgents().size() == 1);
    }

    @Test
    public void deleteAgentWithNoId() {
        Agent agent1 = testAgent1Builder().build();
        Agent agent2 = testAgent2Builder().build();
        manager.createAgent(agent2);
        manager.deleteAgent(agent1.getId());

        assertTrue(manager.findAllAgents().size() == 1);
    }

    @Test
    public void findAgentById() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        manager.createAgent(agent1);
        assertEquals(manager.findAgentById(agent1.getId()), agent1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAgentByNonexistentId() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        Agent agent2 = testAgent2Builder().build();
        manager.createAgent(agent1);
        manager.findAgentById(agent2.getId());
    }

    @Test
    public void findAllAgentsEmpty() throws Exception {
        assertTrue(manager.findAllAgents().isEmpty());
    }

    @Test
    public void findAllAgents() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        Agent agent2 = testAgent2Builder().build();

        manager.createAgent(agent1);
        manager.createAgent(agent2);
        assertFalse(manager.findAllAgents().isEmpty());
        assertTrue(manager.findAllAgents().size() == 2);
        assertTrue(manager.findAllAgents().contains(agent1));
        assertTrue(manager.findAllAgents().contains(agent2));
    }

}
package cz.muni.fi.agents;

import org.junit.*;
import org.junit.rules.ExpectedException;
import javax.sql.DataSource;

import static org.junit.Assert.*;
//TODO UPDATE BODY
/**
 * @author Slavomir Katkin
 */
public class AgentManagerImplTest {
    private AgentManagerImpl manager;
    private DataSource ds;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        manager = new AgentManagerImpl();
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


    @Test(expected = IllegalArgumentException.class)
    public void createAgentDuplicateId () throws Exception {
        Agent agent1 = testAgent1Builder().build();
        manager.createAgent(agent1);
        Agent agent2 = testAgent2Builder().id(agent1.getId()).build();
        manager.createAgent(agent2);
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


    @Test(expected = IllegalArgumentException.class)
    public void deleteNonexistentAgent() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        manager.deleteAgent(agent1.getId());
    }

    @Test
    public void findAgentById() throws Exception {
        Agent agent1 = testAgent1Builder().build();
        manager.createAgent(agent1);
        assertEquals(manager.findAgentById(agent1.getId()), agent1);
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
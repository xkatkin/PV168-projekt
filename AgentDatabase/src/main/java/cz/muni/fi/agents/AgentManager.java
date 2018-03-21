package cz.muni.fi.agents;




import java.util.List;

/**
 * @author Slavomir Katkin
 */
public interface AgentManager {

    /**
     * Creates new agent into database, ID is automatically generated and assigned as attribute
     * @param agent - agent to be created
     * @throws IllegalArgumentException - if agent or any of his parameters are null; agent already has id
     */
    void createAgent(Agent agent) throws IllegalArgumentException;

    /**
     * Updates agent in the database
     * @param agent - updated agent to be stored in database
     * @throws IllegalArgumentException - if agent or any of his parameteres are null; agent is not in database
     */
    void updateAgent(Agent agent) throws IllegalArgumentException;

    /**
     * Deletes agent from the database
     * @param agentId - ID primary key) of agent in the database
     * @return true if agent was deleted, false otherwise
     */
    boolean deleteAgent(long agentId);

    /**
     * Returns agent with given ID
     * @param agentId - ID (primary key) of agent in the database to be found
     * @return agent with given ID or null if not found
     */
    Agent findAgentById(long agentId);

    /**
     * Returns all agents within database
     * @return list of all agents in the database
     */
    List<Agent> findAllAgents();

}

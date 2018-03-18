package cz.muni.fi.agents;




import java.util.List;

/**
 * @author Slavomir Katkin
 */
public interface AgentManager {
    /**
     *
     * @param agent
     * @return
     */
    Agent createAgent(Agent agent);

    /**
     *
     * @param agent
     * @return
     */
    Agent updateAgent(Agent agent);

    /**
     *
     * @param agentId
     * @return
     */
    Boolean deleteAgent(long agentId);

    /**
     *
     * @param agentId
     * @return
     */
    Agent findAgentById(long agentId);

    /**
     *
     * @return
     */
    List<Agent> findAllAgents();

}

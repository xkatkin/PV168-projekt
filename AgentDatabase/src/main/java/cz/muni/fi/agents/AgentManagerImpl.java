package cz.muni.fi.agents;

import java.util.List;

/**
 * @author Slavomir Katkin
 */
public class AgentManagerImpl implements AgentManager {
    private long idCounter;

    public AgentManagerImpl() {
        this.idCounter = 0;
    }

    @Override
    public Agent createAgent(Agent agent) {
        return null;
    }

    @Override
    public Agent updateAgent(Agent agent) {
        return null;
    }

    @Override
    public Boolean deleteAgent(long agentId) {
        return null;
    }

    @Override
    public Agent findAgentById(long agentId) {
        return null;
    }

    @Override
    public List<Agent> findAllAgents() {
        return null;
    }
}

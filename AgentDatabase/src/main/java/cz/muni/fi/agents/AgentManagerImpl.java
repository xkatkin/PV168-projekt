package cz.muni.fi.agents;

import javax.naming.OperationNotSupportedException;
import java.util.List;

/**
 * @author Slavomir Katkin
 */
public class AgentManagerImpl implements AgentManager {
    private long idCounter = 0;

    public AgentManagerImpl() {
    }

    @Override
    public Agent createAgent(Agent agent) {
        throw new UnsupportedOperationException("no implementation");
    }

    @Override
    public Agent updateAgent(Agent agent) {
        throw new UnsupportedOperationException("no implementation");
    }

    @Override
    public Boolean deleteAgent(long agentId) {
        throw new UnsupportedOperationException("no implementation");
    }

    @Override
    public Agent findAgentById(long agentId) {
        throw new UnsupportedOperationException("no implementation");
    }

    @Override
    public List<Agent> findAllAgents() {
        throw new UnsupportedOperationException("no implementation");
    }


}

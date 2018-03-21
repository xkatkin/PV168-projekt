package cz.muni.fi.agents;

/**
 * @author Slavomir Katkin
 */
public class AgentBuilder {
    private long id;
    private String fullName;
    private String secretName;
    private Equipment equipment;

    public Agent build() {
        Agent agent = new Agent();
        agent.setId(id);
        agent.setFullName(fullName);
        agent.setSecretName(secretName);
        agent.setEquipment(equipment);
        return agent;
    }

    public AgentBuilder id(long id) {
        this.id = id;
        return this;
    }

    public AgentBuilder fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public AgentBuilder secretName(String secretName) {
        this.secretName = secretName;
        return this;
    }

    public AgentBuilder equipment(Equipment equipment) {
        this.equipment = equipment;
        return this;
    }
}

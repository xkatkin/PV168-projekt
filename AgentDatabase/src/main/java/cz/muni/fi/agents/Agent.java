package cz.muni.fi.agents;

/**
 * @author Slavomir Katkin
 */
public class Agent {
    private long id;
    private String fullName;
    private String secretName;
    private Equipment equipment;

    public Agent(long id, String fullName, String secretName, Equipment equipment) {
        this.id = id;
        this.fullName = fullName;
        this.secretName = secretName;
        this.equipment = equipment;
    }

    public Agent() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    //TODO:
    //tostring


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agent agent = (Agent) o;

        return id == agent.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}

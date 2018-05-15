package cz.muni.fi.agents;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Slavomir Katkin
 */
public class Agent {
    private ResourceBundle bundle = ResourceBundle.getBundle("lan", Locale.getDefault());
    private Long id;
    private String fullName;
    private String secretName;
    private Equipment equipment;

    public Agent(Long id, String fullName, String secretName, Equipment equipment) {
        this.id = id;
        this.fullName = fullName;
        this.secretName = secretName;
        this.equipment = equipment;

    }

    public Agent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return String.format(bundle.getString("AgentInfo"), id, fullName, secretName, equipment);
    }

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

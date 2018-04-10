package cz.muni.fi.agents;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Slavomir Katkin
 */
public class AgentManagerImpl implements AgentManager {
    private JdbcTemplate jdbc;

    public AgentManagerImpl(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    private RowMapper<Agent> agentMapper = (rs, rowNum) ->
            new Agent(
                    rs.getLong("id"),
                    rs.getString("fullName"),
                    rs.getString("secretName"),
                    Enum.valueOf(Equipment.class, rs.getString("equipment")));

    private boolean hasNulls(Agent agent) {
        return (agent == null ||
                agent.getFullName() == null ||
                agent.getSecretName() == null ||
                agent.getEquipment() == null);
    }

    public void createAgent(Agent agent) {
        if(hasNulls(agent)) {
            throw new IllegalArgumentException("Cannot create agent with null parameters");
        }
        if( agent.getId() != 0L) {
            throw new IllegalArgumentException("Agent already exists within database");
        }

        SimpleJdbcInsert insertAgent = new SimpleJdbcInsert(jdbc)
                .withTableName("agents").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("fullName", agent.getFullName())
                .addValue("secretName", agent.getSecretName())
                .addValue("equipment", agent.getEquipment());

        Number id = insertAgent.executeAndReturnKey(parameters);
        agent.setId(id.longValue());
    }

    public void updateAgent(Agent agent) {
        if(hasNulls(agent)) {
            throw new IllegalArgumentException("Cannot update with null parameters");
        }
        jdbc.update("UPDATE agents SET fullName=?,secretName=?,equipment=? WHERE id=?",
                agent.getFullName(),
                agent.getSecretName(),
                agent.getEquipment().name(),
                agent.getId());
    }

    public boolean deleteAgent(Long agentId) {
        return jdbc.update("DELETE FROM agents WHERE id=?", agentId) == 1;
    }

    public Agent findAgentById(Long agentId) {
        try {
            return jdbc.queryForObject("SELECT * FROM agents WHERE id=?", agentMapper, agentId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No such agent in database");
        }
    }

    public List<Agent> findAllAgents() {
        return jdbc.query("SELECT * FROM agents", agentMapper);
    }


}

package cz.muni.fi.agents;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
    private final static Logger log = LoggerFactory.getLogger(AgentManagerImpl.class);
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
            log.error("Agent {} with null arguments", agent);
            throw new IllegalArgumentException("Cannot create agent with null parameters");
        }
        if( agent.getId() != 0L) {
            log.error("Agent {} with illegal ID", agent);
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
        log.debug("Created angent {}", agent);
    }

    public void updateAgent(Agent agent) {
        if(hasNulls(agent)) {
            log.error("Agent {} with null arguments", agent);
            throw new IllegalArgumentException("Cannot update with null parameters");
        }
        jdbc.update("UPDATE agents SET fullName=?,secretName=?,equipment=? WHERE id=?",
                agent.getFullName(),
                agent.getSecretName(),
                agent.getEquipment().name(),
                agent.getId());
        log.debug("Updated angent {}", agent);
    }

    public boolean deleteAgent(Long agentId) {
        log.debug("Deleted angent with ID {}", agentId);
        return jdbc.update("DELETE FROM agents WHERE id=?", agentId) == 1;
    }

    public Agent findAgentById(Long agentId) {
        try {
            log.debug("Finding agent with ID {}", agentId);
            return jdbc.queryForObject("SELECT * FROM agents WHERE id=?", agentMapper, agentId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            log.error("No agent with ID {}", agentId);
            throw new IllegalArgumentException("No such agent in database");
        }
    }

    public List<Agent> findAllAgents() {
        log.debug("Finding all agents");
        return jdbc.query("SELECT * FROM agents", agentMapper);
    }


}

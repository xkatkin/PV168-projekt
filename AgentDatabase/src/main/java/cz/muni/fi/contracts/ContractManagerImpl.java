package cz.muni.fi.contracts;

import cz.muni.fi.agents.AgentManagerImpl;
import cz.muni.fi.missions.MissionManagerImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Slavomir Katkin
 */
public class ContractManagerImpl implements ContractManager{
    private JdbcTemplate jdbc;
    private LocalDate now;

    private AgentManagerImpl agentManager;
    private MissionManagerImpl missionManager;


    public ContractManagerImpl(DataSource dataSource, LocalDate now) {
        this.jdbc = new JdbcTemplate(dataSource);
        this.missionManager = new MissionManagerImpl(dataSource);
        this.agentManager = new AgentManagerImpl(dataSource);
        this.now = now;
    }

    private boolean hasNulls(Contract contract) {
        return (contract == null ||
                contract.getAgent() == null ||
                contract.getMission() == null ||
                contract.getStartDate() == null ||
                contract.getEndDate() == null);
    }

    private RowMapper<Contract> contractMapper = (rs, rowNum) ->
            new Contract(rs.getLong("id"),
                    LocalDate.parse(rs.getString("startDate")),
                    LocalDate.parse(rs.getString("endDate")),
                    agentManager.findAgentById(rs.getLong("agent")),
                    missionManager.findMissionByid(rs.getLong("mission")));

    private boolean hasInvalidDates(Contract contract) {
        return contract.getStartDate().isAfter(contract.getEndDate()) &&
                contract.getEndDate().isBefore(now);
    }

    //I GUESS LOL
    private boolean hasInvalidAgent(Contract contract) {
        List<Contract> agentsContracts = findContractsByAgentId(contract.getAgent().getId());
        boolean invalid = false;
        for(Contract agentsContract : agentsContracts) {
            invalid = invalid ||
                    //newStart oldStart newEnd oldEnd
                    (agentsContract.getEndDate().isAfter(contract.getStartDate()) &&
                    agentsContract.getEndDate().isBefore(contract.getEndDate())) ||
                    //oldStart newStart oldEnd newEnd
                    (agentsContract.getStartDate().isBefore(contract.getEndDate()) &&
                     agentsContract.getEndDate().isBefore(contract.getEndDate())) ||
                    // newStart oldStart oldEnd newEnd
                    (agentsContract.getStartDate().isAfter(contract.getStartDate()) &&
                    agentsContract.getEndDate().isBefore(contract.getEndDate())) ||
                    //oldStart newStart newEnd oldEnd
                    (agentsContract.getStartDate().isBefore(contract.getStartDate()) &&
                    agentsContract.getEndDate().isAfter(agentsContract.getEndDate()));
        }
        return invalid || agentManager.findAgentById(contract.getAgent().getId()) != null;
    }

    private boolean hasInvalidMission(Contract contract) {
        return findContractByMissionId(contract.getMission().getId()) != null ||
                missionManager.findMissionByid(contract.getMission().getId()) == null;
    }

    @Override
    public void createContract(Contract contract) {
        if(hasNulls(contract) ||
                contract.getId() != null ||
                hasInvalidDates(contract) ||
                hasInvalidAgent(contract) ||
                hasInvalidMission(contract)) {
            throw new IllegalArgumentException("Cannot create contract");
        }

        SimpleJdbcInsert insertContract = new SimpleJdbcInsert(jdbc)
                .withTableName("contracts").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("startDate", contract.getStartDate())
                .addValue("endDate", contract.getEndDate() )
                .addValue("agent", contract.getAgent().getId())
                .addValue("mission", contract.getMission().getId());

        Number id = insertContract.executeAndReturnKey(parameters);
        contract.setId(id.longValue());
    }

    @Override
    public void updateContract(Contract contract) {
        if(hasNulls(contract)) {
            throw new IllegalArgumentException("Cannot update with null parameters");
        }
        jdbc.update("UPDATE missions set startDate=?,endDate=?,agent=?,mission=? where id=?",
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getAgent().getId(),
                contract.getMission().getId());
    }

    @Override
    public boolean deleteContract(Long contractId) {
        return jdbc.update("DELETE FROM contracts WHERE id=?", contractId) == 1;
    }

    @Override
    public Contract findContractById(Long contractId) {
        return jdbc.queryForObject("SELECT * FROM contracts WHERE id=?", contractMapper, contractId);
    }

    @Override
    public List<Contract> findContractsByAgentId(Long agentId) {
        return jdbc.query("SELECT * FROM contracts WHERE agent=?", contractMapper, agentId);
    }

    @Override
    public Contract findContractByMissionId(Long missionId) {
        return jdbc.queryForObject("SELECT * FROM contracts WHERE agent=?", contractMapper, missionId);
    }

    @Override
    public List<Contract> findAllContracts() {
        return jdbc.query("SELECT * FROM contracts", contractMapper);
    }
}

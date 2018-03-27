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
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Slavomir Katkin
 */
public class ContractManagerImpl implements ContractManager{
    private JdbcTemplate jdbc;
    private LocalDate now;

    private AgentManagerImpl agentManager;
    private MissionManagerImpl missionManager;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy");

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
                    LocalDate.parse(rs.getString("startDate"), dateFormatter),
                    LocalDate.parse(rs.getString("endDate"), dateFormatter),
                    agentManager.findAgentById(rs.getLong("agent")),
                    missionManager.findMissionByid(rs.getLong("mission")));

    private boolean hasInvalidDates(Contract contract) {
        return contract.getStartDate().isAfter(contract.getEndDate()) ||
                contract.getEndDate().isBefore(now) ||
                contract.getMission().getDeadline().isBefore(contract.getEndDate() );
    }

    private boolean inRange(LocalDate begin, LocalDate val, LocalDate end){
        return begin.isBefore(val) && end.isAfter(val) && begin.isBefore(end);
    }


    private boolean hasInvalidAgent(Contract contract) {
        List<Contract> agentsContracts = findContractsByAgentId(contract.getAgent().getId());
        //situations, when dates are in conflict (4 cases solves all options)
        for(Contract agentsContract : agentsContracts) {
              //oldStart newEnd oldEnd
              if (inRange(agentsContract.getStartDate(), contract.getEndDate(), agentsContract.getEndDate())){
                  return false;
              }
              //oldStart newStart oldEnd
              if (inRange(agentsContract.getStartDate(), contract.getStartDate(), agentsContract.getEndDate())){
                  return false;
              }
              //newStart oldEnd newEnd
              if (inRange(contract.getStartDate(), agentsContract.getEndDate(), contract.getEndDate())){
                return false;
              }
              //newStart oldStart newEnd
              if (inRange(contract.getStartDate(), agentsContract.getStartDate(), contract.getEndDate())){
                return false;
              }
        }
        return agentManager.findAgentById(contract.getAgent().getId()) != null;
    }


    private boolean hasInvalidMission(Contract contract) {
        return findContractByMissionId(contract.getMission().getId()) != null ||
                missionManager.findMissionByid(contract.getMission().getId()) == null;
    }

    @Override
    public void createContract(Contract contract) {
        if(hasNulls(contract) ||
                contract.getId() != 0 ||
                hasInvalidDates(contract) ||
                hasInvalidAgent(contract) ||
                hasInvalidMission(contract)) {
            throw new IllegalArgumentException("Cannot create contract");
        }

        SimpleJdbcInsert insertContract = new SimpleJdbcInsert(jdbc)
                .withTableName("contracts").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("startDate", dateFormatter.format(contract.getStartDate()))
                .addValue("endDate", dateFormatter.format(contract.getEndDate()))
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
        return jdbc.queryForObject("SELECT * FROM contracts WHERE mission=?", contractMapper, missionId);
    }

    @Override
    public List<Contract> findAllContracts() {
        return jdbc.query("SELECT * FROM contracts", contractMapper);
    }
}

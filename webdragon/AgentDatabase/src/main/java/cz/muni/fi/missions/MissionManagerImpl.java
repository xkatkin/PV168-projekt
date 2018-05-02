package cz.muni.fi.missions;

import cz.muni.fi.agents.Equipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author Samuel Gorta
 */
public class MissionManagerImpl implements MissionManager{
    private final static Logger log = LoggerFactory.getLogger(MissionManagerImpl.class);
    private JdbcTemplate jdbc;

    public MissionManagerImpl(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy");

    private RowMapper<Mission> missionMapper = (rs, rowNum) ->
            new Mission(rs.getLong("id"),
                    rs.getString("target"),
                    Enum.valueOf(Equipment.class, rs.getString("necessaryEquipment")),
                    LocalDate.parse(rs.getString("deadline"), dateFormatter));

    private boolean hasNulls(Mission mission) {
        return (mission == null ||
                mission.getTarget() == null ||
                mission.getDeadline() == null ||
                mission.getNecessaryEquipment() == null);
    }

    @Override
    public void createMission(Mission mission){
        if(hasNulls(mission) || mission.getId() != 0) {
            throw new IllegalArgumentException("Cannot create mission");
        }

        SimpleJdbcInsert insertMission = new SimpleJdbcInsert(jdbc)
            .withTableName("missions").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("target", mission.getTarget())
                .addValue("necessaryEquipment", mission.getNecessaryEquipment())
                .addValue("deadline", dateFormatter.format(mission.getDeadline()));

        Number id = insertMission.executeAndReturnKey(parameters);
        mission.setId(id.longValue());
        log.debug("Creating mission {}", mission);
    }

    public void updateMission(Mission mission){
        if(hasNulls(mission)) {
            log.error("Updating mission {} with null arguments", mission);
            throw new IllegalArgumentException("Cannot update with null parameters");
        }
        boolean b = jdbc.update("UPDATE missions set target=?,necessaryEquipment=?,deadline=? where id=?",
             mission.getTarget(),
             mission.getNecessaryEquipment().name(),
             dateFormatter.format(mission.getDeadline()),
             mission.getId()) == 1;

        if (!b){
            log.error("Updating mission {} with illegal sql", mission);
            throw new IllegalArgumentException("Updating not existing mission.");
        }
        log.debug("Updated mission {}", mission);
    }

    public boolean deleteMission(Long missionId){
    if ( jdbc.update("DELETE FROM missions WHERE id=?", missionId) != 1){
        log.error("Unsuccessful deletion of mission with ID {}", missionId);
        throw new IllegalArgumentException("Invalid mission ID.");
    }
    log.debug("Deleted mission with ID {}", missionId);
    return true;
    }

    public Mission findMissionByid(Long missionId){
        try {
            log.debug("Finding mission with ID {}", missionId);
            return jdbc.queryForObject("SELECT * FROM missions WHERE id=?", missionMapper, missionId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e){
            log.error("Error while finding mission with ID {}", missionId);
            throw new IllegalArgumentException("No Mission in database.");
        }
    }

    @Override
    public List<Mission> findAllMissions(){
        log.debug("Finding all missions");
        return jdbc.query("SELECT * FROM missions", missionMapper);
    }

}

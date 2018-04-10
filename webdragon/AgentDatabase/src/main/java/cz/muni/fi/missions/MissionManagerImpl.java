package cz.muni.fi.missions;

import cz.muni.fi.agents.Equipment;
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
    }

    public void updateMission(Mission mission){
        if(hasNulls(mission)) {
            throw new IllegalArgumentException("Cannot update with null parameters");
        }
        boolean b = jdbc.update("UPDATE missions set target=?,necessaryEquipment=?,deadline=? where id=?",
             mission.getTarget(),
             mission.getNecessaryEquipment().name(),
             dateFormatter.format(mission.getDeadline()),
             mission.getId()) == 1;

        if (!b){
            throw new IllegalArgumentException("Updating not existing mission.");
        }
    }

    public boolean deleteMission(Long missionId){
    if ( jdbc.update("DELETE FROM missions WHERE id=?", missionId) != 1){
        throw new IllegalArgumentException("Invalid mission ID.");
    }
    return true;
    }

    public Mission findMissionByid(Long missionId){
        try {
            return jdbc.queryForObject("SELECT * FROM missions WHERE id=?", missionMapper, missionId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e){
            throw new IllegalArgumentException("No Mission in database.");
        }
    }

    @Override
    public List<Mission> findAllMissions(){
        return jdbc.query("SELECT * FROM missions", missionMapper);
    }

}

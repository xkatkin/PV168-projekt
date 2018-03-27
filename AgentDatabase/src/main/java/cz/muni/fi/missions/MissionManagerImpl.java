package cz.muni.fi.missions;

import cz.muni.fi.agents.Equipment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Samuel Gorta
 */
public class MissionManagerImpl implements MissionManager{
    private JdbcTemplate jdbc;

    public MissionManagerImpl(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    public MissionManagerImpl() {

    }


    public void createMission(Mission mission){
        //TODO: if-y
        SimpleJdbcInsert insertMission = new SimpleJdbcInsert(jdbc)
                .withTableName("missions").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("target", mission.getTarget())
                .addValue("necessaryEquipment", mission.getNecesarryEquipment())
                .addValue("deadline", mission.getDeadline());

        Number id = insertMission.executeAndReturnKey(parameters);
        mission.setId(id.longValue());
    }
/*
    private RowMapper<Mission> missionMapper = (rs, rowNum) ->
            new Mission(rs.getLong("id"),
                    rs.getString("target"),
                    Enum.valueOf(Equipment.class, rs.getString("necessaryEquipment"));
*/
    public void updateMission(Mission mission){
        jdbc.update("UPDATE missions set target=?,necessaryEquipment=?,deadline=? where id=?",
                mission.getTarget(),
                mission.getNecesarryEquipment(),
                mission.getDeadline(),
                mission.getId());
    }

    public boolean deleteMission(long missionId){
        return jdbc.update("DELETE FROM customers WHERE id=?", missionId) == 1;
    }

    public Mission findMissionByid(long missionId){
        throw new UnsupportedOperationException("no implementation");
    }

    public List<Mission> findAllMissions(){
        throw new UnsupportedOperationException("no implementation");
    }

}

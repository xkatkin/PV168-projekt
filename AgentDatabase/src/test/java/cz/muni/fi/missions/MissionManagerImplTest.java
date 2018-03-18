package cz.muni.fi.missions;

import cz.muni.fi.agents.Equipment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.Month;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * @author Samuel Gorta
 */
public class MissionManagerImplTest {
    private MissionManagerImpl missionManager;
    private DataSource ds;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        missionManager = new MissionManagerImpl();
    }

    private MissionBuilder testMission1(){
        return new MissionBuilder().target("Buckingham Palace")
                                   .necesaryEquipmnt(Equipment.BADASSCAR)
                                   .deadline(LocalDate.of(2018, Month.JUNE, 14));
    }

    private MissionBuilder testMission2(){
        return new MissionBuilder().target("Bratislava's UFO")
                                   .necesaryEquipmnt(Equipment.MOJITO)
                                   .deadline(LocalDate.of(2027, Month.APRIL, 1));
    }

    private MissionBuilder testMission3(){
        return new MissionBuilder().target("FI at MUNI")
                                   .necesaryEquipmnt(Equipment.CHARMINGCOMPANION)
                                   .deadline(LocalDate.of(2018, Month.MARCH, 24));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullAgent () throws Exception {
        missionManager.createMission(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMissionWithoutTarget () throws Exception {
        Mission mission = new MissionBuilder()
                .target(null)
                .necesaryEquipmnt(Equipment.GUN)
                .deadline(LocalDate.of(2018, Month.MARCH, 24))
                .build();
        missionManager.createMission(mission);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMissionWithoutNecesaryEquipment () throws Exception {
        Mission mission = new MissionBuilder()
                .target("Ultra Super Meno")
                .necesaryEquipmnt(null)
                .deadline(LocalDate.of(2018, Month.MARCH, 24))
                .build();
        missionManager.createMission(mission);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMissionWithoutDeadline () throws Exception {
        Mission mission = new MissionBuilder()
                .target("Ultra Super Meno")
                .necesaryEquipmnt(Equipment.SNIPERRIFLE)
                .deadline(null)
                .build();
        missionManager.createMission(mission);
    }

    @Test
    public void simplyCreateMissions ()  {
        Mission mission1 = testMission1().build();
        missionManager.createMission(mission1);
        assertFalse(missionManager.findAllMissions().isEmpty());
        assertTrue(missionManager.findAllMissions().contains(mission1));
    }

    @Test
    public void createMissions ()  {
        Mission mission1 = testMission1().build();
        Mission mission2 = testMission2().build();
        Mission mission3 = testMission3().build();

        missionManager.createMission(mission1);
        missionManager.createMission(mission2);
        missionManager.createMission(mission3);

        assertFalse(missionManager.findAllMissions().isEmpty());
        assertTrue(missionManager.findAllMissions().contains(mission1));
        assertTrue(missionManager.findAllMissions().contains(mission2));
        assertTrue(missionManager.findAllMissions().contains(mission3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMissionsWithDuplicateId () throws Exception {
        Mission mission1 = testMission1().build();
        missionManager.createMission(mission1);
        Mission mission2 = testMission2().id(mission1.getId()).build();
        missionManager.createMission(mission2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDuplicatedMissions () throws Exception {
        Mission mission1 = testMission1().build();
        missionManager.createMission(mission1);
        missionManager.createMission(mission1);
    }

    @Test
    public void simplyDeleteMission ()  {
        Mission mission1 = testMission1().build();
        missionManager.createMission(mission1);
        assertFalse(missionManager.findAllMissions().isEmpty());
        assertTrue(missionManager.findAllMissions().contains(mission1));

        missionManager.deleteMission(mission1.getId());
        assertTrue(missionManager.findAllMissions().isEmpty());
        assertFalse(missionManager.findAllMissions().contains(mission1));
    }

    @Test
    public void complexDeleteMission ()  {
        Mission mission1 = testMission1().build();
        Mission mission2 = testMission2().build();
        Mission mission3 = testMission3().build();

        missionManager.createMission(mission1);
        missionManager.createMission(mission2);
        missionManager.createMission(mission3);

        assertFalse(missionManager.findAllMissions().isEmpty());
        assertTrue(missionManager.findAllMissions().contains(mission1));

        missionManager.deleteMission(mission1.getId());
        assertFalse(missionManager.findAllMissions().contains(mission1));
        assertFalse(missionManager.findAllMissions().isEmpty());

        missionManager.deleteMission(mission2.getId());
        assertFalse(missionManager.findAllMissions().contains(mission2));
        assertFalse(missionManager.findAllMissions().isEmpty());

        missionManager.deleteMission(mission3.getId());
        assertFalse(missionManager.findAllMissions().contains(mission3));
        assertTrue(missionManager.findAllMissions().isEmpty());
    }

    @Test
    public void deleteAndAddMission ()  {
        Mission mission1 = testMission1().build();
        Mission mission2 = testMission2().build();
        Mission mission3 = testMission3().build();

        missionManager.createMission(mission1);
        missionManager.createMission(mission2);
        missionManager.createMission(mission3);

        assertFalse(missionManager.findAllMissions().isEmpty());
        assertTrue(missionManager.findAllMissions().contains(mission1));

        missionManager.deleteMission(mission1.getId());
        assertFalse(missionManager.findAllMissions().contains(mission1));
        assertFalse(missionManager.findAllMissions().isEmpty());

        missionManager.createMission(mission1);
        assertFalse(missionManager.findAllMissions().isEmpty());
        assertTrue(missionManager.findAllMissions().contains(mission1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteInvalidIdMission ()  {
        Mission mission1 = testMission1().build();
        Mission mission2 = testMission2().build();
        Mission mission3 = testMission3().build();

        missionManager.createMission(mission1);
        missionManager.createMission(mission2);
        missionManager.deleteMission(mission3.getId());
    }

    @Test
    public void simplyFindAllMissions ()  {
        Mission mission1 = testMission1().build();
        Mission mission2 = testMission2().build();
        Mission mission3 = testMission3().build();

        assertTrue(missionManager.findAllMissions().isEmpty());

        missionManager.createMission(mission1);
        assertTrue(missionManager.findAllMissions().size() == 1);
        assertTrue(missionManager.findAllMissions().contains(mission1));

        missionManager.createMission(mission2);
        missionManager.createMission(mission3);
        assertTrue(missionManager.findAllMissions().size() == 3);
        assertTrue(missionManager.findAllMissions().contains(mission2));
    }

    @Test
    public void simplyFindMissionById ()  {
        Mission mission1 = testMission1().build();
        Mission mission2 = testMission2().build();
        Mission mission3 = testMission3().build();

        missionManager.createMission(mission1);
        assertEquals(missionManager.findMissionByid(mission1.getId()), mission1);
    }

    @Test
    public void findMissionByIdWithMoreMissions ()  {
        Mission mission1 = testMission1().build();
        Mission mission2 = testMission2().build();
        Mission mission3 = testMission3().build();

        missionManager.createMission(mission1);
        missionManager.createMission(mission2);
        missionManager.createMission(mission3);
        assertEquals(missionManager.findMissionByid(mission1.getId()), mission1);
        assertEquals(missionManager.findMissionByid(mission2.getId()), mission2);
        assertEquals(missionManager.findMissionByid(mission3.getId()), mission3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findMissionByInvalidId ()  {
        Mission mission1 = testMission1().build();
        Mission mission2 = testMission2().build();

        missionManager.createMission(mission1);
        assertEquals(missionManager.findMissionByid(mission2.getId()), mission1);
    }

    @Test
    public void simplyUpdateMission() throws Exception {
        Mission mission1 = testMission1().build();
        Mission mission2 = testMission2().build();

        missionManager.createMission(mission1);
        missionManager.createMission(mission2);

        mission1.setTarget("NewTarget");
        missionManager.updateMission(mission1);

        assertTrue(missionManager.findAllMissions().size() == 2);
        assertEquals(missionManager.findMissionByid(mission1.getId()), mission1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNonExistingMission() throws Exception {
        Mission mission1 = testMission1().build();
        Mission mission2 = testMission2().build();

        missionManager.createMission(mission1);
        missionManager.updateMission(mission2);
    }
}

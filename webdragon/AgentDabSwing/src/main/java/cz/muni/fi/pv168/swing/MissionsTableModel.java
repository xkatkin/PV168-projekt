package cz.muni.fi.pv168.swing;


import cz.muni.fi.missions.Mission;
import cz.muni.fi.missions.MissionManagerImpl;
import cz.muni.fi.agents.Equipment;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.*;


public class MissionsTableModel extends AbstractTableModel {
    private ResourceBundle bundle = ResourceBundle.getBundle("lan", Locale.getDefault());
    private MissionManagerImpl missionManager = new MissionManagerImpl(new Data().getDataSource());


    @Override
    public int getRowCount() {
        return missionManager.findAllMissions().size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    public Collection<Mission> getAllMissions() {
        return missionManager.findAllMissions();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mission mission = getMission(rowIndex);
        switch (columnIndex) {
            case 0:
                return mission.getId();
            case 1:
                return mission.getTarget();
            case 2:
                return mission.getNecessaryEquipment();
            case 3:
                return mission.getDeadline();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return bundle.getString("Id");
            case 1:
                return  bundle.getString("Target");
            case 2:
                return  bundle.getString("Necessary Equipment");
            case 3:
                return  bundle.getString("Deadline");
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Long.class;
            case 1:
                return String.class;
            case 2:
                return Equipment.class;
            case 3:
                return LocalDate.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    public Mission getMission(int row) {
        return missionManager.findAllMissions().get(row);
    }

    public void addMission(Mission mission) {
        missionManager.createMission(mission);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void removeMission(int[] rows) {
        List<Long> toDelete = new ArrayList<>();
        for(int i : rows) {
            toDelete.add(getMission(i).getId());
        }
        for(long j : toDelete) {
            missionManager.deleteMission(j);
        }

        fireTableDataChanged();

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Mission mission = getMission(rowIndex);
        switch (columnIndex) {
            case 0:
                mission.setId((Long) aValue);
                break;
            case 1:
                mission.setTarget((String) aValue);
                break;
            case 2:
                mission.setNecessaryEquipment(Equipment.valueOf((String) aValue));
                break;
            case 3:
                mission.setDeadline(LocalDate.parse((String)aValue));
                break;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
        missionManager.updateMission(mission);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 1:
            case 2:
            case 3:
                return true;
            case 0:
                return false;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }



}


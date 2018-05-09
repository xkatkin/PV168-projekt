package cz.muni.fi.pv168.swing;


import cz.muni.fi.agents.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class AgentsTableModel extends AbstractTableModel {

    private AgentManagerImpl agentManager = new AgentManagerImpl(new Data().dataSource());


    @Override
    public int getRowCount() {
        return agentManager.findAllAgents().size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Agent agent = getAgent(rowIndex);
        switch (columnIndex) {
            case 0:
                return agent.getId();
            case 1:
                return agent.getFullName();
            case 2:
                return agent.getSecretName();
            case 3:
                return agent.getEquipment();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Id";
            case 1:
                return "Full Name";
            case 2:
                return "Secret Name";
            case 3:
                return "Equipment";
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
            case 2:
                return String.class;
            case 3:
                return Equipment.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    public Agent getAgent(int row) {
        return agentManager.findAllAgents().get(row);
    }

    public void addAgent(Agent agent) {
        agentManager.createAgent(agent);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void removeAgent(int[] rows) {
        List<Long> toDelete = new ArrayList<>();
        for(int i : rows) {
            toDelete.add(getAgent(i).getId());
        }
        for(long j : toDelete) {
            agentManager.deleteAgent(j);
        }

        fireTableDataChanged();

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Agent agent = getAgent(rowIndex);
        switch (columnIndex) {
            case 0:
                agent.setId((Long) aValue);
                break;
            case 1:
                agent.setFullName((String) aValue);
                break;
            case 2:
                agent.setSecretName((String) aValue);
                break;
            case 3:
                agent.setEquipment((Equipment) aValue);
                break;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
        agentManager.updateAgent(agent);
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


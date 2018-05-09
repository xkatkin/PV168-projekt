package cz.muni.fi.pv168.swing;


import cz.muni.fi.agents.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AgentsTableModel extends AbstractTableModel {

    private List<Agent> agents = new ArrayList<Agent>();

    @Override
    public int getRowCount() {
        return agents.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Agent agent = agents.get(rowIndex);
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
        return agents.get(row);
    }

    public void addAgent(Agent agent) {
        agents.add(agent);
        fireTableRowsInserted(agents.size() - 1, agents.size() - 1);
    }

    public void removeAgent(int[] rows) {
        for(int i = 0; i < rows.length; i++) {
            agents.remove(rows[i]);
            fireTableRowsDeleted(i,i);
        }

    }

    private Random random = new Random();
    private static final String[] AGENT_FULL = {"James Bond", "Will Smith", "Joe Bart", "El Homo", "Wut McWutface"};
    private static final String[] AGENT_SECRET = {"001", "002", "003", "004","005","006"};
    private static final Equipment[] AGENT_EQUIPMENT = Equipment.values();

    public Agent randomAgent() {
        return new Agent(
                random.nextLong() % 10,
                AGENT_FULL[random.nextInt(AGENT_FULL.length)],
                AGENT_SECRET[random.nextInt(AGENT_SECRET.length)],
                AGENT_EQUIPMENT[random.nextInt(AGENT_EQUIPMENT.length)]);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Agent agent = agents.get(rowIndex);
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


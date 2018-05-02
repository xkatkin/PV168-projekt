package cz.muni.fi.pv168.swing;


import cz.muni.fi.agents.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

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

    public void addAgent(Agent agent) {
        agents.add(agent);
        fireTableRowsInserted(agents.size() - 1, agents.size() - 1);
    }
}


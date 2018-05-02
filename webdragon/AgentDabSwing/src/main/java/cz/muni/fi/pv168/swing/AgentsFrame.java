package cz.muni.fi.pv168.swing;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import cz.muni.fi.agents.Agent;
import cz.muni.fi.agents.Equipment;
import cz.muni.fi.pv168.swing.AgentsTableModel;

public class AgentsFrame {
    private JPanel mainPanel;
    private JTable agentTable;
    private JPanel tablePanel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTable getAgentTable() {
        return agentTable;
    }

    public JPanel getTablePanel() {
        return tablePanel;
    }

    public AgentsFrame() {
        AgentsTableModel model = (AgentsTableModel) agentTable.getModel();
        model.addAgent(new Agent(1L, "James", "007", Equipment.MOJITO));
        model.addAgent(new Agent(2L, "Ja", "Ja", Equipment.BADASSCAR));

    }

    private void createUIComponents() {
        agentTable = new JTable();
        agentTable.setModel(new AgentsTableModel());
    }
}

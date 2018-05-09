package cz.muni.fi.pv168.swing;

import javax.swing.*;

import cz.muni.fi.agents.Agent;
import cz.muni.fi.agents.Equipment;
import cz.muni.fi.pv168.swing.AgentsTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class AgentsFrame {
    private JPanel mainPanel;
    private JTable agentTable;
    private JButton addAgentButton;
    private JButton removeAgentButton;
    private JTextField createSecretName;
    private JTextField createFullName;
    private JComboBox createEquipment;
    private JPanel createAgentPanel;
    private JPanel buttonPanel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTable getAgentTable() {
        return agentTable;
    }

    public AgentsFrame() {
        AgentsTableModel model = (AgentsTableModel) agentTable.getModel();

        addAgentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgentsTableModel model = (AgentsTableModel) agentTable.getModel();
                Agent agent = new Agent(
                        0L,
                        createFullName.getText(),
                        createSecretName.getText(),
                        (Equipment)createEquipment.getSelectedItem());
                model.addAgent(agent);
            }
        });
        removeAgentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgentsTableModel model = (AgentsTableModel) agentTable.getModel();
                model.removeAgent(agentTable.getSelectedRows());

            }
        });
    }

    private void createUIComponents() {
        agentTable = new JTable();
        agentTable.setModel(new AgentsTableModel());
        JComboBox<Equipment> equipmentJComboBox = new JComboBox<>(Equipment.values());
        agentTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(equipmentJComboBox));
        createEquipment = new JComboBox<>();
        createEquipment.setModel(new DefaultComboBoxModel(Equipment.values()));


    }

    public JButton getAddAgentButton() {
        return addAgentButton;
    }


}

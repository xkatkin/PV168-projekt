package cz.muni.fi.pv168.swing;

import javax.sql.DataSource;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import cz.muni.fi.agents.Agent;
import cz.muni.fi.agents.AgentManagerImpl;
import cz.muni.fi.agents.Equipment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


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
    private JTextField filterField;
    private JComboBox filterBox;
    private JPanel filterPanel;



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
                Runnable addAgent = new Runnable() {
                    @Override
                    public void run() {
                        AgentsTableModel model = (AgentsTableModel) agentTable.getModel();
                        Agent agent = new Agent(
                                0L,
                                createFullName.getText(),
                                createSecretName.getText(),
                                (Equipment)createEquipment.getSelectedItem());
                        model.addAgent(agent);

                    }
                };
                new Thread(addAgent).start();

            }
        });
        removeAgentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgentsTableModel model = (AgentsTableModel) agentTable.getModel();
                model.removeAgent(agentTable.getSelectedRows());
                agentTable.clearSelection();

            }
        });
        filterField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RowFilter<AgentsTableModel, Object> rf;
                //If current expression doesn't parse, don't update.
                try {
                    rf = RowFilter.regexFilter(filterField.getText(), filterBox.getSelectedIndex());
                } catch (java.util.regex.PatternSyntaxException x) {
                    return;
                }
                TableRowSorter sorter = (TableRowSorter)agentTable.getRowSorter();
                sorter.setRowFilter(rf);
                agentTable.setRowSorter(sorter);
            }
        });
    }

    private void createUIComponents() {
        //main table
        agentTable = new JTable();
        agentTable.setModel(new AgentsTableModel());
        JComboBox<Equipment> equipmentJComboBox = new JComboBox<>(Equipment.values());
        agentTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(equipmentJComboBox));

        //sorter
        TableRowSorter sorter = new TableRowSorter<TableModel>(agentTable.getModel());
        agentTable.setRowSorter(sorter);
        //equipment box for create
        createEquipment = new JComboBox<>();
        createEquipment.setModel(new DefaultComboBoxModel(Equipment.values()));
        filterBox = new JComboBox<>();
        filterBox.addItem("ID");
        filterBox.addItem("Full Name");
        filterBox.addItem("Secret Name");
        filterBox.addItem("Equipment");
    }


    public JButton getAddAgentButton() {
        return addAgentButton;
    }


}

package cz.muni.fi.pv168.swing;

import cz.muni.fi.agents.Agent;
import cz.muni.fi.agents.AgentManagerImpl;
import cz.muni.fi.contracts.Contract;
import cz.muni.fi.missions.Mission;
import cz.muni.fi.missions.MissionManagerImpl;
import org.jdesktop.swingx.JXDatePicker;

import javax.sql.DataSource;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Slavomir Katkin
 */
public class ContractsFrame {
    private JPanel mainPanel;
    private JTable contractsTable;
    private JScrollPane contractPanel;
    private JButton createContract;
    private JButton removeContract;
    private JComboBox agentsComboBox;
    private JComboBox missionsComboBox;
    private JPanel buttonPanel;
    private JButton updateContract;
    private JPanel datePanel;
    private JPanel startPanel;
    private JPanel endPanel;
    private JPanel createComboPanel;


    public ContractsFrame() {
        ContractsTableModel model = (ContractsTableModel) contractsTable.getModel();
        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                AgentManagerImpl aManager = new AgentManagerImpl(new Data().getDataSource());
                for(Agent agent : aManager.findAllAgents()) {
                    agentsComboBox.addItem(agent.toString());
                }
                MissionManagerImpl mManager = new MissionManagerImpl(new Data().getDataSource());
                for(Mission mission : mManager.findAllMissions()) {
                    missionsComboBox.addItem(mission.toString());
                }
            }
        });
        createContract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate startDate = ((JXDatePicker) startPanel.getComponent(0)).getDate().toInstant().atZone((ZoneId.systemDefault())).toLocalDate();
                LocalDate endDate = ((JXDatePicker) endPanel.getComponent(0)).getDate().toInstant().atZone((ZoneId.systemDefault())).toLocalDate();
                Contract contract = new Contract(
                        0L,
                        startDate,
                        endDate,
                        model.getAgent(agentsComboBox.getSelectedIndex()),
                        model.getMission(missionsComboBox.getSelectedIndex()));
                try{
                    model.addContract(contract);
                } catch (IllegalArgumentException iAE){
                    JOptionPane.showMessageDialog(null,iAE.getMessage());
                }

            }
        });
        removeContract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.removeContract(contractsTable.getSelectedRows());
            }
        });
        updateContract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = contractsTable.getSelectedRow();
                Contract contract = model.getContract(row);
                LocalDate startDate = ((JXDatePicker) startPanel.getComponent(0)).getDate().toInstant().atZone((ZoneId.systemDefault())).toLocalDate();
                LocalDate endDate = ((JXDatePicker) endPanel.getComponent(0)).getDate().toInstant().atZone((ZoneId.systemDefault())).toLocalDate();

                contract.setStartDate(startDate);
                contract.setEndDate(endDate);
                contract.setAgent(model.getAgent(agentsComboBox.getSelectedIndex()));
                contract.setMission(model.getMission(missionsComboBox.getSelectedIndex()));
                model.updateContract(contract, row);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        contractsTable = new JTable();
        contractsTable.setModel(new ContractsTableModel());
        TableRowSorter sorter = new TableRowSorter<TableModel>(contractsTable.getModel());
        contractsTable.setRowSorter(sorter);
        startPanel = new JPanel();
        startPanel.add(new JXDatePicker(Calendar.getInstance().getTime()));
        endPanel = new JPanel();
        endPanel.add(new JXDatePicker(Calendar.getInstance().getTime()));

    }

}


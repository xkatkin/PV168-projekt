package cz.muni.fi.pv168.swing;

import cz.muni.fi.missions.Mission;
import cz.muni.fi.agents.Equipment;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;


public class MissionsFrame {
    private JTable missionTable;
    private JButton removeMissionButton;
    private JButton addMissionButton;
    private JTextField createTarget;
    private JComboBox createNecEquipment;
    private JTextField filterField;
    private JComboBox filterBox;
    private JPanel mainPanel;
    private JPanel createMissionPanel;
    private JPanel filterPanel;
    private JPanel buttonPanel;
    private JPanel createDate;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTable getMissionTable() {
        return missionTable;
    }

    public MissionsFrame() {
        MissionsTableModel model = (MissionsTableModel) missionTable.getModel();

        addMissionButton.addActionListener(e -> {
            class AddMissionSwingWorker extends SwingWorker<Void,Void> {

                @Override
                protected Void doInBackground() throws Exception {
                    MissionsTableModel model1 = (MissionsTableModel) missionTable.getModel();
                    LocalDate endDate = ((JXDatePicker) createDate.getComponent(0)).getDate().toInstant().atZone((ZoneId.systemDefault())).toLocalDate();
                    Mission mission = new Mission(
                            0L,
                            createTarget.getText(),
                            (Equipment)createNecEquipment.getSelectedItem(),
                            endDate);
                    model1.addMission(mission);
                    return null;
                }
            }
            AddMissionSwingWorker addMissionSwingWorker = new AddMissionSwingWorker();
            addMissionSwingWorker.execute();
        });
        removeMissionButton.addActionListener(e -> {
            class RemoveMissionSwingWorker extends SwingWorker<Void,Void> {

                @Override
                protected Void doInBackground() throws Exception {
                    MissionsTableModel model12 = (MissionsTableModel) missionTable.getModel();
                    model12.removeMission(missionTable.getSelectedRows());
                    missionTable.clearSelection();
                    return null;
                }
            }
            RemoveMissionSwingWorker removeMissionSwingWorker = new RemoveMissionSwingWorker();
            removeMissionSwingWorker.execute();
        });
        filterField.addActionListener(e -> {
            class FilterFieldSwingWorker extends SwingWorker<Void,Void> {

                @Override
                protected Void doInBackground() throws Exception {
                    RowFilter<MissionsTableModel, Object> rf;
                    //If current expression doesn't parse, don't update.
                    try {
                        rf = RowFilter.regexFilter(filterField.getText(), filterBox.getSelectedIndex());
                    } catch (java.util.regex.PatternSyntaxException x) {
                        return null;
                    }
                    TableRowSorter sorter = (TableRowSorter)missionTable.getRowSorter();
                    sorter.setRowFilter(rf);
                    missionTable.setRowSorter(sorter);
                    return null;
                }
            }
            FilterFieldSwingWorker filterFieldSwingWorker = new FilterFieldSwingWorker();
            filterFieldSwingWorker.execute();
        });
    }

    private void createUIComponents() {
        //main table
        missionTable = new JTable();
        missionTable.setModel(new MissionsTableModel());
        JComboBox<Equipment> equipmentJComboBox = new JComboBox<>(Equipment.values());
        missionTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(equipmentJComboBox));

        //sorter
        TableRowSorter sorter = new TableRowSorter<TableModel>(missionTable.getModel());
        missionTable.setRowSorter(sorter);
        //equipment box for create
        createNecEquipment = new JComboBox<>();
        createNecEquipment.setModel(new DefaultComboBoxModel(Equipment.values()));
        filterBox = new JComboBox<>();
        filterBox.addItem("ID");
        filterBox.addItem("Target");
        filterBox.addItem("Necessary equipment");
        filterBox.addItem("Deadline");
        //createDate
        createDate = new JPanel();
        createDate.add(new JXDatePicker(Calendar.getInstance().getTime()));
    }

    public JButton getAddMissionButton() {
        return addMissionButton;
    }
}

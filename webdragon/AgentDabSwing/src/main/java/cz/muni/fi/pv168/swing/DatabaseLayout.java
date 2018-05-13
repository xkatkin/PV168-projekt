package cz.muni.fi.pv168.swing;

import javax.swing.*;

/**
 * @author Slavomir Katkin
 */
public class DatabaseLayout {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel Missions;
    private JPanel Agents;
    private JPanel Contracts;


    private void createUIComponents() {
        Missions = new MissionsFrame().getMainPanel();
        Agents = new AgentsFrame().getMainPanel();
        Contracts = new ContractsFrame().getMainPanel();
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public JPanel getMissions() {
        return Missions;
    }

    public JPanel getAgents() {
        return Agents;
    }

    public JPanel getContracts() {
        return Contracts;
    }

}

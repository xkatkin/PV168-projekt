package cz.muni.fi.pv168.swing;

import cz.muni.fi.agents.AgentManagerImpl;
import cz.muni.fi.contracts.ContractManagerImpl;
import cz.muni.fi.missions.MissionManagerImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import javax.swing.*;
import javax.xml.crypto.Data;

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

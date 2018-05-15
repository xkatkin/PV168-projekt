package cz.muni.fi.pv168.swing;

import cz.muni.fi.agents.Agent;
import cz.muni.fi.agents.AgentManagerImpl;
import cz.muni.fi.agents.Equipment;
import cz.muni.fi.contracts.Contract;
import cz.muni.fi.contracts.ContractManagerImpl;
import cz.muni.fi.missions.Mission;
import cz.muni.fi.missions.MissionManagerImpl;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Slavomir Katkin
 */
public class ContractsTableModel extends AgentsTableModel {
    private ResourceBundle bundle = ResourceBundle.getBundle("lan", Locale.getDefault());
    DataSource data = new Data().getDataSource();
    private ContractManagerImpl contractManager = new ContractManagerImpl(data, java.time.LocalDate.now());

    @Override
    public int getRowCount() {
        return contractManager.findAllContracts().size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    public Collection<Contract> getAllMissions() {
        return contractManager.findAllContracts();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Contract contract = getContract(rowIndex);
        switch (columnIndex) {
            case 0:
                return contract.getId();
            case 1:
                return contract.getStartDate();
            case 2:
                return contract.getEndDate();
            case 3:
                return contract.getAgent().getId();
            case 4:
                return contract.getMission().getId();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return bundle.getString("Id");
            case 1:
                return bundle.getString("Start Date");
            case 2:
                return bundle.getString("End Date");
            case 3:
                return bundle.getString("Agent Id");
            case 4:
                return bundle.getString("Mission Id");
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
                return LocalDate.class;
            case 2:
                return LocalDate.class;
            case 3:
                return Long.class;
            case 4:
                return Long.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    public Contract getContract(int row) {
        return contractManager.findAllContracts().get(row);
    }

    public void addContract(Contract contract) {
        contractManager.createContract(contract);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void removeContract(int[] rows) {
        List<Long> toDelete = new ArrayList<>();
        for(int i : rows) {
            toDelete.add(getContract(i).getId());
        }
        for(long j : toDelete) {
            contractManager.deleteContract(j);
        }

        fireTableDataChanged();

    }

    public Agent getAgent(int agentIndex) {
        return new AgentsTableModel().getAgent(agentIndex);
    }

    public Mission getMission(int missionIndex) {
        return new MissionsTableModel().getMission(missionIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 0:
                return false;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    public void updateContract(Contract contract, int row) {
        contractManager.updateContract(contract);
        fireTableRowsUpdated(row, row);
    }
}

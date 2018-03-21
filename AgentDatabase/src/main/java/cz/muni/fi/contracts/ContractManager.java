package cz.muni.fi.contracts;
import java.util.List;
/**
 * @author Slavomir Katkin
 */
public interface ContractManager {

    /**
     * Creates new contract into database
     * @param contract - contract to be created
     * @throws IllegalArgumentException - if contract or any of his parameters are null,
     * agent is already busy during given time or when mission or agent do not
     * exist within database
     */
    void createContract(Contract contract) throws IllegalArgumentException;

    /**
     * Updates contract in the database
     * @param contract - updated contract to be stored in database
     * @throws IllegalArgumentException - contract or any of his parameters are null,
     * contract is not in database
     */
    void updateContract(Contract contract) throws IllegalArgumentException;

    /**
     * Deletes contract from the database
     * @param contract - contract to be deleted
     * @return - true if contract was deleted, false otherwise
     */
    boolean deleteContract(Contract contract);

    /**
     * Returns all contracts within database.
     * @return list of all contracts within database
     */
    List<Contract> findAllContracts();

}

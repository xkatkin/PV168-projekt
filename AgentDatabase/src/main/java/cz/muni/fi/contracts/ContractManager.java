package cz.muni.fi.contracts;
import java.util.List;
/**
 * @author Slavomir Katkin
 */
public interface ContractManager {
    /**
     *
     * @param contract
     * @return
     */
    Contract createContract(Contract contract);

    /**
     *
     * @param contract
     * @return
     */
    Contract updateContract(Contract contract);

    /**
     *
     * @param contract
     * @return
     */
    Contract deleteContract(Contract contract);

    /**
     *
     * @return
     */
    List<Contract> findAllContracts();

}

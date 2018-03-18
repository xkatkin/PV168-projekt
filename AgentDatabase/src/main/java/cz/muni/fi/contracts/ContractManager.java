package cz.muni.fi.contracts;

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
     * @param contract
     * @return
     */
    Contract findAllContracts(Contract contract);

}

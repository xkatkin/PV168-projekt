package cz.muni.fi.contracts;

/**
 * @author Slavomir Katkin
 */
public class ContractManagerImpl implements ContractManager{
    private long idCounter;

    public ContractManagerImpl() {
        this.idCounter = 0;
    }

    @Override
    public Contract createContract(Contract contract) {
        return null;
    }

    @Override
    public Contract updateContract(Contract contract) {
        return null;
    }

    @Override
    public Contract deleteContract(Contract contract) {
        return null;
    }

    @Override
    public Contract findAllContracts(Contract contract) {
        return null;
    }
}

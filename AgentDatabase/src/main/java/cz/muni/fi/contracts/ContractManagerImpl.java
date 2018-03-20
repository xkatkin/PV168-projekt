package cz.muni.fi.contracts;

import javax.naming.OperationNotSupportedException;

/**
 * @author Slavomir Katkin
 */
public class ContractManagerImpl implements ContractManager{

    public ContractManagerImpl() {

    }

    @Override
    public Contract createContract(Contract contract) {
        throw new UnsupportedOperationException("no implementation");
    }

    @Override
    public Contract updateContract(Contract contract) {
        throw new UnsupportedOperationException("no implementation");
    }

    @Override
    public Contract deleteContract(Contract contract) {
        throw new UnsupportedOperationException("no implementation");
    }

    @Override
    public Contract findAllContracts(Contract contract) {
        throw new UnsupportedOperationException("no implementation");
    }
}

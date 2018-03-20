package cz.muni.fi.contracts;

import javax.naming.OperationNotSupportedException;
import java.util.List;

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
    public List<Contract> findAllContracts() {
        throw new UnsupportedOperationException("no implementation");
    }
}

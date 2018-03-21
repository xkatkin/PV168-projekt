package cz.muni.fi.contracts;

import javax.naming.OperationNotSupportedException;
import java.util.List;

/**
 * @author Slavomir Katkin
 */
public class ContractManagerImpl implements ContractManager{

    @Override
    public void createContract(Contract contract) {
        throw new UnsupportedOperationException("no implementation");
    }

    @Override
    public void updateContract(Contract contract) {
        throw new UnsupportedOperationException("no implementation");
    }

    @Override
    public boolean deleteContract(Contract contract) {
        throw new UnsupportedOperationException("no implementation");
    }

    @Override
    public List<Contract> findAllContracts() {
        throw new UnsupportedOperationException("no implementation");
    }
}

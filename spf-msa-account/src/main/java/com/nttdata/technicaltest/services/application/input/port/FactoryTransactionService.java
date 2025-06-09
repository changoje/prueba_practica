package com.nttdata.technicaltest.services.application.input.port;

import com.nttdata.technicaltest.services.model.Transactions;

public interface FactoryTransactionService {
    Operation getOperation(Transactions transactions);
}

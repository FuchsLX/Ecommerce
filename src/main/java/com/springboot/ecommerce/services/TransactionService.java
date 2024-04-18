package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.transaction.Transaction;

public interface TransactionService {

    void saveTransaction(Transaction transaction);

    void setSuccessTransaction(Transaction transaction);

    void setCancelledTransaction(Transaction transaction);

}

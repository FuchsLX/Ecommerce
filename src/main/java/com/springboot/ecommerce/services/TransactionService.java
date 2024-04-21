package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.transaction.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    void saveTransaction(Transaction transaction);

    void setSuccessTransaction(Transaction transaction);

    void setCancelledTransaction(Transaction transaction);

}

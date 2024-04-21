package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.entities.transaction.Transaction;
import com.springboot.ecommerce.entities.transaction.TransactionStatus;
import com.springboot.ecommerce.repositories.TransactionRepository;
import com.springboot.ecommerce.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;


    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }


    @Override
    public void setSuccessTransaction(Transaction transaction) {
        transaction.setStatus(TransactionStatus.SUCCESS);
        this.saveTransaction(transaction);
    }

    @Override
    public void setCancelledTransaction(Transaction transaction) {
        transaction.setStatus(TransactionStatus.CANCELLED);
        this.saveTransaction(transaction);
    }


}

